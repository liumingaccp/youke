package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAccount implements Serializable {
    private Long id;

    private String wxpersonal;

    private Date createtime;

    private Date lastoptime;

    private Integer state;

    private String wx;

    private Date lastaddfriendtime;

    private Integer addnum;

    private Integer addednum;

    private Date lastaddedfriendtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWxpersonal() {
        return wxpersonal;
    }

    public void setWxpersonal(String wxpersonal) {
        this.wxpersonal = wxpersonal == null ? null : wxpersonal.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastoptime() {
        return lastoptime;
    }

    public void setLastoptime(Date lastoptime) {
        this.lastoptime = lastoptime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx == null ? null : wx.trim();
    }

    public Date getLastaddfriendtime() {
        return lastaddfriendtime;
    }

    public void setLastaddfriendtime(Date lastaddfriendtime) {
        this.lastaddfriendtime = lastaddfriendtime;
    }

    public Date getLastaddedfriendtime() {
        return lastaddedfriendtime;
    }

    public void setLastaddedfriendtime(Date lastaddedfriendtime) {
        this.lastaddedfriendtime = lastaddedfriendtime;
    }

    public Integer getAddnum() {
        return addnum;
    }

    public void setAddnum(Integer addnum) {
        this.addnum = addnum;
    }

    public Integer getAddednum() {
        return addednum;
    }

    public void setAddednum(Integer addednum) {
        this.addednum = addednum;
    }
}