package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerMultipleRecord implements Serializable {
    private Long id;

    private Long taskid;

    private Long deviceid;

    private String wxpersonal;

    private Date createtime;

    private Date sendtime;

    private Integer sendnum;

    private String content;

    private Integer state;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public String getWxpersonal() {
        return wxpersonal;
    }

    public void setWxpersonal(String wxpersonal) {
        this.wxpersonal = wxpersonal == null ? null : wxpersonal.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getSendnum() {
        return sendnum;
    }

    public void setSendnum(Integer sendnum) {
        this.sendnum = sendnum;
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

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}