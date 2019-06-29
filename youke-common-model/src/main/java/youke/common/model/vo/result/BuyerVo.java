package youke.common.model.vo.result;

import java.io.Serializable;

public class BuyerVo implements Serializable {

    private String mobile;
    private String truename;
    //购物账户
    private String buyerName;
    //最近交易
    private String latelyDeal;
    //合计交易
    private String totalDeal;
    //单价
    private String unitDeal;
    //上次交易时间
    private String latelyTime;
    //省份
    private String province;
    //城市
    private String city;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getLatelyDeal() {
        return latelyDeal;
    }

    public void setLatelyDeal(String latelyDeal) {
        this.latelyDeal = latelyDeal;
    }

    public String getTotalDeal() {
        return totalDeal;
    }

    public void setTotalDeal(String totalDeal) {
        this.totalDeal = totalDeal;
    }

    public String getUnitDeal() {
        return unitDeal;
    }

    public void setUnitDeal(String unitDeal) {
        this.unitDeal = unitDeal;
    }

    public String getLatelyTime() {
        return latelyTime;
    }

    public void setLatelyTime(String latelyTime) {
        this.latelyTime = latelyTime;
    }

    public String getProvince() {
        return province==null?"":province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city==null?"":city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
