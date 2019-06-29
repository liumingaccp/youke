package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TSubscrConfig implements Serializable {
    private String appid;

    private Integer openautoreply;

    private Integer openkefu;

    private Integer opentagrule0;

    private Integer opentagrule1;

    private Integer opentagrule2;

    private Integer paybuss;

    private Integer paystate;

    private String paynumber;

    private String paycert;

    private Date validtimebeg;

    private Date validtimeend;

    private String filecert;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public Integer getOpenautoreply() {
        return openautoreply;
    }

    public void setOpenautoreply(Integer openautoreply) {
        this.openautoreply = openautoreply;
    }

    public Integer getOpenkefu() {
        return openkefu;
    }

    public void setOpenkefu(Integer openkefu) {
        this.openkefu = openkefu;
    }

    public Integer getOpentagrule0() {
        return opentagrule0;
    }

    public void setOpentagrule0(Integer opentagrule0) {
        this.opentagrule0 = opentagrule0;
    }

    public Integer getOpentagrule1() {
        return opentagrule1;
    }

    public void setOpentagrule1(Integer opentagrule1) {
        this.opentagrule1 = opentagrule1;
    }

    public Integer getOpentagrule2() {
        return opentagrule2;
    }

    public void setOpentagrule2(Integer opentagrule2) {
        this.opentagrule2 = opentagrule2;
    }

    public Integer getPaybuss() {
        return paybuss;
    }

    public void setPaybuss(Integer paybuss) {
        this.paybuss = paybuss;
    }

    public Integer getPaystate() {
        return paystate;
    }

    public void setPaystate(Integer paystate) {
        this.paystate = paystate;
    }

    public String getPaynumber() {
        return paynumber;
    }

    public void setPaynumber(String paynumber) {
        this.paynumber = paynumber == null ? null : paynumber.trim();
    }

    public String getPaycert() {
        return paycert;
    }

    public void setPaycert(String paycert) {
        this.paycert = paycert == null ? null : paycert.trim();
    }

    public Date getValidtimebeg() {
        return validtimebeg;
    }

    public void setValidtimebeg(Date validtimebeg) {
        this.validtimebeg = validtimebeg;
    }

    public Date getValidtimeend() {
        return validtimeend;
    }

    public void setValidtimeend(Date validtimeend) {
        this.validtimeend = validtimeend;
    }

    public String getFilecert() {
        return filecert;
    }

    public void setFilecert(String filecert) {
        this.filecert = filecert == null ? null : filecert.trim();
    }
}