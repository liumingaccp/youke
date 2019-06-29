package youke.facade.helper.vo;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 15:47
 */
public class TrialWealActiveVo implements Serializable{
    private Integer id;
    private Integer shopId;
    private String goodsId;
    private String goodsUrl;
    private String cover;
    private String title;
    private String startTime;
    private String endTime;
    private String intros;
    private Integer price;
    private Integer totalNum;
    private Integer waitDay;
    private Integer openType;
    //----------------------
    private Integer state;
    private Integer rewardType;
    private Integer backReward;
    private Integer costIntegral;
    private Integer fansLimit;
    private Integer sexLimit;
    private Integer openExamineImg;
    private String examineImg;
    private JSONArray demoPics;
    private JSONArray intro;
    private String youkeId;
    private String appId;


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

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getBackReward() {
        return backReward;
    }

    public void setBackReward(Integer backReward) {
        this.backReward = backReward;
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

    public Integer getSexLimit() {
        return sexLimit;
    }

    public void setSexLimit(Integer sexLimit) {
        this.sexLimit = sexLimit;
    }

    public Integer getOpenExamineImg() {
        return openExamineImg;
    }

    public void setOpenExamineImg(Integer openExamineImg) {
        this.openExamineImg = openExamineImg;
    }

    public JSONArray getDemoPics() {
        return demoPics;
    }

    public void setDemoPics(JSONArray  demoPics) {
        this.demoPics = demoPics;
    }

    public void setExamineImg(String examineImg) {
        this.examineImg = examineImg;
    }

    public String getExamineImg() {
        return examineImg;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
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

    public String getIntros() {
        return intros;
    }

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }
}
