package youke.common.model.vo.param.wxper;

import java.io.Serializable;

public class DeviceSaveVo implements Serializable {
    private String imel;
    private String deviceName;
    private String remark;

    public void setImel(String imel) {
        this.imel = imel;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImel() {
        return imel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getRemark() {
        return remark;
    }
}
