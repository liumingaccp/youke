package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * 群发临时名单结果集
 */
public class MassTempListVo implements Serializable {
    private static final long serialVersionUID = 3123817712041140564L;

    private Long id;                    //fansId
    private String nickname;            //昵称
    private String truename;            //真实姓名
    private Date lastTime;              //最后交易时间
    private String mobile;              //手机号
    private Integer shopId;             //店铺id

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTruename() {
        return truename;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public String getMobile() {
        return mobile;
    }


    @Override
    public String toString() {
        return "MassTempListVo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", truename='" + truename + '\'' +
                ", lastTime=" + lastTime +
                ", mobile='" + mobile + '\'' +
                ", shopId=" + shopId +
                '}';
    }
}
