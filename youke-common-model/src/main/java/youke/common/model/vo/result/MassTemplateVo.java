package youke.common.model.vo.result;

import java.io.Serializable;

public class MassTemplateVo implements Serializable {
    private String template;
    private Integer state;  //0审核通过 1审核不通过

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public String getTemplate() {
        return template;
    }
}
