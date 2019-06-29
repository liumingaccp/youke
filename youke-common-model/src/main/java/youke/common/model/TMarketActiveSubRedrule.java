package youke.common.model;

import java.io.Serializable;

public class TMarketActiveSubRedrule implements Serializable {
    private Long activeid;

    private Integer openmidlimit;

    private Integer midlimitbeg;

    private Integer midlimitend;

    private Integer midmoneyback;

    private Integer midrandmoneybeg;

    private Integer midrandmoneyend;

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public Integer getOpenmidlimit() {
        return openmidlimit;
    }

    public void setOpenmidlimit(Integer openmidlimit) {
        this.openmidlimit = openmidlimit;
    }

    public Integer getMidlimitbeg() {
        return midlimitbeg;
    }

    public void setMidlimitbeg(Integer midlimitbeg) {
        this.midlimitbeg = midlimitbeg;
    }

    public Integer getMidlimitend() {
        return midlimitend;
    }

    public void setMidlimitend(Integer midlimitend) {
        this.midlimitend = midlimitend;
    }

    public Integer getMidmoneyback() {
        return midmoneyback;
    }

    public void setMidmoneyback(Integer midmoneyback) {
        this.midmoneyback = midmoneyback;
    }

    public Integer getMidrandmoneybeg() {
        return midrandmoneybeg;
    }

    public void setMidrandmoneybeg(Integer midrandmoneybeg) {
        this.midrandmoneybeg = midrandmoneybeg;
    }

    public Integer getMidrandmoneyend() {
        return midrandmoneyend;
    }

    public void setMidrandmoneyend(Integer midrandmoneyend) {
        this.midrandmoneyend = midrandmoneyend;
    }
}