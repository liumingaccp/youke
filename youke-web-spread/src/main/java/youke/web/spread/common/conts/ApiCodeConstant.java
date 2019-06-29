package youke.web.spread.common.conts;

import java.util.HashMap;
import java.util.Map;

public class ApiCodeConstant {

    public final static String DOMAIN_PC = "https://pc.dianyk.com/";

    public final static String DOMAIN_PC_INVITECODE = DOMAIN_PC+"#/register?invitecode={code}";

    public final static int COMMON_SUCCESS = 1000;
    //业务错误
    public final static int COMMON_ERROR_BIZ = 1001;
    //系统错误
    public final static int COMMON_ERROR_SYS = 1002;
    //用户被拒绝登录
    public final static int COMMON_ERROR_USER_REFUSED = 1003;
    //sign签名错误
    public final static int COMMON_ERROR_SING = 1004;
    //登录过期
    public final static int COMMON_ERROR_LOGIN_TIMEOUT = 1005;
    //timestamp超时
    public final static int COMMON_ERROR_TIMESTAMP_TIMEOUT = 1006;
    //JSON参数错误
    public final static int COMMON_ERROR_PARAM = 1008;

    public final static String USER_FLAG_BR = "Br";
    public final static String USER_FLAG_BB = "Bb";
    public final static String USER_FLAG_T = "T";
    public final static String USER_FLAG_C = "C";


}
