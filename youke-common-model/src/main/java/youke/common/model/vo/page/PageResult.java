package youke.common.model.vo.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/8
 * Time: 17:28
 */
public class PageResult<T> implements Serializable{
    /**
     * 结果集
     */
    private List<T> list;

    /**
     * 页面信息
     */
    private Page page;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


}
