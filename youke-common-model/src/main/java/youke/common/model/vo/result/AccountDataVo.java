package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 12:22
 */
public class AccountDataVo implements Serializable {
    private int accountMoney;
    private int accountMobCode;
    private int vip;
    private int openTotalDay;
    private Date endTime;
    private Date openTime;
    private int shopNum;
    private int vipShopNum;
    private int openLeftDay;
    private int shopLeftNum;

    public int getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(int accountMoney) {
        this.accountMoney = accountMoney;
    }

    public int getAccountMobCode() {
        return accountMobCode;
    }

    public void setAccountMobCode(int accountMobCode) {
        this.accountMobCode = accountMobCode;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getOpenTotalDay() {
        return pastDays(this.endTime,this.openTime);
    }

    public void setOpenTotalDay(int openTotalDay) {
        this.openTotalDay = openTotalDay;
    }

    public void setOpenLeftDay(int openLeftDay) {
        this.openLeftDay = openLeftDay;
    }

    public void setShopLeftNum(int shopLeftNum) {
        this.shopLeftNum = shopLeftNum;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getVipShopNum() {
        return vipShopNum;
    }

    public void setVipShopNum(int vipShopNum) {
        this.vipShopNum = vipShopNum;
    }

    public int getOpenLeftDay() {
        return pastDays(this.endTime,new Date());
    }

    public int getShopLeftNum() {
        return this.vipShopNum - this.shopNum;
    }

    public static int pastDays(Date date1, Date date2) {
        long t = date1.getTime()-date2.getTime();
        return (int)(t/(24*60*60*1000));
    }
}
