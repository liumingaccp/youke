package youke.common.queue.message;

import java.io.Serializable;

public class SysPayFailMessage implements Serializable {

    public SysPayFailMessage(String openId, int money, String reason) {
        this.openId = openId;
        this.money = money;
        this.reason = reason;
    }

    private String openId;
    private int money;
    private String reason;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
