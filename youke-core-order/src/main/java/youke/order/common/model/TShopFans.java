package youke.order.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TShopFans implements Serializable {
    private Long id;

    private String nickname;

    private String truename;

    private String mobile;

    private String email;

    private Integer sex;

    private String headimgurl;

    private String city;

    private String province;

    private String country;

    private String language;

    private Integer integral;

    private Integer experience;

    private String unionid;

    private Integer loginCount;

    private String regip;

    private Date regtime;

    private Date lasttime;

    private Integer state;

    private Double dealtotal;

    private Double avgdealtotal;

    private Integer dealnum;

    private String remark;

    private Integer comefrom;

    private Integer shopid;

    private String youkeid;

    private TShop shop;

    private List<TTag> tags = new ArrayList<TTag>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip == null ? null : regip.trim();
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getDealtotal() {
        return dealtotal;
    }

    public void setDealtotal(Double dealtotal) {
        this.dealtotal = dealtotal;
    }

    public Double getAvgdealtotal() {
        return avgdealtotal;
    }

    public void setAvgdealtotal(Double avgdealtotal) {
        this.avgdealtotal = avgdealtotal;
    }

    public Integer getDealnum() {
        return dealnum;
    }

    public void setDealnum(Integer dealnum) {
        this.dealnum = dealnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getComefrom() {
        return comefrom;
    }

    public void setComefrom(Integer comefrom) {
        this.comefrom = comefrom;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid;
    }

    public TShop getShop() {
        return shop;
    }

    public void setShop(TShop shop) {
        this.shop = shop;
    }

    public List<TTag> getTags() {
        return tags;
    }

    public void setTags(List<TTag> tags) {
        this.tags = tags;
    }
}