package youke.common.model.vo.param;


import java.io.Serializable;

/**
 * 群发任务
 */
public class MassTaskParamVo implements Serializable {
    private static final long serialVersionUID = -4048133202044869040L;
    private String mobiles;     //多个手机
    private int filterDay;      //过滤天数,默认为0
    private int filterWxFans;   //是否过滤已关注公总号的客户 0不过滤 1过滤
    private String content;     //短信内容
    private String label;       //短信签名
    private String taskTime;      //任务执行时间
    private String timestamp;   //时间戳
    private String dykId;       //店有客id
    private String appId;       //appid

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "MassTaskParamVo{" +
                "mobiles='" + mobiles + '\'' +
                ", filterDay=" + filterDay +
                ", filterWxFans=" + filterWxFans +
                ", content='" + content + '\'' +
                ", label='" + label + '\'' +
                ", taskTime=" + taskTime +
                ", timestamp=" + timestamp +
                '}';
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public void setFilterDay(int filterDay) {
        this.filterDay = filterDay;
    }

    public void setFilterWxFans(int filterWxFans) {
        this.filterWxFans = filterWxFans;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getMobiles() {
        return mobiles;
    }

    public int getFilterDay() {
        return filterDay;
    }

    public int getFilterWxFans() {
        return filterWxFans;
    }

    public String getContent() {
        return  content+"退订回N";
    }

    public String getLabel() {
        return "【"+label+"】";
    }
}
