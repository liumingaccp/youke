package youke.common.model;

import java.util.Date;

public class TUser {
    private Integer id;

    private String mobile;

    private String password;

    private String truename;

    private String youkeid;

    private Integer role;

    private String qq;

    private String wechat;

    private String email;

    private Integer followsubscr;

    private String followopenid;

    private String loginip;

    private String logindevice;

    private Date logintime;

    private Date createtime;

    private Integer state;

    private Date exptime;

    public void setExptime(Date exptime) {
        this.exptime = exptime;
    }

    public Date getExptime() {
        return exptime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getFollowsubscr() {
        return followsubscr;
    }

    public void setFollowsubscr(Integer followsubscr) {
        this.followsubscr = followsubscr;
    }

    public String getFollowopenid() {
        return followopenid;
    }

    public void setFollowopenid(String followopenid) {
        this.followopenid = followopenid == null ? null : followopenid.trim();
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public String getLogindevice() {
        return logindevice;
    }

    public void setLogindevice(String logindevice) {
        this.logindevice = logindevice == null ? null : logindevice.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}