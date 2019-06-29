package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/9
 * Time: 11:38
 */
public class FollowOrderPosterVo implements Serializable {
    private String openId;
    private String appId;
    private Integer activeId;
    private String codeUrl;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId:null;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = StringUtils.hasLength(appId)?appId:null;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = StringUtils.hasLength(codeUrl)?codeUrl:null;
    }
}
