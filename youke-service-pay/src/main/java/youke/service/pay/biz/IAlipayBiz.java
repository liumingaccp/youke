package youke.service.pay.biz;

public interface IAlipayBiz {

    /**
     * 获取支付宝扫码支付二维码URL
     * @param tradeNo  订单号
     * @param body   订单内容
     * @param totalFee   付款金额（分）
     * @return
     */
    String getScanPayUrl(String tradeNo, String body, int totalFee);

}
