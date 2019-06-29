package youke.common.queue.message;

import java.io.Serializable;

public class SysRechargeMessage implements Serializable {

    public SysRechargeMessage(String openId, String title, String type, Integer price) {
        this.openId = openId;
        this.title = title;
        this.type = type;
        this.price = price;
    }

    private String openId;
    private String title;
    private String type;
    private Integer price;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
