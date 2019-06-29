package youke.service.market.provider;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import youke.common.base.Base;
import youke.common.constants.ComeType;
import youke.common.constants.SubOrderMessageConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.IntegralActiveOrderVo;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.*;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.NewsMsgMessage;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.market.provider.IIntegralWealService;
import youke.facade.market.vo.IntegralActiveParamVo;
import youke.service.market.biz.IIntegraWealBiz;
import youke.service.market.queue.producter.ActiveMessageProducer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:54
 */
@Service("integralWealService")
public class IntegraWealService extends Base implements IIntegralWealService {
    @Resource
    private IIntegraWealBiz integraWealBiz;
    @Resource
    private IIntegralActiveDao integralActiveDao;
    @Resource
    private IIntegralOrderDao integralOrderDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private ActiveMessageProducer activeMessageProducer;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IYoukeDao youkeDao;

    public PageInfo<IntegralActiveVo> getIntegralActiveList(String title, int state, int page, int limit, String youkeId) {

        title = StringUtils.hasLength(title) ? "%" + title + "%" : null;

        if (page < 0) {
            page = 1;
        }
        if (limit < 0) {
            limit = 20;
        }

        PageInfo<IntegralActiveVo> infos = integraWealBiz.getActiveList(title, state, page, limit, youkeId);
        return infos;
    }

    @Override
    public PageInfo<IntegralActiveVo> getIntegralActiveList(String appId, int page, int limit) {
        if (appId == null) {
            throw new BusinessException("传入正确的appId");
        }
        if (page < 0) {
            page = 1;
        }
        if (limit < 0) {
            limit = 20;
        }
        String dyk = subscrDao.selectDyk(appId);

        PageInfo<IntegralActiveVo> infos = integraWealBiz.getActiveList(null, 1, page, limit, dyk);
        return infos;
    }

    public int addIntegraActive(IntegralActiveParamVo params) {

        TIntegralActive model = new TIntegralActive();
        paramToModel(params, model);
        model.setYoukeid(params.getYoukeId());

        if (null == params.getStartTime() || null == params.getEndTime()) {
            throw new BusinessException("任务开始或者结束时间不能为空");
        }
        Date today = DateUtil.parseDate(DateUtil.todayStartDate());
        Date startDate = DateUtil.parseDate(params.getStartTime());
        if (startDate.getTime() < today.getTime()) {
            throw new BusinessException("开始时间不能小于当前时间");
        }
        if (startDate.getTime() > DateUtil.parseDate(params.getEndTime()).getTime()) {
            throw new BusinessException("结束时间要大于开始时间");
        }

        Integer shopId = params.getShopId();
        if (shopId == null || shopId < 0) {
            throw new BusinessException("请选择店铺");
        }
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if (shop == null || shop.getState() == null) {
            throw new BusinessException("请选择店铺");
        } else {
            if (shop.getState() != 1) {
                throw new BusinessException("店铺:" + shop.getTitle() + "已过期或者未授权");
            }
        }

        Integer id = params.getId();
        if (id != null && id > -1) {
            if (params.getState() != null && params.getState() != 0) {
                throw new BusinessException("活动已经开始或者结束,不支持修改");
            }
            model.setId(id);
            integraWealBiz.updateActice(model);
            return id;
        } else {
            int payState = subscrConfigDao.selectPayState(params.getAppId());
            if (payState == 0) {
                throw new BusinessException("尚未设置绑定微信支付");
            }
            return integraWealBiz.addIntegraActive(model);
        }
    }

    public PageInfo<IntegralOrderVo> getIntegralOrderList(WealOrderQueryVo params) {

        if (params.getTitle() != null) {
            params.setTitle("%" + params.getTitle() + "%");
        }
        if (params.getWxFansName() != null) {
            params.setWxFansName("%" + params.getWxFansName() + "%");
        }
        if (params.getShopFansName() != null) {
            params.setShopFansName("%" + params.getShopFansName() + "%");
        }

        if (params.getPage() == null || params.getPage() < -2) {
            params.setPage(1);
        }
        if (params.getLimit() == null || params.getLimit() < -2) {
            params.setLimit(20);
        }

        return integraWealBiz.getOrderList(params);
    }

    @Override
    public long getIntegralOrderCount(WealOrderQueryVo params) {
        params.setLimit(0);
        Long count = integraWealBiz.getIntegralOrderCount(params);

        return count == null ? 0 : count;
    }

