package youke.common.model.vo.param.wxper;

import org.springframework.util.StringUtils;
import youke.common.model.vo.param.DykPcBaseQueryVo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/10
 * Time: 17:39
 */
public class MassTaskRecordQueryVo extends DykPcBaseQueryVo implements Serializable{
    private String nickName;        //发送设备登录的昵称
    private String begCreateTime;   //开始创建时间
    private String endCreateTime;   //结束创建时间
    private String begSendTime;     //开始发送时间
    private String endSendTime;     //结束发送时间
    private Integer sendNumBegs;    //发送人数
    private Integer sendNumEnds;    //发送人数
    private String content;         //发送内容模糊匹配
    private Integer state = -1;      //发送状态匹配

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = hasLenght(nickName)?"%" +  nickName+"%" :null;
    }

    public String getBegCreateTime() {
        return begCreateTime;
    }

    public void setBegCreateTime(String begCreateTime) {
        this.begCreateTime = hasLenght(begCreateTime)?begCreateTime:null;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = hasLenght(endCreateTime)?endCreateTime:null;
    }

    public String getBegSendTime() {
        return begSendTime;
    }

    public void setBegSendTime(String begSendTime) {
        this.begSendTime = hasLenght(begSendTime)?begSendTime:null;
    }

    public String getEndSendTime() {
        return endSendTime;
    }

    public void setEndSendTime(String endSendTime) {
        this.endSendTime = hasLenght(endSendTime)?endSendTime:null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = hasLenght(content)?content:null;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSendNumBegs() {
        return sendNumBegs;
    }

    public void setSendNumBegs(Integer sendNumBegs) {
        this.sendNumBegs = sendNumBegs;
    }

    public Integer getSendNumEnds() {
        return sendNumEnds;
    }

    public void setSendNumEnds(Integer sendNumEnds) {
        this.sendNumEnds = sendNumEnds;
    }
}
