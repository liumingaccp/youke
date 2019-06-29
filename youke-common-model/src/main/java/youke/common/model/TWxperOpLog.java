package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxperOpLog implements Serializable {
    private Long id;

    private Integer optype;

    private String content;

    private Long taskid;

    private Integer state;

    private Date optime;

    private Date realoptime;

    private Date createtime;

    private Long deviceid;

    private Long wxaccountid;

    private Date updatetime;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOptype() {
        return optype;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public Date getRealoptime() {
        return realoptime;
    }

    public void setRealoptime(Date realoptime) {
        this.realoptime = realoptime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Long getWxaccountid() {
        return wxaccountid;
    }

    public void setWxaccountid(Long wxaccountid) {
        this.wxaccountid = wxaccountid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}