package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/27
 * Time: 18:31
 */
public class SubFansIntegralVo implements Serializable {
    private Long id;

    private String openid;

    private String title;

    private Integer integral;

    private Date createtime;

    private String wxfansName;

    private int comeType;

    private String comeTypeDisplay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public String getWxfansName() {
        return wxfansName;
    }

    public void setComeType(int comeType) {
        this.comeType = comeType;
    }

    public int getComeType() {
        return comeType;
    }

    public void setComeTypeDisplay(String comeTypeDisplay) {
        this.comeTypeDisplay = comeTypeDisplay;
    }

    public String getComeTypeDisplay() {
        return comeTypeDisplay;
    }
}
