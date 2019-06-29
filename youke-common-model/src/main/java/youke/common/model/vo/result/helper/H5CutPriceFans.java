package youke.common.model.vo.result.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * 砍价粉丝列表
 */
public class H5CutPriceFans implements Serializable {
    private String openId;
    private String wxName;
    private String wxFace;
    private Integer cutPrice;
    private Date createTime;

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public void setWxFace(String wxFace) {
        this.wxFace = wxFace;
    }

    public void setCutPrice(Integer cutPrice) {
        this.cutPrice = cutPrice;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openId;
    }

    public String getWxName() {
        return wxName;
    }

    public String getWxFace() {
        return wxFace;
    }

    public Integer getCutPrice() {
        return cutPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
