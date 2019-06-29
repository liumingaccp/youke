package youke.facade.market.vo;

import net.sf.json.JSONArray;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class IntegralActiveParamVo implements Serializable {
    private Integer id;

    private Integer shopId;

    private String goodsId;

    private String goodsUrl;

    private String cover;

    private String title;

    private String startTime;

    private String endTime;

    private String intros;

    private Integer price;

    private Integer totalNum;

    private Integer num;

    private Integer openIntegral;

    private Integer openMoney;

    private Integer integral;

    private Integer moneyIntegral;

    private Integer money;

    private Integer validHour;

    private Integer state;

    private String taoCode;

    private String appId;

    private String youkeId;

    private JSONArray intro;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getGoodsId() {
        return StringUtils.hasLength(goodsId)?goodsId:null;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsUrl() {
        return StringUtils.hasLength(goodsUrl)?goodsUrl:null;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getCover() {
        return StringUtils.hasLength(cover)?cover:null;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return StringUtils.hasLength(title)?title:null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return StringUtils.hasLength(startTime)?startTime:null;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return StringUtils.hasLength(endTime)?endTime:null;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Integer getValidHour() {
        return validHour;
    }

    public void setValidHour(Integer validHour) {
        this.validHour = validHour;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setTaoCode(String taoCode) {
        this.taoCode = taoCode;
    }

    public String getTaoCode() {
        return taoCode;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getAppId() {
        return appId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public String getIntros() {
        return intros;
    }

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }
}