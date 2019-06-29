package youke.common.model.vo.param.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/9
 * Time: 11:03
 */
public class H5TaokeDetailQueryVo implements Serializable {
    private String taokeOpenId;
    private Integer activeId;
    private Integer page = 1;
    private Integer limit = 20;

    public String getTaokeOpenId() {
        return taokeOpenId;
    }

    public void setTaokeOpenId(String taokeOpenId) {
        this.taokeOpenId = taokeOpenId;
    }

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0)
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }
}
