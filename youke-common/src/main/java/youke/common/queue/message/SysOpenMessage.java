package youke.common.queue.message;

import java.io.Serializable;

public class SysOpenMessage implements Serializable {

    public SysOpenMessage(String openId, Integer vip, Integer price, String title, String endTime) {
        this.openId = openId;
        this.vip = vip;
        this.price = price;
        this.title = title;
        this.endTime = endTime;
    }

    private String openId;
    private Integer vip;
    private Integer price;
    private String title;
    private String endTime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
