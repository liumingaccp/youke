package youke.service.fans.queue.message;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 18:51
 */
public class FansImportMessage implements Serializable {

    private String url; //导入的文件路径
    private int shopId; //店铺id
    private String youkeId;    //youkeId
    private int importId;   //用于修改导入后的表

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        this.youkeId = youkeId;
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public FansImportMessage(){

    }

    public FansImportMessage(String url, int shpoId, String youkeId, int importId){
        this.url = url;
        this.shopId = shpoId;
        this.youkeId = youkeId;
        this.importId = importId;
    }
}
