package youke.common.model.vo.result.market;

import java.io.Serializable;

public class BaseMarketActiveRetVo implements Serializable {
    private Integer show;       //是否展示弹出页面
    private Integer rewardType; //奖励类型
    private Integer money;      //金额
    private Integer integral;   //积分
    private String headImg;     //Logo
    private String nickName;    //商家名称

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getShow() {
        return show;
    }

    public Integer getRewardType() {
        return rewardType;
    }
}
