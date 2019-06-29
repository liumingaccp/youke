package youke.service.pay.biz;

public interface IYoukePayBiz {
    /**
     * 企业支付零钱
     *
     * @return
     */
    boolean doPayMoney(int comeType, int money, int commision, String openId, String appId, String youkeId);

    /**
     * 验证证书
     * @param mchId
     * @param mchKey
     * @param filecert
     * @return
     */
    String doValidSSL(String mchId, String mchKey,String filecert);

    boolean doRedPackage(String youkeId, String appId, String openId,int totalAmount,String comeTypeTitle);

    /**
     * 更新活动状态
     * @param recordId
     * @param comeType
     * @param type 0返利成功，1返利失败
     */
    void updateActiveState(Long recordId, int comeType, int type);

    /**
     * 自动支付
     */
    void doAutoPay();
}