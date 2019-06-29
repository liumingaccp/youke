package youke.common.model.vo.result.helper;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class TaokeOrderQueryRetVo implements Serializable {
    private Integer id;
    private String cover;
    private String title;
    private String goodsId;
    private String orderNo;
    private int orderState;
    private String buyerOpenId;
    private String buyerFace;
    private String buyerMobile;
    private String buyerName;
    private String taokeOpenId;
    private String taokeFace;
    private String taokeMobile;
    private String taokeName;
    private Integer backMoney;
    private Integer totalPrice;
    private Integer totalNum;
    private Integer commision;
    private Date backTime;
    private Date createTime;
    private Integer state;
    private Integer activeId;
    private String taokeTime;
    private String stateDisplay;

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBuyerOpenId() {
        return buyerOpenId;
    }

    public void setBuyerOpenId(String buyerOpenId) {
        this.buyerOpenId = buyerOpenId;
    }

    public String getBuyerFace() {
        return buyerFace;
    }

    public void setBuyerFace(String buyerFace) {
        this.buyerFace = buyerFace;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getTaokeOpenId() {
        return taokeOpenId;
    }

    public void setTaokeOpenId(String taokeOpenId) {
        this.taokeOpenId = taokeOpenId;
    }

    public String getTaokeFace() {
        return taokeFace;
    }

    public void setTaokeFace(String taokeFace) {
        this.taokeFace = taokeFace;
    }

    public String getTaokeMobile() {
        return taokeMobile;
    }

    public void setTaokeMobile(String taokeMobile) {
        this.taokeMobile = taokeMobile;
    }

    public String getTaokeName() {
        return taokeName;
    }

    public void setTaokeName(String taokeName) {
        this.taokeName = taokeName;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateDisplay() {
        return stateDisplay;
    }

    public void setStateDisplay(String stateDisplay) {
        this.stateDisplay = stateDisplay;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public void setTaokeTime(String taokeTime) {
        this.taokeTime = taokeTime;
    }

    public String getTaokeTime() {
        return taokeTime;
    }
}
