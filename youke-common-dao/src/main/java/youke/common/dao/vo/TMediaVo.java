package youke.common.dao.vo;

public class TMediaVo {

    /**
     * text,news,sysnews,img
     */
    private String mediaType;
    /**
     * 自己业务库的素材Id
     */
    private Integer materialId;
    private String content;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
