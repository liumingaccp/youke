package youke.core.scheduler.service;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.model.TShopFans;
import youke.common.model.TShopFansTag;
import youke.common.model.TTagRule;
import youke.common.model.vo.result.TagRuleVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.core.scheduler.utils.QueueSender;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FansService extends Base {

    @Resource
    private IShopFansDao shopFansDao;
    @Resource
    private IShopFansTagDao shopFansTagDao;
    @Resource
    private ITagRuleDao tagRuleDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private QueueSender queueSender;

    /**
     *
     */
    public void doShopFansTag(){
        //查找有开启自动打标的配置定期给购物粉丝打标签
        List<Map> openMaps = subscrConfigDao.selectOpenTagRules();
        for (Map map:openMaps) {
            String appId = (String)map.get("appId");
            String youkeId = subscrDao.selectDyk(appId);
            Integer openTagRule0 = (Integer) map.get("openTagRule0");
            Integer openTagRule1 = (Integer) map.get("openTagRule1");
            Integer openTagRule2 = (Integer) map.get("openTagRule2");
            if(openTagRule0>0){
               saveFansTag(0,appId,youkeId);
            }
            if(openTagRule1>0){
               saveFansTag(1,appId,youkeId);
            }
            if(openTagRule2>0){
               saveFansTag(2,appId,youkeId);
            }
        }
    }

    public void doTestFansTag(){
            //String appId = "wx0c0baba2618c2330";
            //String youkeId = "dykWF21ZLd";
            //saveFansTag(0,appId,youkeId);
            //saveFansTag(1,appId,youkeId);
            //saveFansTag(2,appId,youkeId);
    }

    private void saveFansTag(int type,String appId,String youkeId){
        List<TagRuleVo> tagRules = tagRuleDao.getRules(type,appId);
        if(tagRules.size()>0){
            for (TagRuleVo ruleVo:tagRules) {
                List<Long> fansIds = new ArrayList<>();
                if(type==0)
                    fansIds = shopFansDao.selectFansIdByLastDay(ruleVo.getThanNum(),youkeId);
                else if(type==1)
                    fansIds = shopFansDao.selectFansIdByDealNum(ruleVo.getThanNum(),youkeId);
                else if(type==2)
                    fansIds = shopFansDao.selectFansIdByDealTotal(ruleVo.getThanNum()*100,youkeId);
                for(Long fansId:fansIds){
                    if(shopFansTagDao.selectCount(fansId,ruleVo.getTagId(),youkeId)==0){
                        //删除同类型标签
                        shopFansTagDao.deleteSameTags(fansId,ruleVo.getType(),appId,youkeId);
                        TShopFansTag shopFansTag = new TShopFansTag();
                        shopFansTag.setFansid(fansId);
                        shopFansTag.setTagid(ruleVo.getTagId());
                        shopFansTag.setYoukeid(youkeId);
                        shopFansTagDao.insertSelective(shopFansTag);
                        System.out.println("插入"+shopFansTag.getFansid()+","+shopFansTag.getTagid()+","+shopFansTag.getYoukeid());
                    }
                }
            }
        }
    }

    /**
     * 每隔1周同步一次微信粉丝
     */
    public void doSyncWxFans(){
        String key = "SYNC-WXFANS-TIME";
        String slastTime = DateUtil.formatDate(DateUtil.addDays(new Date(),-7),"yyyy-MM-dd");
        List<Map> maps = subscrDao.selectAll();
        if(maps.size()>0){
            for (Map map:maps) {
                String appId = (String) map.get("appId");
                boolean sync = false;
                if(RedisSlaveUtil.hHasKey(key,appId)){
                    String lastTime = (String)RedisSlaveUtil.hget(key,appId);
                    if(slastTime.equals(lastTime)){
                        sync = true;
                    }
                }else{
                    RedisUtil.hset(key,appId,DateUtil.getDate());
                    sync = true;
                }
                if(sync){
                    //发送同步任务
                    queueSender.send("syncsubscrFans.queue",appId);
                    System.out.println("同步微信粉丝："+appId);
                }
            }
        }
    }

}
