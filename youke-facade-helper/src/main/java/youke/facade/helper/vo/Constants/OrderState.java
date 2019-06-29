package youke.facade.helper.vo.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 10:48
 */
public class OrderState {
    ////0待购买,1超时未购买, 2待收货, 3已收货, 4待审核，5审核通过，6审核失败，7已返利
    public static final int WAIT_FOR_BUY = 0;
    public static final int OVER_TIME_TO_BUY = 1;
    public static final int WAIT_FOR_RECEVIVE = 2;
    public static final int RECEVIVE = 3;
    public static final int WAIT_EXAMINE = 4;
    public static final int PASS_EXAMINE = 5;
    public static final int FAIL_EXAMINE = 6;
    public static final int REBATE = 7;

    public static final int BARGAIN = 0;
    public static final int BARGAIN_FAILED = 1;
    public static final int WAIT_BUY = 2;
    public static final int WAIT_RECEIPT = 3;
    public static final int TIME_OUT = 4;
    public static final int WAIT_REBAIT = 5;
    public static final int ALREADY_REBAIT = 6;
    public static final int REBAIT_FAILED = 7;

    public static final Map<Integer, String> MAP_STATE = new HashMap<>();

    static {
        MAP_STATE.put(WAIT_FOR_BUY, "待购买");
        MAP_STATE.put(OVER_TIME_TO_BUY, "超时未购买");
        MAP_STATE.put(WAIT_FOR_RECEVIVE, "待收货");
        MAP_STATE.put(RECEVIVE, "已收货");
        MAP_STATE.put(WAIT_EXAMINE, "待审核");
        MAP_STATE.put(PASS_EXAMINE, "审核通过");
        MAP_STATE.put(FAIL_EXAMINE, "审核失败");
        MAP_STATE.put(REBATE, "已返利");

        MAP_STATE.put(BARGAIN, "砍价中");
        MAP_STATE.put(BARGAIN_FAILED, "砍价失败");
        MAP_STATE.put(WAIT_BUY, "待购买");
        MAP_STATE.put(WAIT_RECEIPT, "待收货");
        MAP_STATE.put(TIME_OUT, "超时未购买");
        MAP_STATE.put(WAIT_REBAIT, "待返利");
        MAP_STATE.put(ALREADY_REBAIT, "已返利");
        MAP_STATE.put(REBAIT_FAILED, "返利失败");
    }
}
