package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TWxPerCircleMarketTask implements Serializable{
    private Long id;

    private String deviceids;

    private Integer type;

    private Integer linktype;

    private String media;

    private Integer mediaid;

    private String content;

    private String comment;

    private Date pushtime;

    private Integer delay;

    private Date createtime;

    private Integer state;

    private String youkeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceids() {
        return deviceids;
    }

    public void setDeviceids(String deviceids) {
        this.deviceids = deviceids == null ? null : deviceids.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinktype() {
        return linktype;
    }

    public void setLinktype(Integer linktype) {
        this.linktype = linktype;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media == null ? null : media.trim();
    }

    public Integer getMediaid() {
        return mediaid;
    }

    public void setMediaid(Integer mediaid) {
        this.mediaid = mediaid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getPushtime() {
        return pushtime;
    }

    public void setPushtime(Date pushtime) {
        this.pushtime = pushtime;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}