package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class SubAccountListRetVo implements Serializable {
    private Integer id;
    private String mobile;
    private String empName;
    private Date createTime;
    private String positionName;
    private Integer state;
    private Date expTime;

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmpName() {
        return empName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getState() {
        return state;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
