package youke.common.model.vo.result;

import java.io.Serializable;

public class MoneyRecordVo implements Serializable {
    private Long id;

    private String openId;

    private int comeType;

    private String title;

    private Integer money;

    private String createTime;

    public MoneyRecordVo(Long id, String openId, int comeType, String title, Integer money, String createTime) {
        this.id = id;
        this.openId = openId;
        this.comeType = comeType;
        this.title = title;
        this.money = money;
        this.createTime = createTime;
    }

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

    public int getComeType() {
        return comeType;
    }

    public void setComeType(int comeType) {
        this.comeType = comeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
