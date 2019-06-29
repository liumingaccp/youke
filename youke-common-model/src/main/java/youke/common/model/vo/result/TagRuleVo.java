package youke.common.model.vo.result;

import java.io.Serializable;

public class TagRuleVo implements Serializable {
    private Integer serialNum;
    private Integer thanNum;
    private Integer tagId;
    private Integer type;
    private String tagTitle;

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public void setThanNum(Integer thanNum) {
        this.thanNum = thanNum;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public Integer getThanNum() {
        return thanNum;
    }

    public Integer getTagId() {
        return tagId;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return tagTitle;
    }

    @Override
    public String toString() {
        return "TagRuleVo{" +
                "serialNum=" + serialNum +
                ", thanNum=" + thanNum +
                ", tagId=" + tagId +
                ", type=" + type +
                ", tagTitle='" + tagTitle + '\'' +
                '}';
    }
}
