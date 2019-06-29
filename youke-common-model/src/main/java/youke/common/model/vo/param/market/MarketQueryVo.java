package youke.common.model.vo.param.market;

import youke.common.model.vo.param.QueryObjectVO;

public class MarketQueryVo extends QueryObjectVO {
    private String title;               //活动标题
    private Integer type;               //活动类型
    private Integer state;              //状态
    private String begTime;             //开始时间
    private String endTime;             //结束时间
    private String youkeId;             //店有客id

    public String getTitle() {
        return empty2null(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return empty2null(endTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }
}
