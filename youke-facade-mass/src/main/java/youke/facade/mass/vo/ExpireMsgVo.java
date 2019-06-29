package youke.facade.mass.vo;

import java.io.Serializable;

public class ExpireMsgVo implements Serializable {

    public ExpireMsgVo(String mobile, int day,String endtime,String title) {
        this.mobile = mobile;
        this.day = day;
        this.endtime = endtime;
        this.title = title;
    }

    private String mobile;
    private int day;
    private String endtime;
    private String title;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
