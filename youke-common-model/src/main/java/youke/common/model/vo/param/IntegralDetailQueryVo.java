package youke.common.model.vo.param;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/10
 * Time: 14:23
 */
public class IntegralDetailQueryVo implements Serializable {
    private String openId;
    private Integer page =1;
    private Integer limit =20;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
        if(limit != null && limit > 0){
            this.limit = limit;
        }
    }
}
