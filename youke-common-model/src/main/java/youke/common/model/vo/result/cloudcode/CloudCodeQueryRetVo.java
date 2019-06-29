package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;
import java.util.Date;

/**
 * 云码列表检索结果类
 */
public class CloudCodeQueryRetVo implements Serializable {
    private Long id;
    private String title;
    private String remark;
    private Date createTime;
    private Integer state;
    private String preUrl;
    private String preCodeUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public void setPreCodeUrl(String preCodeUrl) {
        this.preCodeUrl = preCodeUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getState() {
        return state;
    }

    public String getPreUrl() {
        return preUrl;
    }

    public String getPreCodeUrl() {
        return preCodeUrl;
    }
}
