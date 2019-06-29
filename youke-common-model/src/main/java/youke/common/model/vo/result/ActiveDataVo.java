package youke.common.model.vo.result;

import java.io.Serializable;

/**
 * 活动统计
 */
public class ActiveDataVo implements Serializable {
    private String title;           //活动标题
    private Integer type;           //活动类型
    private Integer state;          //活动状态
    private Integer pv;             //浏览量
    private Integer uv;             //访客数

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public String getTitle() {
        return title;
    }

    public Integer getType() {
        return type;
    }

    public Integer getState() {
        return state;
    }

    public Integer getPv() {
        return pv;
    }

    public Integer getUv() {
        return uv;
    }
}
