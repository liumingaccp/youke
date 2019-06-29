package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 15:47
 */
public class WealOrderQueryVo implements Serializable {
    private String title;
    private String wxFansName;
    private String shopFansName;
    private Integer state;
    private String orderNo;
    private String timeBeg;
    private String timeEnd;
    private Integer integralBeg;
    private Integer integralEnd;
    private Integer moneyBeg;
    private Integer moneyEnd;
    private String openId;
    private Integer page =1;
    private Integer limit =20;

    private String dykId;

    public String getTitle() {
        return StringUtils.hasLength(title)? title.trim(): null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWxFansName() {
        return StringUtils.hasLength(wxFansName)? wxFansName.trim(): null;
    }

    public void setWxFansName(String wxFansName) {
        this.wxFansName = wxFansName;
    }

    public String getShopFansName() {
        return StringUtils.hasLength(shopFansName)? shopFansName.trim(): null;
    }

    public void setShopFansName(String shopFansName) {
        this.shopFansName = shopFansName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNo() {
        return StringUtils.hasLength(orderNo)? orderNo.trim(): null;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTimeBeg() {
        return StringUtils.hasLength(timeBeg)? timeBeg: null;
    }

    public void setTimeBeg(String timeBeg) {
        this.timeBeg = timeBeg;
    }

    public String getTimeEnd() {
        return StringUtils.hasLength(timeEnd)? timeEnd: null;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getIntegralBeg() {
        return integralBeg;
    }

    public void setIntegralBeg(Integer integralBeg) {
        if(integralBeg != null && integralBeg != -1){
            this.integralBeg = integralBeg;
        }
    }

    public Integer getIntegralEnd() {
        return integralEnd;
    }

    public void setIntegralEnd(Integer integralEnd) {
        if(integralEnd != null && integralEnd != -1){
            this.integralEnd = integralEnd;
        }
    }

    public Integer getMoneyBeg() {
        return moneyBeg;
    }

    public void setMoneyBeg(Integer moneyBeg) {
        if(moneyBeg != null && moneyBeg != -1){
            this.moneyBeg = moneyBeg;
        }
    }

    public Integer getMoneyEnd() {
        return moneyEnd;
    }

    public void setMoneyEnd(Integer moneyEnd) {
        if(moneyEnd != null && moneyEnd != -1){
            this.moneyEnd = moneyEnd;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -1){
            this.limit = limit;
        }
    }

    public void setDykId(String dykId) {
        this.dykId = dykId;
    }

    public String getDykId() {
        return dykId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId.trim():null;
    }

    public String getOpenId() {
        return openId;
    }

    public static void main(String[] arags){
        System.err.println(256&255);
    }
}
