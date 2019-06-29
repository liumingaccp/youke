package youke.service.fans.biz;

import youke.common.model.TTag;
import youke.common.model.TTagGroup;
import youke.common.model.TTagRule;
import youke.common.model.vo.result.*;

import java.util.List;

/**
 * 标签管理业务
 */
public interface IFansTagBiz {
    /**
     * 添加标签
     *
     * @param tag
     */
    void addTag(TTag tag);

    /**
     * 添加标签组
     *
     * @param tagGroup
     */
    void addGroup(TTagGroup tagGroup);

    /**
     * 保存规则
     *
     * @param tagRule
     */
    void saveRule(TTagRule tagRule);
    //删除之前的规则

    /**
     * 保存前删除所有对应的规则
     *
     * @param type
     * @param appId
     */
    void deleteRuleByTypeAndAppId(int type, String appId);

    /**
     * 删除标签
     *
     * @param tagId
     * @param appId
     */
    void deleteTag(int tagId, String youkeId, String appId);

    /**
     * 开启或关闭规则
     *
     * @param open
     * @param type
     * @param appId
     */
    void updateStateWithTagRule(boolean open, int type, String appId);

    /**
     * 获取对应类型的标签规则
     *
     * @param type
     * @param appId
     * @return
     */
    List<TagRuleVo> getRules(int type, String appId);

    /**
     * 更新标签
     *
     * @param id
     * @param title
     * @param appId
     */
    void updateTag(Integer id, String title, String appId);

    /**
     * 删除标签组
     *
     * @param id
     * @param appId
     */
    void deleteGroup(Integer id, String appId, String youkeId);

    /**
     * 更新标签组
     *
     * @param id
     * @param title
     * @param appId
     */
    void updateGroup(Integer id, String title, String appId);

    /**
     * 获取所有标签
     *
     * @param appId
     * @return
     */
    List<TagListVo> getList(String appId);

    /**
     * 获取对应appid下的所有标签规则
     *
     * @param appId
     * @return
     */
    List<TagRuleVo> getAllRules(String appId);

    List<TagVo> getWxList(String appId);

    /**
     * 获取系统标签规则状态
     * @param appId
     * @return
     */
    TagRuleStateVo getRuleState(String appId);

    List<TagVo> getFriendTags(String friendId, String dykId,String weixinId);

    void addFriendTag(Integer tagId, String friendId, String weixinId, String youkeId);

    List<FriendIdVo> getTagFriendIds(String[] tagIdArr, String weixinId, String youkeId);

    List<TagVo> getWeixinTags(String weixinId);

    void deleteFriendTag(Integer tagId, String friendId, String weixinId, String youkeId);

    void doFansRule(String appId);
}
