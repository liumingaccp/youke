package youke.common.model.vo.result.helper;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 15:17
 */
public class FollowQueryRetVo implements Serializable {
    private Integer id;
    private String title;
    private Date starTtime;
    private Date endTime;
    private Integer state;
    private Integer commision;

    private String slogan;

    private String headImg;      //公众号的图片地址
    private String name;        //公众号名称
    private String preUrl;
    private String preCodeUrl;
    private String stateDisplay;
    private Integer posterNum = 0;
    private Integer totalCommision = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStarTtime() {
        return starTtime;
    }

    public void setStarTtime(Date starTtime) {
        this.starTtime = starTtime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPreUrl() {
        return preUrl;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public String getPreCodeUrl() {
        return preCodeUrl;
    }

    public void setPreCodeUrl(String preCodeUrl) {
        this.preCodeUrl = preCodeUrl;
    }

    public void setStateDisplay(String stateDisplay) {
        this.stateDisplay = stateDisplay;
    }

    public String getStateDisplay() {
        return stateDisplay;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Integer getPosterNum() {
        return posterNum;
    }

    public void setPosterNum(Integer posterNum) {
        this.posterNum = posterNum;
    }

    public Integer getTotalCommision() {
        return totalCommision;
    }

    public void setTotalCommision(Integer totalCommision) {
        this.totalCommision = totalCommision;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
