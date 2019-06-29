package youke.facade.helper.vo.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 10:48
 */
public class ActiveState {

    public static final String KJ = "h5_cutprice_page";

    //0未开始，1进行中，2已结束，3已删除
    public static final int STATE_WAIT_FOR_START = 0;
    public static final int STATE_ACTIVEING = 1;
    public static final int STATE_FINISHED = 2;
    public static final int STATE_DELETE = 3;


    public static final Map<Integer, String> MAP_STATE = new HashMap<>();
    static {
        MAP_STATE.put(STATE_WAIT_FOR_START, "未开始");
        MAP_STATE.put(STATE_ACTIVEING, "进行中");
        MAP_STATE.put(STATE_FINISHED, "已结束");
        MAP_STATE.put(STATE_DELETE, "已删除");
    }
}
