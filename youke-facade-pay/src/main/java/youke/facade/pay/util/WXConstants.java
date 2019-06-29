package youke.facade.pay.util;

import youke.common.constants.ApiCodeConstant;

/**
 * 微信支付常类
 */
public class WXConstants {

    /**
     * 店有客公众号AppId
     */
    public final static String APPID  = "wxe3e582584ba16db1";
    /**
     * 店有客公众号AppSecret
     */
    public final static String APPSECRET = "b40e9713fd8e535fddb73299a793e499";
    /**
     * 店有客商户号
     */
    public final static String PARTNET="1496498832";
    /**
     * 店有客商户密钥
     */
    public final static String PARTNETKEY="4277147287d8137f9c6c4893dc8f46b4";

    public final static int PAYLIMIT = 20000; //红包和企业零钱分界额度，默认200元

    /**
     * 客户端IP
     */
    public final static String CLIENTIP = "120.78.134.148";
    /**
     * 扫码支付回调URL
     */
    public final static String NOTIFYURL = ApiCodeConstant.DOMAIN_PCAPI+"pay/wxpayback";
    /**
     * 被扫支付API
     */
    public final static String PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 被扫支付查询API
     */
    public final static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 退款API
     */
    public final static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 退款查询API
     */
    public final static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 撤销API
     */
    public final static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    /**
     * 下载对账单API
     */
    public final static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";
    /**
     * 统计上报API
     */
    public final static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

}
