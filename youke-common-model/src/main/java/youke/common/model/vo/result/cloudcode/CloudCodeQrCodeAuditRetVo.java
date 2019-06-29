package youke.common.model.vo.result.cloudcode;

import java.io.Serializable;

public class CloudCodeQrCodeAuditRetVo implements Serializable {
    private Long id;
    private String url;
    private String remark;

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
