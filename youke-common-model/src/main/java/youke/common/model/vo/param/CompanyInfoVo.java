package youke.common.model.vo.param;

public class CompanyInfoVo extends QueryObjectVO {
    private String dykId;           //店有客id
    private String shopUrl;         //店铺网址
    private String company;         //公司名称
    private String linkTruename;    //联系人姓名
    private String linkPosition;    //联系人职位
    private String linkQQ;          //联系人QQ

    @Override
    public String toString() {
        return "CompanyInfoVo{" +
                "dykId='" + dykId + '\'' +
                ", shopUrl='" + shopUrl + '\'' +
                ", company='" + company + '\'' +
                ", linkTruename='" + linkTruename + '\'' +
                ", linkPosition='" + linkPosition + '\'' +
                ", linkQQ='" + linkQQ + '\'' +
                '}';
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLinkTruename(String linkTruename) {
        this.linkTruename = linkTruename;
    }

    public void setLinkPosition(String linkPosition) {
        this.linkPosition = linkPosition;
    }

    public void setLinkQQ(String linkQQ) {
        this.linkQQ = linkQQ;
    }

    public String getDykId() {
        return dykId;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public String getCompany() {
        return company;
    }

    public String getLinkTruename() {
        return linkTruename;
    }

    public String getLinkPosition() {
        return linkPosition;
    }

    public String getLinkQQ() {
        return linkQQ;
    }
}
