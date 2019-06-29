package youke.common.model.vo.param.cloudcode;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * 扫码记录检索类
 */
public class CloudCodeRecordQueryVo extends QueryObjectVO implements Serializable {
    private String title;
    private String scanTimeBeg;
    private String scanTimeEnd;
    private String remark;
    private Integer numBeg;
    private Integer numEnd;
    private String appId;
    private String dykId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScanTimeBeg(String scanTimeBeg) {
        this.scanTimeBeg = scanTimeBeg;
    }

    public void setScanTimeEnd(String scanTimeEnd) {
        this.scanTimeEnd = scanTimeEnd;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setNumBeg(Integer numBeg) {
        this.numBeg = numBeg;
    }

    public void setNumEnd(Integer numEnd) {
        this.numEnd = numEnd;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getTitle() {
        return empty2null(title);
    }

    public String getScanTimeBeg() {
        return empty2null(scanTimeBeg);
    }

    public String getScanTimeEnd() {
        return empty2null(scanTimeEnd);
    }

    public String getRemark() {
        return empty2null(remark);
    }

    public Integer getNumBeg() {
        return numBeg;
    }

    public Integer getNumEnd() {
        return numEnd;
    }

    public String getAppId() {
        return appId;
    }

    public String getDykId() {
        return dykId;
    }
}
