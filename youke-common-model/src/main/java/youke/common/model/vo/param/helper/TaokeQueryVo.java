package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 15:07
 */
public class TaokeQueryVo implements Serializable {
    private String title;
    private Integer shopId;
    private Integer state;
    private String goodId;
    private String begTime;
    private String endTime;
    private Integer page = 1;
    private Integer limit =20;

    private String appId;
    private String youkeId;
    private String type = "pc";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = StringUtils.hasLength(title)? "%" + title + "%" : null;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = StringUtils.hasLength(goodId)?goodId : null;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = StringUtils.hasLength(begTime)?begTime : null;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = StringUtils.hasLength(endTime)?endTime : null;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setType(String type) {
        if(StringUtils.hasLength(type)){
            this.type = type;
        }
    }

    public String getType() {
        return type;
    }
}
