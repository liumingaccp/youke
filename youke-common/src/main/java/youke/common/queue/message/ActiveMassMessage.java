package youke.common.queue.message;

import java.io.Serializable;

/**
 * pay_wx
 */
public class ActiveMassMessage implements Serializable {
    private Long recordId;                //活动参与Id
    private Integer comeType;               //类型
    private Integer money;                  //金额:单位分
    private Integer integral;               //积分:单位分
    private String title;                   //标题
    private String openId;
    private String appId;
    private String youkeId;
    private Integer state = 1;                  //1审核通过，2审核未通过
    private String failReason;              //失败原因

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setComeType(Integer comeType) {
        this.comeType = comeType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public Integer getComeType() {
        return comeType;
    }

    public String getTitle() {
        return title;
    }

    public String getOpenId() {
        return openId;
    }

    public String getAppId() {
        return appId;
    }

    public String getYoukeId() {
        return youkeId;
    }
}
