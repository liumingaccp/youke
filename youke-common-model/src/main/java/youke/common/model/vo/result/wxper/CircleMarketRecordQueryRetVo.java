package youke.common.model.vo.result.wxper;

import java.io.Serializable;
import java.util.Date;

public class CircleMarketRecordQueryRetVo implements Serializable {
    private Long id;
    private String wxPersonal;
    private Date createTime;
    private Date sendTime;
    private Date endTime;
    private Integer type;
    private Integer linkType;
    private String url;
    private Integer mediaId;
    private String content;
    private Integer state;
    private Integer likeNum;
    private Integer commentNum;


    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Integer getType() {
        return type == 2 ? (linkType == 0 ? 2 : 3) : type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWxPersonal(String wxPersonal) {
        this.wxPersonal = wxPersonal;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Long getId() {
        return id;
    }

    public String getWxPersonal() {
        return wxPersonal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getContent() {
        return getType() + content;
    }

    public Integer getState() {
        return state;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }
}
