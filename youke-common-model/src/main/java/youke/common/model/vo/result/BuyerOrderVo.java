package youke.common.model.vo.result;

import java.io.Serializable;

public class BuyerOrderVo implements Serializable {


    private Long id;
    //商品Id
    private String goodsId;
    //店铺类别 0淘宝，1天猫，2京东
    private int shopType;
    //店铺类别 图标
    private String shopTypeIcon;
    //店铺名
    private String shopTitle;
    //商品名称
    private String title;
    //商品图片
    private String picPath;
    //单价
    private String price;
    //数量
    private int num;
    //总价
    private String totalPrice;
    //交易状态
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getShopTypeIcon() {
        return shopTypeIcon;
    }

    public void setShopTypeIcon(String shopTypeIcon) {
        this.shopTypeIcon = shopTypeIcon;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
