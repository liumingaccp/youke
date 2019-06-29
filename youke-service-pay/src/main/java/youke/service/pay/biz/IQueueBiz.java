package youke.service.pay.biz;

public interface IQueueBiz {
    void sendSysRecharge(String openId, String title, String type, Integer price);

    void sendSysOpenAccount(String openId, Integer vip, Integer price, String title, String endTime);

    void sendPayFailMsg(String mobile, int money, String reason);

    void sendSysPayFail(String openId, int money, String reason);
}
