package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class IntegralOrderVo implements Serializable{
    private Long id;

    private String orderno;

    private String title;

    private String wxfansname;

    private String shopfansname;

    private Date createtime;

    private Integer price;

    private Integer integral;

    private Integer money;

    private Integer backmoney;

    private Integer state;

    private String stateDisplay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getWxfansname() {
        return wxfansname;
    }

    public void setWxfansname(String wxfansname) {
        this.wxfansname = wxfansname == null ? null : wxfansname.trim();
    }

    public String getShopfansname() {
        return shopfansname;
    }

    public void setShopfansname(String shopfansname) {
        this.shopfansname = shopfansname == null ? null : shopfansname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public Integer getBackmoney() {
        return backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public String getStateDisplay() {
        //"state":0  0未使用,1待收货,2返现中,3已返现,4已失效
        switch (this.state){
            case 0:
                return "未使用";
            case 1:
                return "待收货";
            case 2:
                return "返现中";
            case 3:
                return "已返现";
            case 4:
                return "返利失败";
            case 5:
                return "已失效";
                default:
                    return null;
        }
    }
}