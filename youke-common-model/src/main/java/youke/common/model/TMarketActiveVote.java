package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMarketActiveVote implements Serializable {
    private Long id;

    private Long picid;

    private String openid;

    private Long activeid;

    private Date createtime;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPicid() {
        return picid;
    }

    public void setPicid(Long picid) {
        this.picid = picid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}