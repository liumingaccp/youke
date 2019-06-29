package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMarketActive implements Serializable {
    private Long id;

    private String title;

    private String cover;

    private Integer type;

    private String intro;

    private Date begtime;

    private Date endtime;

    private Date createtime;

    private Date updateTime;

    private Integer pv;

    private Integer uv;

    private Integer rewardtype;

    private Integer fixedmoney;

    private Integer minrandmoney;

    private Integer maxrandmoney;

    private Integer integral;

    private Integer limitcount;

    private Integer maxjoin;

    private Integer fanslimit;

    private Integer examinetype;

    private Integer examinehour;

    private Integer state;

    private Integer opentype;

    private String filename;

    private String fileurl;

    private String appid;

    private String youkeid;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Date getBegtime() {
        return begtime;
    }

    public void setBegtime(Date begtime) {
        this.begtime = begtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getRewardtype() {
        return rewardtype;
    }

    public void setRewardtype(Integer rewardtype) {
        this.rewardtype = rewardtype;
    }

    public Integer getFixedmoney() {
        return fixedmoney;
    }

    public void setFixedmoney(Integer fixedmoney) {
        this.fixedmoney = fixedmoney;
    }

    public Integer getMinrandmoney() {
        return minrandmoney;
    }

    public void setMinrandmoney(Integer minrandmoney) {
        this.minrandmoney = minrandmoney;
    }

    public Integer getMaxrandmoney() {
        return maxrandmoney;
    }

    public void setMaxrandmoney(Integer maxrandmoney) {
        this.maxrandmoney = maxrandmoney;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getLimitcount() {
        return limitcount;
    }

    public void setLimitcount(Integer limitcount) {
        this.limitcount = limitcount;
    }

    public Integer getMaxjoin() {
        return maxjoin;
    }

    public void setMaxjoin(Integer maxjoin) {
        this.maxjoin = maxjoin;
    }

    public Integer getFanslimit() {
        return fanslimit;
    }

    public void setFanslimit(Integer fanslimit) {
        this.fanslimit = fanslimit;
    }

    public Integer getExaminetype() {
        return examinetype;
    }

    public void setExaminetype(Integer examinetype) {
        this.examinetype = examinetype;
    }

    public Integer getExaminehour() {
        return examinehour;
    }

    public void setExaminehour(Integer examinehour) {
        this.examinehour = examinehour;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOpentype() {
        return opentype;
    }

    public void setOpentype(Integer opentype) {
        this.opentype = opentype;
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