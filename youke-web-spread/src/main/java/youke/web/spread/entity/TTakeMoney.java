package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 提现表
 * @author liuming
 */
public class TTakeMoney implements Serializable {
    /**
     * 流水
     */
    @Id
    private String id;

    private String spreadId;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 标识
     */
    private String flag;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 提现金额
     */
    private int takeMoney;

    private String mobile;

    private String alipayAccount;

    private String alipayName;

    /**
     * 当时余额
     */
    private int balance;
    /**
     * 提现方式  0提现支付宝
     */
    private int type;

    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 处理时间
     */
    private Date doTime;
    /**
     * 状态 0处理中，1提现成功，2提现失败
     */
    private int state;
    /**
     * 失败原因
     */
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpreadId() {
        return spreadId;
    }

    public void setSpreadId(String spreadId) {
        this.spreadId = spreadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getTakeMoney() {
        return takeMoney;
    }

    public void setTakeMoney(int takeMoney) {
        this.takeMoney = takeMoney;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
