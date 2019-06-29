package youke.common.model.vo.result;

import java.io.Serializable;

/**
 * 系统公告
 */
public class SystemNoticeVo implements Serializable {
    private String content;
    private String startTime;
    private String endTime;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "SystemNoticeVo{" +
                "content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
