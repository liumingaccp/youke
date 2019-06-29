package youke.service.market.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.HotActivityParamVo;
import youke.common.model.vo.result.market.*;
import youke.facade.market.provider.IHotActivityService;
import youke.service.market.biz.IHotActivityBiz;

import javax.annotation.Resource;

@Service
public class HotActivityService extends Base implements IHotActivityService {

    @Resource
    private IHotActivityBiz hotActivityBiz;

    public BaseMarketActiveRetVo takePartInActivity(HotActivityParamVo param) {
        if (empty(param.getActiveId()) && empty(param.getOpenId()) && empty(param.getAppId())) {
            throw new BusinessException("无效的参数或请求");
        } else {
            switch (param.getType()) {
                case 0:
                    return hotActivityBiz.saveSdActiveRecord(param);
                case 1:
                    return hotActivityBiz.saveZpActiveRecord(param);
                case 2:
                    return hotActivityBiz.saveStActiveRecord(param);
                case 3:
                    return hotActivityBiz.saveSsfActiveRecord(param);
                case 4:
                    return hotActivityBiz.saveTpActiveRecord(param);
                case 5:
                    return hotActivityBiz.saveSbActiveRecord(param);
                case 6:
                    return hotActivityBiz.saveQdActiveRecord(param);
                case 8:
                    return hotActivityBiz.saveHbActiveRecord(param);
                default:
                    return null;
            }
        }
    }

    @Override
    public ActivityRuleH5Vo getActivityRule(String appId, Long activeId, String openId, int type) {
        if (empty(appId) && empty(activeId) && empty(openId)) {
            throw new BusinessException("无效的请求或参数");
        } else {
            switch (type) {
                case 1:
                    return hotActivityBiz.getSdActiveInfo(appId, activeId, openId);
                case 2:
                    return hotActivityBiz.getZpActiveInfo(appId, activeId, openId);
                case 3:
                    return hotActivityBiz.getStActiveInfo(appId, activeId, openId);
                case 4:
                    return hotActivityBiz.getTpActiveInfo(appId, activeId, openId);
                case 5:
                    return hotActivityBiz.getHbActiveInfo(appId, activeId, openId);
                case 6:
                    return hotActivityBiz.getSbActiveInfo(appId, activeId, openId);
                case 7:
                    return hotActivityBiz.getQdActiveInfo(appId, activeId, openId);
                case 8:
                    return hotActivityBiz.getCjActiveInfo(appId, activeId, openId);
                case 13:
                    return hotActivityBiz.getSsfActiveInfo(appId, activeId, openId);
                default:
                    return null;
            }
        }
    }

    @Override
    public LuckyLotteryRetVo lottery(HotActivityParamVo param) {
        if (empty(param))
            throw new BusinessException("无效的请求或参数");
        return hotActivityBiz.saveCjActiveRecord(param);
    }

    @Override
    public int getRunDay(String appId, String openId) {
        if (empty(appId) && empty(openId))
            throw new BusinessException("无效的请求或参数");
        return hotActivityBiz.getRunDay(appId, openId);
    }

    @Override
    public PageInfo<ActivityRecordVo> getRecordList(String openId, int page, int limit) {
        if (empty(openId)) {
            throw new BusinessException("无效的请求或参数");
        }
        if (empty(limit)) {
            limit = 20;
        }
        return hotActivityBiz.getRecordList(openId, page, limit);
    }

    @Override
    public ActivityRecordDetailsVo getRecordDetails(String openId, long recordId) {
        if (empty(openId) && empty(recordId))
            throw new BusinessException("无效的请求或参数");
        return hotActivityBiz.getRecordDetails(openId, recordId);
    }

    @Override
    public PageInfo<ActivityRecordVo> getRecordDetailList(String openId, int type, int page, int limit) {
        if (empty(openId) && empty(type)) {
            throw new BusinessException("无效的请求或参数");
        }
        if (empty(limit)) {
            limit = 20;
        }
        return hotActivityBiz.getRecordDetailList(openId, type, page, limit);
    }

    @Override
    public int getRemainingTimes(String openId, Long activeId) {
        if (empty(openId) && empty(activeId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return hotActivityBiz.getRemainingTimes(openId, activeId);
    }
}
