package youke.common.model.vo.param;


import java.io.Serializable;

/**
 * 群发模板
 */
public class MassTemplateParamVo implements Serializable {
    private static final long serialVersionUID = -7570656008157312651L;
    private String name;
    private String content;
    private Integer type;
    private Integer role;

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MassTemplateParamVo{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
