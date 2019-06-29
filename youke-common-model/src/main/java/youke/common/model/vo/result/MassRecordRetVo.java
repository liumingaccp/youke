package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class MassRecordRetVo implements Serializable {
    private Integer id;
    private Date taskTime;
    private Integer sendType;
    private String content;
    private Integer state;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public Integer getSendType() {
        return sendType;
    }

    public String getContent() {
        return content;
    }

    public Integer getState() {
        return state;
    }
}
