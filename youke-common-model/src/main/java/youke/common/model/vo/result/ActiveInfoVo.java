package youke.common.model.vo.result;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Pc活动编辑通用类
 */
public class ActiveInfoVo implements Serializable {
    private Long id;

    private String title;

    private String cover;

    private Integer type;

    private Date begtime;

    private Date endtime;

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

    private Integer opentype;

    private String filename;

    private String fileurl;

    private Integer openminlimit;

    private Integer openmidlimit;

    private Integer openmaxlimit;

    private Integer minlimit;

    private Integer minmoneyback;

    private Integer midlimitbeg;

    private Integer midlimitend;

    private Integer midmoneyback;

    private Integer midrandmoneybeg;

    private Integer midrandmoneyend;

    private Integer maxlimit;

    private Integer maxmoneyback;

    private Integer maxrandmoneybeg;

    private Integer maxrandmoneyend;

    private Integer costIntegral;

    private List<Map<String, Object>> demoPics = new ArrayList<>();

    private List<Map<String, Integer>> rule = new ArrayList<>();

    private List<Map<String, Object>> prize = new ArrayList<>();

    private List<Map<String, Object>> inputOrders = new ArrayList<>();

    private List<Map<String,Object>> redRules = new ArrayList<>();

    private JSONArray intro;

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public List<Map<String, Object>> getRedRules() {
        return redRules;
    }

    public void setRedRules(List<Map<String, Object>> redRules) {
        this.redRules = redRules;
    }

    public void setInputOrders(List<Map<String, Object>> inputOrders) {
        this.inputOrders = inputOrders;
    }

    public List<Map<String, Object>> getInputOrders() {
        return inputOrders;
    }

    public void setCostIntegral(Integer costIntegral) {
        this.costIntegral = costIntegral;
    }

    public Integer getCostIntegral() {
        return costIntegral;
    }

    public void setPrize(List<Map<String, Object>> prize) {
        this.prize = prize;
    }

    public List<Map<String, Object>> getPrize() {
        return prize;
    }

    public void setRule(List<Map<String, Integer>> rule) {
        this.rule = rule;
    }

    public List<Map<String, Integer>> getRule() {
        return rule;
    }

    public List<Map<String, Object>> getDemoPics() {
        return demoPics;
    }

