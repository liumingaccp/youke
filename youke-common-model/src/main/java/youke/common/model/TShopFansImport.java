package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TShopFansImport implements Serializable {
    private Integer id;

    private String filename;

    private String url;

    private Integer state;

    private Date createtime;

    private Date completetime;

    private Integer successnum;

    private Integer failnum;

    private String failcsvurl;

    private Integer shopid;

    private String youkeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public Integer getFailnum() {
        return failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }

    public String getFailcsvurl() {
        return failcsvurl;
    }

    public void setFailcsvurl(String failcsvurl) {
        this.failcsvurl = failcsvurl == null ? null : failcsvurl.trim();
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}