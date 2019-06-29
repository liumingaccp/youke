package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCloudCodeQrCode implements Serializable {
    private Long id;

    private String url;

    private Integer state;

    private String remark;

    private Long cloudid;

    private Long ruleid;

    private Integer scantimes;

    private Date lastscantime;

    private String youkeid;

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCloudid() {
        return cloudid;
    }

    public void setCloudid(Long cloudid) {
        this.cloudid = cloudid;
    }

    public Long getRuleid() {
        return ruleid;
    }

    public void setRuleid(Long ruleid) {
        this.ruleid = ruleid;
    }

    public Integer getScantimes() {
        return scantimes;
    }

    public void setScantimes(Integer scantimes) {
        this.scantimes = scantimes;
    }

    public Date getLastscantime() {
        return lastscantime;
    }

    public void setLastscantime(Date lastscantime) {
        this.lastscantime = lastscantime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}