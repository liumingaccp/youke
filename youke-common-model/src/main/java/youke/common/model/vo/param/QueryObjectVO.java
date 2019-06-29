package youke.common.model.vo.param;


import java.io.Serializable;

public class QueryObjectVO implements Serializable {
    private static final long serialVersionUID = 8354333651263644837L;
    protected int page = 1;               //当前页
    protected int limit = 20;             //每页记录数
    protected String timestamp;             //当前系统时间

    public String empty2null(String str) {
        return str != null && str.length() > 0 ? str.trim() : null;
    }

    public boolean hasLength(String str){
        return empty2null(str) != null ? true : false;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
