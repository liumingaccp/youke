package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 配置表
 * @author liuming
 */
public class TConfig implements Serializable {

    /**
     * 配置Id
     */
    @Id
    private String id;

    private String type;
    /**
     * 配置值
     */
    private String val;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