    @Override
    public IntegralSetVo getIntegralSet(String dykId) {
        if(RedisSlaveUtil.hasKey(JI_FEN_SET_KEY))
        {
            if(RedisSlaveUtil.hHasKey(JI_FEN_SET_KEY,dykId)){
                return (IntegralSetVo)RedisSlaveUtil.hget(JI_FEN_SET_KEY,dykId);
            }
        }
        IntegralSetVo setVo = new IntegralSetVo();
        setVo.setIntegralRate(1000);
        setVo.setNumForIntegral(1);
        setVo.setNumForMoney(1000); //单位分
        setVo.setOpenBackIntegral(0);
        saveIntegralSet(setVo,dykId);
        return setVo;
    }

    @Override
    public void saveIntegralSet(IntegralSetVo setVo, String dykId) {
        Date endTime = youkeDao.selectByPrimaryKey(dykId).getEndtime();
        setVo.setIntegralExp(empty(endTime)?"":DateUtil.formatDate(endTime,"yyyy-MM-dd"));
        RedisUtil.hset(JI_FEN_SET_KEY,dykId,setVo);
    }

    public long getTotalIntegral(WealIntegralQueryVo params) {
        params.setLimit(0);
        Long count = integraWealBiz.getIntegralCount(params);
        return count == null ? 0 : count;
    }

    public PageInfo<SubFansIntegralVo> getIntegralList(WealIntegralQueryVo params) {
        PageInfo<SubFansIntegralVo> info = integraWealBiz.getIntegralList(params);
        if (info != null && info.getList().size() > 0) {
            for (SubFansIntegralVo vo : info.getList()) {
                vo.setComeTypeDisplay(ComeType.COME_TYPE.get(vo.getComeType()));
            }
        }
        return info;
    }

    @Override
    public String getPageUrl() {
        return configDao.selectByPrimaryKey("h5_integral_page").getVal();
    }

    @Override
    public void updateActice(int id, String tkl) {
        TIntegralActive ac = new TIntegralActive();
        ac.setId(id);
        ac.setTaocode(tkl);
        integraWealBiz.updateActice(ac);
    }

    @Override
    public IntegralActiveDetailVo getActice(int activeId, String appId) {
        return integraWealBiz.getActice(activeId, appId);
    }

    @Override
    public void deleteActive(int activeId) {
        Integer state = integralActiveDao.selectState(activeId);
        if (state == null) {
            throw new BusinessException("不存在" + activeId + "对应的活动记录");
        }
        if (state == 0) {
            integralActiveDao.deleteByPrimaryKey(activeId);
        } else if (state == 1 || state == 2) {
            integralActiveDao.updateState(activeId);
        }
    }

    @Override
    public IntegralOrderRetVo submitOrder(IntegralActiveOrderVo params) {
        if (params.getActiveId() == null || params.getActiveId() <= 0) {
            throw new BusinessException("请传入活动id的参数");
        }
        Integer integral = 0;
        Integer openType = params.getOpenType();
        String openId = params.getOpenId();
        TIntegralActive active = integralActiveDao.selectByPrimaryKey(params.getActiveId());

        //数量检测
        if (active == null) {
            throw new BusinessException("不存在" + "[" + active.getTitle() + "] 活动");
        }
        if (active.getNum() == 0) {
            throw new BusinessException("[" + active.getTitle() + "]活动已经结束");
        }

        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);

        if (subscrFans.getState() == 1) {
            throw new BusinessException("请联系公众号管理员");
        }
        if (subscrFans == null || subscrFans.getSubstate() == 1) {
            throw new H5SubscrException();
        }

        TIntegralOrder order = new TIntegralOrder();
        order.setShopid(active.getShopid() + "");
        order.setActiveid(active.getId());
        order.setTitle(active.getTitle());
        order.setWxfansname(subscrFans.getNickname());
        order.setOpenid(subscrFans.getOpenid());
        order.setPrice(active.getPrice());
        order.setShopid(active.getShopid() + "");
        order.setOpentype(openType);
        if (openType == null) {
            throw new BusinessException("请选择积分兑换方式");
        }
        if (openType == 0) {
            order.setIntegral(active.getIntegral());
            order.setMoney(0);
            integral = active.getIntegral();
        } else if (openType == 1) {
            order.setIntegral(active.getMoneyintegral());
            order.setMoney(active.getMoney());
            integral = active.getMoneyintegral();
        }
        order.setBackmoney(order.getPrice() - order.getMoney());

        Integer shopType = shopDao.selectShopTypeByShopId(order.getShopid());
        order.setShoptype(shopType);

        order.setAppid(params.getAppId());
        order.setYoukeid(active.getYoukeid());
        order.setState(0);
        order.setCreatetime(new Date());

        //扣除对应的积分
        if (subscrFans.getIntegral() < integral) {
            throw new BusinessException("积分不足.所需积分:" + integral + "当前积分:" + subscrFans.getIntegral());
        }

