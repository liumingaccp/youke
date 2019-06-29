package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 20:28
 */
public class WxFansBaseQueryVo  implements Serializable {
    /**
     * 分页参数
     */
    //默认 1
    private Integer page = 1;
    //默认 20
    private Integer limit = 20;

    //最近互动时间范围开始
    protected String lastTimeBeg;
    //最近互动时间范围结束，表示1个月内的
    protected String lastTimeEnd;
    //nickname昵称,条件查询
    protected String nickname;
    //是否注册（绑定手机） 0否，1是
    protected Integer hasMobile;
    protected String province;
    protected String city;
    protected Integer sex = -1;
    //具有的标签id列表字符串
    protected String  tagIds;

    //appId
    protected String appId;


    protected List<String> tags = new ArrayList<String>();
    protected Integer tagCount;
    protected List<String> openIds = new ArrayList<String>();
    protected Integer openIdCount;

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(String tagIds) {
        if(tagIds != null){
            String[] split = tagIds.split(",");
            for(String str : split){
                this.tags.add(str);
            }
        }
    }

    public List<String> getOpenIds() {
        return openIds;
    }

    public void setOpenIds(List<String> openIds) {
        if(openIds!= null){
            this.openIds = openIds;
        }
    }

    public String getNickname() {
        return StringUtils.hasLength(this.nickname) ? nickname.trim() : null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(Integer hasMobile) {
        this.hasMobile = hasMobile;
    }

    public String getProvince() {
        return StringUtils.hasLength(this.province) ? province.trim() : null;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return StringUtils.hasLength(this.city) ? city.trim() : null;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTagIds() {
        return StringUtils.hasLength(this.tagIds) ? tagIds : null;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }

    public Integer getTagCount() {
        return tags.size();
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public Integer getOpenIdCount() {
        return openIds.size();
    }

    public void setOpenIdCount(Integer openIdCount) {
        this.openIdCount = openIdCount;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return StringUtils.hasLength(this.appId) ? appId : null;
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
}
