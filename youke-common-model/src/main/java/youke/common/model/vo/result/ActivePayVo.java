package youke.common.model.vo.result;

import java.io.Serializable;

public class ActivePayVo implements Serializable {

    private Long id;

    private String openId;

    private String tOpenId;

    private Integer money = 0;

    private Integer commision = 0;

    private String appId;

    private String youkeId;

    private int comeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String gettOpenId() {
        return tOpenId;
    }

    public void settOpenId(String tOpenId) {
        this.tOpenId = tOpenId;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public int getComeType() {
        return comeType;
    }

    public void setComeType(int comeType) {
        this.comeType = comeType;
    }
}
