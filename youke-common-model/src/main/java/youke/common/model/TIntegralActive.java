package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TIntegralActive implements Serializable {
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

    private Integer num;

    private Integer openintegral;

    private Integer openmoney;

    private Integer integral;

    private Integer moneyintegral;

    private Integer money;

    private Integer validhour;

    private Integer state;

    private String taocode;

    private Date createtime;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOpenintegral() {
        return openintegral;
    }

    public void setOpenintegral(Integer openintegral) {
        this.openintegral = openintegral;
    }

    public Integer getOpenmoney() {
        return openmoney;
    }

    public void setOpenmoney(Integer openmoney) {
        this.openmoney = openmoney;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMoneyintegral() {
        return moneyintegral;
    }

    public void setMoneyintegral(Integer moneyintegral) {
        this.moneyintegral = moneyintegral;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getValidhour() {
        return validhour;
    }

    public void setValidhour(Integer validhour) {
        this.validhour = validhour;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTaocode() {
        return taocode;
    }

    public void setTaocode(String taocode) {
        this.taocode = taocode == null ? null : taocode.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}