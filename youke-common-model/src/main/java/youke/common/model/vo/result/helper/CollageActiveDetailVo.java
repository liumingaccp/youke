package youke.common.model.vo.result.helper;


import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:51
 */
public class CollageActiveDetailVo implements Serializable {
    private Integer id;

    private Integer shopId;

    private String goodsId;

    private String goodsUrl;

    private String cover;

    private String coverPics;

    private String title;

    private String content;

    private Date startTime;

    private Date endTime;


    private Integer price;

    private Integer totalNum;

    private Integer tuanPrice;

    private Integer tuanFansNum;

    private Integer num;

    private Integer tuanHour;

    private Integer fansLimit;

    private Integer waitDay;

    private Date createTime;

    private JSONArray intro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
        this.fansLimit = fansLimit;
    }

    public Integer getWaitDay() {
        return waitDay;
    }

    public void setWaitDay(Integer waitDay) {
        this.waitDay = waitDay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }
}
