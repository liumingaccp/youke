package youke.facade.wx.queue.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/9
 * Time: 15:03
 */
public class SuperMassMessage implements Serializable{

    private Integer massTaskId;

    private String mediatype;

    private String content;

    private Integer materialid;

    private String tasktime;

    private String appid;

    private int sendType;

    private List<String> openIds = new ArrayList<String>();

    public Integer getMassTaskId() {
        return massTaskId;
    }

    public void setMassTaskId(Integer massTaskId) {
        this.massTaskId = massTaskId;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setOpenIds(List<String> openIds) {
        this.openIds = openIds;
    }

    public List<String> getOpenIds() {
        return openIds;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getSendType() {
        return sendType;
    }
}
