package youke.core.scheduler.service;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.model.*;
import youke.common.queue.message.ActiveMassMessage;
import youke.core.scheduler.utils.QueueSender;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketService extends Base {

    @Resource
    private ICutpriceActiveDao cutpriceActiveDao;
    @Resource
    private ICutpriceActiveOrderDao cutpriceActiveOrderDao;
    @Resource
    private ICollageActiveDao collageActiveDao;
    @Resource
    private ICollageActiveTuanDao collageActiveTuanDao;
    @Resource
    private ICollageActiveOrderDao collageActiveOrderDao;
    @Resource
    private IMarketActiveDao marketActiveDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;
    @Resource
    private IIntegralActiveDao integralActiveDao;
    @Resource
    private IIntegralOrderDao integralOrderDao;
    @Resource
    private ITrialActiveDao trialActiveDao;
    @Resource
    private ITrialActiveOrderDao trialActiveOrderDao;
    @Resource
    private IRebateActiveDao rebateActiveDao;
    @Resource
    private IRebateActiveOrderDao rebateActiveOrderDao;
    @Resource
    private IFollowActiveDao followActiveDao;
    @Resource
    private ITaokeActiveDao taokeActiveDao;
    @Resource
    private ITaokeActiveOrderDao taokeActiveOrderDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private QueueSender queueSender;
    @Resource
    private IWeixinMessageService weixinMessageService;

    /**
     * 实时更新营销活动/积分兑换/销售助手状态
     */
    public void doActiveState() {
        int total = 0;
        //实时更新营销活动活动状态
        total += marketActiveDao.updateStateIng();
        total += marketActiveDao.updateStateEnd();
        //实时更新积分兑换活动状态
        total += integralActiveDao.updateStateIng();
        total += integralActiveDao.updateStateEnd();
        //实时更新试用福利活动状态
        total += trialActiveDao.updateStateIng();
        total += trialActiveDao.updateStateEnd();
        //实时更新购物返利活动状态
        total += rebateActiveDao.updateStateIng();
        total += rebateActiveDao.updateStateEnd();
        //实时更新推广关注活动状态
        total += followActiveDao.updateStateIng();
        total += followActiveDao.updateStateEnd();
        //实时更新微淘客活动状态
        total += taokeActiveDao.updateStateIng();
        total += taokeActiveDao.updateStateEnd();
        //实时更新砍价活动状态
        total += cutpriceActiveDao.updateStateIng();
        total += cutpriceActiveDao.updateStateEnd();
        //砍价返利砍价时间结束状态更新
        cutpriceActiveOrderDao.updateTimeOverState();
        //实时更新拼团活动状态
        total += collageActiveDao.updateStateIng();
        total += collageActiveDao.updateStateEnd();
        doCollageStateForBeg();
        if (total > 0)
            System.out.println("共更新了" + total + "个活动状态");
    }

    /**
     * 定时监听超时自动审核的营销活动
     */
    public void doActiveMonitor() {
        List<Map> maps = marketActiveRecordDao.selectMonitorRecord();
        System.out.println("找到"+maps.size()+"条数据");
        if (maps != null && maps.size() > 0) {
            for (Map map : maps) {
                //更新活动记录状态
                marketActiveRecordDao.updateState((long) map.get("id"), 1);
                ActiveMassMessage massMessage = new ActiveMassMessage();
                massMessage.setTitle((String) map.get("title"));
                massMessage.setYoukeId((String) map.get("youkeId"));
                massMessage.setOpenId((String) map.get("openId"));
                if ((int) map.get("rewardType") < 2) {
                    massMessage.setMoney((Integer) map.get("money"));
                } else {
                    massMessage.setIntegral((Integer) map.get("integral"));
                }
                massMessage.setComeType(ComeType.getComeTypeFromActiveType((int) map.get("type")));
                massMessage.setAppId((String) map.get("appId"));
                massMessage.setRecordId((long) map.get("id"));
                massMessage.setState(1);
                System.out.println("成功推送:"+massMessage.getTitle());
                //发送审核通过通知
                queueSender.send("activeexamine.queue", massMessage);
                //发放零钱
                queueSender.send("activemass.queue", massMessage);
            }
        }
    }

    /**
     * 定时监听积分兑换活动
     */
    public void doIntegralMonitor() {
        //更新积分兑换超时未下单
        integralOrderDao.updateAutoExpireState();
        //监听待收货的积分兑换
        List<Map> orders = integralOrderDao.selectOrderMapByState(1);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            List<String> okNos = shopOrderDao.selectSHOrderNoInOrderno(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    integralOrderDao.updateBatchState(orderIds, 2);
                    //推送返利
                    for (Long orderId : orderIds) {
                        TIntegralOrder order = integralOrderDao.selectByPrimaryKey(orderId);
                        ActiveMassMessage massMessage = new ActiveMassMessage();
                        massMessage.setRecordId(order.getId());
                        massMessage.setAppId(order.getAppid());
                        massMessage.setComeType(ComeType.JI_FEN_DUI_HUAN);
                        massMessage.setMoney(order.getBackmoney());
                        massMessage.setOpenId(order.getOpenid());
                        massMessage.setYoukeId(order.getYoukeid());
                        massMessage.setTitle(order.getTitle());
                        massMessage.setState(1);
                        queueSender.send("activemass.queue", massMessage);
                    }
                }
            }
        }
    }

    /**
     * 定时监听购物返利活动状态
     */
    public void doRebateStateMonitor() {
        //更新购物返利超时未下单
        rebateActiveOrderDao.updateAutoExpireState();
        //监听待收货的购物返利
        List<Map> orders = rebateActiveOrderDao.selectOrderMapByState(2);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            List<String> okNos = shopOrderDao.selectSHOrderNoInOrderno(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    rebateActiveOrderDao.updateBatchState(orderIds, 3); //将状态改成待审核
                }
            }
        }
        //更新购物返利超时自动审核通过
        rebateActiveOrderDao.updateAutoExamineState();
    }

    /**
     * 定时监听购物返利订单审核状态
     */
    public void doRebateMonitor() {
        List<TRebateActiveOrder> orders = rebateActiveOrderDao.selectOrderByState(4);
        if (orders != null && orders.size() > 0) {
            for (TRebateActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                massMessage.setRecordId(order.getId());
                massMessage.setAppId(order.getAppid());
                massMessage.setComeType(ComeType.GOU_WU_FAN_LI);
                massMessage.setMoney(order.getBackmoney());
                massMessage.setOpenId(order.getOpenid());
                massMessage.setYoukeId(order.getYoukeid());
                massMessage.setTitle(order.getTitle());
                massMessage.setState(1);
                queueSender.send("activemass.queue", massMessage);
            }
        }
    }

    /**
     * 定时监听微淘客活动状态
     */
    public void doTaokeStateMonitor() {
        //监听待收货的微淘客
        List<Map> orders = taokeActiveOrderDao.selectOrderMapByState(2);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            List<String> okNos = shopOrderDao.selectSHOrderNoInOrderno(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    taokeActiveOrderDao.updateBatchState(orderIds, 3); //将状态改成待审核
                }
            }
        }
        //更新购物返利超时自动审核通过
        taokeActiveOrderDao.updateAutoExamineState();
    }

    /**
     * 定时监听微淘客活动状态
     */
    public void doTaokeMonitor() {
        List<TTaokeActiveOrder> orders = taokeActiveOrderDao.selectOrderByState(4);
        if (orders != null && orders.size() > 0) {
            for (TTaokeActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                if (order.getBackmoney() > 0) {
                    massMessage.setRecordId(order.getId());
                    massMessage.setAppId(order.getAppid());
                    massMessage.setComeType(ComeType.WEI_TAO_KE);
                    massMessage.setMoney(order.getBackmoney());
                    massMessage.setOpenId(order.getBuyeropenid());
                    massMessage.setYoukeId(order.getYoukeid());
                    massMessage.setTitle(order.getTitle());
                    massMessage.setState(1);
                    if (order.getCommision() > 0) {
                        massMessage.setIntegral(order.getCommision());
                        massMessage.setOpenId(order.getBuyeropenid() + "," + order.getTaokeopenid());
                    }
                    queueSender.send("activemass.queue", massMessage);
                }
            }
        }
    }

    /**
     * 定时监听试用福利活动状态
     */
    public void doTrialStateMonitor() {
        //监听待收货的试用福利
        List<Map> orders = trialActiveOrderDao.selectOrderMapByState(2);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            List<String> okNos = shopOrderDao.selectSHOrderNoInOrderno(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    trialActiveOrderDao.updateBatchState(orderIds, 3); //将状态改成待审核
                }
            }
        }
        //更新购物返利超时自动审核通过
        trialActiveOrderDao.updateAutoExamineState();
    }

    /**
     * 定时监听试用福利活动状态
     */
    public void doTrialMonitor() {
        List<TTrialActiveOrder> orders = trialActiveOrderDao.selectOrderByState(4);
        if (orders != null && orders.size() > 0) {
            for (TTrialActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                massMessage.setRecordId(order.getId());
                massMessage.setAppId(order.getAppid());
                massMessage.setComeType(ComeType.SHI_YONG_FU_LI);
                if (order.getRewardtype() == 0)
                    massMessage.setMoney(order.getBackreward());
                else if (order.getRewardtype() == 1)
                    massMessage.setIntegral(order.getBackreward());
                massMessage.setOpenId(order.getOpenid());
                massMessage.setYoukeId(order.getYoukeid());
                massMessage.setTitle(order.getTitle());
                massMessage.setState(1);
                queueSender.send("activemass.queue", massMessage);
            }
        }
    }

    /**
     * 定时监听拼团活动执行返利
     */
    public void doCollageMonitor() {
        //监听待返利的砍价订单
        List<TCollageActiveOrder> orders = collageActiveOrderDao.selectOrderByState(3);
        if (orders != null && orders.size() > 0) {
            for (TCollageActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                massMessage.setRecordId(order.getId());
                massMessage.setAppId(order.getAppid());
                massMessage.setComeType(ComeType.PIN_TUAN);
                massMessage.setMoney(order.getBackmoney());
                massMessage.setOpenId(order.getOpenid());
                massMessage.setYoukeId(order.getYoukeid());
                massMessage.setTitle(order.getTitle());
                massMessage.setState(1);
                queueSender.send("activemass.queue", massMessage);
            }
        }
    }


    /**
     * 定时监听拼团活动如果到时还没成功的活动
     */
    public void doCollageStateForBeg(){
        List<Map> maps = collageActiveTuanDao.selectEndTuan();
        if(maps != null && maps.size() > 0){
            List<Long> ids = new ArrayList<>();
            for (Map map : maps){
                Long tuanId = (Long) map.get("id");
                String appId = (String)map.get("appId");
                String title = (String)map.get("title");
                ids.add(tuanId);
                //发送模板消息
                List<String> openIds = collageActiveOrderDao.selectOpenIdByTuanId(tuanId, appId);
                collageActiveOrderDao.updateStateByTuanId(appId, tuanId);
                for (String openId : openIds){
                    weixinMessageService.sendTempPinTuan(appId, openId, title, tuanId);
                }
            }
            if(ids != null && ids.size()> 0){
                collageActiveTuanDao.updateStateForBeg(ids);
                collageActiveOrderDao.updateStateForBeg(ids);
            }
        }
    }

    /**
     * 定时监听拼团返利活动状态(拼团是完成的,订单是否已收货)
     */
    public void doCollageStateMonitor() {
        //监听待收货的拼团订单,立即发放
        List<Map> orders = collageActiveOrderDao.selectOrderMapByState(1);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            //查询对应订单号的订单,并且订单状态为待评价或交易成功
            List<String> okNos = shopOrderDao.selectSuccessOrderNoInOrderNo(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    collageActiveOrderDao.updateBatchState(orderIds, 3);//将状态改成待返利
                }
            }
        }

        //拼团返利待返利状态更新(这里是活动设置了waitDay > 0的情况)
        collageActiveOrderDao.updateTimeOutRebate();
    }

    /**
     * 定时监听砍价返利活动状态
     */
    public void doCutPriceStateMonitor() {
        //监听待收货的砍价订单(处于待收货状态并且对应活动的奖励发放为立即发放)
        List<Map> orders = cutpriceActiveOrderDao.selectOrderMapByState(3);
        if (orders != null && orders.size() > 0) {
            List<String> ordernos = new ArrayList<>();
            for (Map map : orders) {
                ordernos.add((String) map.get("orderno"));
            }
            //查询对应订单号的订单,并且订单状态为待评价或交易成功
            List<String> okNos = shopOrderDao.selectSuccessOrderNoInOrderNo(ordernos);
            if (okNos != null && okNos.size() > 0) {
                List<Long> orderIds = new ArrayList<>();
                for (String okNo : okNos) {
                    for (Map map : orders) {
                        if (okNo.equals(map.get("orderno").toString())) {
                            orderIds.add((Long) map.get("id"));
                            break;
                        }
                    }
                }
                if (orderIds.size() > 0) {
                    cutpriceActiveOrderDao.updateBatchState(orderIds, 5);//将状态改成待返利
                }
            }
        }
        //砍价返利超时未购买状态更新
        cutpriceActiveOrderDao.updateTimeOutState();
        //砍价返利超时自动待返利状态更新
        cutpriceActiveOrderDao.updateTimeOutRebate();
    }

    /**
     * 定时监听砍价返利活动状态
     */
    public void doCutPriceMonitor() {
        //监听待返利的砍价订单
        List<TCutpriceActiveOrder> orders = cutpriceActiveOrderDao.selectOrderByState(5);
        if (orders != null && orders.size() > 0) {
            for (TCutpriceActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                massMessage.setRecordId(order.getId());
                massMessage.setAppId(order.getAppid());
                massMessage.setComeType(ComeType.KAN_JIA);
                massMessage.setMoney(order.getBackmoney());
                massMessage.setOpenId(order.getOpenid());
                massMessage.setYoukeId(order.getYoukeid());
                massMessage.setTitle(order.getTitle());
                massMessage.setState(1);
                queueSender.send("activemass.queue", massMessage);
            }
        }
    }
}
