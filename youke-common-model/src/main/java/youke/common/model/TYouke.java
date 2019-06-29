package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TYouke implements Serializable {
    private String id;

    private String country;

    private String province;

    private String city;

    private String company;

    private String address;

    private String website;

    private String shopurl;

    private String linktruename;

    private String linkposition;

    private String linkqq;

    private String linktel;

    private Date opentime;

    private Date endtime;

    private Integer vip;

    private Integer money;

    private Integer extshopnum;

    private Date createtime;

    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getShopurl() {
        return shopurl;
    }

    public void setShopurl(String shopurl) {
        this.shopurl = shopurl == null ? null : shopurl.trim();
    }

    public String getLinktruename() {
        return linktruename;
    }

    public void setLinktruename(String linktruename) {
        this.linktruename = linktruename == null ? null : linktruename.trim();
    }

    public String getLinkposition() {
        return linkposition;
    }

    public void setLinkposition(String linkposition) {
        this.linkposition = linkposition == null ? null : linkposition.trim();
    }

    public String getLinkqq() {
        return linkqq;
    }

    public void setLinkqq(String linkqq) {
        this.linkqq = linkqq == null ? null : linkqq.trim();
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel == null ? null : linktel.trim();
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getExtshopnum() {
        return extshopnum;
    }

    public void setExtshopnum(Integer extshopnum) {
        this.extshopnum = extshopnum;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}