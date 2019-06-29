package youke.common.model.vo.result;

import java.io.Serializable;

public class ResultOfVotingVo implements Serializable {
    private Integer rank;
    private Integer total;
    private String title;
    private String picUrl;

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getTotal() {
        return total;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public String toString() {
        return "ResultOfVotingVo{" +
                "rank=" + rank +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
