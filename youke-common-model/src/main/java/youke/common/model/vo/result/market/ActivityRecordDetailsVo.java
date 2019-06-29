package youke.common.model.vo.result.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户活动记录明细
 */
public class ActivityRecordDetailsVo implements Serializable {
    private Long id;                //记录id
    private Long activeId;          //活动id
    private String title;           //活动标题
    private Integer type;           //活动类型
    private Integer rewardType;     //奖励类型
    private Integer money;          //金额
    private Integer integral;       //积分
    private Integer state;          //状态
    private Date createTime;        //参与时间
    private String orderno;         //订单号
    private String userPics;        //用户提交截图

    public void setId(Long id) {
        this.id = id;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUserPics(String userPics) {
        this.userPics = userPics;
    }

    public Long getId() {
        return id;
    }

    public Long getActiveId() {
        return activeId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getType() {
        return type;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getState() {
        return state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUserPics() {
        return userPics;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderno() {
        return orderno;
    }
}
