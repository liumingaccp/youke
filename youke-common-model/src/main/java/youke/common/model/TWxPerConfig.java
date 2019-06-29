package youke.common.model;

import java.io.Serializable;

public class TWxPerConfig implements Serializable {
    private String youkeid;

    private Integer dailylimit;

    private Integer opendistinct;

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }

    public Integer getDailylimit() {
        return dailylimit;
    }

    public void setDailylimit(Integer dailylimit) {
        this.dailylimit = dailylimit;
    }

    public Integer getOpendistinct() {
        return opendistinct;
    }

    public void setOpendistinct(Integer opendistinct) {
        this.opendistinct = opendistinct;
    }
}