package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/14
 * Time: 9:30
 */
public class CollageQueryRetVoByOpenId implements Serializable{
    private Integer id;
    private String cover;
    private String title;
    private Integer leftTuanNum;
    private String endTime;
    private long end;
    private String orderNo;
    private Integer state;
    private Integer tuanId;

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEnd() {
        return end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLeftTuanNum() {
        return leftTuanNum;
    }

    public void setLeftTuanNum(Integer leftTuanNum) {
        this.leftTuanNum = leftTuanNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setTuanId(Integer tuanId) {
        this.tuanId = tuanId;
    }

    public Integer getTuanId() {
        return tuanId;
    }
}
