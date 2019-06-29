package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员推广表
 * @author liuming
 */
public class TUserSpread implements Serializable {

    /**
     * 推广Id
     */
    @Id
    private String id;
    /**
     * 会员标识 Br, Bb, T, C
     */
    private String flag;
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 角色  0 管理员，1子账户
     */
    private int role;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 支付宝账户
     */
    private String alipayAccount;
    /**
     * 支付宝姓名
     */
    private String alipayName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 父级别Id
     */
    private String parentId;
    /**
     * 父级会员标识
     */
    private String parentFlag;
    /**
     * 可提现余额
     */
    private int balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentFlag() {
        return parentFlag;
    }

    public void setParentFlag(String parentFlag) {
        this.parentFlag = parentFlag;
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
