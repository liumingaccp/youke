package youke.common.model.vo.result.wxper;

import java.io.Serializable;
import java.util.Date;

public class AutoLikeTaskAuditRetVo implements Serializable {
    private Long id;
    private Integer isOpenTask;
    private String deviceName;
    private Integer type;
    private Date begTime;
    private Date endTime;
    private Integer likeNum;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getBegTime() {
        return begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsOpenTask(Integer isOpenTask) {
        this.isOpenTask = isOpenTask;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Integer getIsOpenTask() {
        return isOpenTask;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Integer getType() {
        return type;
    }
}
