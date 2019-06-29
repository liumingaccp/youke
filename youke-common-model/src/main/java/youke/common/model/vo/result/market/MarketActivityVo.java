package youke.common.model.vo.result.market;

import java.io.Serializable;
import java.util.Date;


public class MarketActivityVo implements Serializable {
    private Long id;                    //活动id
    private String title;               //活动标题
    private Integer type;               //活动类型
    private Date begTime;               //开始时间
    private Date endTime;               //结束时间
    private Date createTime;            //创建时间
    private Date updateTime;            //更新时间
    private Integer pv;                 //页面访问量
    private Integer uv;                 //客户访问量
    private String preUrl;              //预览页面url
    private String preCodeUrl;          //预览二维码url
    private Integer state;              //活动状态
    private Integer rewardType;         //奖励

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getPreCodeUrl() {
        return preCodeUrl;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public void setPreCodeUrl(String preCodeUrl) {
        this.preCodeUrl = preCodeUrl;
    }

    public String getPreUrl() {
        return preUrl;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public Date getEndTime() {
        return endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getPv() {
        return pv;
    }

    public Integer getUv() {
        return uv;
    }

    public Integer getState() {
        return state;
    }
}
