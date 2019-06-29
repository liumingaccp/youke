package youke.common.model.vo.result.helper;

import net.sf.json.JSONArray;

import javax.ws.rs.GET;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/4/17
 * Time: 18:09
 */
public class RebateDetailVo implements Serializable{
    private Integer id;

    private Integer shopid;

    private String goodsid;

    private String goodsurl;

    private String cover;

    private String title;

    private Date starttime;

    private Date endtime;

    private Integer price;

    private Integer backmoney;

    private Integer totalnum;

    private Integer num;

    private Integer costintegral;

    private Integer fanslimit;

    private Integer limitminute;

    private Integer limitcount;

    private Integer waitday;

    private Integer opentype;

    private Integer state;

    private String taocode;

    private Date createtime;

    private Integer shopType;

    private JSONArray intro;

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


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBackmoney() {
        return backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
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

    public Integer getCostintegral() {
        return costintegral;
    }

    public void setCostintegral(Integer costintegral) {
        this.costintegral = costintegral;
    }

    public Integer getFanslimit() {
        return fanslimit;
    }

    public void setFanslimit(Integer fanslimit) {
        this.fanslimit = fanslimit;
    }

    public Integer getLimitminute() {
        return limitminute;
    }

    public void setLimitminute(Integer limitminute) {
        this.limitminute = limitminute;
    }

    public Integer getLimitcount() {
        return limitcount;
    }

    public void setLimitcount(Integer limitcount) {
        this.limitcount = limitcount;
    }

    public Integer getWaitday() {
        return waitday;
    }

    public void setWaitday(Integer waitday) {
        this.waitday = waitday;
    }

    public Integer getOpentype() {
        return opentype;
    }

    public void setOpentype(Integer opentype) {
        this.opentype = opentype;
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

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }
}
