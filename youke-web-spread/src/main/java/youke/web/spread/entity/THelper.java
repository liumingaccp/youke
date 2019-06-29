package youke.web.spread.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮助文档表
 * @author liuming
 */
public class THelper implements Serializable {

    @Id
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;

    private String type;
    /**
     * 封面
     */
    private String cover;
    /**
     * 内容
     */
    private String content;
    /**
     * 描述
     */
    private String describe;
    /**
     * 更新时间
     */
    private Date createTime;
    /**
     * 0未发布，1已发布
     */
    private int state;

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
