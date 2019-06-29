package youke.common.model.vo.result.helper;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 15:17
 */
public class TrialWealQueryRetVo implements Serializable {
    private Integer id;
    private String cover;
    private String title;
    private String goodsId;
    private String goodsUrl;
    private Integer shopType;
    private String shopName;
    private Integer price;
    private Integer rewardType;
    private String backReward;
    private Date starTtime;
    private Date endTime;
    private Integer totalNum;
    private Integer num;
    private Integer state;
    private Integer openExamineImg;
    private String examineImg;
    private Integer joinNum;
    private Integer successNum;

    private Integer demoPicCount = 0;

    private String preUrl;
    private String preCodeUrl;

    private String stateDisplay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getBackReward() {
        return backReward;
    }

    public void setBackReward(String backReward) {
        this.backReward = backReward;
    }

    public Date getStarTtime() {
        return starTtime;
    }

    public void setStarTtime(Date starTtime) {
        this.starTtime = starTtime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public String getPreUrl() {
        return preUrl;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public String getPreCodeUrl() {
        return preCodeUrl;
    }

    public void setPreCodeUrl(String preCodeUrl) {
        this.preCodeUrl = preCodeUrl;
    }

    public String getStateDisplay() {
        return stateDisplay;
    }

    public void setStateDisplay(String stateDisplay) {
        this.stateDisplay = stateDisplay;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setOpenExamineImg(Integer openExamineImg) {
        this.openExamineImg = openExamineImg;
    }

    public Integer getOpenExamineImg() {
        return openExamineImg;
    }

    public void setExamineImg(String examineImg) {
        this.examineImg = examineImg;
    }

    public String getExamineImg() {
        return examineImg;
    }

    public void setDemoPicCount(Integer demoPicCount) {
        if(demoPicCount != null){
            this.demoPicCount = demoPicCount;
        }
    }

    public Integer getDemoPicCount() {
        return demoPicCount;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public Integer getSuccessNum() {
        return successNum;
    }
}
