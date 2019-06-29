package youke.common.model;

import java.io.Serializable;

public class TWxPerAccountDevice implements Serializable {
    private Long id;

    private Long wxaccountid;

    private Long deviceid;

    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWxaccountid() {
        return wxaccountid;
    }

    public void setWxaccountid(Long wxaccountid) {
        this.wxaccountid = wxaccountid;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}