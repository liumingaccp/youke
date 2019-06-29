package youke.common.queue.message;

import java.io.Serializable;

public class ActiveChartMassMessage implements Serializable {
    private Long activeId;
    private String openId;

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getActiveId() {
        return activeId;
    }

    public String getOpenId() {
        return openId;
    }
}
