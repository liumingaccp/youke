package youke.common.model.vo.param.wxper;

import youke.common.model.vo.param.DykPcBaseQueryVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/11
 * Time: 14:44
 */
public class WxPerFansQueryVo extends DykPcBaseQueryVo implements Serializable {


    private Integer shopId = -1;
    private Long wxAccountId;
    private Integer subState = -1;
    private String mobile;
    private String weChatNum;
    private String nickName;
    private String accountName;

    private Long deviceId = -1L;

    private List<Integer> tagIdList = new ArrayList<>();
    private Integer tagCount;
    private List<Long> fansIds = new ArrayList<>();
    private Integer fansIdsCount;

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public Integer getTagCount() {
        return tagIdList == null? 0: tagIdList.size();
    }

    public void setFansIds(List<Long> fansIds) {
        this.fansIds = fansIds;
    }

    public List<Long> getFansIds() {
        return fansIds;
    }

    public Integer getFansIdsCount() {
        return fansIds == null ? 0 : fansIds.size();
    }

    public void setDeviceId(Long deviceId) {
        if(deviceId != null && deviceId > -1){
            this.deviceId = deviceId;
        }
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        if(shopId != null && shopId > -1){
            this.shopId = shopId;
        }
    }

    public void setWxAccountId(Long wxAccountId) {
        this.wxAccountId = wxAccountId;
    }

    public Long getWxAccountId() {
        return wxAccountId;
    }

    public Integer getSubState() {
        return subState;
    }

    public void setSubState(Integer subState) {
        if(subState != null && subState > -1){
            this.subState = subState;
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = hasLenght(mobile)? "%"+ mobile +"%" : null;
    }

    public String getWeChatNum() {
        return weChatNum;
    }

    public void setWeChatNum(String weChatNum) {
        this.weChatNum = hasLenght(weChatNum)? weChatNum : null;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = hasLenght(nickName)? "%"+ nickName +"%" : null;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = hasLenght(accountName)? "%"+ accountName +"%" : null;
    }
}
