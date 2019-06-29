package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class SignInDataVo implements Serializable {
    private Integer runDay;         //连续签到天数
    private Date lastDate;          //上次签到日期
    private Integer integral;       //当前用户总积分

    public void setRunDay(Integer runDay) {
        this.runDay = runDay;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getRunDay() {
        return runDay;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public Integer getIntegral() {
        return integral;
    }
}
