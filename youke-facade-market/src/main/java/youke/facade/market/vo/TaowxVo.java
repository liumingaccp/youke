package youke.facade.market.vo;

import java.io.Serializable;

public class TaowxVo implements Serializable {
    /**
     * 海报Id
     */
    private Integer posterId;
    /**
     * 短链接
     */
    private String shortUrl;
    /**
     * 二维码链接
     */
    private String codeUrl;

    public Integer getPosterId() {
        return posterId;
    }

    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
