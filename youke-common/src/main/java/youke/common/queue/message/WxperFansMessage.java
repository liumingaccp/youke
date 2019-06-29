package youke.common.queue.message;

import java.io.Serializable;

/**
 * 微信个人号粉丝
 */
public class WxperFansMessage implements Serializable {

    private String youkeId;

    private String weixinId;

    private String nickName;

    private String mobile;

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
