package youke.facade.market.vo;

import java.util.HashMap;
import java.util.Map;

public class MarketConstant {

    public static final String KEY_RECORD = "ACTIVE_RECORD";                        //活动记录
    public static final String KEY_CHART = "ACTIVE_CHART";                          //活动图表
    public final static String KEY_TRANCHART = "ACTIVE_TRANCHART";                  //活动转化图表

    public static final String KEY_HOME_DATA = "ACTIVE_HOMEDATA";                  //首页数据
    public static final String KEY_HOME_CHART = "ACTIVE_HOME_CHART";               //首页图表数据

    public static final int WAIT_ONLINE = 0;    //待上线
    public static final int ONLINE = 1;         //上线
    public static final int IS_OVER = 2;        //已下线
    public static final int IS_DEL = 3;         //已删除

    public static final int WAIT_AUDIT = 0;     //待审核
    public static final int AUDIT_PASS = 1;     //审核通过
    public static final int AUDIT_FAIL = 2;     //审核失败
    public static final int IS_REBATE = 3;      //已返利
    public static final int REBATE_FAIL = 4;    //返利失败


    public static final String SDYL = "h5_activity_shaidan_page";
    public static final String ZPYL = "h5_activity_zhuiping_page";
    public static final String STYL = "h5_activity_shaitu_page";
    public static final String SSF = "h5_activity_suishifan_page";
    public static final String TPYL = "h5_activity_toupiao_page";
    public static final String SBYL = "h5_activity_shoubang_page";
    public static final String QDYL = "h5_activity_qiandao_page";
    public static final String CJYL = "h5_activity_choujiang_page";
    public static final String FXHB = "h5_activity_haibao_page";

    public static final Map<Integer, String> ACTIVE_STATE = new HashMap<>();
    public static final Map<Integer, String> RECORD_STATE = new HashMap<>();

    static {
        ACTIVE_STATE.put(WAIT_ONLINE, "待上线");
        ACTIVE_STATE.put(ONLINE, "进行中");
        ACTIVE_STATE.put(IS_OVER, "已结束");
        ACTIVE_STATE.put(IS_DEL, "已删除");
        RECORD_STATE.put(WAIT_AUDIT, "待审核");
        RECORD_STATE.put(AUDIT_PASS, "审核通过");
        RECORD_STATE.put(AUDIT_FAIL, "审核未通过");
        RECORD_STATE.put(IS_REBATE, "已返利");
        RECORD_STATE.put(REBATE_FAIL, "返利失败");
    }

}
