package youke.common.model.vo.param.wxper;

import java.io.Serializable;

public class GrowTaskSaveVo implements Serializable {
    private Integer isOpenTask;
    private String type;
    private String deviceIds;
    private Integer period;
    private String invokeDays;
    private String youkeId;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;

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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setIsOpenTask(Integer isOpenTask) {
        this.isOpenTask = isOpenTask;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setInvokeDays(String invokeDays) {
        this.invokeDays = invokeDays;
    }

    public Integer getIsOpenTask() {
        return isOpenTask;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public Integer getPeriod() {
        return period;
    }

    public String getInvokeDays() {
        return invokeDays;
    }
}

