package youke.common.constants;

import java.util.HashMap;
import java.util.Map;

public class ComeType {

    /**
     * 积分兑换
     */
    public final static int JI_FEN_DUI_HUAN = 0;

    /**
     * 晒单有礼
     */
    public final static int SHAI_DAN_YOU_LI = 1;

    /**
     * 追评有礼
     */
    public final static int ZHUI_PING_YOU_LI = 2;

    /**
     * 晒图有礼
     */
    public final static int SHAI_TU_YOU_LI = 3;

    /**
     * 投票测款
     */
    public final static int TOU_PIAO_CE_KUAN = 4;

    /**
     * 分享海报
     */
    public final static int FEN_XIANG_HAI_BAO = 5;

    /**
     * 首绑有礼
     */
    public final static int SHOU_BANG_YOU_LI = 6;

    /**
     * 每日签到
     */
    public final static int MEI_RI_QIAN_DAO = 7;

    /**
     * 幸运抽奖
     */
    public final static int XING_YUN_CHOU_JIANG = 8;

    /**
     * 试用福利
     */
    public final static int SHI_YONG_FU_LI = 9;

    /**
     * 购物返利
     */
    public final static int GOU_WU_FAN_LI = 10;

    /**
     * 微淘客
     */
    public final static int WEI_TAO_KE = 11;

    /**
     * 推广关注
     */
    public final static int TUI_GUANG_GUAN_ZHU = 12;

    /**
     * 随时返
     */
    public final static int SUI_SHI_FAN = 13;

    /**
     * 砍价
     */
    public final static int KAN_JIA = 14;

    /**
     * 拼团
     */
    public final static int PIN_TUAN = 15;

    //拼团详情
    public final static String GO_PIN_TUAN_URL = "https://m.dianyk.com/#/go-group-booking?appId={appId}&id={id}";
    //发起拼团详情
    public final static String DO_PIN_TUAN_URL = "https://m.dianyk.com/#/do-group-booking?appId={appId}&id={id}";
    //我的拼团详情
    public final static String MY_PIN_TUAN_URL = "https://m.dianyk.com/#/my-group-booking?appId={appId}&id={id}";

    public static boolean isMarketActive(int comeType){
        if(comeType==SHAI_DAN_YOU_LI)
            return true;
        if(comeType==ZHUI_PING_YOU_LI)
            return true;
        if(comeType==SHAI_TU_YOU_LI)
            return true;
        if(comeType==TOU_PIAO_CE_KUAN)
            return true;
        if(comeType==FEN_XIANG_HAI_BAO)
            return true;
        if(comeType==SHOU_BANG_YOU_LI)
            return true;
        if(comeType==MEI_RI_QIAN_DAO)
            return true;
        if(comeType==XING_YUN_CHOU_JIANG)
            return true;
        if(comeType==SUI_SHI_FAN)
            return true;
        return false;
    }

    /**
     * 营销活动type转cometype
     * @param type
     * @return
     */
    public static Integer getComeTypeFromActiveType(int type){
        switch (type) {
            case 0:
                return ComeType.SHAI_DAN_YOU_LI;
            case 1:
                return ComeType.ZHUI_PING_YOU_LI;
            case 2:
                return ComeType.SHAI_TU_YOU_LI;
            case 3:
                return ComeType.SUI_SHI_FAN;
            case 4:
                return ComeType.TOU_PIAO_CE_KUAN;
            case 5:
                return ComeType.SHOU_BANG_YOU_LI;
            case 6:
                return ComeType.MEI_RI_QIAN_DAO;
            case 7:
                return ComeType.XING_YUN_CHOU_JIANG;
            case 8:
                return ComeType.FEN_XIANG_HAI_BAO;
            default:
                return -1;
        }
    }

    public static Map<Integer, String> COME_TYPE = new HashMap<>();

    public static Map<Integer, String> COME_TYPE_PIC = new HashMap<>();

    public static Map<Integer, String> COME_TYPE_URL = new HashMap<>();

