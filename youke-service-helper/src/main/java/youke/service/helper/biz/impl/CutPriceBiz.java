package youke.service.helper.biz.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.helper.*;
import youke.common.utils.DateUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.MoneyUtil;
import youke.facade.helper.vo.CutPriceActiveVo;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.service.helper.biz.ICutPriceBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CutPriceBiz extends Base implements ICutPriceBiz {
    @Resource
    private ICutpriceActiveOrderDao cutpriceActiveOrderDao;
    @Resource
    private ICutpriceActiveFansDao cutpriceActiveFansDao;
    @Resource
    private IWeixinMessageService weixinMessageService;
    @Resource
    private ICutpriceActiveDao cutpriceActiveDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private IShopDao shopDao;

    @Override
    public Integer saveActive(CutPriceActiveVo params) {
        int payState = subscrConfigDao.selectPayState(params.getAppId());
        if (payState == 0) {
            throw new BusinessException("尚未设置绑定微信支付");
        }
        int shopState = shopDao.selectShopStateByShopId(params.getShopId());
        if (shopState == 2 || shopState == 0) {
            throw new BusinessException("店铺尚未授权或授权已过期，请前往 账号管理/店铺授权 中更新授权。");
        }
        TCutpriceActive active;
        if (params.getId() == 0) {
            active = new TCutpriceActive();
        } else {
            active = cutpriceActiveDao.selectByPrimaryKey(params.getId());
        }
        if (notEmpty(active)) {
            active.setState(0);
            active.setCreatetime(new Date());
            active.setAppid(params.getAppId());
            active.setYoukeid(params.getDykId());
            active.setShopid(params.getShopId());
            active.setCover(params.getCover());
            active.setIntro(JSON.toJSONString(params.getIntro()));
            active.setTitle(params.getTitle());
            if (notEmpty(params.getPrice()) && params.getPrice() > 0) {
                active.setPrice(params.getPrice());
            } else {
                throw new BusinessException("商品价格设置错误");
            }
            active.setNum(params.getTotalNum());
            active.setWaitday(params.getWaitDay());
            active.setGoodsid(params.getGoodsId());
            active.setDealhour(params.getDealHour());
            active.setTotalnum(params.getTotalNum());
            active.setGoodsurl(params.getGoodsUrl());
            if (notEmpty(params.getDealPrice()) && params.getDealPrice() >= 0) {
                active.setDealprice(params.getDealPrice());
            } else {
                throw new BusinessException("成交价格设置错误");
            }
            active.setFanslimit(params.getFansLimit());
            if (params.getDealFansNum() <= 1) {
                throw new BusinessException("砍价人数设置数额不能为1");
            }
            active.setDealfansnum(params.getDealFansNum());
            active.setWaitpayminute(params.getWaitPayMinute());
            if (empty(params.getStartTime()) && empty(params.getEndTime())) {
                throw new BusinessException("活动开始和结束时间不能为空");
            }
            active.setEndtime(DateUtil.parseDate(params.getEndTime()));
            active.setStarttime(DateUtil.parseDate(params.getStartTime()));
            active.setCutpirce(params.getCutPrice());
            if (params.getId() == 0) {
                cutpriceActiveDao.insertSelective(active);
            } else {
                cutpriceActiveDao.updateByPrimaryKeySelective(active);
            }
            return active.getId();
        } else {
            throw new BusinessException("活动不存在");
        }
    }

    @Override
    public PageInfo<CutPriceActiveRetVo> getActiveList(CutPriceActiveQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CutPriceActiveRetVo> list = cutpriceActiveDao.selectActiveList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<CutPriceOrderRetVo> getOrderList(CutPriceOrderQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CutPriceOrderRetVo> list = cutpriceActiveOrderDao.selectOrderList(params);
        return new PageInfo<>(list);
    }

    @Override
    public List<CutPriceOrderRetVo> getExportOrderList(CutPriceOrderQueryVo params) {
        return cutpriceActiveOrderDao.selectOrderList(params);
    }

    @Override
    public CutPriceOrderDetailRetVo getExamineDetail(Long orderId, String dykId) {
        TCutpriceActiveOrder order = cutpriceActiveOrderDao.selectByPrimaryKey(orderId);
        CutPriceOrderDetailRetVo detail;
        if (order != null) {
            if (order.getOrderno() != null) {
                TShopOrder shopOrder = shopOrderDao.selectByOrderno(order.getOrderno());
                if (shopOrder != null) {
                    detail = new CutPriceOrderDetailRetVo();
                    detail.setOrderno(shopOrder.getOrderno());
                    detail.setNum(shopOrder.getNum());
                    detail.setOrderState(shopOrder.getState());
                    detail.setPicPath(shopOrder.getPicpath());
                    detail.setPrice(shopOrder.getPrice());
                    detail.setReceiveAddress(shopOrder.getReceiveaddress());
                    detail.setReceiveName(shopOrder.getReceivename());
                    detail.setTitle(shopOrder.getTitle());
                    detail.setTotalPrice(shopOrder.getTotalprice());
                    return detail;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteActive(String dykId, Integer activeId) {
        TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(activeId);
        if (notEmpty(active)) {
            if (active.getState() == 1) {
                throw new BusinessException("活动进行中,请下线后删除");
            } else {
                active.setState(3);
                cutpriceActiveDao.updateByPrimaryKeySelective(active);
            }
        } else {
            throw new BusinessException("活动不存在");
        }
    }

    @Override
    public CutPriceActiveDetailRetVo getActiveDetail(Integer activeId, String dykId) {
        TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(activeId);
        if (notEmpty(active)) {
            CutPriceActiveDetailRetVo vo = new CutPriceActiveDetailRetVo();
            vo.setId(active.getId());
            vo.setCover(active.getCover());
            vo.setCreateTime(active.getCreatetime());
            vo.setCutPrice(active.getCutpirce());
            vo.setIntro(JSON.parseArray(active.getIntro(), String.class));
            vo.setDealFansNum(active.getDealfansnum());
            vo.setDealHour(active.getDealhour());
            vo.setDealPrice(active.getDealprice());
            vo.setEndTime(active.getEndtime());
            vo.setFansLimit(active.getFanslimit());
            vo.setGoodsId(active.getGoodsid());
            vo.setGoodsUrl(active.getGoodsurl());
            vo.setNum(active.getNum());
            vo.setPrice(active.getPrice());
            vo.setShopId(active.getShopid());
            vo.setStartTime(active.getStarttime());
            vo.setState(active.getState());
            vo.setTitle(active.getTitle());
            vo.setTotalNum(active.getTotalnum());
            vo.setWaitDay(active.getWaitday());
            vo.setWaitPayMinute(active.getWaitpayminute());
            return vo;
        } else {
            throw new BusinessException("不存在该活动");
        }
    }

    @Override
    public void updateActiveState(Integer activeId, Integer state, String dykId) {
        TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(activeId);
        if (active != null) {
            if (state == 1) {
                if (active.getState() != 0) {
                    throw new BusinessException("只能上线待开始的活动");
                }
                active.setStarttime(new Date());
            } else if (state == 2) {
                if (active.getState() != 1) {
                    throw new BusinessException("只能下线进行中的活动");
                }
                active.setEndtime(new Date());
            }
            active.setState(state);
            cutpriceActiveDao.updateByPrimaryKeySelective(active);
        }
    }

    @Override
    public PageInfo<H5CutPriceActiveRetVo> getH5ActiveList(String appId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TCutpriceActive> list = cutpriceActiveDao.selectActiveListByAppId(appId);
        H5CutPriceActiveRetVo vo;
        List<H5CutPriceActiveRetVo> activeList = new ArrayList<>();
        PageInfo<H5CutPriceActiveRetVo> info = new PageInfo<>();
        if (list.size() > 0) {
            for (TCutpriceActive active : list) {
                vo = new H5CutPriceActiveRetVo();
                vo.setId(active.getId());
                vo.setCover(active.getCover());
                vo.setTitle(active.getTitle());
                vo.setPrice(active.getPrice());
                vo.setDealPrice(active.getDealprice());
                vo.setUsedNum(IDUtil.getGrowId(active.getId()));//伪随机人数
                activeList.add(vo);
            }
            info.setList(activeList);
        }
        return info;
    }

    @Override
    public PageInfo<H5CutPriceOrderRetVo> getMyOrderList(String appId, String openId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TCutpriceActiveOrder> list = cutpriceActiveOrderDao.selectMyOrderList(appId, openId);
        PageInfo<H5CutPriceOrderRetVo> info = new PageInfo<>();
        List<H5CutPriceOrderRetVo> orderList;
        H5CutPriceOrderRetVo vo;
        TCutpriceActive active;
        if (list.size() > 0) {
            orderList = new ArrayList<>();
            for (TCutpriceActiveOrder order : list) {
                vo = new H5CutPriceOrderRetVo();
                active = cutpriceActiveDao.selectByPrimaryKey(order.getActiveid());
                vo.setId(order.getId());
                vo.setPrice(order.getPrice());
                if (notEmpty(order.getOrderno())) {
                    vo.setOrderno(order.getOrderno());
                }
                vo.setCover(active.getCover());
                vo.setTitle(order.getTitle());
                vo.setState(order.getState());
                vo.setAlreadyCutPrice(order.getAlreadycutprice());
                vo.setLeftCutPrice(order.getLeftcutprice());
                vo.setDealPrice(order.getDealprice());
                vo.setCreateTime(order.getCreatetime());
                vo.setEndTime(order.getCutendtime());
                vo.setUsedNum(IDUtil.getGrowId(active.getId()));
                vo.setGoodsId(active.getGoodsid());
                vo.setShopType(shopDao.selectShopTypeByShopId(active.getShopid().toString()));
                orderList.add(vo);
            }
            info.setList(orderList);
        }
        return info;
    }

    @Override
    public Integer doSubmitCut(String appId, String openId, Long orderId) {
        TCutpriceActiveOrder order = cutpriceActiveOrderDao.selectByPrimaryKey(orderId);
        if (notEmpty(order)) {
            TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(order.getActiveid());
            TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
            int num2 = cutpriceActiveFansDao.selectFansRecordNum(openId, orderId);//当前砍价参与次数
            submitCutCheck(subscrFans, active, order, openId.equals(order.getOpenid()) ? 0 : cutpriceActiveFansDao.selectJoinNum(openId), num2);
            TCutpriceActiveFans fans = new TCutpriceActiveFans();
            fans.setOpenid(openId);
            fans.setOrderid(orderId);
            fans.setCreatetime(new Date());
            if (order.getDealfansnum() - order.getJoinfansnum() == 1) {
                fans.setCutprice(order.getLeftcutprice());
            } else {
                fans.setCutprice(active.getCutpirce() == -1 ? MoneyUtil.getCutPrice(order.getDealfansnum() - order.getJoinfansnum(), order.getLeftcutprice().doubleValue()) : active.getCutpirce());
            }
            cutpriceActiveFansDao.insertSelective(fans);
            order.setAlreadycutprice(order.getAlreadycutprice() + fans.getCutprice());//已砍金额增加
            order.setLeftcutprice(order.getLeftcutprice() - fans.getCutprice());//还需砍价金额减少
            order.setJoinfansnum(order.getJoinfansnum() + 1);//已参与粉丝增加
            if (order.getDealfansnum().equals(order.getJoinfansnum()) //满足砍价人数
                    && order.getAlreadycutprice().equals(order.getBackmoney())//到达成交价格
                    && order.getLeftcutprice() == 0) {//还需砍价为0
                order.setState(2);
                order.setCutendtime(new Date());
            }
            if (!openId.equals(order.getOpenid())) {
                weixinMessageService.sendTempKanJia(appId, order.getOpenid(), order.getId(), active.getTitle(), subscrFans.getNickname(), MoneyUtil.fenToYuan(fans.getCutprice() + ""), MoneyUtil.fenToYuan(order.getLeftcutprice() + ""), MoneyUtil.fenToYuan(order.getDealprice() + ""), DateUtil.formatSecond(active.getWaitpayminute() * 60 + ""));
            }
            cutpriceActiveOrderDao.updateByPrimaryKey(order);
            return fans.getCutprice();
        } else {
            throw new BusinessException("无效的请求");
        }
    }

    @Override
    public H5CutPriceOrderDetailRetVo createOrGetOrderDetail(String appId, String openId, Long orderId, Integer activeId) {
        H5CutPriceOrderDetailRetVo vo = new H5CutPriceOrderDetailRetVo();
        TSubscrFans subscrFans;
        TCutpriceActiveOrder order;
        TCutpriceActive active;
        if (orderId > -1) {//获取订单
            order = cutpriceActiveOrderDao.selectByPrimaryKey(orderId);
            subscrFans = subscrFansDao.selectByPrimaryKey(order.getOpenid());
            active = cutpriceActiveDao.selectByPrimaryKey(order.getActiveid());
            int num = cutpriceActiveFansDao.selectFansRecordNum(openId, order.getId());
            vo.setId(order.getId());
            vo.setOpenId(order.getOpenid());
            vo.setState(order.getState());
            vo.setCutState(num == 0 ? 0 : 1);
            vo.setTitle(order.getTitle());
            vo.setIntro(JSON.parseArray(active.getIntro(), String.class));
            vo.setGoodsId(active.getGoodsid());
            vo.setWxName(subscrFans.getNickname());
            vo.setWxFace(subscrFans.getHeadimgurl());
            vo.setAlreadyCutPrice(order.getAlreadycutprice());
            vo.setCover(active.getCover());
            vo.setPrice(order.getPrice());
            vo.setDealPrice(order.getDealprice());
            vo.setLeftCutPrice(order.getLeftcutprice());
            vo.setCreateTime(order.getCreatetime());
            vo.setEndTime(order.getCutendtime());
            vo.setShopType(shopDao.selectShopTypeByShopId(active.getShopid() + ""));
            vo.setUsedNum(IDUtil.getGrowId(active.getId()));//伪随机
            return vo;
        } else {//初始化订单
            List<TCutpriceActiveOrder> list = cutpriceActiveOrderDao.selectMyOrder(appId, openId, activeId);
            active = cutpriceActiveDao.selectByPrimaryKey(activeId);
            subscrFans = subscrFansDao.selectByPrimaryKey(openId);
            if (list.size() > 0) {
                order = list.get(0);
            } else {
                if (active.getNum() == 0) {
                    throw new BusinessException("该活动商品已下架,去看看其他商品吧");
                }
                order = new TCutpriceActiveOrder();
                order.setState(0);
                order.setAppid(appId);
                order.setOpenid(openId);
                order.setActiveid(activeId);
                order.setYoukeid(active.getYoukeid());
                order.setTitle(active.getTitle());
                order.setWxfansname(subscrFans.getNickname());
                order.setPrice(active.getPrice());
                order.setDealprice(active.getDealprice());
                order.setAlreadycutprice(0);
                order.setLeftcutprice(active.getPrice() - active.getDealprice());
                order.setDealfansnum(active.getDealfansnum());
                order.setJoinfansnum(0);
                order.setBackmoney(active.getPrice() - active.getDealprice());
                order.setCreatetime(new Date());
                order.setCutendtime(DateUtil.parseDate(DateUtil.getHourAfter(order.getCreatetime(), active.getDealhour())));
                cutpriceActiveOrderDao.insertSelective(order);
                active.setNum(active.getNum() - 1);
                if (active.getNum() == 0) {
                    active.setState(2);
                }
                cutpriceActiveDao.updateByPrimaryKeySelective(active);//扣库存,如果库存为0则结束活动
            }
            int num = cutpriceActiveFansDao.selectFansRecordNum(openId, order.getId());
            vo.setCutState(num == 0 ? 0 : 1);
            vo.setId(order.getId());
            vo.setOpenId(openId);
            vo.setState(order.getState());
            vo.setTitle(order.getTitle());
            vo.setIntro(JSON.parseArray(active.getIntro(), String.class));
            vo.setGoodsId(active.getGoodsid());
            vo.setWxName(subscrFans.getNickname());
            vo.setWxFace(subscrFans.getHeadimgurl());
            vo.setAlreadyCutPrice(order.getAlreadycutprice());
            vo.setCover(active.getCover());
            vo.setPrice(order.getPrice());
            vo.setDealPrice(order.getDealprice());
            vo.setLeftCutPrice(order.getLeftcutprice());
            vo.setCreateTime(order.getCreatetime());
            vo.setEndTime(order.getCutendtime());
            vo.setShopType(shopDao.selectShopTypeByShopId(active.getShopid() + ""));
            vo.setUsedNum(IDUtil.getGrowId(active.getId()));//伪随机
            return vo;
        }
    }

    @Override
    public H5CutPriceOrderDetailRetVo getPreviewDetail(String appId, String openId, Integer activeId) {
        TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(activeId);
        TSubscrFans fans = subscrFansDao.selectByPrimaryKey(openId);
        H5CutPriceOrderDetailRetVo vo;
        if (notEmpty(active) && notEmpty(fans)) {
            vo = new H5CutPriceOrderDetailRetVo();
            vo.setUsedNum(IDUtil.getGrowId(active.getId()));
            vo.setTitle(active.getTitle());
            vo.setCover(active.getCover());
            vo.setPrice(active.getPrice());
            vo.setDealPrice(active.getDealprice());
            vo.setAlreadyCutPrice(0);
            vo.setLeftCutPrice(active.getPrice());
            vo.setWxName(fans.getNickname());
            vo.setWxFace(fans.getHeadimgurl());
            vo.setIntro(JSON.parseArray(active.getIntro(), String.class));
            vo.setShopType(shopDao.selectShopTypeByShopId(active.getShopid() + ""));
            return vo;
        } else {
            throw new BusinessException("活动不存在");
        }
    }

    @Override
    public PageInfo<H5CutPriceFans> getFansList(String orderId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TCutpriceActiveFans> list = cutpriceActiveFansDao.selectFansListByOrderId(orderId);
        H5CutPriceFans fans;
        TSubscrFans subscrFans;
        List<H5CutPriceFans> fansList;
        if (list.size() > 0) {
            fansList = new ArrayList<>();
            PageInfo<H5CutPriceFans> info = new PageInfo<>();
            for (TCutpriceActiveFans cfans : list) {
                fans = new H5CutPriceFans();
                subscrFans = subscrFansDao.selectByPrimaryKey(cfans.getOpenid());
                fans.setOpenId(cfans.getOpenid());
                fans.setWxName(subscrFans.getNickname());
                fans.setWxFace(subscrFans.getHeadimgurl());
                fans.setCreateTime(cfans.getCreatetime());
                fans.setCutPrice(cfans.getCutprice());
                fansList.add(fans);
            }
            info.setList(fansList);
            return info;
        }
        return null;
    }

    @Override
    public JsonResult doSubmitOrderno(String orderno, Long orderId, String openId) {
        TCutpriceActiveOrder order = cutpriceActiveOrderDao.selectByPrimaryKey(orderId);
        if (notEmpty(order)) {
            TCutpriceActive active = cutpriceActiveDao.selectByPrimaryKey(order.getActiveid());
            TSubscrFans fans = subscrFansDao.selectByPrimaryKey(openId);
            TShopOrder shopOrder = shopOrderDao.getByOrderNo(orderno, active.getYoukeid());
            submitOrdernoCheck(fans, active, order, shopOrder);
            int count = cutpriceActiveOrderDao.selectOrderByOrderNo(shopOrder.getOrderno());
            if (count > 0) {
                throw new BusinessException("请不要重复使用订单号");
            }
            order.setOrderno(orderno);
            order.setOrdertime(new Date());
            order.setBuyername(shopOrder.getBuyername());
            cutpriceActiveOrderDao.updateByPrimaryKey(order);
            if (order.getState() == 4) {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "超时未购买");
            } else {
                return new JsonResult();
            }
        } else {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "无效的请求");
        }
    }

    /**
     * 砍价检测
     *
     * @param fans   微信粉丝
     * @param active 砍价活动
     * @param order  砍价订单
     * @param num1   当天砍价次数
     * @param num2   当前砍价次数
     */
    private void submitCutCheck(TSubscrFans fans, TCutpriceActive active, TCutpriceActiveOrder order, int num1, int num2) {
        if (notEmpty(fans)) {
            if (fans.getState() != 0 || fans.getSubstate() != 0) {
                throw new H5SubscrException();
            }
        } else {
            throw new H5SubscrException();
        }
        if (notEmpty(active)) {
            if (active.getFanslimit() == 1) {
                if (fans.getMobile() == null) {
                    throw new H5MobileException();
                }
            }
        } else {
            throw new BusinessException("活动不存在");
        }
        if (notEmpty(order)) {
            if (order.getState() != 0) {
                throw new BusinessException("砍价已结束");
            }
        } else {
            throw new BusinessException("无效记录");
        }
        if (num1 >= 3) {
            throw new BusinessException("今日砍价次数已用光");
        }
        if (num2 >= 1) {
            throw new BusinessException("你已经砍过价了");
        }
    }

    /**
     * 提交订单号检测
     *
     * @param fans
     * @param active
     * @param order
     * @param shopOrder
     */
    private void submitOrdernoCheck(TSubscrFans fans, TCutpriceActive active, TCutpriceActiveOrder order, TShopOrder shopOrder) {
        if (notEmpty(fans)) {
            if (fans.getState() != 0 || fans.getSubstate() != 0) {//是否关注公众号
                throw new H5SubscrException();
            }
            if (empty(fans.getMobile())) {//是否绑定手机
                throw new H5MobileException();
            }
        } else {
            throw new H5SubscrException();
        }
        if (notEmpty(order)) {
            Date endTime = DateUtil.getMinuteAfter(order.getCutendtime(), active.getWaitpayminute());
            if (endTime.getTime() < new Date().getTime()) {//是否超时
                order.setState(4);
            }
            if (notEmpty(order.getOrderno())) {
                throw new BusinessException("订单已提交");
            }
        } else {
            throw new BusinessException("无效记录");
        }
        if (notEmpty(shopOrder)) {
            if (shopOrder.getReceivemobile().equals(fans.getMobile())) {
                if (shopOrder.getState() > 0 && shopOrder.getState() < 5) {//是否为有效订单
                    if (!active.getGoodsid().equals(shopOrder.getGoodid())) {//店铺和商品是否对应
                        throw new BusinessException("请选择该订单对应的砍价商品");
                    } else {
                        order.setState(3);
                    }
                } else {
                    throw new BusinessException("订单尚未交易成功");
                }
            } else {
                throw new BusinessException("该订单关联的手机号和微信绑定手机号不一致,请修改绑定手机号或联系客服处理");
            }
        } else {
            throw new BusinessException("无效订单");
        }
    }

    @Override
    public String getConfig(String key) {
        return configDao.selectByPrimaryKey(key).getVal();
    }
}
