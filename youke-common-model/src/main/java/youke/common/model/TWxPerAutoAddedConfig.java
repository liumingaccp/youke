package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerAutoAddedConfig implements Serializable{
    private Long id;

    private Long deviceid;

    private Integer autopass;

    private Integer dailylimit;

    private Integer autoreply;

    private String content;

    private String contenttype;

    private Date createtime;

    private Date updatetime;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public Long getDeviceid() {
        return deviceid;
    }

    public Integer getAutopass() {
        return autopass;
    }

    public void setAutopass(Integer autopass) {
        this.autopass = autopass;
    }

    public Integer getAutoreply() {
        return autoreply;
    }

    public void setAutoreply(Integer autoreply) {
        this.autoreply = autoreply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setDailylimit(Integer dailylimit) {
        this.dailylimit = dailylimit;
    }

    public Integer getDailylimit() {
        return dailylimit;
    }
}