package youke.facade.user.vo;

import java.io.Serializable;

/**
 * 子账户保存
 */
public class SubAccountSaveVo implements Serializable {
    private Integer id;
    private String mobile;
    private String code;
    private String empName;
    private Integer positionId;
    private String password;
    private String expTime;

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getExpTime() {
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }

    public String getEmpName() {
        return empName;
    }

    public String getPassword() {
        return password;
    }
}
