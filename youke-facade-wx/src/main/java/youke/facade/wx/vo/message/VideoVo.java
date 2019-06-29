package youke.facade.wx.vo.message;

import java.io.Serializable;

public class VideoVo implements Serializable {

    public VideoVo(String mediaId, String title, String description) {
        this.MediaId = mediaId;
        this.Title = title;
        this.Description = description;
    }

    private String MediaId;

    private String Title;

    private String Description;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
