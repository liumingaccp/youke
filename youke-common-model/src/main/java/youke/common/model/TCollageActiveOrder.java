package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCollageActiveOrder implements Serializable {
    private Long id;

    private String title;

    private String orderno;

    private String wxfansname;

    private String buyername;

    private Integer price;

    private Integer tuanprice;

    private Date ordertime;

    private Date backtime;

    private Date jointime;

    private Integer backmoney;

    private Integer state;

    private Integer activeid;

    private Long tuanid;

    private String openid;

    private String appid;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getWxfansname() {
        return wxfansname;
    }

    public void setWxfansname(String wxfansname) {
        this.wxfansname = wxfansname == null ? null : wxfansname.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTuanprice() {
        return tuanprice;
    }

    public void setTuanprice(Integer tuanprice) {
        this.tuanprice = tuanprice;
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

    public Date getJointime() {
        return jointime;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    public Integer getBackmoney() {
        return backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
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

    public Long getTuanid() {
        return tuanid;
    }

    public void setTuanid(Long tuanid) {
        this.tuanid = tuanid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
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