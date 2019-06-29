package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class TrialWealOrderQueryVo implements Serializable {
    private String title;
    private String wxFansName;
    private String buyerName;
    private Integer state;
    private String orderNo;
    private Integer shopId;
    private Integer shopType;
    private String timeBeg;
    private String timeEnd;
    private String backTimeBeg;
    private String backTimeEnd;
    private Integer rewardType;
    private Integer backRewardBeg;
    private Integer backRewardEnd;
    private Integer page = 1;
    private Integer limit =20;

    private String openId;
    private String type = "pc";

    private String appId;
    private String youkeId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = StringUtils.hasLength(title) ? "%" + title + "%" : null;
    }

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = StringUtils.hasLength(wxFansName) ? "%" + wxFansName + "%" : null;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = StringUtils.hasLength(buyerName) ? "%" + buyerName + "%" : null;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = StringUtils.hasLength(orderNo) ? orderNo : null;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getTimeBeg() {
        return timeBeg;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = StringUtils.hasLength(timeBeg) ? timeBeg : null;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = StringUtils.hasLength(timeEnd) ? timeEnd : null;
    }

    public String getBackTimeBeg() {
        return backTimeBeg;
    }

    public void setBackTimeBeg(String backTimeBeg) {
        this.backTimeBeg = StringUtils.hasLength(backTimeBeg) ? backTimeBeg : null;
    }

    public String getBackTimeEnd() {
        return backTimeEnd;
    }

    public void setBackTimeEnd(String backTimeEnd) {
        this.backTimeEnd = StringUtils.hasLength(backTimeEnd) ? backTimeEnd : null;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getBackRewardBeg() {
        return backRewardBeg;
    }

    public void setBackRewardBeg(Integer backRewardBeg) {
        this.backRewardBeg = backRewardBeg;
    }

    public Integer getBackRewardEnd() {
        return backRewardEnd;
    }

    public void setBackRewardEnd(Integer backRewardEnd) {
        this.backRewardEnd = backRewardEnd;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null &&  page > 0){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)? openId : null;
    }

    public String getOpenId() {
        return openId;
    }

    public void setType(String type) {
        if(StringUtils.hasLength(type)){
            this.type = type;
        }
    }

    public String getType() {
        return type;
    }
}
