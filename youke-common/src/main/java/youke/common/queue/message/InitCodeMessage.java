package youke.common.queue.message;

import java.io.Serializable;

public class InitCodeMessage implements Serializable {

    public InitCodeMessage() {
    }

    public InitCodeMessage(String appId, String content, String label) {
        this.appId = appId;
        this.content = content;
        this.label = label;
    }

    private String appId;

    private String content;

    private String label;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
