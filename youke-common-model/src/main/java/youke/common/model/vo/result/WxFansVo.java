package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 19:55
 */
public class WxFansVo  implements Serializable {
    private String openid;

    private String nickname;

    private String truename;

    private String mobile;

    private Integer sex;

    private String headimgurl;

    private String city;

    private String province;

    private String country;

    private String language;

    private Integer integral;

    private Integer substate;

    private String remark;

    private String lastTime;

    private Date subTime;

    private List<TagVo> tags = new ArrayList<TagVo>();

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        this.headimgurl = headimgurl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getSubstate() {
        return substate;
    }

    public void setSubstate(Integer substate) {
        this.substate = substate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<TagVo> getTags() {
        return tags;
    }

    public void setTags(List<TagVo> tags) {
        this.tags = tags;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public Date getSubTime() {
        return subTime;
    }
}
