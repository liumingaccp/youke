package youke.facade.wx.vo.reply;

import java.io.Serializable;

public class AutoReplyVo implements Serializable {
    private String mediaType;

    private String content;

    private int materialId;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public AutoReplyVo(){}

    public AutoReplyVo(String content,String mediaType, int materialId){
        this.content = content;
        this.mediaType = mediaType;
        this.materialId = materialId;
    }
}
