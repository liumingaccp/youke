package youke.common.model.vo.result;

import java.io.Serializable;

public class IntegralSetVo implements Serializable {

    private int integralRate;

    private String integralExp;

    private int openBackIntegral;

    private int numForMoney;

    private int numForIntegral;

    public int getIntegralRate() {
        return integralRate;
    }

    public void setIntegralRate(int integralRate) {
        this.integralRate = integralRate;
    }

    public String getIntegralExp() {
        return integralExp;
    }

    public void setIntegralExp(String integralExp) {
        this.integralExp = integralExp;
    }

    public int getOpenBackIntegral() {
        return openBackIntegral;
    }

    public void setOpenBackIntegral(int openBackIntegral) {
        this.openBackIntegral = openBackIntegral;
    }

    public int getNumForMoney() {
        return numForMoney;
    }

    public void setNumForMoney(int numForMoney) {
        this.numForMoney = numForMoney;
    }

    public int getNumForIntegral() {
        return numForIntegral;
    }

    public void setNumForIntegral(int numForIntegral) {
        this.numForIntegral = numForIntegral;
    }
}
