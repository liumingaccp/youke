package youke.common.model.vo.param.wxper;

import java.io.Serializable;

public class CircleMessageSaveVo implements Serializable {
    private Integer type;
    private Integer linkType;
    private String media;
    private Integer mediaId;
    private String content;
    private String comment;
    private String deviceIds;
    private String pushTime;
    private Integer delay;

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getType() {
        return type;
    }

    public String getMedia() {
        return media;
    }

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public Integer getDelay() {
        return delay;
    }
}
