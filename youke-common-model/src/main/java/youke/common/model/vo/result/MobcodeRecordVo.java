package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * 群发记录结果集
 */
public class MobcodeRecordVo implements Serializable {
    private static final long serialVersionUID = 7203253170504280132L;
    private Integer id;                                 //记录Id
    private String content;                             //短信内容
    private String label;                               //短信签名
    private Integer state;                              //任务状态
    private Integer sendType;                           //短信发送类型
    private Integer sendNum;                            //短信发送数量
    private Integer successNum;                         //短信发送成功数量
    private Integer costNum;                            //短信消耗数量:等同于发送成功数量
    private Date taskTime;                              //任务时间

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public void setCostNum(Integer costNum) {
        this.costNum = costNum;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getContent() {
        return getLabel() + content;
    }

    public Integer getState() {
        return state;
    }

    public Integer getSendType() {
        return sendType;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public Integer getCostNum() {
        return getSuccessNum();
    }

    public Date getTaskTime() {
        return taskTime;
    }
}
