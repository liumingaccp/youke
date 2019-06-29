package youke.common.model;

import java.io.Serializable;

public class TMobcode implements Serializable {
    private static final long serialVersionUID = 5053196785111730893L;
    private String youkeid;

    private Integer count;

    private Integer icecount;

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIcecount() {
        return icecount;
    }

    public void setIcecount(Integer icecount) {
        this.icecount = icecount;
    }
}