package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloudCodeRuleAuditRetVo implements Serializable {
    private String begTime;
    private String endTime;
    private Integer isRandom;
    private Integer inOrder;
    private Integer dayTimes;
    private Integer totalTimes;
    private Long timeBeg;
    private Long timeEnd;
    private List<CloudCodeQrCodeAuditRetVo> qrcodes = new ArrayList<>();

    public void setTimeBeg(Long timeBeg) {
        this.timeBeg = timeBeg;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Long getTimeBeg() {
        return timeBeg;
    }

    public Long getTimeEnd() {
        return timeEnd;
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

    public void setQrcodes(List<CloudCodeQrCodeAuditRetVo> qrcodes) {
        this.qrcodes = qrcodes;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public List<CloudCodeQrCodeAuditRetVo> getQrcodes() {
        return qrcodes;
    }
}
