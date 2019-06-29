package youke.common.model;

import java.io.Serializable;

public class TMarketActiveLuck implements Serializable {
    private Long activeid;

    private Integer costintegral;

    private String prizeobj;

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public Integer getCostintegral() {
        return costintegral;
    }

    public void setCostintegral(Integer costintegral) {
        this.costintegral = costintegral;
    }

    public String getPrizeobj() {
        return prizeobj;
    }

    public void setPrizeobj(String prizeobj) {
        this.prizeobj = prizeobj == null ? null : prizeobj.trim();
    }
}