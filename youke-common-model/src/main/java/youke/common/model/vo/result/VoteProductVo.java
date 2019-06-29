package youke.common.model.vo.result;

import java.io.Serializable;

public class VoteProductVo implements Serializable {
    private Long id;            //图片id
    private String title;       //图片标题
    private String PicUrl;      //图片路径

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPicUrl() {
        return PicUrl;
    }
}
