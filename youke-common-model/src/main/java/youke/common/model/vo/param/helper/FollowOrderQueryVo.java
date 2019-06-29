package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class FollowOrderQueryVo implements Serializable {
    private String wxFansName;
    private String mobile;

    private Integer page = 1;
    private Integer limit =20;

    private String appId;
    private String youkeId;

    private String openId;

    public String getWxFansName() {
        return wxFansName;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = StringUtils.hasLength(wxFansName)? "%" + wxFansName + "%" : null;
    }

    public void setMobile(String mobile) {
        this.mobile = StringUtils.hasLength(mobile)? mobile : null;
    }

    public String getMobile() {
        return mobile;
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
        if(limit != null){
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

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId:null;
    }

    public String getOpenId() {
        return openId;
    }
}
