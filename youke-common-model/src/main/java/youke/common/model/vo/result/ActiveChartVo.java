package youke.common.model.vo.result;

import java.io.Serializable;

public class ActiveChartVo implements Serializable {
    private String date;            //日期
    private Integer pv;             //日活
    private Integer uv;             //访问量

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPv() {
        return pv;
    }

    public Integer getUv() {
        return uv;
    }
}
