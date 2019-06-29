package youke.service.market.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.HotActivityParamVo;
import youke.common.model.vo.result.market.*;

public interface IHotActivityBiz {

    /**
     * 晒单有礼活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveSdActiveRecord(HotActivityParamVo param);

    /**
     * 追评有礼活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveZpActiveRecord(HotActivityParamVo param);

    /**
     * 晒图有礼活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveStActiveRecord(HotActivityParamVo param);

    /**
     * 随时返活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveSsfActiveRecord(HotActivityParamVo param);

    /**
     * 投票活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveTpActiveRecord(HotActivityParamVo param);

    /**
     * 分享海报活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveHbActiveRecord(HotActivityParamVo param);

    /**
     * 首次绑定活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveSbActiveRecord(HotActivityParamVo param);

    /**
     * 签到活动记录保存
     *
     * @param param
     */
    BaseMarketActiveRetVo saveQdActiveRecord(HotActivityParamVo param);

    /**
     * 抽奖活动记录保存
     *
     * @param param
     */
    LuckyLotteryRetVo saveCjActiveRecord(HotActivityParamVo param);

    /**
     * 晒单有礼活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getSdActiveInfo(String appId, Long activeId, String openId);

    /**
     * 追评有礼活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getZpActiveInfo(String appId, Long activeId, String openId);

    /**
     * 晒图有礼活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getStActiveInfo(String appId, Long activeId, String openId);

    /**
     * 投票测款活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getTpActiveInfo(String appId, Long activeId, String openId);

    /**
     * 分享海报活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getHbActiveInfo(String appId, Long activeId, String openId);

    /**
     * 首绑有礼活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getSbActiveInfo(String appId, Long activeId, String openId);

    /**
     * 幸运抽奖活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getCjActiveInfo(String appId, Long activeId, String openId);

    /**
     * 随时返活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getSsfActiveInfo(String appId, Long activeId, String openId);

    /**
     * 每日签到活动信息获取
     *
     * @param appId
     * @return
     */
    ActivityRuleH5Vo getQdActiveInfo(String appId, Long activeId, String openId);

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
     * 获取用户剩余抽奖次数
     *
     * @param openId
     * @return
     */
    int getRemainingTimes(String openId, Long activeId);

}
