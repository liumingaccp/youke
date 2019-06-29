package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员佣金流水表
 * @author liuming
 */
public class TUserCommission implements Serializable {
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
    /**
     * 佣金比例
     */
    private String commissionScale;
    /**
     * 佣金推广Id
     */
    private String commissionId;
    /**
     * 佣金推广标识
     */
    private String commissionFlag;
    /**
     * 佣金推广手机号
     */
    private String commissionMobile;
    /**
     * 佣金推广注册时间
     */
    private Date commissionRegisterTime;
    /**
     * 佣金
     */
    private int commission;


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

    public String getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
    }

    public String getCommissionFlag() {
        return commissionFlag;
    }

    public void setCommissionFlag(String commissionFlag) {
        this.commissionFlag = commissionFlag;
    }

    public String getCommissionScale() {
        return commissionScale;
    }

    public void setCommissionScale(String commissionScale) {
        this.commissionScale = commissionScale;
    }

    public String getCommissionMobile() {
        return commissionMobile;
    }

    public void setCommissionMobile(String commissionMobile) {
        this.commissionMobile = commissionMobile;
    }

    public Date getCommissionRegisterTime() {
        return commissionRegisterTime;
    }

    public void setCommissionRegisterTime(Date commissionRegisterTime) {
        this.commissionRegisterTime = commissionRegisterTime;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
}
