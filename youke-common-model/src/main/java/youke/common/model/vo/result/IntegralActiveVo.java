package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class IntegralActiveVo implements Serializable {
    private Integer id;

    private String cover;

    private String title;

    private Date startTime;

    private Date endTime;

    private Integer openIntegral;

    private Integer openMoney;

    private Integer integral;

    private Integer moneyIntegral;

    private Integer money;

    private Integer totalNum;

    private Integer num;

    private Integer state;

    private Integer price;

    private String goodsUrl;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOpenIntegral() {
        return openIntegral;
    }

    public void setOpenIntegral(Integer openIntegral) {
        this.openIntegral = openIntegral;
    }

    public Integer getOpenMoney() {
        return openMoney;
    }

    public void setOpenMoney(Integer openMoney) {
        this.openMoney = openMoney;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getMoneyIntegral() {
        return moneyIntegral;
    }

    public void setMoneyIntegral(Integer moneyIntegral) {
        this.moneyIntegral = moneyIntegral;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }
}