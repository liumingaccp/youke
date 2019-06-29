package youke.facade.helper.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 15:53
 */
public class DemoPics implements Serializable{
    private Integer type;
    private String picUrl;
    private String picName;
    private String picInfo;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicInfo() {
        return picInfo;
    }

    public void setPicInfo(String picInfo) {
        this.picInfo = picInfo;
    }
}
