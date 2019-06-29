package youke.common.model.vo.param.helper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * 砍价活动记录分页查询Vo
 */
public class CutPriceOrderQueryVo extends QueryObjectVO implements Serializable {
    private String title;
    private String wxfansName;
    private Integer state;
    private String orderno;
    private Integer shopId;
    private String timeBeg;
    private String timeEnd;
    private String dykId;

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = timeBeg;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTitle() {
        return empty2null(title);
    }

    public String getWxfansName() {
        return empty2null(wxfansName);
    }

    public Integer getState() {
        return state;
    }

    public String getOrderno() {
        return empty2null(orderno);
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getTimeBeg() {
        return empty2null(timeBeg);
    }

    public String getTimeEnd() {
        return empty2null(timeEnd);
    }
}
