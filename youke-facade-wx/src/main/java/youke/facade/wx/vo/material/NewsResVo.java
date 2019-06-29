package youke.facade.wx.vo.material;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

public class NewsResVo implements Serializable {

    private Integer id;

    private String title;

    private String intro;

    private String thumbUrl;

    private String wxThumbUrl;

    private String url;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getWxThumbUrl() {
        return wxThumbUrl;
    }

    public void setWxThumbUrl(String wxThumbUrl) {
        this.wxThumbUrl = wxThumbUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
