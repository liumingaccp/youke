package youke.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MessageUtil {

    private static final String appId = "19411";                                                    //appId
    private static final String signature = "f537cef507f2e5ef9893cfe602dd5966";                     //signature
    private static final String subhookkey = "59c8af0cc09d4bf60a63abfc034044ba";
    private static final String API_SEND = "http://api.submail.cn/message/send.json";               //普通短信发送
    private static final String API_XSEND = "http://api.submail.cn/message/xsend.json";             //加强短信发送
    private static final String API_MULTIXSEND = "http://api.submail.cn/message/multixsend.json";   //短信群发
    private static final String API_LOG = "http://api.submail.cn/log/message.json";                 //短信日志
    private static final String API_TEMPLATE = "http://api.submail.cn/message/template.json";       //模板
    private static final String API_TIMESTAMP = "http://api.submail.cn/service/timestamp.json";     //时间戳

    /**
     * 发送【店有客】验证码
     *
     * @param mobile
     * @param code
     */
    public static void sendMobCode(String mobile, String code) {
        Map<String, String> data = new HashMap<>();
        data.put("appid", appId);
        data.put("to", mobile);
        data.put("project", "frsHa1");
        data.put("vars", "{\"code\":\"" + code + "\",\"time\":\"30\"}");
        data.put("signature", signature);
        System.out.println(HttpConnUtil.doHttpRequest(API_XSEND, data));
    }


    /**
     * 发送【商家】验证码
     *
     * @param mobile
     * @param code
     */
    public static void sendBusMobCode(String mobile, String code, String templateId) {
        Map<String, String> data = new HashMap<>();
        data.put("appid", appId);
        data.put("to", mobile);
        data.put("project", templateId);
        data.put("vars", "{\"code\":\"" + code + "\",\"time\":\"30\"}");
        data.put("signature", signature);
        System.out.println(HttpConnUtil.doHttpRequest(API_XSEND, data));
    }

    /**
     * xsend方式发送短信
     *
     * @param mobile
     * @param vars
     * @param projectId
     */
    public static String xsend(String mobile, String vars, String projectId) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appId);
        data.put("to", mobile);
        data.put("project", projectId);
        data.put("vars", vars);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_XSEND, data);
    }

    /**
     * 群发
     *
     * @param multi
     * @param projectId
     */
    public static String multixsend(String multi, String projectId) {
        Map<String, String> data = new HashMap<>();
        data.put("appid", appId);
        data.put("project", projectId);
        data.put("multi", multi);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_MULTIXSEND, data);
    }

    /**
     * 获取当天发送成功的短信日志
     *
     * @param projectId
     */
    public static String massagelog(String projectId) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appId);
        data.put("result_status", "delivered ");
        data.put("project", projectId);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_LOG, data);
    }

    /**
     * 获取模板
     *
     * @param projectId
     * @return
     */
    public static String get(String projectId) {
        String url = API_TEMPLATE + "?appid=" + appId + "&signature=" + signature + "&template_id=" + projectId;
        return HttpConnUtil.doHttpRequest(url);
    }

    /**
     * 创建模板
     *
     * @param sms_signature 短信签名
     * @param sms_content   短信内容
     * @return
     */
    public static String post(String sms_signature, String sms_content) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appId);
        data.put("sms_signature", sms_signature);
        data.put("sms_content", sms_content);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_TEMPLATE, data);
    }

    /**
     * 更新模板
     *
     * @param sms_signature 短信签名 使用全角大括号"【" 和 "】" 括起来
     * @param sms_content   短信内容
     * @param projectId     模板id
     * @return
     */
    public static String put(String sms_signature, String sms_content, String projectId) {
        Map<String, String> data = new HashMap<>();
        data.put("appid", appId);
        data.put("sms_signature", sms_signature);
        data.put("sms_content", sms_content);
        data.put("project", projectId);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_TEMPLATE, data);
    }

    /**
     * 删除模板
     *
     * @param projectId 模板id
     * @return
     */
    public static String delete(String projectId) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appId);
        data.put("project", projectId);
        data.put("signature", signature);
        return HttpConnUtil.doHttpRequest(API_TEMPLATE, data);
    }

    /**
     * 判断响应中是否包含错误
     *
     * @param response
     * @return
     */
    public static boolean ret(String response) {
        if (response == null) {
            return false;
        }
        return response.contains("success");
    }

    /**
     * 获取subhook的key
     *
     * @return
     */
    public static String getKey() {
        return subhookkey;
    }

    public static void main(String[] args) {
        //sendBusMobCode("15971284605", "0013", "9eTAd3");
        //System.out.println(ret("{\"status\":\"success\",\"send_id\":\"c774d73f23c77ce7be07bde68fd55830\",\"fee\":1,\"sms_credits\":\"3128\",\"transactional_sms_credits\":\"50090\"}"));
    }
}
