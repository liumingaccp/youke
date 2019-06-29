package youke.common.model;

import java.io.Serializable;

public class TSubscrFansTag implements Serializable {
    private Integer id;

    private String openid;

    private Integer tagid;

    private Integer syncstate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getSyncstate() {
        return syncstate;
    }

    public void setSyncstate(Integer syncstate) {
        this.syncstate = syncstate;
    }
}