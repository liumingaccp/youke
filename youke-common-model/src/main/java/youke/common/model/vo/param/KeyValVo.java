package youke.common.model.vo.param;

import java.io.Serializable;

public class KeyValVo implements Serializable {

    public KeyValVo() {
    }

    public KeyValVo(String key, String val) {
        this.key = key;
        this.val = val;
    }

    private String key;

    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
