package youke.common.model.vo.param;

/**
 * 群发
 */
public class MassSMSQueryVo extends QueryObjectVO {
    private String dykId;               //店有客id

    private String keyword;             //关键字
    private Integer type;               //任务类型
    private Integer role;               //任务分类

    private Integer state;              //任务状态
    private String taskTimeBeg;         //任务时间开始
    private String taskTimeEnd;         //任务时间结束

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setTaskTimeBeg(String taskTimeBeg) {
        this.taskTimeBeg = taskTimeBeg;
    }

    public void setTaskTimeEnd(String taskTimeEnd) {
        this.taskTimeEnd = taskTimeEnd;
    }

    public String getTaskTimeBeg() {
        return empty2null(taskTimeBeg);
    }

    public String getTaskTimeEnd() {
        return empty2null(taskTimeEnd);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDykId() {
        return dykId;
    }

    public String getKeyword() {
        return empty2null(keyword);
    }

    public Integer getType() {
        return type;
    }

    public Integer getRole() {
        return role;
    }

    public Integer getState() {
        return state;
    }

}
