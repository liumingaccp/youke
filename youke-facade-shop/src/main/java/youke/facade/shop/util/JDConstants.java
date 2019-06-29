package youke.facade.shop.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 京东常量类
 */
public class JDConstants {

    public static final String APIURL = "https://api.jd.com/routerjson";
    /**
     * 开放平台appKey
     */
    public static final String OPENAPPKEY = "7E0B8010A75011E563E47772F431FAE4";
    /**
     * 开放平台appsecert
     */
    public static final String OPENAPPSECRET = "c79dd53fa35942e9b6f86c1353bb4d1c";

    public static final String UID = "6597826251";
    public static final String UID2 = "4998319522";

    public static final String USERNICK = "jd_fk12017";
    public static final String USERNICK2 = "仲玉旗舰店";

    public static final String access_token = "60d13642-50ac-4cce-a7e2-ca5fa24bad9a";

    public static final String access_token2 = "54c122e9-89ab-4528-87b6-10968f3f34b4";

    public static final String BASE_REQUEST_URL = "http://125.94.44.202:8081/jdsync/";

    public static final String ORDER_SEARCH_URL = BASE_REQUEST_URL + "searchJdOrders";

    public static final String GET_PRODUCT_URL = BASE_REQUEST_URL + "getProductInfo";

    public static final String GET_SHOP_URL = BASE_REQUEST_URL + "getShopInfo";

    public static final String IMAGE_PREFIX = "http://img14.360buyimg.com/n7/";
    /**
     * 1）WAIT_SELLER_STOCK_OUT 等待出库
     * 2）SEND_TO_DISTRIBUTION_CENER 发往配送中心（只适用于LBP，SOPL商家）
     * 3）DISTRIBUTION_CENTER_RECEIVED 配送中心已收货（只适用于LBP，SOPL商家）
     * 4）WAIT_GOODS_RECEIVE_CONFIRM 等待确认收货
     * 5）RECEIPTS_CONFIRM 收款确认（服务完成）（只适用于LBP，SOPL商家）
     * 6）WAIT_SELLER_DELIVERY等待发货（只适用于海外购商家，等待境内发货 标签下的订单）
     * 7）FINISHED_L 完成
     * 8）TRADE_CANCELED 取消
     * 9）LOCKED 已锁定
     */
    public static Map<String, Integer> JD_ORDER_STATUS = new HashMap<String, Integer>();

    static {
        JD_ORDER_STATUS.put("WAIT_SELLER_STOCK_OUT", 1);
        JD_ORDER_STATUS.put("WAIT_SELLER_DELIVERY", 1);
        JD_ORDER_STATUS.put("SEND_TO_DISTRIBUTION_CENER", 2);
        JD_ORDER_STATUS.put("DISTRIBUTION_CENTER_RECEIVED", 2);
        JD_ORDER_STATUS.put("WAIT_GOODS_RECEIVE_CONFIRM", 2);
        JD_ORDER_STATUS.put("RECEIPTS_CONFIRM", 4);
        JD_ORDER_STATUS.put("FINISHED_L", 4);
        JD_ORDER_STATUS.put("TRADE_CANCELED", 6);
    }
}
