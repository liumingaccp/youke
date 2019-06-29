package youke.service.helper.provider;

import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.facade.helper.provider.IHelperService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/31
 * Time: 15:43
 */
@Service
public class HelperService implements IHelperService{

    @Resource
    private ICollageActiveDao collageActiveDao;
    @Resource
    private ICutpriceActiveDao cutpriceActiveDao;
    @Resource
    private IFollowActiveDao followActiveDao;
    @Resource
    private IRebateActiveDao rebateActiveDao;
    @Resource
    private ITaokeActiveDao taokeActiveDao;
    @Resource
    private ITrialActiveDao trialActiveDao;
    @Resource
    private ISubscrDao subscrDao;

    @Override
    public List<String> getHelperActives(String appId) {

        List<String> actives = new ArrayList<>();
        String dyk = subscrDao.selectDyk(appId);
        Long collage = collageActiveDao.selectActiveCount(appId);
        Long cutprice = cutpriceActiveDao.selectActiveCount(appId);
        Long follow = followActiveDao.selectActiveCount(appId);
        Long rebate = rebateActiveDao.selectActiveCount(dyk);
        Long taoke = taokeActiveDao.selectActiveCount(dyk);
        Long trial = trialActiveDao.selectActiveCount(dyk);
        if(collage != null && collage > 0){
            actives.add("PinTuan");
        }
        if(cutprice != null && cutprice > 0){
            actives.add("KanJia");
        }
        if(follow != null && follow > 0){
            actives.add("TuiGuang");
        }
        if(rebate != null && rebate > 0){
            actives.add("Fanli");
        }
        if(taoke != null && taoke > 0){
            actives.add("Taoke");
        }
        if(trial != null && trial > 0){
            actives.add("ShiYong");
        }
        return actives;
    }
}
