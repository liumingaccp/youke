package youke.facade.wx.vo.message;

public class MusicVo {

    public MusicVo(String title, String description, String musicURL, String HQMusicUrl, String thumbMediaId) {
        this.Title = title;
        this.Description = description;
        this.MusicURL = musicURL;
        this.HQMusicUrl = HQMusicUrl;
        this.ThumbMediaId = thumbMediaId;
    }

    private String Title;
    private String Description;
    private String MusicURL;
    private String HQMusicUrl;
    private String ThumbMediaId;

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

    public String getMusicURL() {
        return MusicURL;
    }

    public void setMusicURL(String musicURL) {
        MusicURL = musicURL;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
