package youke.facade.pay.vo;

import java.io.Serializable;

public class OrderPayVo implements Serializable {

    public OrderPayVo() {
    }

    public OrderPayVo(String oid, String body, Integer totalFee) {
        this.oid = oid;
        this.body = body;
        this.totalFee = totalFee;
    }

    private String oid;
    private String body;
    private Integer totalFee;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }
}
