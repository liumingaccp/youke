package youke.service.helper.provider;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;
import org.apache.activemq.ActiveMQMessageProducer;
import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.constants.SubOrderMessageConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.helper.HelperOrderVo;
import youke.common.model.vo.param.helper.RebateOrderExamineParam;
import youke.common.model.vo.param.helper.RebateOrderQueryVo;
import youke.common.model.vo.param.helper.RebateQueryVo;
import youke.common.model.vo.result.helper.RebateDetailVo;
import youke.common.model.vo.result.helper.RebateOrderDetailRetVo;
import youke.common.model.vo.result.helper.RebateOrderQueryRetVo;
import youke.common.model.vo.result.helper.RebateQueryRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.NewsMsgMessage;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.IRebateService;
import youke.facade.helper.vo.Constants.ActiveState;
import youke.facade.helper.vo.Constants.OrderState;
import youke.facade.helper.vo.RebateActiveVo;
import youke.service.helper.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 12:04
 */
@Service
public class RebateService implements IRebateService {
    @Resource
    private IRebateActiveDao rebateActiveDao;
    @Resource
    private IRebateActiveOrderDao rebateActiveOrderDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    @Override
    public int addActive(RebateActiveVo vo) {
        TRebateActive active = new TRebateActive();
        active.setShopid(vo.getShopId());
        active.setGoodsid(vo.getGoodsId());
        active.setGoodsurl(vo.getGoodsUrl());
        active.setCover(vo.getCover());
        active.setTitle(vo.getTitle());
        active.setCreatetime(new Date());
        active.setState(0);

        if(null == vo.getStartTime() || null == vo.getEndTime()){
            throw new BusinessException("任务开始或者结束时间不能为空");
        }
        active.setStarttime(DateUtil.parseDate(vo.getStartTime()));
        active.setEndtime(DateUtil.parseDate(vo.getEndTime()));

        Date today = DateUtil.parseDate(DateUtil.todayStartDate());
        if(active.getStarttime().getTime() < today.getTime()){
            throw new BusinessException("开始时间不能小于当前时间");
        }

        if(active.getStarttime().getTime() > active.getEndtime().getTime()){
            throw new BusinessException("结束时间要大于开始时间");
        }
        if(vo.getPrice() == null || vo.getPrice() <= 0){
            throw new BusinessException("价格不能小于0");
        }
        if(vo.getTotalNum() == null || vo.getTotalNum() <= 0){
            throw new BusinessException("数量不能小于0");
        }
        if(vo.getBackMoney() == null || vo.getBackMoney() <= 0){
            throw new BusinessException("返利不能小于0");
        }
        active.setPrice(vo.getPrice());
        active.setBackmoney(vo.getBackMoney());
        active.setTotalnum(vo.getTotalNum());
        active.setCostintegral(vo.getCostIntegral());
        active.setFanslimit(vo.getFansLimit());
        active.setLimitminute(vo.getLimitMinute());
        active.setLimitcount(vo.getLimitCount());
        active.setWaitday(vo.getWaitDay());
        active.setOpentype(vo.getOpenType());
        active.setIntro(JSONUtils.valueToString(vo.getIntro()));

        active.setNum(vo.getTotalNum());
        active.setYoukeid(vo.getYoukeId());

        Integer shopId = vo.getShopId();
        if(shopId == null || shopId < 0){
            throw new BusinessException("请选择店铺");
        }
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if(shop==null || shop.getState()== null){
            throw new BusinessException("请选择店铺");
        }else {
            if(shop.getState() != 1){
                throw new BusinessException("店铺:"+shop.getTitle()+"已过期或者未授权");
            }
        }


        Integer id = vo.getId();
        if(id != null && id > -1){
            Integer state = vo.getState();
            TRebateActive active1 = rebateActiveDao.selectByPrimaryKey(vo.getId());
            if(active1 == null){
                throw new BusinessException("不存在此活动");
            }
            if((state != null && state != 0) || active1.getState() != 0){
                throw new BusinessException("活动已开始或者结束,不支持修改");
            }
            active.setId(vo.getId());
            rebateActiveDao.updateByPrimaryKeySelective(active);

        }else{
            int payState = subscrConfigDao.selectPayState(vo.getAppId());
            if(payState == 0){
                throw new BusinessException("尚未设置绑定微信支付");
            }
            rebateActiveDao.insertSelective(active);
        }

        return active.getId();
    }

    @Override
    public void deleteActive(Integer activeId) {
        if(activeId != null && activeId > 0 ){
            Integer state = rebateActiveDao.selectState(activeId);
            if(state == null){
                throw new BusinessException("不存在"+ activeId +"对应的活动");
            }
            if(state == 0){
                rebateActiveDao.deleteByKey(activeId);
            }else if(state == 1 || state == 2){
                rebateActiveDao.updateState(activeId);
            }
        }
    }

