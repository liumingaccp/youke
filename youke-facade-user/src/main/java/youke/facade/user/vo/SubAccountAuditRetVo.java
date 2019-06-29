package youke.facade.user.vo;

import java.io.Serializable;
import java.util.Date;

public class SubAccountAuditRetVo implements Serializable {
    private String mobile;
    private String empName;
    private Integer positionId;
    private String password;
    private Date expTime;

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmpName() {
        return empName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}
