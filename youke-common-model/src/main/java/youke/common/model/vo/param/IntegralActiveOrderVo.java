package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/10
 * Time: 15:00
 */
public class IntegralActiveOrderVo implements Serializable{
    private Integer activeId;
    private String openId;
    private Integer openType;
    private String appId;

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId:null;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = StringUtils.hasLength(appId)?appId:null;
    }
}
