package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * 砍价活动记录分页结果Vo
 */
public class CutPriceOrderRetVo implements Serializable {
    private Integer id;
    private String title;
    private String wxfansName;
    private String buyerName;
    private String orderno;
    private Integer price;
    private Integer dealPrice;
    private Integer alreadyCutPrice;
    private Integer leftCutPrice;
    private Integer dealFansNuml;
    private Integer joinFansNum;
    private Date cutEndTime;
    private Date createTime;
    private Date backTime;
    private Integer state;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
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

    public void setDealFansNuml(Integer dealFansNuml) {
        this.dealFansNuml = dealFansNuml;
    }

    public void setJoinFansNum(Integer joinFansNum) {
        this.joinFansNum = joinFansNum;
    }

    public void setCutEndTime(Date cutEndTime) {
        this.cutEndTime = cutEndTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWxfansName() {
        return wxfansName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getOrderno() {
        return orderno;
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

    public Integer getDealFansNuml() {
        return dealFansNuml;
    }

    public Integer getJoinFansNum() {
        return joinFansNum;
    }

    public Date getCutEndTime() {
        return cutEndTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public Integer getState() {
        return state;
    }
}
