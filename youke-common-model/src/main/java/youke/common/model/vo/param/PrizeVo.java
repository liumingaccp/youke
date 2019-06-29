package youke.common.model.vo.param;

import java.io.Serializable;

public class PrizeVo implements Serializable {
    private String id;
    private String prizeName;
    private Integer rewardType;
    private Integer rewardVal;
    private Double percent;

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setRewardVal(Integer rewardVal) {
        this.rewardVal = rewardVal;
    }

    public String getId() {
        return id;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getRewardVal() {
        return rewardVal;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPercent() {
        return percent;
    }
}
