package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/27
 * Time: 14:59
 */
public class TaowxPoterVo implements Serializable{

    private Integer posterId;

    private String sourceUrl;

    private String shortUrl;

    private Date createTime;

    private String body;

    private String posterUrl;

    private String codeUrl;

    public Integer getPosterId() {
        return posterId;
    }

    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }

    public String getSourceUrl() {
        return this.sourceUrl == null ? null : this.sourceUrl.trim();
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getShortUrl() {
        return this.shortUrl == null ? null : this.shortUrl.trim();
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }
}
