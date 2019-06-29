package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class SubAccountLoginRecordRetVo implements Serializable {
    private Long id;
    private String mobile;
    private String loginIp;
    private String region;
    private Date loginTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public String getRegion() {
        return region;
    }

    public Date getLoginTime() {
        return loginTime;
    }
}
