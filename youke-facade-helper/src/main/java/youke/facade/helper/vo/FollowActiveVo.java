package youke.facade.helper.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 15:58
 */
public class FollowActiveVo implements Serializable {
    private Integer id;
    private String cover;
    private String title;
    private String startTime;
    private String endTime;
    private Integer backMoney;
    private String followMsg;
    private String noticeMsg;
    private String slogan;

    private Integer state;
    private String appId;
    private String youkeId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public String getFollowMsg() {
        return followMsg;
    }

    public void setFollowMsg(String followMsg) {
        this.followMsg = followMsg;
    }

    public String getNoticeMsg() {
        return noticeMsg;
    }

    public void setNoticeMsg(String noticeMsg) {
        this.noticeMsg = noticeMsg;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
}
