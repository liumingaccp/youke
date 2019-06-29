package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 二维码编辑|查看页面数据获取
 */
public class CloudCodeAuditRetVo implements Serializable {
    private Long id;
    private String title;
    private List<CloudCodeRuleAuditRetVo> rules = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRules(List<CloudCodeRuleAuditRetVo> rules) {
        this.rules = rules;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<CloudCodeRuleAuditRetVo> getRules() {
        return rules;
    }
}
