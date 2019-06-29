package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMobcodeFans implements Serializable {
    private String mobile;

    private Date lastsendtime;

    private String youkeid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getLastsendtime() {
        return lastsendtime;
    }

    public void setLastsendtime(Date lastsendtime) {
        this.lastsendtime = lastsendtime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}