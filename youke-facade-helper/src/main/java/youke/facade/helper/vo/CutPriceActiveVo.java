package youke.facade.helper.vo;

import net.sf.json.JSONArray;

import java.io.Serializable;

public class CutPriceActiveVo implements Serializable {
    private Integer id;
    private Integer shopId;
    private String goodsId;
    private String goodsUrl;
    private String cover;
    private String title;
    private String startTime;
    private String endTime;
    private Integer price;      //商品售价
    private Integer cutPrice;   //每次砍价 -1随机 >-1固定,单位:分
    private Integer dealPrice;  //成交价
    private Integer totalNum;   //商品数量
    private Integer dealFansNum;//需砍价粉丝数量
    private Integer dealHour;   //砍价有效期
    private Integer waitPayMinute;//等待时间
    private Integer waitDay;     //返利发放时间 0表示立即发送
    private Integer fansLimit;   //粉丝限制
    private String dykId;
    private String appId;
    private String timestamp;
    private JSONArray intro;

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCutPrice(Integer cutPrice) {
        this.cutPrice = cutPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setDealFansNum(Integer dealFansNum) {
        this.dealFansNum = dealFansNum;
    }

    public void setDealHour(Integer dealHour) {
        this.dealHour = dealHour;
    }

    public void setWaitPayMinute(Integer waitPayMinute) {
        this.waitPayMinute = waitPayMinute;
    }

    public void setWaitDay(Integer waitDay) {
        this.waitDay = waitDay;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCutPrice() {
        return cutPrice;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public Integer getDealFansNum() {
        return dealFansNum;
    }

    public Integer getDealHour() {
        return dealHour;
    }

    public Integer getWaitPayMinute() {
        return waitPayMinute;
    }

    public Integer getWaitDay() {
        return waitDay;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
