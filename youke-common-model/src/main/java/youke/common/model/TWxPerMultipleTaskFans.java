package youke.common.model;

import java.io.Serializable;

public class TWxPerMultipleTaskFans implements Serializable {
    private Long id;

    private Long taskid;

    private Long fanid;

    private Integer state;

    private Long wxAccountid;

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

    public Long getFanid() {
        return fanid;
    }

    public void setFanid(Long fanid) {
        this.fanid = fanid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getWxAccountid() {
        return wxAccountid;
    }

    public void setWxAccountid(Long wxAccountid) {
        this.wxAccountid = wxAccountid;
    }
}