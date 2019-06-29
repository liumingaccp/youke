package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 17:28
 */
public class CollageQueryRetVoH5 implements Serializable {

    private Integer id;
    private String cover;
    private String title;
    private Integer tuanPrice;
    private Integer usedNum;

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

    public Integer getTuanPrice() {
        return tuanPrice;
    }

    public void setTuanPrice(Integer tuanPrice) {
        this.tuanPrice = tuanPrice;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }
}
