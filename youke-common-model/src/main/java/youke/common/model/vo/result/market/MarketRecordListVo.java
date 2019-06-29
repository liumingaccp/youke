package youke.common.model.vo.result.market;

import java.io.Serializable;
import java.util.Date;

public class MarketRecordListVo implements Serializable {
    private Long id;                        //记录id
    private Long activeId;                  //活动id
    private String title;                   //活动标题
    private String wxfansName;              //微信粉丝昵称
    private String shopfansName;            //购物账号
    private String orderno;                 //订单号
    private Integer rewardType;             //奖品类型
    private Integer money;                  //金额
    private Integer integral;               //积分
    private Date createTime;                //创建时间
    private Integer state;                  //状态
    private Integer examineType;            //审核类型
    private String examineName;             //审核人姓名
    private String lastOpt;                 //最后审核人

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setExamineType(Integer examineType) {
        this.examineType = examineType;
    }

    public void setExamineName(String examineName) {
        this.examineName = examineName;
    }

    public String getExamineType() {
        switch (examineType) {
            case 0:
                return "系统自动审核";
            case 1:
                return "超时自动审核";
            case 2:
                return "人工审核:";
            case 3:
                return "无需审核";
            default:
                return "";
        }
    }

    public String getExamineName() {
        if (examineName == null) {
            return "";
        } else {
            return examineName;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public void setShopfansName(String shopfansName) {
        this.shopfansName = shopfansName;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }


    public void setState(Integer state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public Long getActiveId() {
        return activeId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getWxfansName() {
        return wxfansName == null ? "" : wxfansName;
    }

    public String getShopfansName() {
        return shopfansName == null ? "" : shopfansName;
    }

    public String getOrderno() {
        return orderno == null ? "" : orderno;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Integer getState() {
        return state;
    }

    public String getLastOpt() {
        return this.getExamineType() + this.getExamineName();
    }

}
