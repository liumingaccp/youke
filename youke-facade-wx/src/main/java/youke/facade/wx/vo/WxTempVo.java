package youke.facade.wx.vo;

import java.io.Serializable;

public class WxTempVo implements Serializable {

    public WxTempVo() {
    }

    public WxTempVo(String title, String templateShort, int type) {
        this.title = title;
        this.templateShort = templateShort;
        this.type = type;
    }

    public WxTempVo(String title, String templateShort, String templateId, int type) {
        this.title = title;
        this.templateShort = templateShort;
        this.templateId = templateId;
        this.type = type;
    }

    private String title;
    private String templateShort;
    private String templateId;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateShort() {
        return templateShort;
    }

    public void setTemplateShort(String templateShort) {
        this.templateShort = templateShort;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
