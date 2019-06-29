package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/14
 * Time: 14:17
 */
public class CollageTuanItem implements Serializable {
    private Integer tuanId;
    private String openId;
    private String wxFace;
    private String wxName;
    private Integer leftTuanNum;
    private Date endTime;

    public Integer getTuanId() {
        return tuanId;
    }

    public void setTuanId(Integer tuanId) {
        this.tuanId = tuanId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxFace() {
        return wxFace;
    }

    public void setWxFace(String wxFace) {
        this.wxFace = wxFace;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public Integer getLeftTuanNum() {
        return leftTuanNum;
    }

    public void setLeftTuanNum(Integer leftTuanNum) {
        this.leftTuanNum = leftTuanNum;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
