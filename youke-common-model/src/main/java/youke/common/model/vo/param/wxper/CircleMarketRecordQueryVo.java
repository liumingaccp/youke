package youke.common.model.vo.param.wxper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CircleMarketRecordQueryVo extends QueryObjectVO implements Serializable {
    private String pushTimeBeg;
    private String pushTimeEnd;
    private String startTime;
    private String endTime;
    private String createTimeBeg;
    private String createTimeEnd;
    private Integer state;
    private String deviceIds;
    private String youkeId;
    private List<String> deviceList = new ArrayList<>();

    public void setDeviceList(List<String> deviceList) {
        this.deviceList = deviceList;
    }

    public List<String> getDeviceList() {
        return deviceList;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setPushTimeBeg(String pushTimeBeg) {
        this.pushTimeBeg = pushTimeBeg;
    }

    public void setPushTimeEnd(String pushTimeEnd) {
        this.pushTimeEnd = pushTimeEnd;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCreateTimeBeg(String createTimeBeg) {
        this.createTimeBeg = createTimeBeg;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getPushTimeBeg() {
        return empty2null(pushTimeBeg);
    }

    public String getPushTimeEnd() {
        return empty2null(pushTimeEnd);
    }

    public String getStartTime() {
        return empty2null(startTime);
    }

    public String getEndTime() {
        return empty2null(endTime);
    }

    public String getCreateTimeBeg() {
        return empty2null(createTimeBeg);
    }

    public String getCreateTimeEnd() {
        return empty2null(createTimeEnd);
    }

    public Integer getState() {
        return state;
    }

    public String getDeviceIds() {
        return empty2null(deviceIds);
    }
}
