package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class CurrentAccountVo implements Serializable {
    private static final long serialVersionUID = 9180840734327261226L;

    private int dykVip;
    private Date dykEndTime;
    private int dykState;
    private String dykCompany;
    private String mobile;
    private String truename;
    private String qq;

    public void setDykVip(int dykVip) {
        this.dykVip = dykVip;
    }

    public void setDykEndTime(Date dykEndTime) {
        this.dykEndTime = dykEndTime;
    }

    public void setDykState(int dykState) {
        this.dykState = dykState;
    }

    public void setDykCompany(String dykCompany) {
        this.dykCompany = dykCompany;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getDykVip() {
        return dykVip;
    }

    public Date getDykEndTime() {
        return dykEndTime;
    }

    public int getDykState() {
        return dykState;
    }

    public String getDykCompany() {
        return dykCompany;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTruename() {
        return truename;
    }

    public String getQq() {
        return qq;
    }
}