        ActiveMassMessage message = new ActiveMassMessage();
        message.setAppId(params.getAppId());
        message.setOpenId(params.getOpenId());
        message.setComeType(ComeType.JI_FEN_DUI_HUAN);
        message.setIntegral(-integral);
        message.setTitle(active.getTitle());
        message.setRecordId(Long.valueOf(active.getId() + ""));
        message.setYoukeId(active.getYoukeid());
        message.setMoney(null);
        message.setState(1);
        activeMessageProducer.send("activemass.queue", message);

        //增加活动参与记录
        integralOrderDao.insertSelective(order);
        //修改活动状态或者数量
        TIntegralActive model = new TIntegralActive();
        model.setId(active.getId());
        model.setNum(active.getNum() - 1);
        if (model.getNum() == 0) {
            model.setState(2);
        }
        integralActiveDao.updateByPrimaryKeySelective(model);

        Map<String, String> msgMap = SubOrderMessageConstant.TYPE.get(SubOrderMessageConstant.JI_FEN_DUI_HUAN);
        //推送消息,提供填写订单号入口
        NewsMsgMessage newsMsgMessage = new NewsMsgMessage();
        newsMsgMessage.setAppId(params.getAppId());
        newsMsgMessage.setDescription(SubOrderMessageConstant.DESCRIPTION.replace("{TITLE}", active.getTitle()));
        newsMsgMessage.setOpenId(params.getOpenId());
        newsMsgMessage.setPicUrl(active.getCover());
        newsMsgMessage.setTitle(msgMap.get("title"));
        newsMsgMessage.setUrl(msgMap.get("url").replace("{APPID}", params.getAppId()));
        activeMessageProducer.send("newsmsg.queue", newsMsgMessage);

