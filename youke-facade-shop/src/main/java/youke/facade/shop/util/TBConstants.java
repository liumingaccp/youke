package youke.facade.shop.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 淘宝常量类
 */
public class TBConstants {

    public static final String URL = "http://www.pudada.com/router/rest";
    /**
     * 开放平台appKey
     */
    public static final String OPENAPPKEY = "12257306";
    /**
     * 开放平台appsecert
     */
    public static final String OPENAPPSECRET = "9e11038010e890e717153198cde3e331";
    /**
     * 解密随机字符
     */
    public static final String RANDOMNUMBER = "GKGSJsdZ0b1bm0tfTdVwvS6IKgWUr6NobE/EymGjVzQ=";

    /**
     * TRADE_NO_CREATE_PAY-----------(没有创建支付宝交易)
     * WAIT_BUYER_PAY----------------(等待买家付款)
     * SELLER_CONSIGNED_PART---------(卖家部分发货)
     * WAIT_SELLER_SEND_GOODS--------(等待卖家发货,即:买家已付款)
     * WAIT_BUYER_CONFIRM_GOODS------(等待买家确认收货,即:卖家已发货)
     * TRADE_BUYER_SIGNED------------(买家已签收,货到付款专用)
     * TRADE_FINISHED----------------(交易成功)
     * TRADE_CLOSED------------------(付款以后用户退款成功，交易自动关闭)
     * TRADE_CLOSED_BY_TAOBAO--------(付款以前，卖家或买家主动关闭交易)
     * PAY_PENDING-------------------(国际信用卡支付付款确认中)
     * WAIT_PRE_AUTH_CONFIRM---------(0元购合约中)
     * PAID_FORBID_CONSIGN-----------(拼团中订单，已付款但禁止发货)
     */
    public static List<String> TB_INCREASE_STATUS = new ArrayList<String>();

    static {
        TB_INCREASE_STATUS.add("SELLER_CONSIGNED_PART");
        TB_INCREASE_STATUS.add("WAIT_SELLER_SEND_GOODS");
        TB_INCREASE_STATUS.add("WAIT_BUYER_CONFIRM_GOODS");
        TB_INCREASE_STATUS.add("TRADE_BUYER_SIGNED");
        TB_INCREASE_STATUS.add("TRADE_FINISHED");
    }

    public static  List<String> TB_COMMON_STATUS = new ArrayList<String>();
    static {
        TB_COMMON_STATUS.add("TRADE_NO_CREATE_PAY");
        TB_COMMON_STATUS.add("WAIT_BUYER_PAY");
        TB_COMMON_STATUS.add("PAY_PENDING");
        TB_COMMON_STATUS.add("WAIT_PRE_AUTH_CONFIRM");
        TB_COMMON_STATUS.add("PAID_FORBID_CONSIGN");
    }

    public static List<String> TB_REDUCE_STATUS = new ArrayList<String>();
    static{
        TB_REDUCE_STATUS.add("TRADE_CLOSED");
        TB_REDUCE_STATUS.add("TRADE_CLOSED_BY_TAOBAO");
    }

    /**
     * 订单状态
     *  TRADE_NO_CREATE_PAY(没有创建支付宝交易)
     *  WAIT_BUYER_PAY(等待买家付款)
     *  WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
     *  WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
     *  TRADE_BUYER_SIGNED(买家已签收,货到付款专用)
     *  TRADE_FINISHED(交易成功)
     *  TRADE_CLOSED(付款以后用户退款成功，交易自动关闭)
     *  TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
     *  PAY_PENDING(国际信用卡支付付款确认中)
     *
     *  交易状态 0待付款，1待发货，2待收货，3待评价，4交易成功，5已退款，6交易关闭
     */
    public static Map<String, Integer> TB_ORDER_STATUS = new HashMap<String, Integer>();
    static {
        TB_ORDER_STATUS.put("WAIT_BUYER_PAY", 0);
        TB_ORDER_STATUS.put("WAIT_SELLER_SEND_GOODS", 1);
        TB_ORDER_STATUS.put("WAIT_BUYER_CONFIRM_GOODS", 2);
        TB_ORDER_STATUS.put("TRADE_BUYER_SIGNED",3);
        TB_ORDER_STATUS.put("TRADE_FINISHED",4);
        TB_ORDER_STATUS.put("TRADE_CLOSED",5);
        TB_ORDER_STATUS.put("TRADE_CLOSED",6);
    }

}
