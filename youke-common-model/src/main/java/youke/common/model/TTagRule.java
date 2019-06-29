package youke.common.model;

import java.io.Serializable;

public class TTagRule implements Serializable {
    private Integer serialnum;

    private Integer thannum;

    private Integer tagid;

    private Integer type;

    private String appid;

    public Integer getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(Integer serialnum) {
        this.serialnum = serialnum;
    }

    public Integer getThannum() {
        return thannum;
    }

    public void setThannum(Integer thannum) {
        this.thannum = thannum;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }
}