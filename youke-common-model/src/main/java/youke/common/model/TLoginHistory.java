package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TLoginHistory implements Serializable{
    private Long id;

    private String mobile;

    private Date logintime;

    private String loginip;

    private String region;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}