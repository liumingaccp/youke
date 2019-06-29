package youke.service.market.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.market.MarketActivitySaveVo;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.*;
import youke.common.model.vo.result.market.MarketActivityVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;

import java.util.List;
import java.util.Map;

public interface IMarketActivityBiz {
    /**
     * 营销活动列表分页获取
     *
     * @param params
     * @return
     */
    PageInfo<MarketActivityVo> getActivityList(MarketQueryVo params);

    /**
     * 营销活动参与记录分页获取
     *
     * @param params
     * @return
     */
    PageInfo<MarketRecordListVo> getRecordList(MarketRecordListQueryVo params);

    /**
     * 参与记录查看审核详情
     *
     * @param recordId
     * @param dykId
     * @return
     */
    MarketExamineDetailVo getExamineDetail(int recordId, String dykId);

    /**
     * 批量审核
     *
     * @param dykId
     */
    void updateState(String recordIds, Integer state, String failReason, String dykId, Integer userId);

    /**
     * 晒单活动保存
     *
     * @param params
     */
    Long savesdActive(MarketActivitySaveVo params);

    /**
     * 追评活动保存
     *
     * @param params
     */
    Long saveZPActive(MarketActivitySaveVo params);

    /**
     * 晒图有礼活动保存
     *
     * @param params
     */
    Long savestActive(MarketActivitySaveVo params);

    /**
     * 随时返活动保存
     *
     * @param params
     */
    Long saveflActive(MarketActivitySaveVo params);

    /**
     * 投票评测活动保存
     *
     * @param params
     */
    Long savetpActive(MarketActivitySaveVo params);

    /**
     * 首绑有礼活动保存
     *
     * @param params
     */
    Long savesbActive(MarketActivitySaveVo params);

    /**
     * 签到活动保存
     *
     * @param params
     */
    Long saveqdActive(MarketActivitySaveVo params);

    /**
     * 分享海报活动保存
     *
     * @param params
     */
    Long savehbActive(MarketActivitySaveVo params);

    /**
     * 获取投票测款数据
     *
     * @param activeId
     * @param dykId
     * @return
     */
    List<ResultOfVotingVo> gettpdatas(Long activeId, String dykId);

    /**
     * 抽奖活动保存
     *
     * @param params
     */
    Long saveCjActive(MarketActivitySaveVo params);

    /**
     * 删除营销活动
     *
     * @param id
     */
    void deleteActive(long id);

    /**
     * 随时返文件上传订单号保存
     *
     * @param activeId
     * @param csvUrl
     * @param dykId
     */
    void saveImportOrders(long activeId, String csvUrl, String dykId);

    /**
     * 随时返手动输入订单号保存
     *
     * @param params
     */
    void saveInputOrders(MarketActivitySaveVo params, Long activeId);

    /**
     * 晒单活动信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getSdActiveInfo(long id);

    /**
     * 晒图有礼信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getStActiveInfo(long id);

    /**
     * 追评有礼信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getZpActiveInfo(long id);

    /**
     * 随时返信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getSsfActiveInfo(long id);

    /**
     * 投票测款信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getTpActiveInfo(long id);

    /**
     * 首绑有礼信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getSbActiveInfo(long id);

    /**
     * 每日签到信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getQdActiveInfo(long id);

    /**
     * 幸运抽奖信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getCjActiveInfo(long id);

    /**
     * 分享海报信息获取
     *
     * @param id
     * @return
     */
    ActiveInfoVo getHbActiveInfo(long id);

    /**
     * 活动上线
     *
     * @param id
     */
    void updateActiveStateForOnLine(long id);

    /**
     * 活动下线
     *
     * @param id
     */
    void updateActiveStateForOffLine(long id);

    /**
     * 首页活动信息获取
     *
     * @param activeId
     * @return
     */
    ActiveDataVo getActiveData(long activeId);

    /**
     * 活动统计数据获取
     *
     * @param activeId
     * @param begDate
     * @param endDate
     * @return
     */
    List<ActiveChartVo> getActiveChart(long activeId, String begDate, String endDate);

    /**
     * 获取活动转化数据
     *
     * @param activeId
     * @param begDate
     * @param endDate
     * @return
     */
    List<ActiveTranChartVo> getActiveTranChart(long activeId, String begDate, String endDate);

    /**
     * 活动参与记录导出数据活动
     *
     * @param params
     * @return
     */
    List<MarketRecordListVo> getExportRecordList(MarketRecordListQueryVo params);


    /**
     * 查询所有营销活动是否有进行中的活动
     *
     * @param appId
     * @return
     */
    List<String> getMarketActives(String appId);

    /**
     * 获取积分/金额消耗
     *
     * @param params
     * @return
     */
    Map<String, Object> getConsumption(MarketRecordListQueryVo params);
}
