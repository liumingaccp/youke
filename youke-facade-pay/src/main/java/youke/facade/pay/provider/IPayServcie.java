package youke.facade.pay.provider;

public interface IPayServcie {

    /**
     * 获取微信支付二维码
     * @param oid
     * @param orderType
     * @return
     */
    String getWxpayCodeUrl(String oid,String orderType);

    /**
     * 获取阿里支付二维码
     * @param oid
     * @param orderType
     * @return
     */
    String getAlipayCodeUrl(String oid, String orderType);

    /**
     * 验证支付证书
     * @param mchId
     * @param mchKey
     * @param filecert
     * @return
     */
    String doValidSSL(String mchId, String mchKey,String filecert);


}
