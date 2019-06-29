package youke.common.model.vo.result;

import youke.common.model.vo.param.KeyValVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VipFuncVo implements Serializable {

    private String key;

    private String val;

    private List<VipFuncVo> childrens = new ArrayList<>();

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

    public List<VipFuncVo> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<VipFuncVo> childrens) {
        this.childrens = childrens;
    }
}
