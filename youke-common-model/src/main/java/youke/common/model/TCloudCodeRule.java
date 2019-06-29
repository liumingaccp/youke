package youke.common.model;

import java.io.Serializable;

public class TCloudCodeRule implements Serializable {
    private Long id;

    private String begintime;

    private String endtime;

    private Integer israndom;

    private Integer inorder;

    private Integer daytimes;

    private Integer totaltimes;

    private Long cloudid;

    private String youkeid;

    public void setId(Long id) {
        this.id = id;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setIsrandom(Integer israndom) {
        this.israndom = israndom;
    }

    public void setInorder(Integer inorder) {
        this.inorder = inorder;
    }

    public void setDaytimes(Integer daytimes) {
        this.daytimes = daytimes;
    }

    public void setTotaltimes(Integer totaltimes) {
        this.totaltimes = totaltimes;
    }

    public void setCloudid(Long cloudid) {
        this.cloudid = cloudid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid;
    }

    public Long getId() {
        return id;
    }

    public String getBegintime() {
        return begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public Integer getIsrandom() {
        return israndom;
    }

    public Integer getInorder() {
        return inorder;
    }

    public Integer getDaytimes() {
        return daytimes;
    }

    public Integer getTotaltimes() {
        return totaltimes;
    }

    public Long getCloudid() {
        return cloudid;
    }

    public String getYoukeid() {
        return youkeid;
    }
}