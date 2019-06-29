package youke.common.model;

import javax.ws.rs.GET;
import java.io.Serializable;
import java.util.Date;

public class TMassTask implements Serializable {
    private Integer id;

    private String tagIds;

    private Integer tagfilter;

    private Integer limitsend;

    private Date lasttimebeg;

    private Date lasttimeend;

    private Date subtimebeg;

    private Date subtimeend;

    private Integer sex;

    private Integer hasmobile;

    private String province;

    private String city;

    private String mediatype;

    private String content;

    private Integer materialid;

    private Date tasktime;

    private Integer state;

    private String appid;

    private Integer sendType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds == null ? null : tagIds.trim();
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

    public Date getLasttimebeg() {
        return lasttimebeg;
    }

    public void setLasttimebeg(Date lasttimebeg) {
        this.lasttimebeg = lasttimebeg;
    }

    public Date getLasttimeend() {
        return lasttimeend;
    }

    public void setLasttimeend(Date lasttimeend) {
        this.lasttimeend = lasttimeend;
    }

    public Date getSubtimebeg() {
        return subtimebeg;
    }

    public void setSubtimebeg(Date subtimebeg) {
        this.subtimebeg = subtimebeg;
    }

    public Date getSubtimeend() {
        return subtimeend;
    }

    public void setSubtimeend(Date subtimeend) {
        this.subtimeend = subtimeend;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getHasmobile() {
        return hasmobile;
    }

    public void setHasmobile(Integer hasmobile) {
        this.hasmobile = hasmobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype == null ? null : mediatype.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public Date getTasktime() {
        return tasktime;
    }

    public void setTasktime(Date tasktime) {
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
        this.appid = appid == null ? null : appid.trim();
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getSendType() {
        return sendType;
    }


}