    static {
        COME_TYPE.put(JI_FEN_DUI_HUAN,"积分兑换");
        COME_TYPE.put(SHAI_DAN_YOU_LI,"晒单有礼");
        COME_TYPE.put(ZHUI_PING_YOU_LI,"追评有礼");
        COME_TYPE.put(SHAI_TU_YOU_LI,"晒图有礼");
        COME_TYPE.put(TOU_PIAO_CE_KUAN,"投票测款");
        COME_TYPE.put(FEN_XIANG_HAI_BAO,"分享海报");
        COME_TYPE.put(SHOU_BANG_YOU_LI,"首绑有礼");
        COME_TYPE.put(MEI_RI_QIAN_DAO,"每日签到");
        COME_TYPE.put(XING_YUN_CHOU_JIANG,"幸运抽奖");
        COME_TYPE.put(SHI_YONG_FU_LI,"试用福利");
        COME_TYPE.put(GOU_WU_FAN_LI,"购物返利");
        COME_TYPE.put(WEI_TAO_KE,"微淘客");
        COME_TYPE.put(TUI_GUANG_GUAN_ZHU,"推广关注");
        COME_TYPE.put(SUI_SHI_FAN,"随时返");
        COME_TYPE.put(KAN_JIA,"砍价返利");
        COME_TYPE.put(PIN_TUAN,"拼团返利");
        COME_TYPE_PIC.put(JI_FEN_DUI_HUAN,"https://pcapi.dianyk.com/file/news/jifenduihuan.png");
        COME_TYPE_PIC.put(SHAI_DAN_YOU_LI,"https://pcapi.dianyk.com/file/news/shandanyouli.png");
        COME_TYPE_PIC.put(ZHUI_PING_YOU_LI,"https://pcapi.dianyk.com/file/news/zuipingyouli.png");
        COME_TYPE_PIC.put(SHAI_TU_YOU_LI,"https://pcapi.dianyk.com/file/news/shaituyouli.png");
        COME_TYPE_PIC.put(TOU_PIAO_CE_KUAN,"https://pcapi.dianyk.com/file/news/toupiaocekuan.png");
        COME_TYPE_PIC.put(FEN_XIANG_HAI_BAO,"https://pcapi.dianyk.com/file/news/fenxianghaibao.png");
        COME_TYPE_PIC.put(SHOU_BANG_YOU_LI,"https://pcapi.dianyk.com/file/news/soubangyouli.png");
        COME_TYPE_PIC.put(MEI_RI_QIAN_DAO,"https://pcapi.dianyk.com/file/news/qiandao.png");
        COME_TYPE_PIC.put(XING_YUN_CHOU_JIANG,"https://pcapi.dianyk.com/file/news/choujiang.png");
        COME_TYPE_PIC.put(SHI_YONG_FU_LI,"https://pcapi.dianyk.com/file/news/soubangyouli.png");
        COME_TYPE_PIC.put(GOU_WU_FAN_LI,"https://pcapi.dianyk.com/file/news/gouwufanli.png");
        COME_TYPE_PIC.put(WEI_TAO_KE,"https://pcapi.dianyk.com/file/news/weitaoke.png");
        COME_TYPE_PIC.put(TUI_GUANG_GUAN_ZHU,"https://pcapi.dianyk.com/file/news/tuiguanguangzhu.png");
        COME_TYPE_PIC.put(SUI_SHI_FAN,"https://pcapi.dianyk.com/file/news/suishifan.png");
        COME_TYPE_PIC.put(KAN_JIA,"https://pcapi.dianyk.com/file/news/kanjiafanli.png");
        COME_TYPE_PIC.put(PIN_TUAN,"https://pcapi.dianyk.com/file/news/pintuanfanli.png");

        COME_TYPE_URL.put(JI_FEN_DUI_HUAN," https://m.dianyk.com/#/points-shopping-detail?appId={appId}&id={id}");
        COME_TYPE_URL.put(SHAI_DAN_YOU_LI,"https://m.dianyk.com/#/show-order-have-gift?appId={appId}");
        COME_TYPE_URL.put(ZHUI_PING_YOU_LI,"https://m.dianyk.com/#/chase-evaluate-have-gift?appId={appId}");
        COME_TYPE_URL.put(SHAI_TU_YOU_LI,"https://m.dianyk.com/#/show-img-have-gift?appId={appId}");
        COME_TYPE_URL.put(TOU_PIAO_CE_KUAN,"https://m.dianyk.com/#/vote-test-section?appId={appId}");
        COME_TYPE_URL.put(FEN_XIANG_HAI_BAO,"https://m.dianyk.com/#/share-poster?appId={appId}");
        COME_TYPE_URL.put(SHOU_BANG_YOU_LI,"https://m.dianyk.com/#/first-tied-have-gift/bow-tie?appId={appId}");
        COME_TYPE_URL.put(MEI_RI_QIAN_DAO,"https://m.dianyk.com/#/sign-in-every-day?appId={appId}");
        COME_TYPE_URL.put(XING_YUN_CHOU_JIANG,"https://m.dianyk.com/#/luckydraw?appId={appId}");
        COME_TYPE_URL.put(SHI_YONG_FU_LI,"https://m.dianyk.com/#/probation-goods-detail?appId={appId}&id={id}");
        COME_TYPE_URL.put(GOU_WU_FAN_LI,"https://m.dianyk.com/#/shopping-return-details?appId={appId}&id={id}");
        COME_TYPE_URL.put(WEI_TAO_KE,"https://m.dianyk.com/#/micro-amoy-details?appId={appId}&id={id}");
        COME_TYPE_URL.put(TUI_GUANG_GUAN_ZHU,"https://m.dianyk.com/#/promotion-of-attention?appId={appId}");
        COME_TYPE_URL.put(SUI_SHI_FAN,"https://m.dianyk.com/#/any-time-back?appId={appId}");
        COME_TYPE_URL.put(KAN_JIA,"https://m.dianyk.com/#/bargain-details?appId={appId}&id={id}");
        COME_TYPE_URL.put(PIN_TUAN,"https://m.dianyk.com/#/my-group-booking?appId={appId}&id={id}");
    }

    public static Map<Integer, String> YING_XIAO_TYPES = new HashMap<>();

    //活动类型 0晒单有礼，1追评有礼，2晒图有礼，3随时返，4投票测款，5首绑有礼，6每日签到，7幸运抽奖,8分享海报
    //["SaiDan","ZuiPing","SaiTu","SuiShiFan","SouBang","QianDao","ChouJiang","TouPiao","Fanli","Taoke","ShiYong","TuiGuang","KanJia","PinTuan","KeFu"]
    static {
        YING_XIAO_TYPES.put(0, "SaiDan");
        YING_XIAO_TYPES.put(1, "ZuiPing");
        YING_XIAO_TYPES.put(2, "SaiTu");
        YING_XIAO_TYPES.put(3, "SuiShiFan");
        YING_XIAO_TYPES.put(4, "TouPiao");
        YING_XIAO_TYPES.put(5, "SouBang");
        YING_XIAO_TYPES.put(6, "QianDao");
        YING_XIAO_TYPES.put(7, "ChouJiang");
    }
}
