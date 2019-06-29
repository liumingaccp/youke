package youke.common.model.vo.result.wxper;

import java.io.Serializable;
import java.util.Date;

public class DeviceQueryRetVo implements Serializable {
    private Long id;
    private String deviceName;
    private String wxPersonal;
    private String remark;
    private Integer fansNum;
    private Integer state;
    private Date lastTime;
    private String recordDetail;

    public void setRecordDetail(String recordDetail) {
        this.recordDetail = recordDetail;
    }

    public String getRecordDetail() {
        return recordDetail;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setWxPersonal(String wxPersonal) {
        this.wxPersonal = wxPersonal;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getWxPersonal() {
        return wxPersonal;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public Date getLastTime() {
        return lastTime;
    }
}
