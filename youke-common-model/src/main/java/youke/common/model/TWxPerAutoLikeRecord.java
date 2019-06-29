package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoLikeRecord implements Serializable {
    private Long id;

    private Long taskid;

    private Date likedate;

    private Integer likenum;

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

    public void setLikedate(Date likedate) {
        this.likedate = likedate;
    }

    public Date getLikedate() {
        return likedate;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}