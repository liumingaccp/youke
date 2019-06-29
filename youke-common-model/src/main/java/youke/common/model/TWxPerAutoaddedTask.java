package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoaddedTask implements Serializable {
    private Long id;

    private String deviceids;

    private Long deviceid;

    private Long wxaccountid;

    private String verifyinfo;

    private String mobiles;

    private String url;

    private Integer taskpriority;

    private Integer state;

    private Long groupid;

    private Date createtime;

    private Date updatetime;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceids() {
        return deviceids;
    }

    public void setDeviceids(String deviceids) {
        this.deviceids = deviceids == null ? null : deviceids.trim();
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Long getWxaccountid() {
        return wxaccountid;
    }

    public void setWxaccountid(Long wxaccountid) {
        this.wxaccountid = wxaccountid;
    }

    public String getVerifyinfo() {
        return verifyinfo;
    }

    public void setVerifyinfo(String verifyinfo) {
        this.verifyinfo = verifyinfo == null ? null : verifyinfo.trim();
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles == null ? null : mobiles.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getTaskpriority() {
        return taskpriority;
    }

    public void setTaskpriority(Integer taskpriority) {
        this.taskpriority = taskpriority;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}