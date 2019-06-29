package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCollageActiveTuan implements Serializable {

    private Long id;

    private Integer activeid;

    private Date createtime;

    private Date endtime;

    private Integer alreadytuannum;

    private Integer state;

    private String openid;

    private String appid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActiveid() {
        return activeid;
    }

    public void setActiveid(Integer activeid) {
        this.activeid = activeid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getAlreadytuannum() {
        return alreadytuannum;
    }

    public void setAlreadytuannum(Integer alreadytuannum) {
        this.alreadytuannum = alreadytuannum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}