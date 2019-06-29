package youke.facade.wx.vo.material;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class NewsVo implements Serializable {

    private Integer id;

    private String title;

    private String author;

    private boolean showCover;

    private String intro;

    private String content;

    private String link;

    private String thumbUrl;

    private String wxThumbUrl;

    private String url;

    private String thumbMediaId;

    private String mediaId;

    private String appId;

    private Date createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = !StringUtils.hasLength(title) ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = !StringUtils.hasLength(author) ? null : author.trim();
    }

    public boolean isShowCover() {
        return showCover;
    }

    public void setShowCover(boolean showCover) {
        this.showCover = showCover;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = !StringUtils.hasLength(intro) ? null : intro.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = !StringUtils.hasLength(content) ? null : content.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = !StringUtils.hasLength(link) ? null : link.trim();
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = !StringUtils.hasLength(thumbUrl) ? null : thumbUrl.trim();
    }

    public String getWxThumbUrl() {
        return thumbUrl;
    }

    public void setWxThumbUrl(String wxThumbUrl) {
        this.wxThumbUrl = !StringUtils.hasLength(wxThumbUrl) ? null : wxThumbUrl.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = !StringUtils.hasLength(url) ? null : url.trim();
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = !StringUtils.hasLength(thumbMediaId) ? null : thumbMediaId.trim();
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

}
