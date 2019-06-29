package youke.facade.market.vo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/25
 * Time: 15:40
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 抓取宝贝信息
 */
public class GoodInfoVo implements Serializable {
    private String shopName;
    private String price;
    private String promotionPrice;
    private List<String> picUrls = new ArrayList<String>();
    private long shopId;
    private String goodId;
    private String goodTitle;
    private String content;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
