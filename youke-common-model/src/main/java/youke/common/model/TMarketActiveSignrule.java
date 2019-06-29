package youke.common.model;

import java.io.Serializable;

public class TMarketActiveSignrule implements Serializable {
    private Long id;

    private Long activeid;

    private Integer runday;

    private Integer integral;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public Integer getRunday() {
        return runday;
    }

    public void setRunday(Integer runday) {
        this.runday = runday;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}