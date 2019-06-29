package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMassFans implements Serializable {

    private String openid;

    private Date lastsendtime;

    private Integer monthtotal;

    private String nickname;

    private String appId;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getLastsendtime() {
        return lastsendtime;
    }

    public void setLastsendtime(Date lastsendtime) {
        this.lastsendtime = lastsendtime;
    }

    public Integer getMonthtotal() {
        return monthtotal;
    }

    public void setMonthtotal(Integer monthtotal) {
        this.monthtotal = monthtotal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}