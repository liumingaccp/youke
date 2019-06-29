package youke.facade.helper.vo;

import net.sf.json.JSONArray;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 15:58
 */
public class TaokeActiveVo implements Serializable {
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

    private Integer backMoney;
    private Integer commision;
    private Integer openOrderLimit;
    private Integer openBackLimit;
    private Integer openSelfCommision;
    private String backBg;
    private Integer fansLimit;
    private String slogan;
    private Integer state;

    private JSONArray intro;

    private String youkeId;
    private String appId;
    private String taoCode;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Integer getOpenOrderLimit() {
        return openOrderLimit;
    }

    public void setOpenOrderLimit(Integer openOrderLimit) {
        this.openOrderLimit = openOrderLimit;
    }

    public void setOpenBackLimit(Integer openBackLimit) {
        this.openBackLimit = openBackLimit;
    }

    public Integer getOpenBackLimit() {
        return openBackLimit;
    }

    public Integer getOpenSelfCommision() {
        return openSelfCommision;
    }

    public void setOpenSelfCommision(Integer openSelfCommision) {
        this.openSelfCommision = openSelfCommision;
    }

    public String getBackBg() {
        return backBg;
    }

    public void setBackBg(String backBg) {
        this.backBg = backBg;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setTaoCode(String taoCode) {
        this.taoCode = taoCode;
    }

    public String getTaoCode() {
        return taoCode;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
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
