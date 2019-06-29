package youke.facade.wx.vo;

import java.io.Serializable;

public class MediaVo implements Serializable {

    private String mediaId;

    //本地路径
    private String url;

    /**
     * 微信路径
     */
    private String wxUrl;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }
}
