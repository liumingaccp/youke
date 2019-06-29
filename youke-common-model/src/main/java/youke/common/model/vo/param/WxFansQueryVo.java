package youke.common.model.vo.param;

import com.github.pagehelper.PageInfo;
import youke.common.model.TShopFans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 11:36
 */
public class WxFansQueryVo extends WxFansBaseQueryVo{

    ////0默认排序，1最近互动时间，2积分最多，3积分最少
    private int sort;

    private Integer tagFilter = 1;

    public WxFansQueryVo(){}

    public WxFansQueryVo(String tagIds, String appId){
        this.tagIds = tagIds;
        this.setTags(tagIds);
        this.setAppId(appId);
    }

    public Integer getTagFilter() {
        return tagFilter;
    }

    public void setTagFilter(Integer tagFilter) {
        this.tagFilter = tagFilter;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSortStr(){
        switch (this.sort){
            case 1 :
                return "lastTime desc";
            case 2 :
                return "integral desc";
            case 3 :
                return "integral";
            default: return null;
        }
    }
}
