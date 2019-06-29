package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * H5我的砍价列表
 */
public class H5CutPriceOrderRetVo implements Serializable {
    private Long id;
    private String title;
    private String cover;
    private String orderno;
    private String goodsId;
    private Integer price;
    private Integer alreadyCutPrice;
    private Integer leftCutPrice;
    private Integer dealPrice;
    private Integer usedNum;
    private Date createTime;
    private Date endTime;
    private Integer state;
    private Integer shopType;

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public Long getId() {
        return id;
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

    public void setAlreadyCutPrice(Integer alreadyCutPrice) {
        this.alreadyCutPrice = alreadyCutPrice;
    }

    public void setLeftCutPrice(Integer leftCutPrice) {
        this.leftCutPrice = leftCutPrice;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAlreadyCutPrice() {
        return alreadyCutPrice;
    }

    public Integer getLeftCutPrice() {
        return leftCutPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getState() {
        return state;
    }

    public Integer getShopType() {
        return shopType;
    }
}
