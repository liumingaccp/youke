package youke.common.model.vo.param.helper;

import org.springframework.util.StringUtils;
import youke.common.model.vo.page.PageModel;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/8
 * Time: 17:13
 */
public class H5TaokeOrderQueryVo implements Serializable {
    private String appId;
    private String openId;
    private Integer type = -1;
    private Integer page = 1;
    private Integer limit = 20;

    private Integer startPage;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = StringUtils.hasLength(appId)?appId:null;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = StringUtils.hasLength(openId)?openId:null;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        if(type != null && type > -1){
            this.type = type;
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
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }

    public Integer getStartPage() {
        return (this.page - 1) * this.limit;
    }
}
