package youke.common.model;

import java.io.Serializable;
import java.util.Date;

public class TCutpriceActiveFans implements Serializable {
    private Long id;

    private Long orderid;

    private String openid;

    private Integer cutprice;

    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getCutprice() {
        return cutprice;
    }

    public void setCutprice(Integer cutprice) {
        this.cutprice = cutprice;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}