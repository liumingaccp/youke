package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TSubscrTemplate implements Serializable {
    private Long id;

    private String appid;

    private String youkeid;

    private String templateid;

    private String templateshort;

    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid == null ? null : templateid.trim();
    }

    public String getTemplateshort() {
        return templateshort;
    }

    public void setTemplateshort(String templateshort) {
        this.templateshort = templateshort == null ? null : templateshort.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}