package youke.common.model.vo.param.helper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:48
 */
public class CollageQueryVo extends QueryObjectVO implements Serializable {
    private String appId;
    private String youkeId;

    private String title;
    private Integer shopId = -1;
    private String goodsId;
    private Integer state = -1;
    private String begTime;
    private String endTime;

    private String openId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = hasLength(title) ? "%"+ title +"%" : null;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        if(shopId != null && shopId > -1){
            this.shopId = shopId;
        }
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = empty2null(goodsId);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = empty2null(begTime);
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = empty2null(appId);
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = empty2null(youkeId);
    }

    public void setEndTime(String endTime) {
        this.endTime = empty2null(endTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }
}
