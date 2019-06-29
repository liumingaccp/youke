package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 19:13
 */
public class IntegralActiveDetailVo implements Serializable{
    private Date createTime;
    private int price;
    private String goodsUrl;
    private String payBuss;
    private String taoCode;
    private String pageQrcode;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public String getPayBuss() {
        return payBuss;
    }

    public void setPayBuss(String payBuss) {
        this.payBuss = payBuss;
    }

    public String getTaoCode() {
        return taoCode;
    }

    public void setTaoCode(String taoCode) {
        this.taoCode = taoCode;
    }

    public void setPageQrcode(String pageQrcode) {
        this.pageQrcode = pageQrcode;
    }

    public String getPageQrcode() {
        return pageQrcode;
    }
}
