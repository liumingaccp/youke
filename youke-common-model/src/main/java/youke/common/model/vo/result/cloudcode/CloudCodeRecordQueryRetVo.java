package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;
import java.util.Date;

public class CloudCodeRecordQueryRetVo implements Serializable {
    private Long id;
    private String title;
    private String remark;
    private Integer scanTimes;
    private Date lastScanTime;
    private Integer state;

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setScanTimes(Integer scanTimes) {
        this.scanTimes = scanTimes;
    }

    public void setLastScanTime(Date lastScanTime) {
        this.lastScanTime = lastScanTime;
    }

    public Integer getScanTimes() {
        return scanTimes;
    }

    public Date getLastScanTime() {
        return lastScanTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRemark() {
        return remark;
    }
}
