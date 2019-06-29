package youke.facade.user.vo;

import java.io.Serializable;

public class LoginHistoryVo implements Serializable {
    private String ip;
    private String loginTime;
    private String hostName;

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public String getLoginTime() {
        return loginTime;
    }
}
