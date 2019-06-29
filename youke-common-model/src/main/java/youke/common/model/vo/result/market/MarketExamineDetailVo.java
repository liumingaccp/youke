package youke.common.model.vo.result.market;

import java.io.Serializable;

public class MarketExamineDetailVo implements Serializable {
    private String userPics;                //用户上传图片
    private String orderno;                 //订单号
    private String receiveName;             //收件人
    private String receiveAddress;          //收货地址
    private Integer orderState;             //订单状态
    private String picPath;                 //商品图片
    private String title;                   //商品名称
    private Integer price;                  //商品单价
    private Integer num;                    //购买数量
    private Integer totalPrice;             //实付款
    private Integer state;                  //审核状态
    private String failReason;              //审核失败原因

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setUserPics(String userPics) {
        this.userPics = userPics;
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

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getUserPics() {
        return userPics;
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

    public Integer getOrderState() {
        return orderState;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public String getFailReason() {
        return failReason;
    }

    @Override
    public String toString() {
        return "MarketExamineDetailVo{" +
                "userPics='" + userPics + '\'' +
                ", orderno='" + orderno + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", orderState=" + orderState +
                ", picPath='" + picPath + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                ", state=" + state +
                ", failReason='" + failReason + '\'' +
                '}';
    }
}
