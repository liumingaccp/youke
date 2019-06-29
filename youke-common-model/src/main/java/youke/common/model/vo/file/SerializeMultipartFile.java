package youke.common.model.vo.file;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/23
 * Time: 12:16
 */
public class SerializeMultipartFile implements Serializable {

    private String name;
    private String originalFilename;
    private String contentType;
    private boolean empty;
    private long size;
    private byte[] bytes;

    public SerializeMultipartFile(String name, String originalFilename, String contentType, boolean empty, long size, byte[] bytes) throws IOException{
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.empty = empty;
        this.size = size;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
