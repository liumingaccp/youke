package youke.common.model.vo.result.helper;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 17:14
 */
public class FollowOrderQueryRetVo implements Serializable {
    private Integer id;
    private String wxfansName;
    private String mobile;
    private Integer backMoney;
    private Date backTime;

    private String followName;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxfansName() {
        return wxfansName;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Integer backMoney) {
        this.backMoney = backMoney;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getRemark() {
        return "推广"+ this.followName + "关注";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getFollowName() {
        return followName;
    }
}
