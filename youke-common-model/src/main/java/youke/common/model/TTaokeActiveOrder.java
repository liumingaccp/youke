package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TTaokeActiveOrder implements Serializable {
    private Long id;

    private String orderno;

    private String title;

    private String buyeropenid;

    private String buyername;

    private String buyerface;

    private String buyermobile;

    private String taokeopenid;

    private String taokename;

    private String taokeface;

    private String taokemobile;

    private Date createtime;

    private Integer totalprice;

    private Integer totalnum;

    private Integer backmoney;

    private Integer commision;

    private Date ordertime;

    private Date backtime;

    private Integer state;

    private Integer activeid;

    private String shopid;

    private Integer shoptype;

    private String appid;

    private String youkeid;

    private String failreason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBuyeropenid() {
        return buyeropenid;
    }

    public void setBuyeropenid(String buyeropenid) {
        this.buyeropenid = buyeropenid == null ? null : buyeropenid.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getBuyerface() {
        return buyerface;
    }

    public void setBuyerface(String buyerface) {
        this.buyerface = buyerface == null ? null : buyerface.trim();
    }

    public String getBuyermobile() {
        return buyermobile;
    }

    public void setBuyermobile(String buyermobile) {
        this.buyermobile = buyermobile == null ? null : buyermobile.trim();
    }

    public String getTaokeopenid() {
        return taokeopenid;
    }

    public void setTaokeopenid(String taokeopenid) {
        this.taokeopenid = taokeopenid == null ? null : taokeopenid.trim();
    }

    public String getTaokename() {
        return taokename;
    }

    public void setTaokename(String taokename) {
        this.taokename = taokename == null ? null : taokename.trim();
    }

    public String getTaokeface() {
        return taokeface;
    }

    public void setTaokeface(String taokeface) {
        this.taokeface = taokeface == null ? null : taokeface.trim();
    }

    public String getTaokemobile() {
        return taokemobile;
    }

    public void setTaokemobile(String taokemobile) {
        this.taokemobile = taokemobile == null ? null : taokemobile.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getBackmoney() {
        return backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Date getBacktime() {
        return backtime;
    }

    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getActiveid() {
        return activeid;
    }

    public void setActiveid(Integer activeid) {
        this.activeid = activeid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }

    public Integer getShoptype() {
        return shoptype;
    }

    public void setShoptype(Integer shoptype) {
        this.shoptype = shoptype;
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

    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }

    public String getFailreason() {
        return failreason;
    }
}