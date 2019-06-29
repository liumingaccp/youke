package youke.order.common.model;

import java.io.Serializable;

public class TTag implements Serializable {
    private Integer id;

    private String wxtagid;

    private String title;

    private Integer groupid;

    private Integer ruletype;

    private String appid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxtagid() {
        return wxtagid;
    }

    public void setWxtagid(String wxtagid) {
        this.wxtagid = wxtagid == null ? null : wxtagid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getRuletype() {
        return ruletype;
    }

    public void setRuletype(Integer ruletype) {
        this.ruletype = ruletype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    @Override
    public String toString() {
        return "TTag{" +
                "id=" + id +
                ", wxtagid='" + wxtagid + '\'' +
                ", title='" + title + '\'' +
                ", groupid=" + groupid +
                ", ruletype=" + ruletype +
                ", appid='" + appid + '\'' +
                '}';
    }
}