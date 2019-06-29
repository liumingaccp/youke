package youke.facade.fans.provider;

import net.sf.json.JSONArray;
import youke.common.model.vo.result.*;

import java.util.List;

public interface IFansTagService {
    /**
     * 添加标签
     *
     * @param groupId
     * @param tittle
     * @param appid
     */
    void addTag(Integer groupId, String tittle, String appid);

    /**
     * 添加标签组
     *
     * @param title
     * @param appId
     */
    void addGroup(String title, String appId);

    /**
     * 开启或关闭标签规则
     *
     * @param type
     * @param appId
     */
    void updateStateWithTagRule(boolean open, int type, String appId);

    /**
     * 保存标签规则
     *
     * @param paramArrs
     */
    void saveTagRule(JSONArray paramArrs, String appid);

    /**
     * 删除标签
     *
     * @param id
     * @param youkeId
     */
    void delTag(int id, String youkeId, String appid);

    /**
     * 获取对应类型的标签规则
     *
     * @param type
     * @param appId
     * @return
     */
    List<TagRuleVo> getRules(int type, String appId);

    /**
     * 修改标签
     *
     * @param id
     * @param title
     * @param appId
     */
    void saveTag(Integer id, String title, String appId);

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
    void saveGroup(Integer id, String title, String appId);

    /**
     * 获取对应appid下的所有标签
     *
     * @param appId
     * @return
     */
    List<TagListVo> getList(String appId);

    List<TagVo> getWxList(String appId);

    /**
     * 获取系统标签规则状态
     * @param appId
     * @return
     */
    TagRuleStateVo getRuleState(String appId);

    List<TagVo> getFriendTags(String friendId, String dykId,String weixinId);

    void bindFriendTag(Integer tagId, String friendId, String weixinId, String youkeId);

    void delFriendTag(Integer tagId, String friendId, String weixinId, String youkeId);

    List<FriendIdVo> getTagFriendIds(String[] tagIdArr, String weixinId, String youkeId);

    List<TagVo> getWeixinTags(String weixinId);

}
