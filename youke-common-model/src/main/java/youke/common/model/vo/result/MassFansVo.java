package youke.common.model.vo.result;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:45
 */
public class MassFansVo  implements Serializable {
    private String openId;
    private String nickname;
    private String lastsendtime;
    private Integer monthTotal;

    private Integer state;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLastsendtime(String lastsendtime) {
        this.lastsendtime = lastsendtime;
    }

    public String getLastsendtime() {
        return lastsendtime;
    }

    public Integer getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(Integer monthTotal) {
        this.monthTotal = monthTotal;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
