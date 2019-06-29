package youke.facade.wx.vo.message;

import java.io.Serializable;

public class MediaIdVo implements Serializable {

    public MediaIdVo(String mediaId) {
        this.MediaId = mediaId;
    }

    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
