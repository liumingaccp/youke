package youke.facade.user.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/7
 * Time: 14:42
 */
public class SubConfigVo implements Serializable {

    private String nickName;

    private String headImg;

    private Integer serviceType;

    private String qrcodeUrl;

    private Integer payBuss;

    private Integer payState;

    private String payNumber;

    private String paycert;

    private String validtimeBeg;

    private String validtimeEnd;

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public void setPaycert(String paycert) {
        this.paycert = paycert;
    }

    public void setValidtimeBeg(String validtimeBeg) {
        this.validtimeBeg = validtimeBeg;
    }

    public void setValidtimeEnd(String validtimeEnd) {
        this.validtimeEnd = validtimeEnd;
    }

    public Integer getPayState() {
        return payState;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public String getPaycert() {
        return paycert;
    }

    public String getValidtimeBeg() {
        return validtimeBeg;
    }

    public String getValidtimeEnd() {
        return validtimeEnd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Integer getPayBuss() {
        return payBuss;
    }

    public void setPayBuss(Integer payBuss) {
        this.payBuss = payBuss;
    }
}
