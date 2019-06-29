package youke.common.model.vo.param;


public class SystemMsgQueryVo extends QueryObjectVO {

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return super.empty2null(keyword);
    }
}
