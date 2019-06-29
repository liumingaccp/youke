package youke.facade.pay.vo;

import java.io.Serializable;

public class ShopPackageVo implements Serializable {

    private int packageId;
    private String title;
    private String content;
    private int num;
    private String serTerm;
    private int price;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSerTerm() {
        return serTerm;
    }

    public void setSerTerm(String serTerm) {
        this.serTerm = serTerm;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
