package youke.common.model;

import java.io.Serializable;

public class TPositionFunc implements Serializable {
    private Long id;

    private Integer positionid;

    private String funcid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPositionid() {
        return positionid;
    }

    public void setPositionid(Integer positionid) {
        this.positionid = positionid;
    }

    public String getFuncid() {
        return funcid;
    }

    public void setFuncid(String funcid) {
        this.funcid = funcid == null ? null : funcid.trim();
    }
}