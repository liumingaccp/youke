package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;

public class CloudCodeNoticeVo implements Serializable {
    private Long id;
    private String title;
    private String remark;
    private Integer totalTimes;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
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

    public Integer getTotalTimes() {
        return totalTimes;
    }
}
