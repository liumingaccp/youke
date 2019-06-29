package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class IntegralRecordVo implements Serializable {
    private Long id;

    private String openId;

    private int comeType;

    private String title;

    private Integer integral;

    private String createTime;

    public IntegralRecordVo(Long id, String openId, int comeType, String title, Integer integral, String createTime) {
        this.id = id;
        this.openId = openId;
        this.comeType = comeType;
        this.title = title;
        this.integral = integral;
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
