package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMassRecord implements Serializable {
    private Integer id;

    private String thumburl;

    private String title;

    private Integer sendtype;

    private Date tasktime;

    private Date overtime;

    private Integer sendnum;

    private Integer successnum;

    private Integer failnum;

    private Integer state;

    private Integer taskid;

    private String failRearon;

    private String appId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl == null ? null : thumburl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getSendtype() {
        return sendtype;
    }

    public void setSendtype(Integer sendtype) {
        this.sendtype = sendtype;
    }

    public Date getTasktime() {
        return tasktime;
    }

    public void setTasktime(Date tasktime) {
        this.tasktime = tasktime;
    }

    public Date getOvertime() {
        return overtime;
    }

    public void setOvertime(Date overtime) {
        this.overtime = overtime;
    }

    public Integer getSendnum() {
        return sendnum;
    }

    public void setSendnum(Integer sendnum) {
        this.sendnum = sendnum;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public Integer getFailnum() {
        return failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getFailRearon() {
        return failRearon;
    }

    public void setFailRearon(String failRearon) {
        this.failRearon = failRearon;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}