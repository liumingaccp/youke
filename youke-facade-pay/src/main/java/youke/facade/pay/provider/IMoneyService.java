package youke.facade.pay.provider;

public interface IMoneyService {
    void addMoney(Integer comeType, String title, String openId, int money, String appId, String youkeId);
}
