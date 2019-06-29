package youke.common.model.vo.result.wxper;

import java.io.Serializable;

public class AutoLikeRecordQueryRetVo implements Serializable {
    private String likeDate;
    private Integer likeNum;

    public void setLikeDate(String likeDate) {
        this.likeDate = likeDate;
    }

    public String getLikeDate() {
        return likeDate;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }
}
