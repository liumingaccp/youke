package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TOpenOrder implements Serializable {
    private String oid;

    private String randomstr;

    private String title;

    private Integer num;

    private Integer marketprice;

    private Integer price;

    private Integer discount;

    private Integer totalyear;

    private Date serbegdate;

    private Date serenddate;

    private Integer totalprice;

    private Integer packageid;

    private Integer state;

    private Date createtime;

    private Date paytime;

    private String paytype;

    private Integer userid;

    private String youkeid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    public String getRandomstr() {
        return randomstr;
    }

    public void setRandomstr(String randomstr) {
        this.randomstr = randomstr == null ? null : randomstr.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(Integer marketprice) {
        this.marketprice = marketprice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotalyear() {
        return totalyear;
    }

    public void setTotalyear(Integer totalyear) {
        this.totalyear = totalyear;
    }

    public Date getSerbegdate() {
        return serbegdate;
    }

    public void setSerbegdate(Date serbegdate) {
        this.serbegdate = serbegdate;
    }

    public Date getSerenddate() {
        return serenddate;
    }

    public void setSerenddate(Date serenddate) {
        this.serenddate = serenddate;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getPackageid() {
        return packageid;
    }

    public void setPackageid(Integer packageid) {
        this.packageid = packageid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}