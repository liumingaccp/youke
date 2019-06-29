package youke.common.model.vo.param.market;


import youke.common.model.vo.param.QueryObjectVO;

public class MarketParamVo extends QueryObjectVO {
    private Integer type;               //图片类型
    private String url;                 //图片地址
    private String title;               //商品标题
    private String orderno;             //订单号
    private Integer price;              //价格
    private Integer runDay;             //签到天数
    private Integer integral;           //积分数
    private String prizeName;           //奖品名称
    private Integer rewardType;         //奖品类型
    private Integer rewardVal;          //奖品数值
    private Double percent;             //中奖几率

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setRewardVal(Integer rewardVal) {
        this.rewardVal = rewardVal;
    }


    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getRewardVal() {
        return rewardVal;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setRunDay(Integer runDay) {
        this.runDay = runDay;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getUrl() {
        return empty2null(url);
    }

    public String getTitle() {
        return empty2null(title);
    }

    public String getOrderno() {
        return empty2null(orderno);
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getRunDay() {
        return runDay;
    }

    public Integer getIntegral() {
        return integral;
    }

    @Override
    public String toString() {
        return "MarketParamVo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", orderno='" + orderno + '\'' +
                ", price=" + price +
                ", runDay=" + runDay +
                ", integral=" + integral +
                ", prizeName='" + prizeName + '\'' +
                ", rewardType=" + rewardType +
                ", rewardVal=" + rewardVal +
                ", percent=" + percent +
                '}';
    }
}
