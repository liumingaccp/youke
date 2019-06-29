package youke.service.fans.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.TTag;
import youke.common.model.TTagGroup;
import youke.common.model.TTagRule;
import youke.common.model.vo.result.*;
import youke.facade.fans.provider.IFansTagService;
import youke.service.fans.biz.IFansTagBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FansTagService extends Base implements IFansTagService {
    @Resource
    private IFansTagBiz tagBiz;

    public void addTag(Integer groupId, String tittle, String appId) {
        if (notEmpty(groupId) && notEmpty(tittle) && notEmpty(appId)) {
            TTag tag = new TTag();
            tag.setGroupid(groupId);
            tag.setTitle(tittle);
            tag.setRuletype(0);
            tag.setAppid(appId);
            tagBiz.addTag(tag);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void addGroup(String title, String appId) {
        if (notEmpty(title) && notEmpty(appId)) {
            TTagGroup tagGroup = new TTagGroup();
            tagGroup.setAppid(appId);
            tagGroup.setTitle(title);
            tagBiz.addGroup(tagGroup);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void updateStateWithTagRule(boolean open, int type, String appId) {
        if (notEmpty(appId) && notEmpty(open)) {
            tagBiz.updateStateWithTagRule(open, type, appId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void saveTagRule(JSONArray paramArrs, String appId) {
        for (int index = 0; index < paramArrs.size(); index++) {
            JSONObject rule = paramArrs.getJSONObject(index);
            if (rule != null) {
                int type = rule.getInt("type");
                int tagId = rule.getInt("tagId");
                int serialNum = rule.getInt("serialNum");
                int thanNum = rule.getInt("thanNum");
                TTagRule tagRule = new TTagRule();
                tagRule.setType(type);
                tagRule.setAppid(appId);
                tagRule.setTagid(tagId);
                tagRule.setSerialnum(serialNum);
                tagRule.setThannum(thanNum);
                if (index == 0) {
                    //删除之前的规则
                    tagBiz.deleteRuleByTypeAndAppId(type, appId);
                }
                tagBiz.saveRule(tagRule);
            } else {
                throw new BusinessException("无效的请求或参数");
            }
        }
    }

    public void delTag(int id, String youkeId, String appid) {
        if (notEmpty(id) && notEmpty(youkeId) && notEmpty(appid)) {
            tagBiz.deleteTag(id, youkeId, appid);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public List<TagRuleVo> getRules(int type, String appId) {
        if (notEmpty(appId)&&notEmpty(type)) {
            if (type >= 0) {
                return tagBiz.getRules(type, appId);
            } else {
                return  tagBiz.getAllRules(appId);
            }
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void saveTag(Integer id, String title, String appId) {
        if (notEmpty(id) && notEmpty(title) && notEmpty(appId)) {
            tagBiz.updateTag(id, title, appId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void deleteGroup(Integer id, String appId, String youkeId) {
        if (notEmpty(id) && notEmpty(appId) && notEmpty(youkeId)) {
            tagBiz.deleteGroup(id, appId, youkeId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void saveGroup(Integer id, String title, String appId) {
        if (notEmpty(id) && notEmpty(title) && notEmpty(appId)) {
            tagBiz.updateGroup(id, title, appId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public List<TagListVo> getList(String appId) {
        if (notEmpty(appId)) {
            List<TagListVo> listVos = tagBiz.getList(appId);
            if(listVos.size()==1&&listVos.get(0).getTags().size()==0&&appId.startsWith("dyk")){
               tagBiz.doFansRule(appId);
               listVos = tagBiz.getList(appId);
            }
            return listVos;
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public List<TagVo> getWxList(String appId) {
        return tagBiz.getWxList(appId);
    }

    public TagRuleStateVo getRuleState(String appId) {
        if (empty(appId))
            throw new BusinessException("无效的请求或参数");
        return tagBiz.getRuleState(appId);
    }

    @Override
    public List<TagVo> getFriendTags(String friendId, String dykId,String weixinId) {
        if (empty(friendId))
            throw new BusinessException("friendId不能为空");
        return tagBiz.getFriendTags(friendId,dykId,weixinId);
    }

    @Override
    public void bindFriendTag(Integer tagId, String friendId, String weixinId, String youkeId) {
        if (empty(tagId)||empty(friendId)||empty(weixinId)){
            throw new BusinessException("无效的请求或参数");
        }
        tagBiz.addFriendTag(tagId,friendId,weixinId,youkeId);
    }

    @Override
    public void delFriendTag(Integer tagId, String friendId, String weixinId, String youkeId) {
        if (empty(tagId)||empty(friendId)||empty(weixinId)){
            throw new BusinessException("无效的请求或参数");
        }
        tagBiz.deleteFriendTag(tagId,friendId,weixinId,youkeId);
    }

    @Override
    public List<FriendIdVo> getTagFriendIds(String[] tagIdArr, String weixinId, String youkeId) {
        return tagBiz.getTagFriendIds(tagIdArr,weixinId,youkeId);
    }

    @Override
    public List<TagVo> getWeixinTags(String weixinId) {
        return tagBiz.getWeixinTags(weixinId);
    }

}
