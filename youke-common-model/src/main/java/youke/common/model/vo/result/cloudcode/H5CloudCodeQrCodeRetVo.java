package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;

/**
 * H5获取二维码
 */
public class H5CloudCodeQrCodeRetVo implements Serializable {
    private Long id;      //二维码Id
    private String url;   //二维码地址
    private String remark;//二维码备注

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public String getRemark() {
        return remark;
    }
}
