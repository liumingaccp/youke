package youke.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/11
 * Time: 17:31
 */
public class WxPerConstants {

    /**
     * 群发间隔时间(单位分钟)
     */
    public static final int MASS_TIME_INTERVAL = 90;

    /**
     * 群发单次人数
     */
    public static final int MASS_NUM = 200;

    /**
     * 前台传入的群发任务执行时间 允许的时间差(单位秒)
     * 如果任务时间与当前时间差小于这个数值,则推迟到这个时间
     */
    public static final int MASS_TASK_TIME_INTERVAL = 45;

    /**
     * 默认每日好友添加数量
     */
    public static final int ADD_FRIEND_NUM = 10;

    /**
     * 个人号指令队列的格式
     */
    public static final String OP_QUQUE = "{YOUKE_ID}-DEVICE_{DEVICE_ID}-WXACCOUNT_{WXACCOUNT_ID}";

    /**
     * 个人号指令
     */
    public static Map<Integer, String> COMMAND_MAP = new HashMap<>();

    /**
     * 粉丝过滤集合
     */
    public static List<String> FANS_FILTER_LIST = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------------------
    /**
     * 1、获取微信号基本数据：微信号、昵称、粉丝数量、所有通讯录
     */
    public static final String GET_ALL = "{\"op\":\"wxGetAll\"}";
    /**
     * 2、好友群发
     */
    public static final String MASS_SEND = "{ \"op\":\"wxBatchSend\", \"url\":\"URL\",\"content\":\"CONTENT\",\"imageUrl\":\"IMAGE\",\"taskId\":\"TASKID\"}";
    /**
     * 3、朋友圈营销
     */
    public static final String CIRCLE_MARKET = "{\"op\":\"wxMoments\",\"url\":\"URL\",\"link\":\"LINK\",\"word\":\"WORD\",\"type\":\"TYPE\",\"taskId\":\"TASKID\",\"comment\":\"COMMENT\"}";
    /**
     * 4、自动加好友
     */
    public static final String ADD_FRIEND = "{ \"op\":\"wxAutoAdd\",\"url\":\"URL\",\"greet\":\"GREET\",\"remark\":\"REMARK\",\"taskId\":\"TASKID\", \"persons\":\"PERSONS\",\"pauseTime\":\"PAUSETIME\",\"totalPerson\":\"TOTALPERSON\"}";
    /**
     * 5、粉丝备注
     */
    public static final String REMARK = "{ \"op\":\"wxRemark\",\"url\":\"URL\"}";
    /**
     * 设备养号
     */
    public static final String YANG_HAO = "{\"op\":\"wxYangHao\",\"type\":\"TYPE\",\"taskId\":\"TASKID\"}";
    /**
     * 7、朋友圈自动点赞评论
     */
    public static final String DIAN_ZAN = "{\"op\":\"wxPraiseAndComment\",\"commontNum\":\"COMMONTNUM\",\"content\":\"CONTENT\",\"praiseNum\":\"PRAISENUM\",\"taskId\":\"TASKID\"}";
    /**
     * 被添加自动通过设置
     */
    public static final String ADD_CONFIG = "{\"op\":\"wxAutoPass\",\"num\":\"NUM\",\"taskId\":\"TASKID\"}";

    /**
     * 判断设备是否连接
     */
    public static final String IS_ONLINE = "{\"op\":\"wxIsOnline\"}";

    static {
        COMMAND_MAP.put(WxPerOpType.ADD_FRIEND, ADD_FRIEND);
        COMMAND_MAP.put(WxPerOpType.FRIEND_MASS, MASS_SEND);
        COMMAND_MAP.put(WxPerOpType.SEND_CICLE, CIRCLE_MARKET);
        COMMAND_MAP.put(WxPerOpType.DIAN_ZAN, DIAN_ZAN);
        COMMAND_MAP.put(WxPerOpType.YANG_HAO, YANG_HAO);

        FANS_FILTER_LIST.add("weixin");
        FANS_FILTER_LIST.add("qqmail");
        FANS_FILTER_LIST.add("shakeapp");
        FANS_FILTER_LIST.add("lbsapp");
        FANS_FILTER_LIST.add("medianote");
        FANS_FILTER_LIST.add("newsapp");
        FANS_FILTER_LIST.add("qqfriend");
        FANS_FILTER_LIST.add("feedsapp");
        FANS_FILTER_LIST.add("voipapp");
        FANS_FILTER_LIST.add("fmessage");
        FANS_FILTER_LIST.add("qmessage");
        FANS_FILTER_LIST.add("filehelper");
        FANS_FILTER_LIST.add("masssendapp");
        FANS_FILTER_LIST.add("floatbottle");
        FANS_FILTER_LIST.add("facebookapp");
        FANS_FILTER_LIST.add("voicevoipapp");
        FANS_FILTER_LIST.add("voiceinputapp");
        FANS_FILTER_LIST.add("notifymessage");
        FANS_FILTER_LIST.add("linkedinplugin");
        FANS_FILTER_LIST.add("officialaccounts");
        FANS_FILTER_LIST.add("appbrandcustomerservicemsg");
    }
}
