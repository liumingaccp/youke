package youke.common.queue.message;

import java.io.Serializable;

/**
 * 系统消息
 */
public class SysMassMessage implements Serializable {
    private String title;
    private String content;
    private String youkeId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getYoukeId() {
        return youkeId;
    }
}
