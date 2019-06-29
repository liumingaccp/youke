package youke.service.wx.biz;

import youke.facade.wx.vo.tag.TagVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 16:26
 */
public interface ITagBiz {

    /**
     * 创建标签
     * @param tagName
     */
    String createTag(String tagName, String appId);

    /**
     * 所有标签情况
     * @return
     */
    List<TagVo> getAllTag(String appId);

    /**
     * 更新
     */
    void updateTag(int tagId, String tagName, String appId);

    void delete(int id, String appId);

    /**
     * 批量打标
     * @param openIds
     * @param tagId
     */
    void batchTagging(List<String> openIds, int tagId, String appId);

    /**
     * 批量删除标
     * @param openIds
     * @param tagId
     */
    void batchuntagging(List<String> openIds, int tagId, String appId);

    /**
     * 获取粉丝的所有标签
     * @param openId
     * @return
     */
    List<Integer> getTagIdList(String openId, String appId);

    /**
     * 获取某一个标签下的所有粉丝id
     * @param wxTagId
     * @param appId
     * @return
     *      见微信参数
     */
    String getOpensByTagId(int wxTagId, String appId, String nextOpenId);

}
