package youke.common.model.vo.result.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * H5活动信息获取
 */
public class ActivityRuleH5Vo implements Serializable {
    private Long id;                                                    //活动id
    private Date begTime;                                             //活动开始时间
    private Date endTime;                                             //活动结束时间
    private Integer limitCount;                                         //每人参与次数
    private Integer rewardType;                                         //奖励设置
    private Integer openType;                                           //活动规则展开方式
    private Integer costIntegral;                                       //抽奖消耗积分
    private List<Map<String, Object>> demoPics = new ArrayList<>();     //截图示例
    private List<Map<String, Object>> goodPics = new ArrayList<>();     //投票商品
    private List<Map<String, Object>> prizeObj = new ArrayList<>();     //奖品
    private List<String>intro = new ArrayList<>();

    public void setIntro(List<String> intro) {
        this.intro = intro;
    }

    public List<String> getIntro() {
        return intro;
    }

    public Integer getCostIntegral() {
        return costIntegral;
    }

    public void setCostIntegral(Integer costIntegral) {
        this.costIntegral = costIntegral;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getBegTime() {
        return begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public void setDemoPics(List<Map<String, Object>> demoPics) {
        this.demoPics = demoPics;
    }

    public void setGoodPics(List<Map<String, Object>> goodPics) {
        this.goodPics = goodPics;
    }

    public void setPrizeObj(List<Map<String, Object>> prizeObj) {
        this.prizeObj = prizeObj;
    }

    public Long getId() {
        return id;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getOpenType() {
        return openType;
    }

    public List<Map<String, Object>> getDemoPics() {
        return demoPics;
    }

    public List<Map<String, Object>> getGoodPics() {
        return goodPics;
    }

    public List<Map<String, Object>> getPrizeObj() {
        return prizeObj;
    }
}


