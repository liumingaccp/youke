package youke.common.model.vo.param;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 12:06
 */
public class FansChartParamVo implements Serializable {
    private String begDate;             //开始时间
    private String endDate;             //结束时间
    private Integer type;               //类型 0粉丝增长趋势，1支付金额，2购物粉丝数

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
