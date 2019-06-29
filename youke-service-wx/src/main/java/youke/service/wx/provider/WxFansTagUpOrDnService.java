package youke.service.wx.provider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.dao.ISubscrFansDao;
import youke.common.dao.ISubscrFansTagDao;
import youke.common.dao.ITagDao;
import youke.common.dao.ITagGroupDao;
import youke.common.model.TSubscrFansTag;
import youke.common.model.TTag;
import youke.common.model.TTagGroup;
import youke.facade.wx.provider.IWxFansTagUpOrDnService;
import youke.facade.wx.vo.tag.TagVo;
import youke.service.wx.biz.ITagBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/12
 * Time: 15:12
 */
@Service
public class WxFansTagUpOrDnService implements IWxFansTagUpOrDnService {

    @Resource
    private ITagBiz tagBiz;
    @Resource
    private ITagDao tagDao;
    @Resource
    private ISubscrFansTagDao subscrFansTagDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ITagGroupDao tagGroupDao;
    /**
     * 同步管理者可能通过非本平台添加的标签
     *
     * @param appId
     */
    public void doDownload(String appId) {
        //1.查询微信端的全部标签
        List<TagVo> allTag = tagBiz.getAllTag(appId);
        //2.查询本地所有上传过的tags
        List<Integer> tagIds = tagDao.selectAllWxTagIdByAppId(appId);
        //3.剔除本地上传过的tag
        if (allTag.size() > 0) {
            Iterator<TagVo> iterator = allTag.iterator();
            while (iterator.hasNext()) {
                TagVo next = iterator.next();
                if (tagIds.contains(next.getId())) {
                    iterator.remove();
                }
            }
        }
        //4.如果还有多余的,同步至本地
        if (allTag.size() > 0) {
            //查看是否存在微信标签组
            TTagGroup group = tagGroupDao.selectTagGroupByTitleAndAppId("微信标签", appId);
            if(group == null){
                //创建微信标签组
                group = new TTagGroup();
                group.setAppid(appId);
                group.setTitle("微信标签");
                tagGroupDao.insertSelective(group);
            }
            for (TagVo vo : allTag) {
                TTag tTag = new TTag();
                tTag.setWxtagid(vo.getId() + "");
                tTag.setAppid(appId);
                tTag.setTitle(vo.getName());
                tTag.setRuletype(0);
                //查询是否存在同名的系统标签
                TTag sysTag = tagDao.selectTagByTitleAndAppId(vo.getName(), appId);
                if(sysTag != null){
                    //如果存在,沿用原先的
                    sysTag.setWxtagid(vo.getId()+"");
                    tagDao.updateByPrimaryKeySelective(sysTag);
                }else{
                    tTag.setGroupid(group.getId());
                    tagDao.insertSelective(tTag);
                    //同步标签-微信粉丝关联表
                    String ret = tagBiz.getOpensByTagId(vo.getId(), appId, null);
                    JSONObject jsonObject = JSONObject.fromObject(ret);
                    int count = jsonObject.getInt("count");
                    if (count > 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray openids = data.getJSONArray("openid");
                        for (int i = 0; i < openids.size(); i++) {
                            String openId = openids.getString(i);
                            //查询是否已经打标
                            Integer id = subscrFansTagDao.isRelative(openId, tTag.getId());
                            if(id == null || id == 0){
                                TSubscrFansTag fansTag = new TSubscrFansTag();
                                fansTag.setSyncstate(1);
                                fansTag.setTagid(tTag.getId());
                                fansTag.setOpenid(openId);
                                subscrFansTagDao.insertSelective(fansTag);
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * @param appId
     */
    public void doUpload(String appId) {
        //1 查询标签(被微信粉丝消费过)
        List<Integer> fansTags = subscrFansTagDao.selectAllTags(appId);
        int wxTagId = 0;
        //2 遍历,获取当下的所有localOpenIds
        for (Integer fansTagId : fansTags) {
            TTag tag = tagDao.selectByPrimaryKey(fansTagId);
            if (tag != null) {

                List<String> localOpenIds = subscrFansDao.selectOpenIdsByTagId(fansTagId);
                if (tag.getWxtagid() != null && tag.getWxtagid() != "") {
                    //2.1 如果标签已经上传至微信
                    //2.1.1 查询微信,获取此标签下的所有wxOpenIds,
                    wxTagId = Integer.parseInt(tag.getWxtagid());
                    List<String> wxOpenIds = new ArrayList<String>();
                    String ret = tagBiz.getOpensByTagId(Integer.parseInt(tag.getWxtagid()), appId, null);
                    JSONObject jsonObject = JSONObject.fromObject(ret);
                    int count = jsonObject.getInt("count");
                    if (count > 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray openid = data.getJSONArray("openid");
                        for (int i = 0; i < openid.size(); i++) {
                            wxOpenIds.add(openid.getString(i));
                        }
                    }
                    //2.1.2 剔除localOpenIds本地已经打上打上此微信标签的openId
                    Iterator<String> iterator = localOpenIds.iterator();
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        if (wxOpenIds.contains(next)) {
                            iterator.remove();
                        }
                    }
                } else {
                    //2.2 如果标签没有上传至微信
                    String ret = tagBiz.createTag(tag.getTitle(), appId);
                    JSONObject jsonObject = JSONObject.fromObject(ret);
                    JSONObject wxTag = jsonObject.getJSONObject("tag");
                    wxTagId = wxTag.getInt("id");
                    tag.setWxtagid(wxTagId + "");
                    //修改tag状态
                    tagDao.updateByPrimaryKeySelective(tag);
                    //修改中间表标签状态
                    subscrFansTagDao.updateByFansTagId(fansTagId, 1);
                }
                //2.2.2 为剩余的localOpenIds批量打标
                if (localOpenIds.size() > 0) {
                    tagBiz.batchTagging(localOpenIds, wxTagId, appId);
                }
            }
        }
    }
}
