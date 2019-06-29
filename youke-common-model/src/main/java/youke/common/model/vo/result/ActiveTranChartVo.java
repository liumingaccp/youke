package youke.common.model.vo.result;

import java.io.Serializable;

/**
 * 活动转化
 */
public class ActiveTranChartVo implements Serializable {
    private Long activeId;
    private String date;          //日期
    private Integer bookNum;    //参与人数
    private Integer winNum;     //获奖人数


    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }


    public Integer getBookNum() {
        return bookNum;
    }

    public Integer getWinNum() {
        return winNum;
    }
}
