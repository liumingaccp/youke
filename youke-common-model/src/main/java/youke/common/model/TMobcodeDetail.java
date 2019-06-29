package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMobcodeDetail implements Serializable {
    private Long id;

    private String mobile;

    private Date sendtime;

    private int textlen;

    private int costnum;

    private String content;

    private int state = -1;

    private String failreason;

    private Integer recordid;

    private Integer taskid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public void setTextlen(int textlen) {
        this.textlen = textlen;
    }

    public int getTextlen() {
        return textlen;
    }

    public void setCostnum(int costnum) {
        this.costnum = costnum;
    }

    public int getCostnum() {
        return costnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason == null ? null : failreason.trim();
    }

    public Integer getRecordid() {
        return recordid;
    }

    public void setRecordid(Integer recordid) {
        this.recordid = recordid;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    @Override
    public String toString() {
        return "TMobcodeDetail{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", sendtime=" + sendtime +
                ", textlen=" + textlen +
                ", costnum=" + costnum +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", failreason='" + failreason + '\'' +
                ", recordid=" + recordid +
                ", taskid=" + taskid +
                ", youkeid='" + youkeid + '\'' +
                '}';
    }
}