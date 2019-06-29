package youke.common.model.vo.result.wxper;

import java.io.Serializable;

public class DeviceDetailRetVo implements Serializable {
    private String imel;
    private String phoneModel;

    public void setImel(String imel) {
        this.imel = imel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getImel() {
        return imel;
    }

    public String getPhoneModel() {
        return phoneModel;
    }
}
