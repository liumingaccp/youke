package youke.common.model.vo.result.helper;

import net.sf.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/14
 * Time: 9:53
 */
public class TuanDetailVo implements Serializable {
    private Integer id;
    private String coverPics;
    private String title;
    private String content;
    private long end;
    private String endTime;
    private String intros;
    private Integer usedNum;
    private Integer price;
    private Integer tuanPrice;
    private Integer tuanFansNum;
    private Integer leftTuanNum;
    private String openId;
    private String wxName;
    private String wxFace;
    private String orderNo;
    private Integer state;
    private String goodsId;
    private String goodsUrl;
    private Integer shopType;
    private Integer activeId;
    private JSONArray intro;
    private List<WxFansLessVo> orderFans = new ArrayList();

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEnd() {
        return end;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public String getIntros() {
        return intros;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverPics() {
        return coverPics;
    }

    public void setCoverPics(String coverPics) {
        this.coverPics = coverPics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setIntros(String intros) {
        this.intros = intros;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTuanPrice() {
        return tuanPrice;
    }

    public void setTuanPrice(Integer tuanPrice) {
        this.tuanPrice = tuanPrice;
    }

    public Integer getTuanFansNum() {
        return tuanFansNum;
    }

    public void setTuanFansNum(Integer tuanFansNum) {
        this.tuanFansNum = tuanFansNum;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxFace() {
        return wxFace;
    }

    public void setWxFace(String wxFace) {
        this.wxFace = wxFace;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<WxFansLessVo> getOrderFans() {
        return orderFans;
    }

    public void setOrderFans(List<WxFansLessVo> orderFans) {
        this.orderFans = orderFans;
    }

    public void setLeftTuanNum(Integer leftTuanNum) {
        this.leftTuanNum = leftTuanNum;
    }

    public Integer getLeftTuanNum() {
        return leftTuanNum;
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

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }
}
