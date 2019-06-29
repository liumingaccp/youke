package youke.common.model.vo.result;

import youke.common.model.TTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagListVo implements Serializable {
    private Integer groupId;
    private String groupTitle;
    private List<TTag> tags = new ArrayList<TTag>();

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public void setTags(List<TTag> tags) {
        this.tags = tags;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public List<TTag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "TagListVo{" +
                "groupId=" + groupId +
                ", groupTitle='" + groupTitle + '\'' +
                ", tags=" + tags +
                '}';
    }
}
