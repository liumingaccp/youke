package youke.facade.cloudcode.vo;

import java.util.HashMap;
import java.util.Map;

public class CloudCodeConstant {

    public static final String CC = "h5_wxpersonalnum_cloudcode";

    public static final int IN_USE = 0;     //正在使用
    public static final int IS_DROP = 1;    //已作废

    public static final Map<Integer, String> MAP_STATE = new HashMap<>();

    static {
        MAP_STATE.put(IN_USE, "正在使用");
        MAP_STATE.put(IS_DROP, "已作废");
    }

}
