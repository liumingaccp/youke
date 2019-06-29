package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CutPriceActiveDetailRetVo implements Serializable {

    private Integer id;

    private Integer shopId;

    private String goodsId;

    private String goodsUrl;

    private String cover;

    private String title;

    private Date startTime;

    private Date endTime;

    private Integer price;

    private Integer totalNum;

    private Integer dealPrice;

    private Integer dealFansNum;

    private Integer num;

    private Integer dealHour;

    private Integer fansLimit;

    private Integer cutPrice;

    private Integer waitDay;

    private Integer waitPayMinute;

    private Integer state;

    private Date createTime;

    private List<String>intro = new ArrayList<>();

    public void setIntro(List<String> intro) {
        this.intro = intro;
    }

    public List<String> getIntro() {
        return intro;
    }

    public void setCutPrice(Integer cutPrice) {
        this.cutPrice = cutPrice;
    }

    public Integer getCutPrice() {
        return cutPrice;
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

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setDealFansNum(Integer dealFansNum) {
        this.dealFansNum = dealFansNum;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setDealHour(Integer dealHour) {
        this.dealHour = dealHour;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public void setWaitDay(Integer waitDay) {
        this.waitDay = waitDay;
    }

    public void setWaitPayMinute(Integer waitPayMinute) {
        this.waitPayMinute = waitPayMinute;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public Integer getDealFansNum() {
        return dealFansNum;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getDealHour() {
        return dealHour;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public Integer getWaitDay() {
        return waitDay;
    }

    public Integer getWaitPayMinute() {
        return waitPayMinute;
    }

    public Integer getState() {
        return state;
    }

    public Date getCreateTime() {
        return createTime;
    }
}

