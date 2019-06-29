package youke.common.queue.message;

import java.io.Serializable;

/**
 * pay_wx
 */
public class NewsMsgMessage implements Serializable {

    private String openId;
    private String appId;
    // 图文消息名称
    private String title;
    // 图文消息描述
    private String description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大360*200，小200*200
    private String picUrl;
    // 点击图文消息跳转链接
    private String url;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
