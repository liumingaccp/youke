package youke.common.model.vo.param.cloudcode;

import net.sf.json.JSONArray;

import java.io.Serializable;

public class CloudCodeRuleSaveVo implements Serializable {
    private String begTime;
    private String endTime;
    private Integer isRandom;
    private Integer inOrder;
    private Integer dayTimes;
    private Integer totalTimes;
    private JSONArray qrcodes;

    public void setQrcodes(JSONArray qrcodes) {
        this.qrcodes = qrcodes;
    }

    public JSONArray getQrcodes() {
        return qrcodes;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setIsRandom(Integer isRandom) {
        this.isRandom = isRandom;
    }

    public void setInOrder(Integer inOrder) {
        this.inOrder = inOrder;
    }

    public void setDayTimes(Integer dayTimes) {
        this.dayTimes = dayTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }


    public String getBegTime() {
        return begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getIsRandom() {
        return isRandom;
    }

    public Integer getInOrder() {
        return inOrder;
    }

    public Integer getDayTimes() {
        return dayTimes;
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

}
