package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/12
 * Time: 9:09
 */
public class MassRecordVo  implements Serializable {
    private Integer id;

    private String thumburl;

    private String title;

    private Integer sendtype;

    private Date tasktime;

    private Date overtime;

    private Integer sendnum;

    private Integer successnum;

    private Integer failnum;

    private Integer state;

    private Integer taskid;

    private String failreason;

    private Integer materialid;

    private String mediatype;

    private String mediaurl;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSendtype() {
        return sendtype;
    }

    public void setSendtype(Integer sendtype) {
        this.sendtype = sendtype;
    }

    public Date getTasktime() {
        return tasktime;
    }

    public void setTasktime(Date tasktime) {
        this.tasktime = tasktime;
    }

    public Date getOvertime() {
        return overtime;
    }

    public void setOvertime(Date overtime) {
        this.overtime = overtime;
    }

    public Integer getSendnum() {
        return sendnum;
    }

    public void setSendnum(Integer sendnum) {
        this.sendnum = sendnum;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public Integer getFailnum() {
        return failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }

    public String getFailreason() {
        return failreason;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getMediaurl() {
        return mediaurl;
    }

    public void setMediaurl(String mediaurl) {
        this.mediaurl = mediaurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
