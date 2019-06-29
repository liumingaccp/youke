package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/5
 * Time: 11:45
 */
public class TaokeCountDataVo implements Serializable {
    private Integer backNum;
    private Integer totalBackMoney;
    private Integer totalCommision;

    public Integer getBackNum() {
        return backNum;
    }

    public void setBackNum(Integer backNum) {
        this.backNum = backNum;
    }

    public Integer getTotalBackMoney() {
        return totalBackMoney;
    }

    public void setTotalBackMoney(Integer totalBackMoney) {
        this.totalBackMoney = totalBackMoney;
    }

    public Integer getTotalCommision() {
        return totalCommision;
    }

    public void setTotalCommision(Integer totalCommision) {
        this.totalCommision = totalCommision;
    }
}
