package youke.common.model.vo.result;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 11:27
 */
public class ShopFansImportVo  implements Serializable {
    private Integer id;

    private String filename;

    private Integer state;

    private Date createTime;

    private Date completetime;

    private Integer successnum;

    private Integer failnum;

    private String failcsvurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public Integer getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Integer successnum) {
        this.successnum = successnum;
    }

    public Integer getFailnum() {
        return failnum;
    }

    public void setFailnum(Integer failnum) {
        this.failnum = failnum;
    }

    public String getFailcsvurl() {
        return failcsvurl;
    }

    public void setFailcsvurl(String failcsvurl) {
        this.failcsvurl = failcsvurl;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
