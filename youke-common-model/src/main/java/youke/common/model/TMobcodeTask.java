package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TMobcodeTask implements Serializable {
    private static final long serialVersionUID = 5564936778771518930L;
    private Integer id;

    private String mobiles;

    private String mymobile;

    private String shopids;

    private Integer filterday;

    private Integer filterwxfans;

    private Date lasttimebeg;

    private Date lasttimeend;

    private Integer avgdealtotalbeg;

    private Integer avgdealtotalend;

    private String label;

    private String content;

    private Integer hasnickvar;

    private Integer hasshopvar;

    private Date tasktime;

    private Integer sendtype;

    private Integer state;

    private String youkeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles == null ? null : mobiles.trim();
    }

    public String getMymobile() {
        return mymobile;
    }

    public void setMymobile(String mymobile) {
        this.mymobile = mymobile == null ? null : mymobile.trim();
    }

    public String getShopids() {
        return shopids;
    }

    public void setShopids(String shopids) {
        this.shopids = shopids == null ? null : shopids.trim();
    }

    public Integer getFilterday() {
        return filterday;
    }

    public void setFilterday(Integer filterday) {
        this.filterday = filterday;
    }

    public Integer getFilterwxfans() {
        return filterwxfans;
    }

    public void setFilterwxfans(Integer filterwxfans) {
        this.filterwxfans = filterwxfans;
    }

    public Date getLasttimebeg() {
        return lasttimebeg;
    }

    public void setLasttimebeg(Date lasttimebeg) {
        this.lasttimebeg = lasttimebeg;
    }

    public Date getLasttimeend() {
        return lasttimeend;
    }

    public void setLasttimeend(Date lasttimeend) {
        this.lasttimeend = lasttimeend;
    }

    public Integer getAvgdealtotalbeg() {
        return avgdealtotalbeg;
    }

    public void setAvgdealtotalbeg(Integer avgdealtotalbeg) {
        this.avgdealtotalbeg = avgdealtotalbeg;
    }

    public Integer getAvgdealtotalend() {
        return avgdealtotalend;
    }

    public void setAvgdealtotalend(Integer avgdealtotalend) {
        this.avgdealtotalend = avgdealtotalend;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getHasnickvar() {
        return hasnickvar;
    }

    public void setHasnickvar(Integer hasnickvar) {
        this.hasnickvar = hasnickvar;
    }

    public Integer getHasshopvar() {
        return hasshopvar;
    }

    public void setHasshopvar(Integer hasshopvar) {
        this.hasshopvar = hasshopvar;
    }

    public Date getTasktime() {
        return tasktime;
    }

    public void setTasktime(Date tasktime) {
        this.tasktime = tasktime;
    }

    public Integer getSendtype() {
        return sendtype;
    }

    public void setSendtype(Integer sendtype) {
        this.sendtype = sendtype;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getYoukeid() {
        return youkeid;
    }

    public void setYoukeid(String youkeid) {
        this.youkeid = youkeid == null ? null : youkeid.trim();
    }
}