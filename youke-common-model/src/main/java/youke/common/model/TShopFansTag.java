package youke.common.model;

import java.io.Serializable;

public class TShopFansTag implements Serializable {
    private Long id;

    private Integer tagid;

    private Long fansid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Long getFansid() {
        return fansid;
    }

    public void setFansid(Long fansid) {
        this.fansid = fansid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid;
    }
}