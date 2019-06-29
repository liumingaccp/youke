package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/24
 * Time: 15:23
 */
public class WealIntegralQueryVo implements Serializable {
    private String wxFansName;//模糊查询
    private String openId;//精确查询
    private Integer type;
    private Integer comeType;
    private String timeBeg;
    private String timeEnd;

    private Integer page = 1;
    private Integer limit = 20;

    private String appId;
    private String dykId;

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = StringUtils.hasLength(wxFansName)? "%" + wxFansName.trim() + "%" : null;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getComeType() {
        return comeType;
    }

    public void setComeType(Integer comeType) {
        this.comeType = comeType;
    }

    public String getTimeBeg() {
        return timeBeg;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = StringUtils.hasLength(timeBeg)? timeBeg : null;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = StringUtils.hasLength(timeEnd)? timeEnd : null;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
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

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId) ? openId.trim() : null;
    }

    public String getOpenId() {
        return openId;
    }
}
