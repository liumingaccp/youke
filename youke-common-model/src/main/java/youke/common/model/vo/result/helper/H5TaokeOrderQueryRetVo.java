package youke.common.model.vo.result.helper;

import com.github.pagehelper.PageInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/8
 * Time: 18:08
 */
public class H5TaokeOrderQueryRetVo implements Serializable{
    private Integer id;
    private String cover;
    private String title;
    private Integer backMoney;
    private Integer commision;
    private Integer price;
    private String orderNo;
    private Integer state;
    private Date createTime;
    private String goodsId;
    private String goodsUrl;
    private String taokeOpenId;

    private Integer shopType;
    private String activeId;

    private PageInfo<H5TaokeOrderDetailQueryRetVo> info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setInfo(PageInfo<H5TaokeOrderDetailQueryRetVo> info) {
        this.info = info;
    }

    public PageInfo<H5TaokeOrderDetailQueryRetVo> getInfo() {
        return info;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setTaokeOpenId(String taokeOpenId) {
        this.taokeOpenId = taokeOpenId;
    }

    public String getTaokeOpenId() {
        return taokeOpenId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
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
