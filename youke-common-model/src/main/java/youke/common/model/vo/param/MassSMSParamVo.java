package youke.common.model.vo.param;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员群发
 */
public class MassSMSParamVo extends QueryObjectVO {
    private String tagIds;                                      //多标签
    private Integer tagFilter;                                  //标签筛选交集或并集
    private String mobiles;                                     //多个手机
    private String shopIds;                                     //多店铺id组合字符串
    private String lastTimeBeg;                                 //交易时间上限
    private String lastTimeEnd;                                 //交易时间下限
    private String content;                                     //短信内容
    private String label;                                       //短信签名
    private String taskTime;                                    //任务执行时间
    private String fileUrl;                                     //阿里云上传文件地址
    private String fileName;                                    //上传文件名
    private String mymobile;                                    //同时发送给我
    private String dykId;                                       //店有客Id
    private String appId;                                       //appId
    private int filterDay;                                      //是否过滤发送时间:默认为0不过滤
    private int filterWxFans;                                   //是否过滤公众号:默认为0不过滤
    private Integer avgDealTotalBeg;                            //消费上限
    private Integer avgDealTotalEnd;                            //消费下限
    private Integer type;                                       //发送类型
    private List<String> shopList = new ArrayList<String>();         //店铺id列表
    private List<String> mobileList = new ArrayList<String>();       //手机号码列表
    private List<String>tagList = new ArrayList<>();           //粉丝标签列表
    private List<Long>fansList = new ArrayList<>();             //粉丝id列表

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void setTagFilter(Integer tagFilter) {
        this.tagFilter = tagFilter;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getTagIds() {
        return tagIds;
    }

    public Integer getTagFilter() {
        return tagFilter;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setShopList(List<String> shopList) {
        this.shopList = shopList;
    }

    public void setMobileList(List<String> mobileList) {
        this.mobileList = mobileList;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public void setMymobile(String mymobile) {
        this.mymobile = mymobile;
    }

    public String getContent() {
        return content + "退订回N";
    }

    public String getLabel() {
        return "【" + label + "】";
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getMymobile() {
        return mymobile;
    }

    public void setLastTimeBeg(String lastTimeBeg) {
        this.lastTimeBeg = lastTimeBeg;
    }

    public void setLastTimeEnd(String lastTimeEnd) {
        this.lastTimeEnd = lastTimeEnd;
    }

    public String getLastTimeBeg() {
        return empty2null(lastTimeBeg);
    }

    public String getLastTimeEnd() {
        return empty2null(lastTimeEnd);
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }

    public void setAvgDealTotalBeg(Integer avgDealTotalBeg) {
        this.avgDealTotalBeg = avgDealTotalBeg;
    }

    public void setAvgDealTotalEnd(Integer avgDealTotalEnd) {
        this.avgDealTotalEnd = avgDealTotalEnd;
    }

    public void setFilterDay(int filterDay) {
        this.filterDay = filterDay;
    }

    public void setFilterWxFans(int filterWxFans) {
        this.filterWxFans = filterWxFans;
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getShopIds() {
        return shopIds;
    }

    public Integer getAvgDealTotalBeg() {
        return avgDealTotalBeg;
    }

    public Integer getAvgDealTotalEnd() {
        return avgDealTotalEnd;
    }

    public int getFilterDay() {
        return filterDay;
    }

    public int getFilterWxFans() {
        return filterWxFans;
    }

    public String getDykId() {
        return dykId;
    }

    public String getAppId() {
        return appId;
    }

    public List<String> getShopList() {
        return shopList;
    }

    public List<String> getMobileList() {
        return mobileList;
    }

    public List<Long> getFansList() {
        return fansList;
    }

    public void setFansList(List<Long> fansList) {
        this.fansList = fansList;
    }
}
