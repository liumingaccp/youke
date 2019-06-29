package youke.common.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppPushUtils {

    //定义常量, appId、appKey、masterSecret  在"个推控制台"中获得的应用配置
    // 由IGetui管理页面生成，是您的应用与SDK通信的标识之一，每个应用都对应一个唯一的AppID
    private static String appId = "yjHOJMlyUaA7ioj5zJ5r0A";
    // 预先分配的第三方应用对应的Key，是您的应用与SDK通信的标识之一。
    private static String appKey = "QWwnMJBQMT7A98tdlBpZlA";
    // 个推服务端API鉴权码，用于验证调用方合法性。在调用个推服务端API时需要提供。（请妥善保管，避免通道被盗用）
    private static String masterSecret = "qSm89Ku9Z87Lm661X7GKW";

    // 设置通知消息模板
    /*
     * 1. appId
     * 2. appKey
     * 3. 要传送到客户端的 msg
     * 3.1 标题栏：key = title,
     * 3.2 通知栏内容： key = titleText,
     * 3.3 穿透内容：key = command
     */
    private static TransmissionTemplate getNotifacationTemplate(String appId, String appKey, Map<String, String> msg) {
        // 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg.get("transText"));
        // 设置appid，appkey
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 穿透消息设置为，1 强制启动应用
        template.setTransmissionType(1);
        // 设置穿透内容
        return template;
    }


    /**
     * 对单个用户推送消息
     *
     * @param cid 用户的clientId
     * @param msg 透传的参数
     * @return
     */
    public static IPushResult pushMsgToSingle(String cid, Map<String, String> msg) {
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush push = new IGtPush(appKey, masterSecret);
        // 创建信息模板
        TransmissionTemplate template = getNotifacationTemplate(appId, appKey, msg);
        //定义消息推送方式为，单推
        SingleMessage message = new SingleMessage();
        // 设置推送消息的内容
        message.setData(template);
        //设置过期时间
        message.setOfflineExpireTime(24 * 3600 * 1000);
        //不限制网络环境
        message.setPushNetWorkType(0);
        // 设置推送目标
        Target target = new Target();
        target.setAppId(appId);
        // 设置cid
        target.setClientId(cid);
        // 获得推送结果
        IPushResult result = push.pushMessageToSingle(message, target);
        /*
         * 1. 失败：{result=sign_error}
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online}
         * 3. 异常
         */
        return result;
    }

    /**
     * 对多个用户推送透传消息
     *
     * @param cids
     * @param msg
     * @return
     */
    public static IPushResult pushMsgToList(List<String> cids, Map<String, String> msg) {
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush push = new IGtPush(appKey, masterSecret);
        // 创建信息模板
        TransmissionTemplate template = getNotifacationTemplate(appId, appKey, msg);
        //定义消息推送方式为，多推
        ListMessage message = new ListMessage();
        // 设置推送消息的内容
        message.setData(template);
        //获取ContentId
        String contentId = push.getContentId(message);
        // 设置推送目标
        List<Target> targetList = new ArrayList<>();
        Target target;
        for (String cid : cids) {
            target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            targetList.add(target);
        }
        // 获得推送结果
        IPushResult result = push.pushMessageToList(contentId, targetList);
        /*
         * 1. 失败：{result=sign_error}
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online}
         * 3. 异常
         */
        return result;
    }

    /**
     * 判断是否推送成功
     *
     * @param result
     * @return
     */
    public static boolean isPushSuccess(IPushResult result) {
        if (result.getResponse() != null && result.getResponse().get("result").toString().equals("ok")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, String> msg = new HashMap<>();
        //String commend = "{\"op\":\"wxMoments\",\"url\":\"https://nuoren.oss-cn-shenzhen.aliyuncs.com/test/b1022295-6a61-4c53-8e25-6c8062328180\",\"link\":\"\",\"word\":\"店有客SCRM上线啦\",\"type\":\"image\",\"taskId\":\"1\",\"comment\":\"终于上线啦\"}";
        //String commend = "{ \"op\":\"wxAutoAdd\",\"url\":\"https://nuoren.oss-cn-shenzhen.aliyuncs.com/test/9d5ea0e0-641e-4cfe-b56b-2e2265fae51a\",\"greet\":\"个人号测试\",\"remark\":\"\",\"taskId\":\"1\", \"persons\":\"1\",\"pauseTime\":\"0\",\"totalPerson\":\"7\"}";
        //String commend = "{\"op\":\"wxYangHao\",\"type\":\"1,2,3\",\"taskId\":\"1\"}";
        //String commend = WxPerConstants.IS_ONLINE;
        //String commend = WxPerConstants.GET_ALL;
        //String commend = "{\"op\":\"wxPraiseAndComment\",\"commontNum\":\"0\",\"content\":\"\",\"praiseNum\":\"3\",\"taskId\":\"1\"}";
        //String commend = "{ \"op\":\"wxBatchSend\", \"url\":\"https://nuoren.oss-cn-shenzhen.aliyuncs.com/test/a0f40bb0-3d2c-4a76-8bf2-221ace49f4f1\",\"content\":\"厉害了我的歌\",\"imageUrl\":\"\",\"taskId\":\"1\"}";
        //String commend = WxPerConstants.REMARK.replace("URL","https://nuoren.oss-cn-shenzhen.aliyuncs.com/test/1f99a424-9f11-41a0-9a71-4660bf24894a");
        String commend = "{\"op\":\"wxAutoPass\",\"num\":\"1\",\"taskId\":\"1\"}";
        msg.put("transText", commend);
        IPushResult result = pushMsgToSingle("5d9a13a65cf300f49c07d841cfd63c92", msg);
        System.out.println(isPushSuccess(result));
    }
}
