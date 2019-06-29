package youke.order.common.model;

import java.io.Serializable;
import java.util.Date;

public class TShopOrder implements Serializable {
    private Long id;

    private String goodid;

    private String title;

    private String picpath;

    private String orderno;

    private Integer shopid;

    private Integer shoptype;

    private String buyername;

    private String receivename;

    private String receivestate;

    private String receivemobile;

    private String receiveaddress;

    private String receiverzip;

    private Integer num;

    private Integer price;

    private Integer totalprice;

    private Integer buyerrate;

    private Date paytime;

    private Date endtime;

    private Integer state;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodid() {
        return goodid;
    }

    public void setGoodid(String goodid) {
        this.goodid = goodid == null ? null : goodid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getShoptype() {
        return shoptype;
    }

    public void setShoptype(Integer shoptype) {
        this.shoptype = shoptype;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename == null ? null : receivename.trim();
    }

    public String getReceivestate() {
        return receivestate;
    }

    public void setReceivestate(String receivestate) {
        this.receivestate = receivestate == null ? null : receivestate.trim();
    }

    public String getReceivemobile() {
        return receivemobile;
    }

    public void setReceivemobile(String receivemobile) {
        this.receivemobile = receivemobile == null ? null : receivemobile.trim();
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress == null ? null : receiveaddress.trim();
    }

    public String getReceiverzip() {
        return receiverzip;
    }

    public void setReceiverzip(String receiverzip) {
        this.receiverzip = receiverzip == null ? null : receiverzip.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getBuyerrate() {
        return buyerrate;
    }

    public void setBuyerrate(Integer buyerrate) {
        this.buyerrate = buyerrate;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}