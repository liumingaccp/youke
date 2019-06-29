package youke.common.model.vo.param.helper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:48
 */
public class CollageOrderQueryVo extends QueryObjectVO implements Serializable {

    private String title;
    private String wxFansName;
    private Integer state = -1;
    private String orderNo;
    private Integer shopId = -1;
    private String timeBeg;
    private String timeEnd;

    private String appId;
    private String youkeId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = hasLength(title)?"%"+ title +"%":null;
    }

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = hasLength(wxFansName)?"%"+ wxFansName +"%":null;;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        if(state != null && state > -1){
            this.state = state;
        }
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = hasLength(orderNo)?orderNo:null;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
       if(shopId != null && shopId > -1){
           this.shopId = shopId;
       }
    }

    public String getTimeBeg() {
        return timeBeg;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = hasLength(timeBeg)?timeBeg:null;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = hasLength(timeEnd)?timeEnd:null;
    }
}
