package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoLikeTask implements Serializable {
    private Long id;

    private Integer isopentask;

    private Integer state;

    private Long deviceid;

    private String devicename;

    private Integer type;

    private Date begtime;

    private Date endtime;

    private Integer likenum;

    private Date createtime;

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

    public Integer getIsopentask() {
        return isopentask;
    }

    public void setIsopentask(Integer isopentask) {
        this.isopentask = isopentask;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename == null ? null : devicename.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getBegtime() {
        return begtime;
    }

    public void setBegtime(Date begtime) {
        this.begtime = begtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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