package youke.order.common.model;

import java.io.Serializable;
import java.util.Date;

public class TShop implements Serializable {
    private Integer id;

    private String dianid;

    private String dianname;

    private String cover;

    private String title;

    private Date createtime;

    private Integer state;

    private Date authtime;

    private Date exptime;

    private Integer type;

    private String accesstoken;

    private String refreshtoken;

    private String youkeid;

    private Date updatetime;

    private int isDemo = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDianid() {
        return dianid;
    }

    public void setDianid(String dianid) {
        this.dianid = dianid == null ? null : dianid.trim();
    }

    public String getDianname() {
        return dianname;
    }

    public void setDianname(String dianname) {
        this.dianname = dianname == null ? null : dianname.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getAuthtime() {
        return authtime;
    }

    public void setAuthtime(Date authtime) {
        this.authtime = authtime;
    }

    public Date getExptime() {
        return exptime;
    }

    public void setExptime(Date exptime) {
        this.exptime = exptime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken == null ? null : accesstoken.trim();
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken == null ? null : refreshtoken.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(int isDemo) {
        this.isDemo = isDemo;
    }
}