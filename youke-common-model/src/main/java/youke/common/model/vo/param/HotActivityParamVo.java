package youke.common.model.vo.param;

import java.io.Serializable;

/**
 * 热门活动通用参数实体类
 */
public class HotActivityParamVo implements Serializable {
    private String appId;           //公众号id
    private Long activeId;          //活动id
    private String openId;          //微信openId
    private String orderno;         //订单号
    private String userPics;        //用户上传截图
    private String picIds;          //宝贝id,多个使用英文逗号分开
    private String mobile;          //手机号
    private String code;            //验证码
    private Integer type;               //活动类型
    private String timestamp;       //时间戳

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    public String getPicIds() {
        return picIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setUserPics(String userPics) {
        this.userPics = userPics;
    }

    public String getOrderno() {
        return orderno;
    }

    public String getUserPics() {
        return userPics;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public Long getActiveId() {
        return activeId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
