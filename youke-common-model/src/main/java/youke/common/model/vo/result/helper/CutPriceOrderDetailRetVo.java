package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * 砍价记录查看审核详情
 */
public class CutPriceOrderDetailRetVo implements Serializable {
    private String title;
    private String orderno;
    private String receiveName;
    private String receiveAddress;
    private String picPath;
    private Integer price;
    private Integer totalPrice;
    private Integer num;
    private Integer orderState;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getTitle() {
        return title;
    }

    public String getOrderno() {
        return orderno;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public String getPicPath() {
        return picPath;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getOrderState() {
        return orderState;
    }
}
