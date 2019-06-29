package youke.common.model.vo.result;

import java.io.Serializable;

public class ShopVo implements Serializable {
    private int id;         //店铺id
    private String title;   //店铺名称
    private Integer state;  //店铺状态


    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
