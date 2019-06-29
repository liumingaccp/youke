package youke.common.queue.message;

import java.io.Serializable;

/**
 * 微信模板消息队列实体类
 */
public class TempMassMessage implements Serializable {
    private String appId;
    private String openId;
    private Integer comeType;
    private String title;
    private Integer integral;
    private Integer money;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setComeType(Integer comeType) {
        this.comeType = comeType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getAppId() {
        return appId;
    }

    public String getOpenId() {
        return openId;
    }

    public Integer getComeType() {
        return comeType;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getMoney() { return money; }

    public void setMoney(Integer money) { this.money = money; }
}
