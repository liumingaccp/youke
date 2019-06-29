package youke.common.model.vo.result.wxper;

import java.io.Serializable;

public class DeviceSelectRetVo implements Serializable {
    private Long id;
    private String deviceName;
    private Integer state;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Integer getState() {
        return state;
    }
}
