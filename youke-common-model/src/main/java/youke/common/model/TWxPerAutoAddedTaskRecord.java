package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoAddedTaskRecord implements Serializable {
    private Long id;

    private Long taskid;

    private String tasksource;

    private Integer state;

    private Date createtime;

    private Integer expectexecutenum;

    private Integer alreadyexecutenum;

    private Integer successnum;

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

    public String getTasksource() {
        return tasksource;
    }

    public void setTasksource(String tasksource) {
        this.tasksource = tasksource == null ? null : tasksource.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getExpectexecutenum() {
        return expectexecutenum;
    }

    public void setExpectexecutenum(Integer expectexecutenum) {
        this.expectexecutenum = expectexecutenum;
    }

    public Integer getAlreadyexecutenum() {
        return alreadyexecutenum;
    }

    public void setAlreadyexecutenum(Integer alreadyexecutenum) {
        this.alreadyexecutenum = alreadyexecutenum;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}