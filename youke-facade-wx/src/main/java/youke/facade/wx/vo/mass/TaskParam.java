package youke.facade.wx.vo.mass;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/9
 * Time: 12:18
 */
public class TaskParam implements Serializable{
    //具有的标签id列表字符串
    private String tagIds;
    private Integer tagFilter;
    private Integer limitSend;
    //最近互动时间范围开始
    private String lastTimeBeg;
    //最近互动时间范围结束，表示1个月内的
    private String lastTimeEnd;
    private String subTimeBeg;
    private String subTimeEnd;
    private Integer sex;
    private String province;
    private String city;
    private String mediaType;
    private String content;
    private Integer materialId;
    private String taskTime;
    private String nickname;
    private Integer hasMobile;
    private String appId;

    public void setHasMobile(Integer hasMobile) {
        if(hasMobile != null){
            this.hasMobile = hasMobile;
        }
    }

    public Integer getHasMobile() {
        return hasMobile;
    }

    public String getTagIds() {
        return StringUtils.hasLength(this.tagIds) ? tagIds : null;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getTagFilter() {
        return tagFilter;
    }

    public void setTagFilter(Integer tagFilter) {
        this.tagFilter = tagFilter;
    }

    public Integer getLimitSend() {
        return limitSend;
    }

    public void setLimitSend(Integer limitSend) {
        this.limitSend = limitSend;
    }

    public String getLastTimeBeg() {
        return StringUtils.hasLength(this.lastTimeBeg) ? lastTimeBeg : null;
    }

    public void setLastTimeBeg(String lastTimeBeg) {
        this.lastTimeBeg = lastTimeBeg;
    }

    public String getLastTimeEnd() {
        return StringUtils.hasLength(this.lastTimeEnd) ? lastTimeEnd : null;
    }

    public void setLastTimeEnd(String lastTimeEnd) {
        this.lastTimeEnd = lastTimeEnd;
    }

    public String getSubTimeBeg() {
        return StringUtils.hasLength(this.subTimeBeg) ? subTimeBeg : null;
    }

    public void setSubTimeBeg(String subTimeBeg) {
        this.subTimeBeg = subTimeBeg;
    }

    public String getSubTimeEnd() {
        return StringUtils.hasLength(this.subTimeEnd) ? subTimeEnd : null;
    }

    public void setSubTimeEnd(String subTimeEnd) {
        this.subTimeEnd = subTimeEnd;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return StringUtils.hasLength(this.province) ? province : null;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return StringUtils.hasLength(this.city) ? city : null;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMediaType() {
        return StringUtils.hasLength(this.mediaType) ? mediaType : null;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContent() {
        return StringUtils.hasLength(this.content) ? content : null;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getTaskTime() {
        return StringUtils.hasLength(this.taskTime) ? taskTime : null;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getNickname() {
        return StringUtils.hasLength(this.nickname) ? nickname : null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return StringUtils.hasLength(this.appId) ? appId : null;
    }
}
