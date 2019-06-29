package youke.common.model.vo.result;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/23
 * Time: 9:38
 */
public class IntegralActiveRetVo implements Serializable{
    private Integer id;

    private Integer shopid;

    private String goodsid;

    private String goodsurl;

    private Integer shopType;

    private String cover;

    private String title;

    private Date starttime;

    private Date endtime;

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

    private String shopName;

    private JSONArray intro;

    private String intros;

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public String getIntros() {
        return intros;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }

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
        this.goodsid = goodsid;
    }

    public String getGoodsurl() {
        return goodsurl;
    }

    public void setGoodsurl(String goodsurl) {
        this.goodsurl = goodsurl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
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
        this.taocode = taocode;
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
        this.youkeid = youkeid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
