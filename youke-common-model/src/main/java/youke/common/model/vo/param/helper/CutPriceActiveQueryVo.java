package youke.common.model.vo.param.helper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * 砍价活动列表查询Vo
 */
public class CutPriceActiveQueryVo extends QueryObjectVO implements Serializable {
    private Integer state;
    private Integer shopId;
    private String title;
    private String goodsId;
    private String begTime;
    private String endTime;
    private String dykId;

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getTitle() {
        return empty2null(title);
    }

    public String getGoodsId() {
        return empty2null(goodsId);
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public String getEndTime() {
        return empty2null(endTime);
    }
}
