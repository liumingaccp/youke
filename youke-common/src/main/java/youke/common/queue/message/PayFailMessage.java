package youke.common.queue.message;

import java.io.Serializable;

public class PayFailMessage implements Serializable {

    public PayFailMessage(String mobile, int money, String reason) {
        this.mobile = mobile;
        this.money = money;
        this.reason = reason;
    }

    private String mobile;
    private int money;
    private String reason;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