        IntegralOrderRetVo vo = new IntegralOrderRetVo();
        vo.setTitle(order.getTitle());
        vo.setCover(active.getCover());
        vo.setCreateTime(order.getCreatetime());
        vo.setPrice(order.getPrice());
        vo.setIntegral(order.getIntegral());
        vo.setMoney(order.getMoney());
        vo.setOpenType(order.getOpentype());
        vo.setBackMoney(order.getBackmoney());
        vo.setState(order.getState());
        vo.setGoodsId(active.getGoodsid());
        vo.setGoodsUrl(active.getGoodsurl());
        vo.setShopType(order.getShoptype());
        vo.setId(order.getId());
        return vo;
    }

    @Override
    public IntegralActiveRetVo getactiveinfo(int activeId, String appId, String youkeId) {
        if (activeId < 0) {
            return null;
        }
        String dyk = youkeId;
        if (!StringUtil.hasLength(dyk) && StringUtil.hasLength(appId)) {
            dyk = subscrDao.selectDyk(appId);
        }
        IntegralActiveRetVo vo = integralActiveDao.getactiveinfo(activeId, dyk);
        if (vo != null) {
            String goodsurl = vo.getGoodsurl();
            vo.setGoodsurl(goodsurl);
            if (StringUtil.hasLength(goodsurl)) {
                if (goodsurl.contains("taobao.com")) {
                    vo.setShopType(0);
                } else if (goodsurl.contains("tmall.com")) {
                    vo.setShopType(1);
                } else if (goodsurl.contains("jd.com")) {
                    vo.setShopType(2);
                }
            }
            if (StringUtil.hasLength(vo.getIntros()) && vo.getIntros().startsWith("[")) {
                vo.setIntro(JSONArray.fromObject(vo.getIntros()));
            }
        }
        return vo;
    }

    @Override
    public PageInfo<IntegralOrderRetVo> getOrderListByOpenId(String appId, String openId, int page, int limit) {
        PageHelper.startPage(page, limit, "createTime desc");
        List<IntegralOrderRetVo> list = integralOrderDao.queryListByOpenId(appId, openId);
        if (list != null && list.size() > 0) {
            for (IntegralOrderRetVo vo : list) {
                String goodsurl = vo.getGoodsUrl();
                if (StringUtil.hasLength(goodsurl)) {
                    if (goodsurl.contains("taobao.com")) {
                        vo.setShopType(0);
                    } else if (goodsurl.contains("tmall.com")) {
                        vo.setShopType(1);
                    } else if (goodsurl.contains("jd.com")) {
                        vo.setShopType(2);
                    }
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public void saveOrderNum(String appId, String openId, Long orderId, String orderNo) {

        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
        if (subscrFans == null) {
            throw new BusinessException("请在微信端打开");
        }
        if (subscrFans == null || subscrFans.getSubstate() == 1) {
            throw new H5SubscrException();
        }
        if (subscrFans.getState() == 1) {
            throw new BusinessException("请联系公众号管理员");
        }

        if (orderId == null) {
            throw new BusinessException("请传入订单号");
        }
        Integer count = integralOrderDao.selectCount(orderNo, appId);
        if (count != null && count > 0) {
            throw new BusinessException("【该订单号已参与过一次试用福利活动】");
        }
        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderNo);
        Map<String, Object> detail = integralOrderDao.selectOrderDetail(orderId);
        if (shopOrder == null) {
            throw new BusinessException("不存在此订单,无效的订单号");
        }
        if (detail == null) {
            throw new BusinessException("参数异常");
        }
        String goodsId = (String) detail.get("goodsId");
        Integer orderState = (Integer) detail.get("state");
        String order_openId = (String) detail.get("openId");
        if (order_openId == null || !order_openId.equals(openId)) {
            throw new BusinessException("参与者和提交订单号者不同");
        }
        if (subscrFans.getMobile() == null) {
            throw new H5MobileException();
        }
        if (shopOrder == null || !shopOrder.getGoodid().equals(goodsId)) {
            throw new BusinessException("【订单对应商品与本活动商品不匹配】");
        }
        if (orderState == 5) {
            throw new BusinessException("【该兑换记录超时未下单已失效】");
        } else if (orderState > 1) {
            throw new BusinessException("【该活动记录已绑定订单号】");
        }

        Integer state = shopOrder.getState();
        if (state == 0 || state == 5 || state == 6) {
            throw new BusinessException("【只能提交已付款的订单号】");
        }
        TIntegralOrder order = new TIntegralOrder();
        order.setId(orderId);
        order.setState(1);
        order.setShopfansname(shopOrder.getBuyername());
        order.setOrderno(orderNo);
        order.setOrdertime(new Date());
        integralOrderDao.updateByPrimaryKeySelective(order);
    }

    public String getConfig() {
        return configDao.selectByPrimaryKey("h5_integral_page").getVal();
    }

    @Override
    public void updateState(Integer activeId, String dykId, Integer state) {
        if (activeId != null) {
            TIntegralActive info = integralActiveDao.selectByPrimaryKeyAndYoukeId(activeId, dykId);
            if (info != null) {
                Integer state1 = info.getState();
                if (state == 1) {
                    if (state1 != 0) {
                        throw new BusinessException("[" + info.getTitle() + "] 活动处于非待开始 手动上线失败");
                    } else {
                        TIntegralActive active = new TIntegralActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        integralActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if (state == 2) {
                    if (state1 != 1) {
                        throw new BusinessException("[" + info.getTitle() + "] 活动处于非上线状态 手动下线失败");
                    } else {
                        TIntegralActive active = new TIntegralActive();
                        active.setId(info.getId());
                        active.setState(state);
                        integralActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }
        }
    }

    @Override
    public IntegralOrderRetVo getOrderInfo(int orderId, String appId, String openId) {
        if (!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(openId)) {
            return null;
        }
        IntegralOrderRetVo vo = integralOrderDao.selectOrderInfo(orderId, appId, openId);
        if (vo != null) {
            String goodsurl = vo.getGoodsUrl();
            if (StringUtil.hasLength(goodsurl)) {
                if (goodsurl.contains("taobao.com")) {
                    vo.setShopType(0);
                } else if (goodsurl.contains("tmall.com")) {
                    vo.setShopType(1);
                } else if (goodsurl.contains("jd.com")) {
                    vo.setShopType(2);
                }
            }
        }
        return vo;
    }

    private static void paramToModel(IntegralActiveParamVo params, TIntegralActive model) {
        model.setShopid(params.getShopId());
        model.setGoodsid(params.getGoodsId());
        model.setGoodsurl(params.getGoodsUrl());
        model.setCover(params.getCover());
        model.setTitle(params.getTitle());
        model.setStarttime(DateUtil.parseDate(params.getStartTime()));
        model.setEndtime(DateUtil.parseDate(params.getEndTime()));
        model.setIntro(JSONUtils.valueToString(params.getIntro()));
        model.setPrice(params.getPrice());
        model.setTotalnum(params.getTotalNum());
        model.setNum(params.getTotalNum());
        model.setCreatetime(new Date());
        model.setState(0);
        model.setOpenintegral(params.getOpenIntegral());
        model.setOpenmoney(params.getOpenMoney());
        model.setIntegral(params.getIntegral());
        model.setMoneyintegral(params.getMoneyIntegral());
        model.setMoney(params.getMoney());
        model.setValidhour(params.getValidHour());
        model.setTaocode(params.getTaoCode());
    }

}
