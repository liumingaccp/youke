package youke.common.model.vo.param.wxper;

import java.io.Serializable;

public class AutoLikeTaskAuditVo implements Serializable {
    private String ids;
    private Integer isOpenTask;
    private Integer type;
    private String begTime;
    private String endTime;
    private Integer likeNum;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
    private String youkeId;

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setIsOpenTask(Integer isOpenTask) {
        this.isOpenTask = isOpenTask;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public String getIds() {
        return ids;
    }

    public Integer getIsOpenTask() {
        return isOpenTask;
    }

    public Integer getType() {
        return type;
    }

    public String getBegTime() {
        return begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getLikeNum() {
        return likeNum;
    }
}
