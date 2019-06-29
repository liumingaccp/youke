package youke.common.model.vo.param;

/**
 * 群发记录明细
 */
public class MassRecordDetailQueryVo extends QueryObjectVO {
    private static final long serialVersionUID = -8456041514203766287L;
    private int state = -1;         //状态
    private String mobile;          //手机号
    private Integer recordId;       //记录id
    private String dykId;           //店有客id

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public int getState() {
        return state;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getRecordId() {
        return recordId;
    }
}
