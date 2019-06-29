package youke.common.model.vo.result.helper;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class TrialWealOrderQueryRetVo implements Serializable {
    private Integer id;
    private String title;
    private String cover;
    private String shopName;
    private String goodsUrl;
    private String buyerName;
    private String wxFansName;
    private String orderNo;
    private Integer price =0;
    private Integer shopType;
    private Date backTime;
    private Date createTime;
    private String rewardType;
    private Integer backReward;
    private Integer activeId;
    private Integer state;
    private String stateDisplay;

    private Integer demoPicCount = 0;

    private String goodsId;

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = wxFansName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getBackReward() {
        return backReward;
    }

    public void setBackReward(Integer backReward) {
        this.backReward = backReward;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateDisplay() {
        return stateDisplay;
    }

    public void setStateDisplay(String stateDisplay) {
        this.stateDisplay = stateDisplay;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setDemoPicCount(Integer demoPicCount) {
        this.demoPicCount = demoPicCount;
    }

    public Integer getDemoPicCount() {
        return demoPicCount;
    }
}
