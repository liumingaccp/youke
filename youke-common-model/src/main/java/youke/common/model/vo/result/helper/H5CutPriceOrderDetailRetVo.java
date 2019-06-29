package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 砍价详情
 */
public class H5CutPriceOrderDetailRetVo implements Serializable {
    private Long id;
    private String openId;
    private String title;
    private String cover;
    private Integer price;
    private Integer dealPrice;
    private Integer alreadyCutPrice;
    private Integer leftCutPrice;
    private Integer usedNum;
    private String wxName;
    private String wxFace;
    private Date createTime;
    private Date endTime;
    private String goodsId;
    private Integer cutState;
    private Integer state;
    private Integer shopType;
    private List<String> intro = new ArrayList<>();

    public void setIntro(List<String> intro) {
        this.intro = intro;
    }

    public List<String> getIntro() {
        return intro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setAlreadyCutPrice(Integer alreadyCutPrice) {
        this.alreadyCutPrice = alreadyCutPrice;
    }

    public void setLeftCutPrice(Integer leftCutPrice) {
        this.leftCutPrice = leftCutPrice;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public void setWxFace(String wxFace) {
        this.wxFace = wxFace;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setCutState(Integer cutState) {
        this.cutState = cutState;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Long getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public Integer getAlreadyCutPrice() {
        return alreadyCutPrice;
    }

    public Integer getLeftCutPrice() {
        return leftCutPrice;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public String getWxName() {
        return wxName;
    }

    public String getWxFace() {
        return wxFace;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public Integer getCutState() {
        return cutState;
    }

    public Integer getState() {
        return state;
    }

    public Integer getShopType() {
        return shopType;
    }
}
