package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class AdminAccountVo implements Serializable {
    private static final long serialVersionUID = -4421152761100499724L;
    private String dykId;           //店有客id
    private String company;         //公司名称
    private String address;         //公司地址
    private String webSite;         //公司网址
    private String shopUrl;         //店铺地址
    private String linkTruename;    //联系人姓名
    private String linkPosition;    //联系人职位
    private String linkQQ;          //联系人QQ
    private String linkTel;         //联系人电话
    private Date openTime;          //开通时间
    private Date createTime;        //注册时间
    private Date endTime;           //会员到期时间
    private int vip;                //是否是vip
    private int state;              //状态

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public void setLinkTruename(String linkTruename) {
        this.linkTruename = linkTruename;
    }

    public void setLinkPosition(String linkPosition) {
        this.linkPosition = linkPosition;
    }

    public void setLinkQQ(String linkQQ) {
        this.linkQQ = linkQQ;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDykId() {
        return dykId;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public String getLinkTruename() {
        return linkTruename;
    }

    public String getLinkPosition() {
        return linkPosition;
    }

    public String getLinkQQ() {
        return linkQQ;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getVip() {
        return vip;
    }

    public int getState() {
        return state;
    }
}
