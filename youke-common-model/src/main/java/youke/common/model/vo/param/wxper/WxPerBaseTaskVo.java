package youke.common.model.vo.param.wxper;

import java.io.Serializable;
import java.util.Date;

public class WxPerBaseTaskVo implements Serializable {
    private Long taskId;
    private Long deviceId;
    private Integer taskType; //任务类型
    private Date begTime; //开始时间 yyy-MM-dd HH:mm:ss
    private Date endTime; //结束时间 yyy-MM-dd HH:mm:ss
    private Integer extType; //0每天 1每周 2单次
    private Integer startHour;  //开始小时
    private Integer startMinute; //开始分钟
    private Integer endHour; //结束小时
    private Integer endMinute; //结束分钟
    private String invokeDays; //星期
    private String youkeId;
    private int delay;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
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

    public void setInvokeDays(String invokeDays) {
        this.invokeDays = invokeDays;
    }

    public Integer getExtType() {
        return extType;
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

    public String getInvokeDays() {
        return invokeDays;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }


    public Long getTaskId() {
        return taskId;
    }

    public String getYoukeId() {
        return youkeId;
    }
}
