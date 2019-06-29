package youke.facade.wx.vo.reply;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/17
 * Time: 18:05
 */
public class KeyReplayParam implements Serializable {
    private int id;
    private String title;
    private List<String> keys;
    private String mediaType;
    private String content;
    private String materialId;
    private int keyMatch;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return StringUtils.hasLength(this.title) ? title : null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaType() {
        return StringUtils.hasLength(this.mediaType) ? mediaType : null;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContent() {
        return StringUtils.hasLength(this.content) ? content : null;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaterialId() {
        return StringUtils.hasLength(this.materialId) ? materialId : null;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public int getKeyMatch() {
        return keyMatch;
    }

    public void setKeyMatch(int keyMatch) {
        this.keyMatch = keyMatch;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getKeys() {
        return keys;
    }
}
