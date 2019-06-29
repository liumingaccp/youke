package youke.common.model;

import java.io.Serializable;

public class TWxPerDeviceGrow implements Serializable{
    private Long id;

    private Long deviceid;

    private Integer isopentask;

    private String type;

    private Integer period;

    private String invokedays;

    private Integer starthour;

    private Integer startminute;

    private Integer endhour;

    private Integer endminute;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getIsopentask() {
        return isopentask;
    }

    public void setIsopentask(Integer isopentask) {
        this.isopentask = isopentask;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getInvokedays() {
        return invokedays;
    }

    public void setInvokedays(String invokedays) {
        this.invokedays = invokedays == null ? null : invokedays.trim();
    }

    public Integer getStarthour() {
        return starthour;
    }

    public void setStarthour(Integer starthour) {
        this.starthour = starthour;
    }

    public Integer getStartminute() {
        return startminute;
    }

    public void setStartminute(Integer startminute) {
        this.startminute = startminute;
    }

    public Integer getEndhour() {
        return endhour;
    }

    public void setEndhour(Integer endhour) {
        this.endhour = endhour;
    }

    public Integer getEndminute() {
        return endminute;
    }

    public void setEndminute(Integer endminute) {
        this.endminute = endminute;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}