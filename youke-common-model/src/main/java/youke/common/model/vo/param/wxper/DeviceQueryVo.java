package youke.common.model.vo.param.wxper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

public class DeviceQueryVo extends QueryObjectVO implements Serializable {
    private String deviceName;      //设备名称
    private String wxPersonal;      //个人号名称
    private String remark;          //备注
    private Integer state;          //状态
    private String youkeId;

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setWxPersonal(String wxPersonal) {
        this.wxPersonal = wxPersonal;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getDeviceName() {
        return empty2null(deviceName);
    }

    public String getWxPersonal() {
        return empty2null(wxPersonal);
    }

    public String getRemark() {
        return empty2null(remark);
    }

    public Integer getState() {
        return state;
    }

    public String getYoukeId() {
        return youkeId;
    }
}
