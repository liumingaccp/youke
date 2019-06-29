package youke.common.model.vo.param;

import java.io.Serializable;

/*当 SUBHOOK 推送一条事件通知时，将会在 POST 数组中加入 token 和 signature 参数。
    token 参数是一个 32 位的随机字符串，signature 参数是 SUBHOOK 创建的唯一数字签名。
    在接收 SUBHOOK 时，请将获得的 token 值和你的 SUBHOOK 密匙拼接成字符串（即 token+key），随后使用 MD5 创建此签名，并与 signature 参数进行比对。*/
public class Message implements Serializable {
    private static final long serialVersionUID = -4125300213783918366L;
    private String events;              //事件类型
    private String address;             //电话号码
    private String app;                 //应用 ID
    private String send_id;             //该条短信的唯一发送标识，可在 API 请求时获取
    private String timestamp;           //事件触发时间
    private String token;               //32 位随机字符串
    private String signature;           //数字签名
    private String report;              //网关失败回执
    private String content;             //短信内容
    private String template_id;         //模板id
    private String reason;              //原因

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Message [events=" + events + ", address=" + address + ", app=" + app + ", send_id=" + send_id
                + ", timestamp=" + timestamp + ", token=" + token + ", signature=" + signature + ", report=" + report
                + ", content=" + content + ", template_id=" + template_id + ", reason=" + reason + "]";
    }
}