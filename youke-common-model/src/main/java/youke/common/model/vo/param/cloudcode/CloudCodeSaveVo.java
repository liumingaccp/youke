package youke.common.model.vo.param.cloudcode;

import net.sf.json.JSONArray;

import java.io.Serializable;

public class CloudCodeSaveVo implements Serializable {
    private Long id;
    private String title;
    private String dykId;
    private String appId;
    private JSONArray rules;

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public void setRules(JSONArray rules) {
        this.rules = rules;
    }

    public JSONArray getRules() {
        return rules;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
