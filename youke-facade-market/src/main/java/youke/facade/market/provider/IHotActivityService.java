package youke.facade.market.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.HotActivityParamVo;
import youke.common.model.vo.result.market.*;

public interface IHotActivityService {

    /**
     * 通用活动参与接口
     *
     * @param param
     */
    BaseMarketActiveRetVo takePartInActivity(HotActivityParamVo param);

    /**
     * 通用活动信息获取
     *
     * @param appId
     * @param type
     * @return
     */
    ActivityRuleH5Vo getActivityRule(String appId, Long activeId, String openId, int type);

    /**
     * 抽奖
     *
     * @param param
     * @return
     */
    LuckyLotteryRetVo lottery(HotActivityParamVo param);

    /**
     * 获取用户连续签到天数
     *
     * @param appId
     * @param openId
     * @return
     */
    int getRunDay(String appId, String openId);

    /**
     * 用户活动参与记录
     *
     * @param openId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<ActivityRecordVo> getRecordList(String openId, int page, int limit);

    /**
     * 用户活动参与记录详情
     *
     * @param openId
     * @param recordId
     * @return
     */
    ActivityRecordDetailsVo getRecordDetails(String openId, long recordId);

    /**
     * 获取我的活动记录明细列表
     *
     * @param openId
     * @param type
     * @param page
     * @param limit
     * @return
     */
    PageInfo<ActivityRecordVo> getRecordDetailList(String openId, int type, int page, int limit);

    /**
     * 获取剩余抽奖次数
     *
     * @param openId
     * @return
     */
    int getRemainingTimes(String openId, Long activeId);

}
