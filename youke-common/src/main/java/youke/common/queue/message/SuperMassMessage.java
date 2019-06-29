package youke.common.queue.message;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/9
 * Time: 15:03
 */
public class SuperMassMessage implements Serializable{
    private Integer massTaskId;

    private String tagids;

    private Integer tagfilter;

    private Integer limitsend;

    private String lasttimebeg;

    private String lasttimeend;

    private String subtimebeg;

    private String subtimeend;

    private Integer sex;

    private String province;

    private String city;

    private String mediatype;

    private String content;

    private Integer materialid;

    private String tasktime;

    private Integer state;

    private String appid;

    private String nickname;

    private Integer sendType;

    public Integer getMassTaskId() {
        return massTaskId;
    }

    public void setMassTaskId(Integer massTaskId) {
        this.massTaskId = massTaskId;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public Integer getTagfilter() {
        return tagfilter;
    }

    public void setTagfilter(Integer tagfilter) {
        this.tagfilter = tagfilter;
    }

    public Integer getLimitsend() {
        return limitsend;
    }

    public void setLimitsend(Integer limitsend) {
        this.limitsend = limitsend;
    }

    public String getLasttimebeg() {
        return lasttimebeg;
    }

    public void setLasttimebeg(String lasttimebeg) {
        this.lasttimebeg = lasttimebeg;
    }

    public String getLasttimeend() {
        return lasttimeend;
    }

    public void setLasttimeend(String lasttimeend) {
        this.lasttimeend = lasttimeend;
    }

    public String getSubtimebeg() {
        return subtimebeg;
    }

    public void setSubtimebeg(String subtimebeg) {
        this.subtimebeg = subtimebeg;
    }

    public String getSubtimeend() {
        return subtimeend;
    }

    public void setSubtimeend(String subtimeend) {
        this.subtimeend = subtimeend;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    @Override
    public String toString() {
        return "SuperMassMessage{" +
                "massTaskId=" + massTaskId +
                ", tagids='" + tagids + '\'' +
                ", tagfilter=" + tagfilter +
                ", limitsend=" + limitsend +
                ", lasttimebeg='" + lasttimebeg + '\'' +
                ", lasttimeend='" + lasttimeend + '\'' +
                ", subtimebeg='" + subtimebeg + '\'' +
                ", subtimeend='" + subtimeend + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", mediatype='" + mediatype + '\'' +
                ", content='" + content + '\'' +
                ", materialid=" + materialid +
                ", tasktime='" + tasktime + '\'' +
                ", state=" + state +
                ", appid='" + appid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sendType=" + sendType +
                '}';
    }
}
