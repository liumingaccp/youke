package youke.facade.wx.provider;


import java.util.Map;

public interface IWeixinMessageService {
    /**
     * 发送客服文本消息
     *
     * @param appId
     * @param openId
     * @param message
     */
    void sendText(String appId, String openId, String message);

    /**
     * 发送积分到账模板消息
     *
     * @param appId
     * @param openId
     * @param comeType 活动来源 见常量类ComeType
     * @param title    活动标题
     * @param integral
     */
    void sendTempIntegralGain(String appId, String openId, int comeType, String title, int integral);

    /**
     * 发送积分消费模板消息
     *
     * @param appId
     * @param openId
     * @param comeType 活动来源 见常量类ComeType
     * @param title    活动标题
     * @param integral
     */
    void sendTempIntegralConsume(String appId, String openId, int comeType, String title, int integral);

    /**
     * 发送审核通过模板消息
     *
     * @param appId
     * @param openId
     * @param comeType 活动来源 见常量类ComeType
     * @param title    活动标题
     */
    void sendTempExamineSuccess(String appId, String openId, int comeType, String title);

    /**
     * 发送审核不通过模板消息
     *
     * @param appId
     * @param openId
     * @param comeType 活动来源 见常量类ComeType
     * @param title    活动标题
     * @param reason
     */
    void sendTempExamineFail(String appId, String openId, int comeType, String title, String reason);

    /**
     * 订单收货
     *
     * @param appId
     * @param openId
     * @param title   活动标题
     * @param orderno
     * @param type    订单类型  0淘宝，1天猫，2京东
     */
    void sendTempOrderReceive(String appId, String openId, String title, String orderno, int type);

    /**
     * 订单支付成功
     *
     * @param appId
     * @param openId
     * @param title   活动标题
     * @param orderno
     * @param type
     */
    void sendTempOrderPay(String appId, String openId, String title, String orderno, int type);

    /**
     * 订单已发货
     *
     * @param appId
     * @param openId
     * @param title   活动标题
     * @param orderno
     * @param type
     */
    void sendTempOrderDeliver(String appId, String openId, String title, String orderno, int type);

    /**
     * 签到提醒
     *
     * @param appId
     * @param openId
     * @param title  活动标题
     */
    void sendTempSignNotice(String appId, String openId, String title);

    /**
     * 发送零钱到帐通知
     * @param appId
     * @param openId
     * @param comeType
     * @param title
     * @param money
     */
    void sendTempMoneyGain(String appId, String openId, Integer comeType, String title, Integer money);

    /**
     * 发送砍价成功通知
     * @param appId
     * @param openId
     * @param orderId
     * @param title
     * @param friendName  好友微信昵称
     * @param cutPrice  好友帮砍价格
     * @param leftPrice  剩余价格
     * @param dealPrice  成交价格
     * @param limitTime  限制时间
     */
    void sendTempKanJia(String appId, String openId, long orderId, String title, String friendName, String cutPrice, String leftPrice,String dealPrice,String limitTime);

    /**
     * 发送拼团成功通知
     * @param appId
     * @param openId
     * @param title
     * @param tuanId
     */
    void sendTempPinTuan(String appId, String openId, String title,long tuanId);


    /**
     * 发送店有客开通成功通知
     * @param openId
     * @param vip
     * @param money
     * @param title
     * @param endTime
     */
    void sendSysOpenAccount(String openId,int vip, int money, String title, String endTime);

    /**
     * 发送店支付证书到期通知
     * @param openId
     * @param title
     * @param payNumber
     * @param lastDay
     * @param endTime
     */
    void sendSysCert(String openId,String title,String payNumber,int lastDay, String endTime);

    /**
     * 发送会员过期通知
     * @param openId
     * @param title
     * @param lastDay
     * @param endTime
     */
    void sendSysExpire(String openId,String title,int lastDay, String endTime);

    /**
     * 发送续费成功通知
     * @param openId
     * @param title
     * @param money
     * @param endTime
     */
    void sendSysRenew(String openId,String title, int money, String endTime);

    /**
     * 返利失败通知
     * @param openId
     * @param money
     * @param reason
     */
    void sendSysPayFail(String openId,int money,String reason);

    /**
     * 余额不足模板
     * @param openId
     * @param title
     * @param account
     * @param money
     */
    void sendSysBalance(String openId, String title, String account, String money);

    /**
     * 发送会员成功通知
     * @param openId
     * @param title
     * @param type 红包，短信
     * @param money
     */
    void sendSysRecharge(String openId,String title, String type, int money);

    /**
     * 发送服务到期通知
     * @param openId
     * @param title
     * @param server
     * @param lastDay
     * @param endTime
     */
    void sendSysServer(String openId, String title, String server, int lastDay, String endTime);

    /**
     * 发送账户审核通过服务模板
     * @param openId
     */
    void sendYoukeSucc(String openId);

    /**
     * 发送通用模板
     * @param appId
     * @param openId
     * @param tempId
     * @param url
     * @param map
     */
    void sendTempMess(String appId, String openId, String tempId,String url, Map<String,String> map);

    void sendUserJoinPush(Map<String,String> map);
}
