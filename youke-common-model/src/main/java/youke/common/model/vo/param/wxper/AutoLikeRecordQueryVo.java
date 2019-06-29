package youke.common.model.vo.param.wxper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

public class AutoLikeRecordQueryVo extends QueryObjectVO implements Serializable {
    private Long id;
    private String begTime;
    private String endTime;
    private String youkeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return empty2null(endTime);
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getBegTime() {
        return empty2null(begTime);
    }
}
