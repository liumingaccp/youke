package youke.common.model.vo.result.wxper;

import youke.common.model.vo.param.QueryObjectVO;

import java.io.Serializable;

public class AutoLikeTaskVo extends QueryObjectVO implements Serializable {
    private Long id;
    private String deviceName;
    private Integer state;
    private Integer type;
    private Integer likeNum;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setLikeNum(Integer likeNum) {
        if (likeNum==null){
            likeNum = 0;
        }
        this.likeNum = likeNum;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return empty2null(deviceName);
    }

    public Integer getState() {
        return state;
    }

    public Integer getType() {
        return type;
    }

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }
}
