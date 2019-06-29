package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class TaokeOrderQueryVo implements Serializable {
    private String title;
    private String wxFansName;
    private String buyerName;
    private Integer state;
    private String orderNo;
    private Integer shopId;
    private Integer page = 1;
    private Integer limit = 20;
    private String timeBeg;
    private String timeEnd;
    private String goodsId;
    private String mobile;


    private String appId;
    private String youkeId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = StringUtils.hasLength(title)? "%"+ title + "%" : null;
    }

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = StringUtils.hasLength(wxFansName)? "%"+ wxFansName + "%" : null;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = StringUtils.hasLength(buyerName)? "%"+ buyerName + "%" : null;
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
        this.orderNo = StringUtils.hasLength(orderNo)? title : null;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
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

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public String getTimeBeg() {
        return timeBeg;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = StringUtils.hasLength(timeBeg)?timeBeg : null;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = StringUtils.hasLength(timeEnd)?timeEnd :null;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = StringUtils.hasLength(goodsId)?goodsId:null;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = StringUtils.hasLength(mobile)?mobile:null;
    }
}
