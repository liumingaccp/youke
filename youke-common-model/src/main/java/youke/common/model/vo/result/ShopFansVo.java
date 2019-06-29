package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 19:55
 */
public class ShopFansVo  implements Serializable {
    private Long id;
    private String trueName;
    private String mobile;
    private String lastTime;
    private Double dealTotal;
    private Double avgDealTotal;
    private Integer dealNum;
    private Integer comeFrom;

    private String shopName;
    private int shopId;

    private List<TagVo> tags = new ArrayList<TagVo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Double getDealTotal() {
        return dealTotal;
    }

    public void setDealTotal(Double dealTotal) {
        this.dealTotal = dealTotal;
    }

    public Double getAvgDealTotal() {
        return avgDealTotal;
    }

    public void setAvgDealTotal(Double avgDealTotal) {
        this.avgDealTotal = avgDealTotal;
    }

    public Integer getDealNum() {
        return dealNum;
    }

    public void setDealNum(Integer dealNum) {
        this.dealNum = dealNum;
    }

    public Integer getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(Integer comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<TagVo> getTags() {
        return tags;
    }

    public void setTags(List<TagVo> tags) {
        this.tags = tags;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getShopId() {
        return shopId;
    }
}
