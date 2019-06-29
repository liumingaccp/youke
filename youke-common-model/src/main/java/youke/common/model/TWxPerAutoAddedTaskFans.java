package youke.common.model;

import java.io.Serializable;

public class TWxPerAutoAddedTaskFans implements Serializable{
    private Long id;

    private Long taskid;

    private String mobile;

    private Integer state;

    private Integer priority;

    private String name;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}