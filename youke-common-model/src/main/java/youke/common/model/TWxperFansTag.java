package youke.common.model;

import java.io.Serializable;

public class TWxperFansTag implements Serializable {
    private Long id;

    private Long fansid;

    private Long tagid;

    private Long wxAccountid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFansid() {
        return fansid;
    }

    public void setFansid(Long fansid) {
        this.fansid = fansid;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public Long getWxAccountid() {
        return wxAccountid;
    }

    public void setWxAccountid(Long wxAccountid) {
        this.wxAccountid = wxAccountid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid;
    }
}