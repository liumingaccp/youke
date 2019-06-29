package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

public class PayConfigVo implements Serializable {

    private Integer payBuss;
    private Integer payState;
    private String payNumber;
    private String paycert;
    private Date validtimeBeg;
    private Date validtimeEnd;
    private String filecert;

    public Integer getPayBuss() {
        return payBuss;
    }

    public void setPayBuss(Integer payBuss) {
        this.payBuss = payBuss;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getPaycert() {
        return paycert;
    }

    public void setPaycert(String paycert) {
        this.paycert = paycert;
    }

    public Date getValidtimeBeg() {
        return validtimeBeg;
    }

    public void setValidtimeBeg(Date validtimeBeg) {
        this.validtimeBeg = validtimeBeg;
    }

    public Date getValidtimeEnd() {
        return validtimeEnd;
    }

    public void setValidtimeEnd(Date validtimeEnd) {
        this.validtimeEnd = validtimeEnd;
    }

    public String getFilecert() {
        return filecert;
    }

    public void setFilecert(String filecert) {
        this.filecert = filecert;
    }
}
