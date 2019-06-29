package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCloudCodeRecord implements Serializable {
    private Long id;

    private String openid;

    private Date scantime;

    private Long cloudid;

    private Long codeid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getScantime() {
        return scantime;
    }

    public void setScantime(Date scantime) {
        this.scantime = scantime;
    }

    public Long getCloudid() {
        return cloudid;
    }

    public void setCloudid(Long cloudid) {
        this.cloudid = cloudid;
    }

    public Long getCodeid() {
        return codeid;
    }

    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}