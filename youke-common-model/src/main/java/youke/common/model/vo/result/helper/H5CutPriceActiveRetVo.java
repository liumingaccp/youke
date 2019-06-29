package youke.common.model.vo.result.helper;

import java.io.Serializable;

/**
 * H5砍价活动列表
 */
public class H5CutPriceActiveRetVo implements Serializable {
    private Integer id;
    private String cover;
    private String title;
    private Integer price;
    private Integer dealPrice;
    private Integer usedNum;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getId() {
        return id;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDealPrice() {
        return dealPrice;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    @Override
    public String toString() {
        return "H5CutPriceActiveRetVo{" +
                "id=" + id +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", dealPrice=" + dealPrice +
                ", usedNum=" + usedNum +
                '}';
    }
}
