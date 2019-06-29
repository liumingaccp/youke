package youke.common.queue.message;

import java.io.Serializable;

public class WxExpireMessage implements Serializable {

    public WxExpireMessage(String openId, String mobile, String title, int day, String endtime) {
        this.openId = openId;
        this.mobile = mobile;
        this.title = title;
        this.day = day;
        this.endtime = endtime;
    }

    private String openId;
    private String mobile;
    private String title;
    private int day;
    private String endtime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
