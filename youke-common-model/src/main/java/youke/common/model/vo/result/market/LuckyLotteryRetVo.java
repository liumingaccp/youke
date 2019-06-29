package youke.common.model.vo.result.market;

import java.io.Serializable;

public class LuckyLotteryRetVo implements Serializable {
    private String id;              //奖品唯一Id
    private String prizeName;       //奖品名称
    private Integer rewardType;     //奖品类型
    private Integer integral;       //奖品积分
    private Integer money;          //奖品金额
    private Integer show;           //是否展示
    private String headImg;         //Logo
    private String nickName;        //商家名称

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

    public void setId(String id) {
        this.id = id;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getShow() {
        return show;
    }
}
