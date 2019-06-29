package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMarketActiveRecord implements Serializable {
    private Long id;

    private Long activeid;

    private String openid;

    private String wxfansname;

    private String shopfansname;

    private Integer rewardtype;

    private Integer money;

    private Integer integral;

    private Integer state;

    private String failreason;

    private String orderno;

    private String usertext;

    private String userpics;

    private Date createtime;

    private Integer examinetype;

    private String examineuserid;

    private String examinename;

    private String appid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiveid() {
        return activeid;
    }

    public void setActiveid(Long activeid) {
        this.activeid = activeid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getWxfansname() {
        return wxfansname;
    }

    public void setWxfansname(String wxfansname) {
        this.wxfansname = wxfansname == null ? null : wxfansname.trim();
    }

    public String getShopfansname() {
        return shopfansname;
    }

    public void setShopfansname(String shopfansname) {
        this.shopfansname = shopfansname == null ? null : shopfansname.trim();
    }

    public Integer getRewardtype() {
        return rewardtype;
    }

    public void setRewardtype(Integer rewardtype) {
        this.rewardtype = rewardtype;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason == null ? null : failreason.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getUsertext() {
        return usertext;
    }

    public void setUsertext(String usertext) {
        this.usertext = usertext == null ? null : usertext.trim();
    }

    public String getUserpics() {
        return userpics;
    }

    public void setUserpics(String userpics) {
        this.userpics = userpics == null ? null : userpics.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getExaminetype() {
        return examinetype;
    }

    public void setExaminetype(Integer examinetype) {
        this.examinetype = examinetype;
    }

    public String getExamineuserid() {
        return examineuserid;
    }

    public void setExamineuserid(String examineuserid) {
        this.examineuserid = examineuserid == null ? null : examineuserid.trim();
    }

    public String getExaminename() {
        return examinename;
    }

    public void setExaminename(String examinename) {
        this.examinename = examinename == null ? null : examinename.trim();
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
}