package youke.service.fans.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TFriendTag;
import youke.common.model.TTag;
import youke.common.model.TTagGroup;
import youke.common.model.TTagRule;
import youke.common.model.vo.result.*;
import youke.service.fans.biz.IFansTagBiz;
import youke.service.fans.queue.producer.FansTagQueue;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FansTagBiz implements IFansTagBiz {
    @Resource
    private FansTagQueue fansTagQueue;
    @Resource
    private ITagDao tagDao;
    @Resource
    private ITagRuleDao tagRuleDao;
    @Resource
    private ITagGroupDao tagGroupDao;
    @Resource
    private IShopFansTagDao shopFansTagDao;
    @Resource
    private ISubscrFansTagDao subscrFansTagDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IFriendTagDao friendTagDao;
    @Resource
    private IConfigDao configDao;

    public void addTag(TTag tag) {
        if (tag.getGroupid() == 0) {
            throw new BusinessException("无法添加系统标签");
        }
        TTag tTag = tagDao.selectTagByTitleAndAppId(tag.getTitle(), tag.getAppid());
        TTagGroup group = tagGroupDao.getGroupByGroupId(tag.getGroupid());
        if (tTag == null && group != null) {//当前不存在该标签,存在标签组
            tagDao.insertSelective(tag);
        } else {
            throw new BusinessException("标签名称重复或不存在对应的标签组");
        }
    }

    public void addGroup(TTagGroup tagGroup) {
        TTagGroup group = tagGroupDao.selectTagGroupByTitleAndAppId(tagGroup.getTitle(), tagGroup.getAppid());
        if (group == null) {
            tagGroupDao.insertSelective(tagGroup);
        } else {
            throw new BusinessException("标签组名称重复");
        }
    }

    /**
     * @param open  true:开启规则 false:关闭规则
     * @param type  0最近成交天数，1购买次数，2购买金额
     * @param appId
     */
    public void updateStateWithTagRule(boolean open, int type, String appId) {
        int count = subscrConfigDao.updateState(open, type, appId);
        if (count == 0) {
            throw new BusinessException("更新失败");
        }
    }

    public void saveRule(TTagRule tagRule) {
        //保存新的规则
        tagRuleDao.insertSelective(tagRule);
    }

    public void deleteRuleByTypeAndAppId(int type, String appId) {
        //删除之前的规则
        tagRuleDao.deleteRuleByTypeAndAppId(type, appId);
    }

    public void deleteTag(int tagId, String youkeId, String appId) {
        TTag tag = tagDao.selectByPrimaryKey(tagId);
        if (tag != null) {
            if (tag.getGroupid() == 0) {
                throw new BusinessException(" 系统标签不能删除");
            }
            if (tag.getWxtagid() != null) {
                fansTagQueue.send("fanstag.queue", tag.getWxtagid() + "," + tag.getAppid());
            }
        } else {
            throw new BusinessException("不存在对应标签或标签删除失败");
        }
        //删除标签库中的标签
        int amount = tagDao.deleteTagByIdANdAppId(tagId, appId);
        if (amount != 0) {
            //将对应购物粉丝的标签设为空
            shopFansTagDao.removeShopFansTag(tagId, youkeId);
            //将对应微信粉丝的标签设为空
            subscrFansTagDao.removeSubScrFansTag(tagId, appId);
        } else {
            throw new BusinessException("不存在对应标签或标签删除失败");
        }
    }

    public List<TagRuleVo> getRules(int type, String appId) {
        return tagRuleDao.getRules(type, appId);
    }

    public void updateTag(Integer id, String title, String appId) {
        TTag tTag = tagDao.selectTagByTitleAndAppId(title, appId);
        if (tTag == null) {
            TTag tag = tagDao.selectByPrimaryKey(id);
            if (tag != null) {
                if (tag.getGroupid() != 0) {
                    tag.setTitle(title);
                    tagDao.updateByPrimaryKeySelective(tag);
                } else {
                    throw new BusinessException("无法编辑系统标签");
                }
            } else {
                throw new BusinessException("标签不存在");
            }
        } else {
            throw new BusinessException("标签名称重复");
        }
    }

    public void deleteGroup(Integer id, String appId, String youkeId) {
        if (id == 0) {
            throw new BusinessException("系统标签组不能删除");
        }
        //删除标签组
        int count = tagGroupDao.deleteByPrimaryKeyAndAppId(id, appId);
        if (count != 0) {
            //解除组下的标签和组的关系
            tagDao.removeRelationWithGroupByGroupIdAndAppId(id, appId);
        } else {
            throw new BusinessException("不存在该标签组");
        }
    }

    public void updateGroup(Integer id, String title, String appId) {
        TTagGroup tagGroup = tagGroupDao.selectTagGroupByTitleAndAppId(title, appId);
        if (tagGroup == null) {
            int count = tagGroupDao.updateByGroupIdAndAppId(id, title, appId);
            if (count == 0) {
                throw new BusinessException("不存在该标签组");
            }
        } else {
            throw new BusinessException("标签组名称重复");
        }
    }

    public List<TagListVo> getList(String appId) {
        List<TagListVo> listVos = tagGroupDao.getList(appId);
        for (TagListVo listVo : listVos) {
            listVo.setTags(tagDao.selectByGroupIdAndAppId(listVo.getGroupId(), appId));
        }
        return listVos;
    }

    public List<TagRuleVo> getAllRules(String appId) {
        return tagRuleDao.getAllRules(appId);
    }

    public List<TagVo> getWxList(String appId) {
        return tagDao.getWxList(appId);
    }

    public TagRuleStateVo getRuleState(String appId) {
        return subscrConfigDao.getRuleState(appId);
    }

    @Override
    public List<TagVo> getFriendTags(String friendId, String youkeId, String weixinId) {
        return friendTagDao.selectFriendTags(friendId, youkeId, weixinId);
    }

    @Override
    public void addFriendTag(Integer tagId, String friendId, String weixinId, String youkeId) {
        if (friendTagDao.selectCount(tagId, friendId) > 0) {
            throw new BusinessException("已绑定该标签");
        }
        TFriendTag friendTag = new TFriendTag();
        friendTag.setFriendId(friendId);
        friendTag.setTagId(tagId);
        friendTag.setWeixinId(weixinId);
        friendTag.setYoukeId(youkeId);
        friendTagDao.insert(friendTag);
    }

    @Override
    public List<FriendIdVo> getTagFriendIds(String[] tagIdArr, String weixinId, String youkeId) {
        return friendTagDao.selectTagFriendIds(tagIdArr, weixinId, youkeId);
    }

    @Override
    public List<TagVo> getWeixinTags(String weixinId) {
        return friendTagDao.selectWeixinTags(weixinId);
    }

    @Override
    public void deleteFriendTag(Integer tagId, String friendId, String weixinId, String youkeId) {
        friendTagDao.delFriendTag(tagId,friendId,weixinId,youkeId);
    }

    /**
     * 初始化购物粉丝规则
     */
    public void doFansRule(String appId){
        if(tagDao.selectSystagCount(appId)==0) {
            String tagConfig0 = configDao.selectVal("sys_tag_rule0");
            String tagConfig1 = configDao.selectVal("sys_tag_rule1");
            String tagConfig2 = configDao.selectVal("sys_tag_rule2");
            saveFansRule(tagConfig0.split(","), 0, appId);
            saveFansRule(tagConfig1.split(","), 1, appId);
            saveFansRule(tagConfig2.split(","), 2, appId);
        }
    }

    private void saveFansRule(String[] tagConfigs,int type,String appId){
        for (int i=0;i<tagConfigs.length;i++) {
            String[] kv = tagConfigs[i].split("-");
            TTag tag = new TTag();
            tag.setAppid(appId);
            tag.setTitle(kv[0]);
            tag.setGroupid(0);
            tag.setRuletype(type);
            tagDao.insertSelective(tag);
            TTagRule tagRule = new TTagRule();
            tagRule.setAppid(appId);
            tagRule.setSerialnum(i+1);
            tagRule.setTagid(tag.getId());
            tagRule.setThannum(Integer.parseInt(kv[1]));
            tagRule.setType(type);
            tagRuleDao.insertSelective(tagRule);
        }
    }

}