    public void setDemoPics(List<Map<String, Object>> demoPics) {
        this.demoPics = demoPics;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setBegtime(Date begtime) {
        this.begtime = begtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public void setRewardtype(Integer rewardtype) {
        this.rewardtype = rewardtype;
    }

    public void setFixedmoney(Integer fixedmoney) {
        this.fixedmoney = fixedmoney;
    }

    public void setMinrandmoney(Integer minrandmoney) {
        this.minrandmoney = minrandmoney;
    }

    public void setMaxrandmoney(Integer maxrandmoney) {
        this.maxrandmoney = maxrandmoney;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public void setLimitcount(Integer limitcount) {
        this.limitcount = limitcount;
    }

    public void setMaxjoin(Integer maxjoin) {
        this.maxjoin = maxjoin;
    }

    public void setFanslimit(Integer fanslimit) {
        this.fanslimit = fanslimit;
    }

    public void setExaminetype(Integer examinetype) {
        this.examinetype = examinetype;
    }

    public void setExaminehour(Integer examinehour) {
        this.examinehour = examinehour;
    }

    public void setOpentype(Integer opentype) {
        this.opentype = opentype;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public void setOpenminlimit(Integer openminlimit) {
        this.openminlimit = openminlimit;
    }

    public void setOpenmidlimit(Integer openmidlimit) {
        this.openmidlimit = openmidlimit;
    }

    public void setOpenmaxlimit(Integer openmaxlimit) {
        this.openmaxlimit = openmaxlimit;
    }

    public void setMinlimit(Integer minlimit) {
        this.minlimit = minlimit;
    }

    public void setMinmoneyback(Integer minmoneyback) {
        this.minmoneyback = minmoneyback;
    }

    public void setMidlimitbeg(Integer midlimitbeg) {
        this.midlimitbeg = midlimitbeg;
    }

    public void setMidlimitend(Integer midlimitend) {
        this.midlimitend = midlimitend;
    }

    public void setMidmoneyback(Integer midmoneyback) {
        this.midmoneyback = midmoneyback;
    }

    public void setMidrandmoneybeg(Integer midrandmoneybeg) {
        this.midrandmoneybeg = midrandmoneybeg;
    }

    public void setMidrandmoneyend(Integer midrandmoneyend) {
        this.midrandmoneyend = midrandmoneyend;
    }

    public void setMaxlimit(Integer maxlimit) {
        this.maxlimit = maxlimit;
    }

    public void setMaxmoneyback(Integer maxmoneyback) {
        this.maxmoneyback = maxmoneyback;
    }

    public void setMaxrandmoneybeg(Integer maxrandmoneybeg) {
        this.maxrandmoneybeg = maxrandmoneybeg;
    }

    public void setMaxrandmoneyend(Integer maxrandmoneyend) {
        this.maxrandmoneyend = maxrandmoneyend;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public Integer getType() {
        return type;
    }

    public Date getBegtime() {
        return begtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public Integer getRewardtype() {
        return rewardtype;
    }

    public Integer getFixedmoney() {
        return fixedmoney;
    }

    public Integer getMinrandmoney() {
        return minrandmoney;
    }

    public Integer getMaxrandmoney() {
        return maxrandmoney;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getLimitcount() {
        return limitcount;
    }

    public Integer getMaxjoin() {
        return maxjoin;
    }

    public Integer getFanslimit() {
        return fanslimit;
    }

    public Integer getExaminetype() {
        return examinetype;
    }

    public Integer getExaminehour() {
        return examinehour;
    }

    public Integer getOpentype() {
        return opentype;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public Integer getOpenminlimit() {
        return openminlimit;
    }

    public Integer getOpenmidlimit() {
        return openmidlimit;
    }

    public Integer getOpenmaxlimit() {
        return openmaxlimit;
    }

    public Integer getMinlimit() {
        return minlimit;
    }

    public Integer getMinmoneyback() {
        return minmoneyback;
    }

    public Integer getMidlimitbeg() {
        return midlimitbeg;
    }

    public Integer getMidlimitend() {
        return midlimitend;
    }

    public Integer getMidmoneyback() {
        return midmoneyback;
    }

    public Integer getMidrandmoneybeg() {
        return midrandmoneybeg;
    }

    public Integer getMidrandmoneyend() {
        return midrandmoneyend;
    }

    public Integer getMaxlimit() {
        return maxlimit;
    }

    public Integer getMaxmoneyback() {
        return maxmoneyback;
    }

    public Integer getMaxrandmoneybeg() {
        return maxrandmoneybeg;
    }

    public Integer getMaxrandmoneyend() {
        return maxrandmoneyend;
    }

    @Override
    public String toString() {
        return "ActiveInfoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", type=" + type +
                ", intro='" + intro + '\'' +
                ", begtime=" + begtime +
                ", endtime=" + endtime +
                ", rewardtype=" + rewardtype +
                ", fixedmoney=" + fixedmoney +
                ", minrandmoney=" + minrandmoney +
                ", maxrandmoney=" + maxrandmoney +
                ", integral=" + integral +
                ", limitcount=" + limitcount +
                ", maxjoin=" + maxjoin +
                ", fanslimit=" + fanslimit +
                ", examinetype=" + examinetype +
                ", examinehour=" + examinehour +
                ", opentype=" + opentype +
                ", filename='" + filename + '\'' +
                ", fileurl='" + fileurl + '\'' +
                ", openminlimit=" + openminlimit +
                ", openmidlimit=" + openmidlimit +
                ", openmaxlimit=" + openmaxlimit +
                ", minlimit=" + minlimit +
                ", minmoneyback=" + minmoneyback +
                ", midlimitbeg=" + midlimitbeg +
                ", midlimitend=" + midlimitend +
                ", midmoneyback=" + midmoneyback +
                ", midrandmoneybeg=" + midrandmoneybeg +
                ", midrandmoneyend=" + midrandmoneyend +
                ", maxlimit=" + maxlimit +
                ", maxmoneyback=" + maxmoneyback +
                ", maxrandmoneybeg=" + maxrandmoneybeg +
                ", maxrandmoneyend=" + maxrandmoneyend +
                ", costIntegral=" + costIntegral +
                ", demoPics=" + demoPics +
                ", rule=" + rule +
                ", prize=" + prize +
                '}';
    }
}
