package youke.common.model.vo.param.market;

import net.sf.json.JSONArray;

import java.io.Serializable;

public class MarketActivitySaveVo implements Serializable {
    private Long id;                                                //活动id
    private String title;                                           //活动名称
    private String begTime;                                         //活动开始时间
    private String endTime;                                         //活动结束时间
    private String fileUrl;                                         //用户上传订单模板阿里云地址
    private String fileName;                                        //用户上传订单模板名称
    private String dykId;                                           //店有客id
    private String appId;                                           //公众号id

    private Integer fansLimit;                                      //限制:粉丝限制 全部粉丝或关注会员
    private Integer maxJoin;                                        //限制:活动名额
    private Integer limitCount;                                     //限制:每人参与次数

    private Integer fixedMoney;                                     //奖励:固定金额
    private Integer minRandMoney;                                   //奖励:最小随机金额
    private Integer maxRandMoney;                                   //奖励:最大随机金额
    private Integer integral;                                       //奖励:积分

    private Integer openMinLimit;                                   //开启订单最低额限制
    private Integer openMaxLimit;                                   //开启订单最高金额限制
    private Integer minLimit;                                       //最低限制订单金额
    private Integer maxLimit;                                       //最高限制订单金额
    private Integer minMoneyBack;                                   //最低限制返现金额
    private Integer maxMoneyBack;                                   //最高限制返现金额
    private Integer maxRandMoneyBeg;                                //最高随机返现金额开始
    private Integer maxRandMoneyEnd;                                //最高随机返现金额结束

    private Integer type;                                           //活动类型
    private Integer openType;                                       //开启方式
    private Integer rewardType;                                     //奖励类型
    private Integer examineType;                                    //审核方式
    private Integer examineHour;                                    //多少小时后自动审核
    private Integer costIntegral;                                   //积分消耗

    private JSONArray intro;                                         //活动说明
    private JSONArray demoPics;                                      //截图示例列表
    private JSONArray inputOrders;                                   //随时返订单列表
    private JSONArray goodPics;                                      //投票测款商品列表
    private JSONArray signRule;                                      //每日签到规则列表
    private JSONArray prizeObj;                                      //幸运抽奖奖励列表
    private JSONArray posterPics;                                    //分享海报图片列表
    private JSONArray redRules;                                      //晒单有礼红包发放规则

    private String timestamp;                                        //时间戳

    public JSONArray getRedRules() {
        return redRules;
    }

    public void setRedRules(JSONArray redRules) {
        this.redRules = redRules;
    }

    public void setIntro(JSONArray intro) {
        this.intro = intro;
    }

    public JSONArray getIntro() {
        return intro;
    }

    public void setDemoPics(JSONArray demoPics) {
        this.demoPics = demoPics;
    }

    public void setInputOrders(JSONArray inputOrders) {
        this.inputOrders = inputOrders;
    }

    public void setGoodPics(JSONArray goodPics) {
        this.goodPics = goodPics;
    }

    public void setSignRule(JSONArray signRule) {
        this.signRule = signRule;
    }

    public void setPrizeObj(JSONArray prizeObj) {
        this.prizeObj = prizeObj;
    }

    public void setPosterPics(JSONArray posterPics) {
        this.posterPics = posterPics;
    }

    public JSONArray getDemoPics() {
        return demoPics;
    }

    public JSONArray getInputOrders() {
        return inputOrders;
    }

    public JSONArray getGoodPics() {
        return goodPics;
    }

    public JSONArray getSignRule() {
        return signRule;
    }

    public JSONArray getPrizeObj() {
        return prizeObj;
    }

    public JSONArray getPosterPics() {
        return posterPics;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setMaxRandMoneyBeg(Integer maxRandMoneyBeg) {
        this.maxRandMoneyBeg = maxRandMoneyBeg;
    }

    public void setMaxRandMoneyEnd(Integer maxRandMoneyEnd) {
        this.maxRandMoneyEnd = maxRandMoneyEnd;
    }

    public Integer getMaxRandMoneyBeg() {
        return maxRandMoneyBeg;
    }

    public Integer getMaxRandMoneyEnd() {
        return maxRandMoneyEnd;
    }

    public void setCostIntegral(Integer costIntegral) {
        this.costIntegral = costIntegral;
    }

    public Integer getCostIntegral() {
        return costIntegral;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setFansLimit(Integer fansLimit) {
        this.fansLimit = fansLimit;
    }

    public void setMaxJoin(Integer maxJoin) {
        this.maxJoin = maxJoin;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public void setFixedMoney(Integer fixedMoney) {
        this.fixedMoney = fixedMoney;
    }

    public void setMinRandMoney(Integer minRandMoney) {
        this.minRandMoney = minRandMoney;
    }

    public void setMaxRandMoney(Integer maxRandMoney) {
        this.maxRandMoney = maxRandMoney;
    }

    public void setOpenMinLimit(Integer openMinLimit) {
        this.openMinLimit = openMinLimit;
    }

    public void setOpenMaxLimit(Integer openMaxLimit) {
        this.openMaxLimit = openMaxLimit;
    }

    public void setMinLimit(Integer minLimit) {
        this.minLimit = minLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public void setMinMoneyBack(Integer minMoneyBack) {
        this.minMoneyBack = minMoneyBack;
    }

    public void setMaxMoneyBack(Integer maxMoneyBack) {
        this.maxMoneyBack = maxMoneyBack;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setExamineType(Integer examineType) {
        this.examineType = examineType;
    }

    public void setExamineHour(Integer examineHour) {
        this.examineHour = examineHour;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBegTime() {
        return begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public Integer getFansLimit() {
        return fansLimit;
    }

    public Integer getMaxJoin() {
        return maxJoin;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public Integer getFixedMoney() {
        return fixedMoney;
    }

    public Integer getMinRandMoney() {
        return minRandMoney;
    }

    public Integer getMaxRandMoney() {
        return maxRandMoney;
    }

    public Integer getMinLimit() {
        return minLimit;
    }

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public Integer getMinMoneyBack() {
        return minMoneyBack;
    }

    public Integer getMaxMoneyBack() {
        return maxMoneyBack;
    }

    public Integer getType() {
        return type;
    }

    public Integer getOpenType() {
        return openType;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getExamineType() {
        return examineType;
    }

    public Integer getExamineHour() {
        return examineHour;
    }

    public Integer getOpenMinLimit() {
        return openMinLimit == null ? 0 : openMinLimit;
    }

    public Integer getOpenMaxLimit() {
        return openMaxLimit == null ? 0 : openMaxLimit;
    }

    public Integer getIntegral() {
        return integral;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
