package youke.common.model.vo.result;

import net.sf.json.JSONArray;
import youke.common.model.TTrialActiveOrderPic;
import youke.common.model.TTrialActivePic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrialActiveRetVo implements Serializable {
    private Integer id;

    private Integer shopid;

    private String goodsid;

    private String goodsurl;

    private Integer shopType;

    private String cover;

    private String title;

    private Date starttime;

    private Date endtime;

    private String intros;

    private Integer price;

    private Integer rewardtype;

    private Integer backreward;

    private Integer totalnum;

    private Integer num;

    private Integer costintegral;

    private Integer fanslimit;

    private Integer sexlimit;

    private Integer waitday;

    private Integer openexamineimg;

    private String examineimg;

    private Integer opentype;

    private Integer state;

    private String taocode;

    private Date createtime;

    private JSONArray intro;


    public void setIntros(String intros) {
        this.intros = intros;
    }

    public String getIntros() {
        return intros;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShopType() {
        return shopType;
    }

    private List<TTrialActivePic> demoPics;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public String getGoodsurl() {
        return goodsurl;
    }

    public void setGoodsurl(String goodsurl) {
        this.goodsurl = goodsurl == null ? null : goodsurl.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRewardtype() {
        return rewardtype;
    }

    public void setRewardtype(Integer rewardtype) {
        this.rewardtype = rewardtype;
    }

    public Integer getBackreward() {
        return backreward;
    }

    public void setBackreward(Integer backreward) {
        this.backreward = backreward;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCostintegral() {
        return costintegral;
    }

    public void setCostintegral(Integer costintegral) {
        this.costintegral = costintegral;
    }

    public Integer getFanslimit() {
        return fanslimit;
    }

    public void setFanslimit(Integer fanslimit) {
        this.fanslimit = fanslimit;
    }

    public Integer getSexlimit() {
        return sexlimit;
    }

    public void setSexlimit(Integer sexlimit) {
        this.sexlimit = sexlimit;
    }

    public Integer getWaitday() {
        return waitday;
    }

    public void setWaitday(Integer waitday) {
        this.waitday = waitday;
    }

    public Integer getOpenexamineimg() {
        return openexamineimg;
    }

    public void setOpenexamineimg(Integer openexamineimg) {
        this.openexamineimg = openexamineimg;
    }

    public String getExamineimg() {
        return examineimg;
    }

    public void setExamineimg(String examineimg) {
        this.examineimg = examineimg == null ? null : examineimg.trim();
    }

    public Integer getOpentype() {
        return opentype;
    }

    public void setOpentype(Integer opentype) {
        this.opentype = opentype;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTaocode() {
        return taocode;
    }

    public void setTaocode(String taocode) {
        this.taocode = taocode == null ? null : taocode.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setDemoPics(List<TTrialActivePic> demoPics) {
        this.demoPics = demoPics;
    }

    public List<TTrialActivePic> getDemoPics() {
        return demoPics;
    }
}