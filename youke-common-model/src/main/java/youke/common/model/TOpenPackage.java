package youke.common.model;

import java.io.Serializable;

public class TOpenPackage implements Serializable {
    private Integer id;

    private String title;

    private Integer marketprice;

    private Integer price;

    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(Integer marketprice) {
        this.marketprice = marketprice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}