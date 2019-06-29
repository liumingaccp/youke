package youke.common.model.vo.param;

import java.io.Serializable;

public class SubAccountLoginRecordQueryVo extends QueryObjectVO implements Serializable {
    private String mobile;
    private String begTime;
    private String endTime;
    private String dykId;

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return empty2null(dykId);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMobile() {
        return empty2null(mobile);
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public String getEndTime() {
        return empty2null(endTime);
    }
}
