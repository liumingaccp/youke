package youke.common.model.vo.param;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/12
 * Time: 16:01
 */
public class DykPcBaseQueryVo implements Serializable{
    protected String appId;
    protected String youkeId;
    protected Integer page =1;
    protected Integer limit =20;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        if(appId != null && !appId.trim().equals("")){
            this.appId = appId;
        }
        this.appId = appId;
    }

    public String getYoukeId() {
        return youkeId;
    }

    public void setYoukeId(String youkeId) {
        if(youkeId != null && !youkeId.trim().equals("")){
            this.youkeId = youkeId;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
            this.page = page;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > -2){
            this.limit = limit;
        }
    }

    public static boolean hasLenght(String str){
        return str != null && !str.trim().equals("") ? true : false;
    }

}
