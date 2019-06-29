package youke.common.model.vo.param.cloudcode;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * 云码列表检索类
 */
public class CloudCodeQueryVo extends QueryObjectVO implements Serializable {
    private String title;
    private String begTime;
    private String endTime;
    private String remark;
    private Integer state;
    private String dykId;
    private String appId;

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return empty2null(title);
    }

    public String getEndTime() {
        return empty2null(endTime);
    }

    public String getRemark() {
        return empty2null(remark);
    }

    public Integer getState() {
        return state;
    }
}
