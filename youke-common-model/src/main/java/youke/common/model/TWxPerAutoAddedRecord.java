package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoAddedRecord implements Serializable {
    private Long id;

    private Long taskid;

    private Integer addnum;

    private Date addtime;

    private Integer type;

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

    public Integer getAddnum() {
        return addnum;
    }

    public void setAddnum(Integer addnum) {
        this.addnum = addnum;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}