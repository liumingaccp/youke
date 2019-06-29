package youke.facade.user.vo;

import java.io.Serializable;

public class UserVo implements Serializable {

    /**
     * 店有客Id
     */
    private String dykId;
    /**
     * 公众号Id
     */
    private String appId;
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 用户名（手机号码）
     */
    private String mobile;

    private int vip;

    private Integer role;

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public String getDykId() {
        return dykId;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
