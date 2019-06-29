package youke.common.model.vo.param.market;

import org.springframework.util.StringUtils;
import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

public class MarketRecordListQueryVo extends QueryObjectVO implements Serializable{
    private Integer shopId;                             //所属店铺
    private String wxfansName;                          //微信昵称
    private String shopfansName;                        //购物账号
    private String orderno;                             //订单号
    private String title;                               //活动标题
    private Integer type;                               //活动类型
    private Integer state;                              //状态
    private String begTime;                             //开始时间
    private String endTime;                             //结束时间
    private String youkeId;                             //店有客id

    public String empty2null(String str) {
        return StringUtils.hasLength(str) ? str : null;
    }

    public String getTitle() {
        return empty2null(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBegTime() {
        return empty2null(begTime);
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return empty2null(endTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setWxfansName(String wxfansName) {
        this.wxfansName = wxfansName;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setShopfansName(String shopfansName) {
        this.shopfansName = shopfansName;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getWxfansName() {
        return empty2null(wxfansName);
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getShopfansName() {
        return empty2null(shopfansName);
    }

    public String getOrderno() {
        return empty2null(orderno);
    }
}
