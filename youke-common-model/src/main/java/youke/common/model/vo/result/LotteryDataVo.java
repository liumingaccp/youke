package youke.common.model.vo.result;

import youke.common.model.vo.param.PrizeVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LotteryDataVo implements Serializable {
    private Integer time;                               //剩余抽奖次数
    private Integer costIntegral;                       //单次抽奖消耗积分
    private Integer integral;                           //当前剩余积分
    private List<PrizeVo> prizeObj = new ArrayList<>(); //奖品

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setCostIntegral(Integer costIntegral) {
        this.costIntegral = costIntegral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public void setPrizeObj(List<PrizeVo> prizeObj) {
        this.prizeObj = prizeObj;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getCostIntegral() {
        return costIntegral;
    }

    public Integer getIntegral() {
        return integral;
    }

    public List<PrizeVo> getPrizeObj() {
        return prizeObj;
    }
}
