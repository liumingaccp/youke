package youke.common.constants;

import java.util.HashMap;
import java.util.Map;

public class ApiCodeConstant {

    public final static String DOMAIN_PC = "https://pc.dianyk.com/";

    public final static String DOMAIN_PCAPI = "https://pcapi.dianyk.com/";

    public final static String OSS_BASE = "https://nuoren.oss-cn-shenzhen.aliyuncs.com/";

    public final static String WX_TAO = "https://t.dianyk.com/m/";

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

    //H5需要关注公众号
    public final static int COMMON_H5_SUBSCR = 2001;

    //H5需要绑定手机
    public final static int COMMON_H5_MOBILE = 2002;

    public final static int IMPORT_FANS_CSV_HEADER_SIZE = 11;

    public final static int MONITOR_MAX_MOBCODE = 20;

    /**
     * 店有客公众号APPID
     */
    public static final String APPID = "wxe3e582584ba16db1";
    /**
     * 店有客体验号APPID
     */
    public static final String APPID_TIYAN = "wxb90758151405384b";
    /**
     * 店有客研发号APPID
     */
    public static final String APPID_YANFA = "wx913f4c4d60d9f820";

    public static final String DEFAULT_CODE_MSG = "您的验证码@var(code)，请在@var(time)分钟内按页面提示输入，切勿将验证码泄露于他人。";

    /**
     * 以下是阿里云对象存储参数
     */
    //子账户id
    public final static String SUB_ACCOUNT_ID = "LTAIM2bVpwZJOnQC";
    //子账户密钥
    public final static String SUB_ACCOUNT_SECRET = "9sUoJAQcueRrcJBLcsGl1tB1j1nWFh";
    //bucket
    public final static String BUCKET_NAME = "nuoren";
    //密钥访问服务域名
    public final static String ENDPOINT = "https://oss-cn-shenzhen.aliyuncs.com";
    //直接访问域名
    public final static String AUTH_DOMAIN = "https://nuoren.oss-cn-shenzhen.aliyuncs.com";

    //系统平台支付商户号
    public static final String PAY_MCHID = "1496498832";
    //系统平台支付密钥
    public static final String PAY_MCHKEY = "4277147287d8137f9c6c4893dc8f46b4";
    //系统平台支付证书
    public static final String PAY_FILECERT = "https://nuoren.oss-cn-shenzhen.aliyuncs.com/p12/b23664556289f317a408149a87c31b94.p12";

    //vip级别对应的名称
    public static Map<Integer, String> VIP_TYPE = new HashMap<>();

    //vip对应赠送短信的数量
    public static Map<Integer, Integer> FREESMS_NUM = new HashMap<>();

    //子账户数量和vip对应的数量
    public static Map<Integer, Integer> SUBACCOUNT_NUM = new HashMap<>();

    //子账户数量对应可登录微信数量
    public static Map<Integer, Integer> OPENWEIXIN_NUM = new HashMap<>();

    //vip对应可添加设备上限数量
    public static Map<Integer, Integer> DEVICE_NUM = new HashMap<>();



    static {
        VIP_TYPE.put(1, "基础版");
        VIP_TYPE.put(2, "专业版");
        VIP_TYPE.put(3, "旗舰版");
        VIP_TYPE.put(4, "企业定制版");
        VIP_TYPE.put(9, "体验版");

        FREESMS_NUM.put(1,100);
        FREESMS_NUM.put(2,100);
        FREESMS_NUM.put(3,100);
        FREESMS_NUM.put(4,100);
        FREESMS_NUM.put(9,100);

        SUBACCOUNT_NUM.put(1, 3);
        SUBACCOUNT_NUM.put(2, 10);
        SUBACCOUNT_NUM.put(3, 30);
        SUBACCOUNT_NUM.put(4, 50);
        SUBACCOUNT_NUM.put(9, 1);

        OPENWEIXIN_NUM.put(1, 5);
        OPENWEIXIN_NUM.put(2, 10);
        OPENWEIXIN_NUM.put(3, 15);
        OPENWEIXIN_NUM.put(4, 20);
        OPENWEIXIN_NUM.put(9, 5);

        DEVICE_NUM.put(1,15);
        DEVICE_NUM.put(2,100);
        DEVICE_NUM.put(3,450);
        DEVICE_NUM.put(4,1000);
        DEVICE_NUM.put(9,5);
    }

    /**
     * 微信上传素材的支持类型
     */
    public final static String[] imageLists = new String[]{"bmp", "png", "jpeg", "gif", "BMP", "PNG", "JPEG", "GIF"};
    public final static String[] voiceLists = new String[]{"mp3", "wma", "wav", "amr", "MP3", "WMA", "WAV", "AMR"};
    public final static String[] videoLists = new String[]{"mp4", "MP4"};
    public final static String[] thumbLists = new String[]{"jpg", "JPG"};

    public static boolean hasType(String[] arr, String key) {
        for (String str : arr) {
            if (str.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public final static String[] IMPORT = new String[]{"csv", "xls", "xlsx"};
}
