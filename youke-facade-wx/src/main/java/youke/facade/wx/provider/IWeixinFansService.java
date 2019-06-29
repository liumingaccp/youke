package youke.facade.wx.provider;

import youke.facade.wx.vo.fans.FansVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 19:01
 */
public interface IWeixinFansService {
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
    void saveFans(String openId,String appId);

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
    void updateFansInBlack(List<String> openIds, String appId);

    /**
     * 更新粉丝出黑名单
     * @param openIds
     * @param appId
     */
    void updateFansOutBlack(List<String> openIds ,String appId);

    /**
     * 上传标签
     */
    void upTags(String appId);

    /**
     * 下载微信标签
     * @param appId
     */
    void downTags(String appId);

    /**
     * 同步微信删除
     * @param openId
     * @param tags
     */
    void deleteOpenIdTags(String openId, String tags, String appId);


    /**
     *
     * @param tagId
     * @param appId
     */
    void deleteTag(int tagId, String appId);
}
