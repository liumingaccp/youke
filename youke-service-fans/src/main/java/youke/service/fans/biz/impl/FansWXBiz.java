package youke.service.fans.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.ISubscrFansDao;
import youke.common.dao.ISubscrFansTagDao;
import youke.common.model.TSubscrFans;
import youke.common.model.TSubscrFansTag;
import youke.common.model.TTag;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.TagVo;
import youke.common.model.vo.result.WxFansVo;
import youke.common.utils.DateUtil;
import youke.service.fans.biz.IFansWXBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 18:49
 */
@Service
public class FansWXBiz implements IFansWXBiz{

    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrFansTagDao subscrFansTagDao;

    public PageInfo<WxFansVo> getList(WxFansQueryVo qo) {

        if(qo.getTagIds() != null &&  !"".equals(qo.getTagIds())){
            qo.setTags(qo.getTagIds());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(qo);
            if(openIds == null || openIds.size() == 0 || openIds.get(0) == null){
                return null;
            }
            qo.setOpenIds(openIds);
        }
        //设置是否排序
        if(qo.getSortStr() != null){
            PageHelper.startPage(qo.getPage(), qo.getLimit(), qo.getSortStr());
        }else{
            PageHelper.startPage(qo.getPage(), qo.getLimit());
        }
        List<TSubscrFans> subscrFans = subscrFansDao.queryList(qo);
        if(subscrFans == null || subscrFans.size() <= 0){
            return new PageInfo<>();
        }
        PageModel<WxFansVo> wxFansVos = new PageModel<WxFansVo>((Page) subscrFans);
        for(TSubscrFans item : subscrFans){
            WxFansVo vo = new WxFansVo();
            vo.setCity(item.getCity());
            vo.setCountry(item.getCountry());
            vo.setHeadimgurl(item.getHeadimgurl());
            vo.setIntegral(item.getIntegral());
            vo.setLanguage(item.getLanguage());
            vo.setMobile(item.getMobile());
            vo.setNickname(item.getNickname());
            vo.setOpenid(item.getOpenid());
            vo.setProvince(item.getProvince());
            vo.setRemark(item.getRemark());
            vo.setSex(item.getSex());
            vo.setSubstate(item.getSubstate());
            vo.setTruename(item.getTruename());
            vo.setSubTime(item.getSubtime());
            if(item.getLasttime() != null){
                vo.setLastTime(DateUtil.formatDateTime(item.getLasttime()));
            }
            List<TTag> tTags = subscrFansDao.selectTTagByOpenId(item.getOpenid());
            if(tTags != null){
                for (TTag tag : tTags){
                    TagVo tagVo = new TagVo();
                    tagVo.setId(tag.getId());
                    tagVo.setTitle(tag.getTitle());
                    vo.getTags().add(tagVo);
                }
            }

            wxFansVos.getResult().add(vo);
        }

        return new PageInfo<WxFansVo>(wxFansVos);
    }

    public void saveRemark(String openId, String remark) {
        subscrFansDao.saveRemark(openId, remark);
    }

    public PageInfo<WxFansVo> getBlackList(String nickName, int page, int limit, String appId) {
        PageHelper.startPage(page, limit);
        List<TSubscrFans> subscrFans =  subscrFansDao.queryBlackList(nickName, appId);
        PageModel<WxFansVo> wxFansVos = new PageModel<WxFansVo>((Page) subscrFans);
        for(TSubscrFans item : subscrFans){
            WxFansVo vo = new WxFansVo();
            vo.setCity(item.getCity());
            vo.setCountry(item.getCountry());
            vo.setHeadimgurl(item.getHeadimgurl());
            vo.setIntegral(item.getIntegral());
            vo.setLanguage(item.getLanguage());
            vo.setMobile(item.getMobile());
            vo.setNickname(item.getNickname());
            vo.setOpenid(item.getOpenid());
            vo.setProvince(item.getProvince());
            vo.setRemark(item.getRemark());
            vo.setSex(item.getSex());
            vo.setSubstate(item.getSubstate());
            vo.setTruename(item.getTruename());
            vo.setLastTime(DateUtil.formatDateTime(item.getLasttime()));
            wxFansVos.getResult().add(vo);
        }

        return new PageInfo<WxFansVo>(wxFansVos);
    }

    public void addTags(String openIds, String tags) {
        String[] openIds2 = openIds.split(",");
        for(String openId : openIds2){
            //查看中间表是否有值
            List<Integer> tagList = subscrFansTagDao.selectTagsIdByOpenId(openId);
            if(tagList == null){
                tagList = new ArrayList<Integer>();
            }
            String[] split = tags.split(",");
            List<String> list = Arrays.asList(split);
            Iterator<String> it = list.iterator();
            while (it.hasNext()){
                int next = Integer.parseInt(it.next());
                if(!tagList.contains(next)){
                    TSubscrFansTag fansTag = new TSubscrFansTag();
                    fansTag.setOpenid(openId);
                    fansTag.setTagid(next);
                    fansTag.setSyncstate(0);
                    subscrFansTagDao.insertSelective(fansTag);
                }
            }
        }
    }

    public void deleteTags(String openId, String tags) {
        String[] split = tags.split(",");
        //删除中间表
        subscrFansTagDao.deleteTagsRelative(openId, split);

    }

    public void batchBlack(List<String> openIds, String appid) {
        subscrFansDao.batchUpdateState(openIds, appid, 1);
    }

    public void batchOutBlack(List<String> openIds, String appid) {
        subscrFansDao.batchUpdateState(openIds, appid, 0);
    }
}
