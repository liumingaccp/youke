package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerFans implements Serializable {
    private Long id;

    private String mobile;

    private String nickname;

    private String wechatnum;

    private String ownwechatnum;

    private String wxpersonal;

    private Integer substate;

    private Integer integral;

    private String comefrom;

    private String name;

    private String accountname;

    private Date lasttradetime;

    private Integer sex;

    private String adress;

    private String remark;

    private String lastactive;

    private Integer state;

    private Integer shopid;

    private Long wxAccountid;

    private Date createtime;

    private String appid;

    private String youkeid;

    public void setOwnwechatnum(String ownwechatnum) {
        this.ownwechatnum = ownwechatnum;
    }

    public String getOwnwechatnum() {
        return ownwechatnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getWechatnum() {
        return wechatnum;
    }

    public void setWechatnum(String wechatnum) {
        this.wechatnum = wechatnum == null ? null : wechatnum.trim();
    }

    public String getWxpersonal() {
        return wxpersonal;
    }

    public void setWxpersonal(String wxpersonal) {
        this.wxpersonal = wxpersonal == null ? null : wxpersonal.trim();
    }

    public Integer getSubstate() {
        return substate;
    }

    public void setSubstate(Integer substate) {
        this.substate = substate;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname == null ? null : accountname.trim();
    }

    public Date getLasttradetime() {
        return lasttradetime;
    }

    public void setLasttradetime(Date lasttradetime) {
        this.lasttradetime = lasttradetime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLastactive() {
        return lastactive;
    }

    public void setLastactive(String lastactive) {
        this.lastactive = lastactive == null ? null : lastactive.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public void setWxAccountid(Long wxAccountid) {
        this.wxAccountid = wxAccountid;
    }

    public Long getWxAccountid() {
        return wxAccountid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }
}