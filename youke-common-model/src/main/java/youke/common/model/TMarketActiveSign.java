package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMarketActiveSign implements Serializable {
    private Long id;

    private Long activeid;

    private String openid;

    private Integer runday;

    private Integer lastrunday;

    private Date lastdate;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getRunday() {
        return runday;
    }

    public void setRunday(Integer runday) {
        this.runday = runday;
    }

    public Integer getLastrunday() {
        return lastrunday;
    }

    public void setLastrunday(Integer lastrunday) {
        this.lastrunday = lastrunday;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}