    @Override
    public PageInfo<RebateQueryRetVo> queryList(RebateQueryVo params, String codePath) {

        if(StringUtil.hasLength(params.getAppId()) && !StringUtil.hasLength(params.getYoukeId())){

            String youkeId = subscrDao.selectDyk(params.getAppId());
            params.setYoukeId(youkeId);
            params.setState(1);
        }
        PageHelper.startPage(params.getPage(), params.getLimit(), "active.createTime desc");
        List<RebateQueryRetVo> list = rebateActiveDao.queryList(params);
        if(list != null && list.size() > 0){
            for (RebateQueryRetVo vo : list){
                if(StringUtil.hasLength(params.getAppId())){
                    //预览连接
                    String h5_taoke_page = configDao.selectVal("h5_rebate_page");
                    String preUrl = h5_taoke_page.replace("{appId}", params.getAppId()) + "&id=" + vo.getId();
                    vo.setPreUrl(preUrl);
                    vo.setPreCodeUrl(codePath + URLEncoder.encode(preUrl));
                }
                vo.setStateDisplay(ActiveState.MAP_STATE.get(vo.getState()));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<RebateOrderQueryRetVo> queryOrderList(RebateOrderQueryVo params) {
        String dyk = subscrDao.selectDyk(params.getAppId());
        PageHelper.startPage(params.getPage(), params.getLimit(), "torder.createTime desc");
        params.setYoukeId(dyk);
        List<RebateOrderQueryRetVo> list = rebateActiveOrderDao.queryList(params);
        if(list != null && list.size() > 0){
            for (RebateOrderQueryRetVo vo : list){
                vo.setStateDisplay(OrderState.MAP_STATE.get(vo.getState()));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public void examinBatch(RebateOrderExamineParam param) {
        String orderIds = param.getOrderIds();
        if(orderIds == null){
            throw new BusinessException("请选择审核的订单");
        }
        List<String> list = Arrays.asList(orderIds.split(","));
        param.setOrderIdList(list);
        rebateActiveOrderDao.updateStateBatch(param);
    }

    @Override
    public RebateOrderDetailRetVo getExamineDetail(Integer orderId, String appId) {
        RebateOrderDetailRetVo detail = rebateActiveOrderDao.selectExamineDetail(orderId, appId);
        if(detail != null){
            detail.setStateDisplay(OrderState.MAP_STATE.get(detail.getState()));
        }
        return detail;
    }

    @Override
    public RebateDetailVo getActiveDetail(Integer activeId, String appId, String dykId) {
        if(activeId == null || activeId < 0){
            throw new BusinessException("传入正确的 id");
        }
        String dyk = dykId;
        if(!StringUtil.hasLength(dyk) && StringUtil.hasLength(appId)){
            if(appId != null){
                dyk = subscrDao.selectDyk(appId);
            }
        }
        TRebateActive active = rebateActiveDao.selectByPrimaryKeyAndDyk(activeId, dyk);
        if(active == null){
            throw new BusinessException("不存在此活动");
        }else{
            RebateDetailVo vo = new RebateDetailVo();
            vo.setId(active.getId());
            vo.setBackmoney(active.getBackmoney());
            vo.setCostintegral(active.getCostintegral());
            vo.setCover(active.getCover());
            vo.setCreatetime(active.getCreatetime());
            vo.setEndtime(active.getEndtime());
            vo.setFanslimit(active.getFanslimit());
            vo.setGoodsid(active.getGoodsid());
            if(active.getIntro() != null && active.getIntro().startsWith("[")){
                vo.setIntro(JSONArray.fromObject(active.getIntro()));
            }
            vo.setTitle(active.getTitle());
            vo.setWaitday(active.getWaitday());
            vo.setLimitcount(active.getLimitcount());
            vo.setLimitminute(active.getLimitminute());
            vo.setNum(active.getNum());
            vo.setOpentype(active.getOpentype());
            vo.setTotalnum(active.getTotalnum());
            vo.setState(active.getState());
            vo.setTaocode(active.getTaocode());
            vo.setStarttime(active.getStarttime());
            vo.setShopid(active.getShopid());
            vo.setPrice(active.getPrice());
            String goodsurl = active.getGoodsurl();
            vo.setGoodsurl(goodsurl);
            if(StringUtil.hasLength(goodsurl)){
                if(goodsurl.contains("taobao.com")){
                    vo.setShopType(0);
                }else if(goodsurl.contains("tmall.com")){
                    vo.setShopType(1);
                } else if(goodsurl.contains("jd.com")){
                    vo.setShopType(2);
                }
            }
            return vo;
        }
    }

    @Override
    public String doImportOrder(RebateOrderQueryVo params) {
        PageInfo<RebateOrderQueryRetVo> info = this.queryOrderList(params);
        List<RebateOrderQueryRetVo> list = info.getList();
        File temp = null;
        FileOutputStream stream = null;
        String path = null;
        if(list == null || list.size() <= 0){
            throw new BusinessException("导入数据为空请重新设置筛选条件");
        }
        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".csv");
            stream = new FileOutputStream(temp);
            if(temp != null){
                this.doImportOrder(list, stream);
            }
            path = FileUpOrDwUtil.uploadLocalFile(temp, "helper/order/" + temp.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(temp != null){
                temp.delete();
            }
        }
        return path;
    }

    @Override
    public long saveRebateOrder(HelperOrderVo params) {

        if(!StringUtil.hasLength(params.getAppId())
                || !StringUtil.hasLength(params.getOpenId())
                || params.getActiveId() == null  || params.getActiveId() <0
                ){
            throw new BusinessException("请检查传入参数是否为空");
        }
        String dyk = subscrDao.selectDyk(params.getAppId());
        if(!StringUtil.hasLength(dyk)){
            throw new BusinessException("[appId]参数错误");
        }
        TRebateActive active = rebateActiveDao.selectByPrimaryKey(params.getActiveId());
        Integer integral = active.getCostintegral();

        //活动数量,状态判断
        if(active == null){
            throw new BusinessException("不存在此活动");
        }
        if(active.getNum() == 0){
            throw new BusinessException("此活动已经结束");
        }

        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(params.getOpenId());
        if(subscrFans.getState() == 1){
            throw new BusinessException("请联系公众号管理员");
        }
        if(subscrFans == null || subscrFans.getSubstate() == 1){
            throw new H5SubscrException();
        }
        if(active.getFanslimit() != null && active.getFanslimit() == 1){
            if(subscrFans.getMobile() == null){
                throw new H5MobileException();
            }
        }
        TRebateActiveOrder order = new TRebateActiveOrder();
        order.setBackmoney(active.getBackmoney());

        Integer limitcount = active.getLimitcount();
        if(limitcount != null && limitcount > 0){
            Integer count = rebateActiveOrderDao.selectCountByActiveIdAndOpenId(params.getOpenId(), params.getActiveId());
            if(count != null && count >= limitcount){
                throw new BusinessException("该活动您已参加");
            }

        }

        //如果消耗积分,发送积分消耗模板
        if(integral != 0){

            //扣除对应的积分
            if(subscrFans.getIntegral() < integral){
                throw new BusinessException("积分不足.所需积分:"+integral + "当前积分:"+ subscrFans.getIntegral());
            }

            ActiveMassMessage message = new ActiveMassMessage();
            message.setAppId(params.getAppId());
            message.setOpenId(params.getOpenId());
            message.setComeType(ComeType.GOU_WU_FAN_LI);
            message.setIntegral(-integral);
            message.setTitle(active.getTitle());
            message.setRecordId(Long.valueOf(active.getId()+""));
            message.setYoukeId(active.getYoukeid());
            message.setMoney(null);
            message.setState(1);

            queueSender.send("activemass.queue", message);

        }

        //增加活动记录

        order.setTitle(active.getTitle());
        order.setWxfansname(subscrFans.getNickname());
        order.setCreatetime(new Date());
        order.setPrice(active.getPrice());
        order.setShopid(active.getShopid() + "");
        order.setOpenid(params.getOpenId());
        order.setAppid(params.getAppId());
        order.setYoukeid(dyk);
        order.setState(0);
        order.setActiveid(active.getId());
        rebateActiveOrderDao.insertSelective(order);

        TRebateActive model = new TRebateActive();
        model.setId(active.getId());
        model.setNum(active.getNum() - 1);
        if(model.getNum() == 0){
            model.setState(2);
        }
        rebateActiveDao.updateByPrimaryKeySelective(model);

        Map<String, String> msgMap = SubOrderMessageConstant.TYPE.get(SubOrderMessageConstant.GOU_WU_FAN_LI);
        //推送消息,提供填写订单号入口
        NewsMsgMessage newsMsgMessage = new NewsMsgMessage();
        newsMsgMessage.setAppId(params.getAppId());
        newsMsgMessage.setDescription(SubOrderMessageConstant.DESCRIPTION.replace("{TITLE}", active.getTitle()));
        newsMsgMessage.setOpenId(params.getOpenId());
        newsMsgMessage.setPicUrl(active.getCover());
        newsMsgMessage.setTitle(msgMap.get("title"));
        newsMsgMessage.setUrl(msgMap.get("url").replace("{APPID}", params.getAppId()));
        queueSender.send("newsmsg.queue", newsMsgMessage);
        return order.getId();
    }

    @Override
    public void updateState(Integer activeId, String youkeId, Integer state) {
        if(activeId != null){
            TRebateActive info = rebateActiveDao.selectByPrimaryKeyAndDyk(activeId, youkeId);
            if(info != null){
                Integer state1 = info.getState();
                if(state == 1){
                    if(state1 != 0){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非待开始 手动上线失败");
                    }else{
                        TRebateActive active = new TRebateActive();
                        active.setId(info.getId());
                        active.setState(state);
                        active.setStarttime(new Date());
                        rebateActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
                if(state == 2){
                    if(state1 != 1){
                        throw new BusinessException("["+ info.getTitle() +"] 活动处于非上线状态 手动下线失败");
                    }else{
                        TRebateActive active = new TRebateActive();
                        active.setId(info.getId());
                        active.setState(state);
                        rebateActiveDao.updateByPrimaryKeySelective(active);
                    }
                }
            }
        }
    }
    
    @Override
    public void saveOrderNum(String appId,String openId, Long orderId, String orderNo) {
        if(orderNo == null){
            throw new BusinessException("请传入订单号");
        }
        String dyk = subscrDao.selectDyk(appId);
        if(dyk == null){
            throw new BusinessException("参数异常");
        }
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
        if(subscrFans == null){
            throw new BusinessException("请在微信端打开");
        }
        if(subscrFans.getState() == 1){
            throw new BusinessException("请联系公众号管理员");
        }

        Map<String, Object> detail = rebateActiveOrderDao.selectOrderDetail(orderId);
        if(detail == null){
            throw new BusinessException("不存在此订单,无效的订单号");
        }
        String goodId = (String)detail.get("goodsId");
        Integer orderState = (Integer) detail.get("state");
        String order_openId = (String)detail.get("openId");
        Integer fansLimit = (Integer) detail.get("fansLimit");
        if(order_openId == null || !order_openId.equals(openId)){
            throw new BusinessException("参与者和提交订单号者不同");
        }
        if(fansLimit != null && fansLimit == 1 && subscrFans.getMobile() == null){
            throw new H5MobileException();
        }
        if(subscrFans == null || subscrFans.getSubstate() == 1){
            throw new H5SubscrException();
        }

        Integer count = rebateActiveOrderDao.selectCount(orderNo, dyk);
        if(count != null && count > 0){
            throw new BusinessException("【该订单号已参与过一次购物返利活动】");
        }

        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderNo);
        if(shopOrder == null || !shopOrder.getGoodid().equals(goodId)){
            throw new BusinessException("【订单对应商品与本活动商品不匹配】");
        }
        if(orderState == 1){
            throw new BusinessException("【该活动记录超时未购买已失效】");
        }else if(orderState > 1){
            throw new BusinessException("【该活动记录已绑定订单号】");
        }

        Integer state = shopOrder.getState();
        if(state == 0 || state == 5 || state ==6){
            throw new BusinessException("【只能提交已付款的订单号】");
        }

        TRebateActiveOrder order = new TRebateActiveOrder();
        order.setId(orderId);
        order.setState(2);
        order.setBuyername(shopOrder.getBuyername());
        order.setOrderno(orderNo);
        order.setOrdertime(new Date());
        rebateActiveOrderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public RebateOrderQueryRetVo getOrderInfo(String appId, String openId, long orderId) {
        if(!StringUtil.hasLength(appId)
                || !StringUtil.hasLength(openId)){
            return null;
        }
        RebateOrderQueryRetVo vo = rebateActiveOrderDao.selectOrderInfo(appId, openId, orderId);
        return vo;
    }

    private static void doImportOrder(List<RebateOrderQueryRetVo> orderList, OutputStream outputStream){
        //写入临时文件
        //表头
        String[] headers = new String[]{
                "记录编号",
                "商品名称",
                "店铺名称",
                "淘宝账户",
                "微信昵称",
                "订单号",
                "兑换时间",
                "返现金额(分)",
                "状态"
        };
        String[] content = new String[9];
        try {
            CsvWriter csvWriter = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers);
            for(RebateOrderQueryRetVo vo : orderList){
                content[0] = vo.getId() + "";
                content[1] = vo.getTitle();
                content[2] = vo.getShopName();
                content[3] = vo.getBuyerName();
                content[4] = vo.getWxFansName();
                content[5] = vo.getOrderNo();
                content[6] = DateUtil.formatDate(vo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                content[7] = vo.getBackMoney() + "";
                content[8] = vo.getStateDisplay();

                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
