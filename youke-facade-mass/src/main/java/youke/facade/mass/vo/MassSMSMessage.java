package youke.facade.mass.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MassSMSMessage implements Serializable {
    private String taskTime;                                            //任务执行时间
    private String content;                                             //短信内容
    private Integer taskId;                                             //任务id
    private String dykId;                                               //店有客id
    private String label;                                               //短信签名
    private String myMobile;                                            //同时发送给我
    private int sendType;                                               //短信发送类型
    private String templateId;
    private List<String> mobiles = new ArrayList<String>();                  //电话列表
    private Map<String, Long> fansIdsMap = new HashMap<String, Long>();            //电话-粉丝列表,获取粉丝id用

    public void setMyMobile(String myMobile) {
        this.myMobile = myMobile;
    }

    public String getMyMobile() {
        return myMobile;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getSendType() {
        return sendType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setFansIdsMap(Map<String, Long> fansIdsMap) {
        this.fansIdsMap = fansIdsMap;
    }

    public Map<String, Long> getFansIdsMap() {
        return fansIdsMap;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getDykId() {
        return dykId;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public String getLabel() {
        return label;
    }
}
