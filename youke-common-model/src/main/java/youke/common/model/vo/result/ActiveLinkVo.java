package youke.common.model.vo.result;

import java.io.Serializable;

/**
 * 活动保存返回地址
 */
public class ActiveLinkVo implements Serializable {
    private String url;     //活动地址链接
    private String qrdUrl; //对应二维码地址

    public void setQrdUrl(String qrdUrl) {
        this.qrdUrl = qrdUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getQrdUrl() {
        return qrdUrl;
    }
}
