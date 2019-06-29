package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 11:36
 */
public class ShopFansQueryVo implements Serializable {

    private Integer shopId;
    private String begDealDate;
    private String endDealDate;
    private String key;
    private String keyType;
    private Double begDealTotal;
    private Double endDealTotal;
    private Integer begDealNum;
    private Integer endDealNum;
    private String tagIds;
    //默认 1
    private Integer page = 1;
    //默认 20
    private Integer limit = 20;

    private String youkeId;

    /**
     * 粉丝状态
     */
    private int state = -1;

    private List<String> tags = new ArrayList<String>();
    private Integer tagCount;

    private List<Integer> ids = new ArrayList<Integer>();
    private Integer idsCount;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getBegDealDate() {
        return this.begDealDate;
    }

    public void setBegDealDate(String begDealDate) {
        this.begDealDate = StringUtils.hasLength(begDealDate)?begDealDate:null;
    }

    public String getEndDealDate() {
        return this.endDealDate;
    }

    public void setEndDealDate(String endDealDate) {
        this.endDealDate = StringUtils.hasLength(endDealDate) ? endDealDate : null;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = StringUtils.hasLength(key) ? "%" + key + "%" : null;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = StringUtils.hasLength(keyType)?keyType:null;
    }

    public Double getBegDealTotal() {
        return begDealTotal;
    }

    public void setBegDealTotal(Double begDealTotal) {
        this.begDealTotal = begDealTotal;
    }

    public Double getEndDealTotal() {
        return endDealTotal;
    }

    public void setEndDealTotal(Double endDealTotal) {
        this.endDealTotal = endDealTotal;
    }

    public Integer getBegDealNum() {
        return begDealNum;
    }

    public void setBegDealNum(Integer begDealNum) {
        this.begDealNum = begDealNum;
    }

    public Integer getEndDealNum() {
        return endDealNum;
    }

    public void setEndDealNum(Integer endDealNum) {
        this.endDealNum = endDealNum;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(String tagIds) {
        if (tagIds != null) {
            String[] split = tagIds.split(",");
            for (String str : split) {
                this.tags.add(str);
            }
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null || page > 0){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if (null != limit && limit > -2) {
            this.limit = limit;
        }
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getTagCount() {
        return tags.size();
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public Integer getIdsCount() {
        if(this.ids != null){
            return ids.size();
        }
        return 0;
    }

    public void setIdsCount(Integer idsCount) {
        this.idsCount = idsCount;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = StringUtils.hasLength(tagIds) ? tagIds : null;
    }

    public String getTagIds() {
        return this.tagIds;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = StringUtils.hasLength(youkeId) ? youkeId : null;
    }

    public String getYoukeId() {
        return this.youkeId;
    }

    public void setState(int state) {
       if(state == 1 || state == 0){
           this.state = state;
        }
    }

    public int getState() {
        return state;
    }
}
