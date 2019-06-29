package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class IntegralOrderRetVo implements Serializable {
    private Long id;

    private String orderNo;

    private String cover;

    private String title;

    private Date createTime;

    private Integer price;

    private Integer integral;

    private Integer money;

    private Integer openType;

    private Integer backMoney;

    private Integer activeId;

    private Integer state;

    private String goodsId;

    private String goodsUrl;

    private Integer shopType;

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }
}