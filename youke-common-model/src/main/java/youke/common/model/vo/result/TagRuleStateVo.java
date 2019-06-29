package youke.common.model.vo.result;

import java.io.Serializable;

public class TagRuleStateVo implements Serializable {
    private static final long serialVersionUID = -3525190832285237766L;
    private Integer openTagRule0;
    private Integer openTagRule1;
    private Integer openTagRule2;

    @Override
    public String toString() {
        return "TagRuleStateVo{" +
                "openTagRule0=" + openTagRule0 +
                ", openTagRule1=" + openTagRule1 +
                ", openTagRule2=" + openTagRule2 +
                '}';
    }

    public void setOpenTagRule0(Integer openTagRule0) {
        this.openTagRule0 = openTagRule0;
    }

    public void setOpenTagRule1(Integer openTagRule1) {
        this.openTagRule1 = openTagRule1;
    }

    public void setOpenTagRule2(Integer openTagRule2) {
        this.openTagRule2 = openTagRule2;
    }

    public Integer getOpenTagRule0() {
        return openTagRule0;
    }

    public Integer getOpenTagRule1() {
        return openTagRule1;
    }

    public Integer getOpenTagRule2() {
        return openTagRule2;
    }
}
