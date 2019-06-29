package youke.common.model.vo.param;

public class MassQueryVo extends QueryObjectVO {
    private String begTime;
    private String endTime;
    private String dykId;

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public String getEndTime() {
        return empty2null(endTime);
    }
}
