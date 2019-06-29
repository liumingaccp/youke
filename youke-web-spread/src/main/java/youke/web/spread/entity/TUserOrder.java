package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员支付订单表
 * @author liuming
 */
public class TUserOrder implements Serializable {
    /**
     * 流水
     */
    @Id
    private String id;
    /**
     * 用户Id
     */
    private int userId;

    private int vip;

    private String mobile;

    /**
     * 付款录入时间
     */
    private Date createTime;
    /**
     * 版本金额
     */
    private int marketMoney;
    /**
     * 实付金额
     */
    private int money;

    private String parentId;
    /**
     * 0未处理，1已处理
     */
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getMarketMoney() {
        return marketMoney;
    }

    public void setMarketMoney(int marketMoney) {
        this.marketMoney = marketMoney;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
