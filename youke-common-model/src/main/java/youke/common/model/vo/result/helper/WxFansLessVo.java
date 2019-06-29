package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/14
 * Time: 9:59
 */
public class WxFansLessVo implements Serializable {
    private String openId;
    private String wxName;
    private String wxFace;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxFace() {
        return wxFace;
    }

    public void setWxFace(String wxFace) {
        this.wxFace = wxFace;
    }
}
