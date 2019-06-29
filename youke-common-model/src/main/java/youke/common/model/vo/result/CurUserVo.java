package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class CurUserVo implements Serializable {
    private int id;                 //用户id
    private String username;        //用户名
    private String truename;        //真实姓名
    private String dykId;           //店有客id
    private String appId;           //appId
    private int appState;        //公众号状态
    private String appNickname;         //公众号昵称
    private int followSubscr;       //是否关注店有客公众号
    private String company;         //公司名称
    private int bindShopNum = 0;    //已绑定店铺数
    private int vip;                //套餐版本
    private int dykState;           //是否开通平台
    private Date dykEndTime;        //套餐过期时间
    private Integer role;

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public int getBindShopNum() {
        return bindShopNum;
    }

    public void setBindShopNum(int bindShopNum) {
        this.bindShopNum = bindShopNum;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip() {
        return vip;
    }

    public void setFollowSubscr(int followSubscr) {
        this.followSubscr = followSubscr;
    }

    public int getFollowSubscr() {
        return followSubscr;
    }

    public int getDykState() {
        return dykState;
    }

    public void setDykState(int dykState) {
        this.dykState = dykState;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public String getCompany() {
        return company;
    }

    public Date getDykEndTime() {
        return dykEndTime;
    }

    public void setDykEndTime(Date dykEndTime) {
        this.dykEndTime = dykEndTime;
    }

    public int getAppState() {
        return appState;
    }

    public void setAppState(int appState) {
        this.appState = appState;
    }

    public String getAppNickname() {
        return appNickname;
    }

    public void setAppNickname(String appNickname) {
        this.appNickname = appNickname;
    }
}
