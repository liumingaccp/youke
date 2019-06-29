package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TFollowActive implements Serializable {
    private Integer id;

    private String cover;

    private String title;

    private Date starttime;

    private Date endtime;

    private String slogan;

    private String followmsg;

    private String noticemsg;

    private Integer backMoney;

    private Integer state;

    private Date createtime;

    private String appid;

    private String youkeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public String getFollowmsg() {
        return followmsg;
    }

    public void setFollowmsg(String followmsg) {
        this.followmsg = followmsg == null ? null : followmsg.trim();
    }

    public String getNoticemsg() {
        return noticemsg;
    }

    public void setNoticemsg(String noticemsg) {
        this.noticemsg = noticemsg == null ? null : noticemsg.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}