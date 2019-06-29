package youke.common.model.vo.param;

import java.io.Serializable;

/**
 * 支付设置
 */
public class PaymentSettingParamVo implements Serializable {
    private String payNumber;       //支付商户名
    private String paycert;         //支付秘钥
    private String validtimeBeg;    //证书有效期开始
    private String validtimeEnd;    //证书有效期截止
    private String filecert;        //证书地址
    private String appId;
    private String timestamp;       //时间戳

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setPaycert(String paycert) {
        this.paycert = paycert;
    }

    public String getPaycert() {
        return paycert;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public void setValidtimeBeg(String validtimeBeg) {
        this.validtimeBeg = validtimeBeg;
    }

    public void setValidtimeEnd(String validtimeEnd) {
        this.validtimeEnd = validtimeEnd;
    }

    public void setFilecert(String filecert) {
        this.filecert = filecert;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public String getValidtimeBeg() {
        return validtimeBeg;
    }

    public String getValidtimeEnd() {
        return validtimeEnd;
    }

    public String getFilecert() {
        return filecert;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

