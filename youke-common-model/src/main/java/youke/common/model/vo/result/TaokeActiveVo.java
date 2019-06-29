package youke.common.model.vo.result;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/24
 * Time: 10:29
 */
public class TaokeActiveVo implements Serializable {
    private Integer id;

    private Integer shopId;

    private String goodsId;

    private String goodsUrl;

    private String cover;

    private String title;

    private Date startTime;

    private Date endTime;

    private Integer price;

    private Integer backMoney;

    private Integer commision;

    private Integer totalNum;

    private Integer num;

    private String backBg;

    private Integer fansLimit;

    private Integer openOrderLimit;

    private Integer openBackLimit;

    private Integer openSelfCommision;

    private Integer waitDay;

    private String slogan;

    private Integer openType;

    private Integer state;

    private Date createTime;

    private Integer shopType;

    private JSONArray intro;

    private String intros;

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public String getIntros() {
        return intros;
    }

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
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
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


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getBackBg() {
        return backBg;
    }

    public void setBackBg(String backBg) {
        this.backBg = backBg;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public Integer getOpenOrderLimit() {
        return openOrderLimit;
    }

    public void setOpenOrderLimit(Integer openOrderLimit) {
        this.openOrderLimit = openOrderLimit;
    }

    public Integer getOpenBackLimit() {
        return openBackLimit;
    }

    public void setOpenBackLimit(Integer openBackLimit) {
        this.openBackLimit = openBackLimit;
    }

    public Integer getOpenSelfCommision() {
        return openSelfCommision;
    }

    public void setOpenSelfCommision(Integer openSelfCommision) {
        this.openSelfCommision = openSelfCommision;
    }

    public Integer getWaitDay() {
        return waitDay;
    }

    public void setWaitDay(Integer waitDay) {
        this.waitDay = waitDay;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }
}
