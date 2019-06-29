package youke.common.model.vo.param.helper;

import net.sf.json.JSONArray;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/9
 * Time: 16:38
 */
public class TrialOrderPicsVo implements Serializable{
    private String appId;
    private Long activeId;
    private Long orderId;
    private String openId;
    private JSONArray demoPics;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public JSONArray getDemoPics() {
        return demoPics;
    }

    public void setDemoPics(JSONArray demoPics) {
        this.demoPics = demoPics;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
