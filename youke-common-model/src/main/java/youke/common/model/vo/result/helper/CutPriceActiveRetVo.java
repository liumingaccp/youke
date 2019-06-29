package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * 砍价活动列表查询类
 */
public class CutPriceActiveRetVo implements Serializable {
    private Integer id;
    private Integer shopId;
    private String cover;
    private String title;
    private String goodsId;
    private String goodsUrl;
    private Integer price;
    private Integer dealPrice;
    private String shopTitle;
    private Date startTime;
    private Date endTime;
    private Integer totalNum;
    private Integer num;
    private Integer state;
    private String preUrl;      //预览页面url
    private String preCodeUrl;  //二维码地址url

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public void setPreCodeUrl(String preCodeUrl) {
        this.preCodeUrl = preCodeUrl;
    }

    public Integer getId() {
        return id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getState() {
        return state;
    }

    public String getPreUrl() {
        return preUrl;
    }

    public String getPreCodeUrl() {
        return preCodeUrl;
    }
}
