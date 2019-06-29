package youke.web.spread.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页封装类
 * @param <T>
 */
public class PageBean<T> implements Serializable {

    private int pageNum = 1;

    private int pageSize = 20;

    private int total = 0;

    private int pages = 0;

    private List<T> list = new ArrayList<>();

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return (int)Math.ceil((double)total/pageSize);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
