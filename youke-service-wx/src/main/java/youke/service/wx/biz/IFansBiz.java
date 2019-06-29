package youke.service.wx.biz;

import org.springframework.web.multipart.MultipartFile;
import youke.facade.wx.vo.MediaVo;
import youke.facade.wx.vo.fans.FansVo;

import java.util.List;

/**
 * 粉丝管理业务
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
 */
public interface IFansBiz {

    /**
     * 关注时候保存粉丝入库(区别新增还是修改)
     * @param fans
     */
    void saveFans(FansVo fans);

    /**
     * 关注时候保存粉丝
     * @param openId
     * @param appId
     */
    boolean saveFans(String openId,String appId);

    /**
     * 更新粉丝进黑名单
     * @param openId
     * @param appId
     */
    void updateFansInBlack(String openId,String appId);

    /**
     * 更新粉丝出黑名单
     * @param openId
     * @param appId
     */
    void updateFansOutBlack(String openId,String appId);

    /**
     * 更新粉丝进黑名单
     * @param openIds
     * @param appId
     */
    void updateFansInBlack(List<String> openIds,String appId);

    /**
     * 更新粉丝出黑名单
     * @param openIds
     * @param appId
     */
    void updateFansOutBlack(List<String> openIds ,String appId);

    void updateFansLastTime(String openId);

    /**
     * 同步公众号粉丝
     * @param appId
     */
    void doSyncFans(String appId);
}