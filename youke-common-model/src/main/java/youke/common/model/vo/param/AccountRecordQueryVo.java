package youke.common.model.vo.param;

/**
 * 账户交易记录
 */
public class AccountRecordQueryVo extends QueryObjectVO {
    private String begTime;   //开始时间
    private String endTime;   //结束时间
    private String dykId;     //店有客id

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
