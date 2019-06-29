package youke.facade.helper.vo;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 14:47
 */
public class RebateActiveVo implements Serializable{

    private Integer id;

    private Integer shopId;

    private String goodsId;

    private String goodsUrl;

    private String cover;

    private String title;

    private String startTime;

    private String endTime;

    private Integer price;

    private Integer totalNum;

    private Integer waitDay;

    private Integer openType;

    //----------------------------
    private Integer state;

    private Integer costIntegral;

    private Integer fansLimit;

    private Integer limitMinute;

    private Integer limitCount;

    private String youkeId;

    private String appId;
    
    private Integer backMoney;

    private JSONArray intro;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCostIntegral() {
        return costIntegral;
    }

    public void setCostIntegral(Integer costIntegral) {
        this.costIntegral = costIntegral;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public Integer getLimitMinute() {
        return limitMinute;
    }

    public void setLimitMinute(Integer limitMinute) {
        this.limitMinute = limitMinute;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getWaitDay() {
        return waitDay;
    }

    public void setWaitDay(Integer waitDay) {
        this.waitDay = waitDay;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
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

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }
}
