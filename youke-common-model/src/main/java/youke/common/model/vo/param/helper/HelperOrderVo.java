package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/16
 * Time: 10:59
 */
public class HelperOrderVo implements Serializable {
    private String appId;
    private String openId;
    private String taokeOpenId;
    private Integer activeId;
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = StringUtils.hasLength(appId)?appId:null;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId:null;
    }

    public String getTaokeOpenId() {
        return taokeOpenId;
    }

    public void setTaokeOpenId(String taokeOpenId) {
        this.taokeOpenId = StringUtils.hasLength(taokeOpenId)?taokeOpenId:null;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }
}
