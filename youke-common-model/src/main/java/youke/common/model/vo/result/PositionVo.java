package youke.common.model.vo.result;

import youke.common.model.vo.param.KeyValVo;

import java.io.Serializable;
import java.util.List;

public class PositionVo implements Serializable {

    private int id;

    private String title;

    private List<KeyValVo> funcs;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<KeyValVo> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<KeyValVo> funcs) {
        this.funcs = funcs;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
