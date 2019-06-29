package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 18:33
 */
public class BaseOrderExamineParam implements Serializable {
    protected String appId;

    protected String orderIds;

    protected Integer state;

    protected String failReason;

    protected List<String> orderIdList;

    public void setAppId(String appId) {
        this.appId = StringUtils.hasLength(appId) ? appId : null;
    }

    public String getAppId() {
        return appId;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = StringUtils.hasLength(failReason) ? failReason : null;
    }

    public List<String> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<String> orderIdList) {
        this.orderIdList = orderIdList;
    }
}
