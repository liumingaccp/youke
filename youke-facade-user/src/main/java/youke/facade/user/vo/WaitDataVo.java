package youke.facade.user.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:42
 */
public class WaitDataVo implements Serializable{
    private Integer ingActiveNum;       //进行中营销活动
    private Integer waitActiveNum;      //待上线营销活动
    private Integer waitCheckNum;       //活动待审核用户数

    public Integer getIngActiveNum() {
        return ingActiveNum;
    }

    public void setIngActiveNum(Integer ingActiveNum) {
        this.ingActiveNum = ingActiveNum;
    }

    public Integer getWaitActiveNum() {
        return waitActiveNum;
    }

    public void setWaitActiveNum(Integer waitActiveNum) {
        this.waitActiveNum = waitActiveNum;
    }

    public Integer getWaitCheckNum() {
        return waitCheckNum;
    }

    public void setWaitCheckNum(Integer waitCheckNum) {
        this.waitCheckNum = waitCheckNum;
    }
}
