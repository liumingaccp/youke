package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerDevice implements Serializable {
    private Long id;

    private String clientid;

    private Long wxaccountid;

    private String wxpersonal;

    private String wechatnum;

    private String devicename;

    private Integer fansnum;

    private String phonemodel;

    private String imel;

    private Date createtime;

    private Date lasttime;

    private Integer state;

    private String remark;

    private String recorddetail;

    private String youkeid;

    public void setWechatnum(String wechatnum) {
        this.wechatnum = wechatnum;
    }

    public String getWechatnum() {
        return wechatnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid == null ? null : clientid.trim();
    }

    public Long getWxaccountid() {
        return wxaccountid;
    }

    public void setWxaccountid(Long wxaccountid) {
        this.wxaccountid = wxaccountid;
    }

    public String getWxpersonal() {
        return wxpersonal;
    }

    public void setWxpersonal(String wxpersonal) {
        this.wxpersonal = wxpersonal == null ? null : wxpersonal.trim();
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename == null ? null : devicename.trim();
    }

    public Integer getFansnum() {
        return fansnum;
    }

    public void setFansnum(Integer fansnum) {
        this.fansnum = fansnum;
    }

    public String getPhonemodel() {
        return phonemodel;
    }

    public void setPhonemodel(String phonemodel) {
        this.phonemodel = phonemodel == null ? null : phonemodel.trim();
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel == null ? null : imel.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRecorddetail() {
        return recorddetail;
    }

    public void setRecorddetail(String recorddetail) {
        this.recorddetail = recorddetail == null ? null : recorddetail.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}