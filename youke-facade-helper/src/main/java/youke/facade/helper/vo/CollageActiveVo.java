package youke.facade.helper.vo;

import net.sf.json.JSONArray;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:33
 */
public class CollageActiveVo implements Serializable {
    private Integer id = 0;
    private Integer shopId;
    private String goodsId;
    private String goodsUrl;
    private String coverPics;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private String intros;
    private Integer price;
    private Integer totalNum;
    private Integer tuanPrice;
    private Integer tuanFansNum;
    private Integer tuanHour;
    private Integer fansLimit = 1;
    private Integer wayDay;
    private JSONArray intro;

    private String youkeId;
    private String appId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(id != null && id > 0){
            this.id = id;
        }
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

    public String getCoverPics() {
        return coverPics;
    }

    public void setCoverPics(String coverPics) {
        this.coverPics = coverPics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getTuanPrice() {
        return tuanPrice;
    }

    public void setTuanPrice(Integer tuanPrice) {
        this.tuanPrice = tuanPrice;
    }

    public Integer getTuanFansNum() {
        return tuanFansNum;
    }

    public void setTuanFansNum(Integer tuanFansNum) {
        this.tuanFansNum = tuanFansNum;
    }

    public Integer getTuanHour() {
        return tuanHour;
    }

    public void setTuanHour(Integer tuanHour) {
        this.tuanHour = tuanHour;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public void setFansLimit(Integer fansLimit) {
        if(fansLimit != null && fansLimit > -1){
            this.fansLimit = fansLimit;
        }
    }

    public Integer getWayDay() {
        return wayDay;
    }

    public void setWayDay(Integer wayDay) {
        this.wayDay = wayDay;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
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
