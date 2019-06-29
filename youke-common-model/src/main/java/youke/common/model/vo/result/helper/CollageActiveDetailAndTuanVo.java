package youke.common.model.vo.result.helper;


import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/14
 * Time: 9:53
 */
public class CollageActiveDetailAndTuanVo implements Serializable {
    private Integer id;
    private String coverPics;
    private String title;
    private String content;
    private Long endTime;
    private String intros;
    private Integer usedNum;
    private Integer price;
    private Integer shopType;
    private String goodsUrl;
    private String goodsId;
    private Integer tuanPrice;
    private Integer tuanFansNum;
    private Integer tuanNum;
    private List<CollageTuanItem> tuanItems = new ArrayList();

    private JSONArray intro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setEndTime(Date date) {
        this.endTime = date.getTime();
    }

    public Long getEndTime() {
        return endTime;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public List<CollageTuanItem> getTuanItems() {
        return tuanItems;
    }

    public void setTuanItems(List<CollageTuanItem> tuanItems) {
        this.tuanItems = tuanItems;
    }

    public void setTuanNum(Integer tuanNum) {
        this.tuanNum = tuanNum;
    }

    public Integer getTuanNum() {
        return tuanNum;
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
