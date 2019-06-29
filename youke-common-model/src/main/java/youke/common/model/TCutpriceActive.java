package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCutpriceActive implements Serializable {
    private Integer id;

    private Integer shopid;

    private String goodsid;

    private String goodsurl;

    private String cover;

    private String title;

    private Date starttime;

    private Date endtime;

    private String intro;

    private Integer price;

    private Integer totalnum;

    private Integer dealprice;

    private Integer dealfansnum;

    private Integer num;

    private Integer dealhour;

    private Integer fanslimit;

    private Integer cutpirce;

    private Integer waitday;

    private Integer waitpayminute;

    private Integer state;

    private Date createtime;

    private String appid;

    private String youkeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public String getGoodsurl() {
        return goodsurl;
    }

    public void setGoodsurl(String goodsurl) {
        this.goodsurl = goodsurl == null ? null : goodsurl.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getDealprice() {
        return dealprice;
    }

    public void setDealprice(Integer dealprice) {
        this.dealprice = dealprice;
    }

    public Integer getDealfansnum() {
        return dealfansnum;
    }

    public void setDealfansnum(Integer dealfansnum) {
        this.dealfansnum = dealfansnum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDealhour() {
        return dealhour;
    }

    public void setDealhour(Integer dealhour) {
        this.dealhour = dealhour;
    }

    public Integer getFanslimit() {
        return fanslimit;
    }

    public void setFanslimit(Integer fanslimit) {
        this.fanslimit = fanslimit;
    }

    public Integer getCutpirce() {
        return cutpirce;
    }

    public void setCutpirce(Integer cutpirce) {
        this.cutpirce = cutpirce;
    }

    public Integer getWaitday() {
        return waitday;
    }

    public void setWaitday(Integer waitday) {
        this.waitday = waitday;
    }

    public Integer getWaitpayminute() {
        return waitpayminute;
    }

    public void setWaitpayminute(Integer waitpayminute) {
        this.waitpayminute = waitpayminute;
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