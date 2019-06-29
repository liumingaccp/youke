package youke.facade.market.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.market.MarketActivitySaveVo;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.*;
import youke.common.model.vo.result.market.MarketActivityVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:48
 */
public interface IMarketActivityService {

    /**
     * 营销活动模板获取
     *
     * @param key
     * @return
     */
    String getTemplate(String key);

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
     * @param dykId
     * @return
     */
    PageInfo<MarketRecordListVo> getRecordList(MarketRecordListQueryVo params, String dykId);

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
     * @param userId
     */
    void batchAudit(String recordIds, Integer state, String failReason, String dykId, Integer userId);

    /**
     * 通用活动保存
     *
     * @param params
     * @param dykId
     */
    Long saveActive(MarketActivitySaveVo params, String dykId);

    /**
     * 获取投票测款排名
     *
     * @param activeId
     * @param dykId
     * @return
     */
    List<ResultOfVotingVo> gettpdatas(Long activeId, String dykId);

    /**
     * 删除营销活动
     *
     * @param id
     */
    void deleteActive(long id);

    /**
     * 随时返文件上传订单保存
     *
     * @param activeId
     * @param csvUrl
     * @param dykId
     */
    void saveImportOrders(long activeId, String csvUrl, String dykId);

    /**
     * 随时返手动输入订单保存
     *
     * @param params
     */
    void saveInputOrders(MarketActivitySaveVo params, String dykId, Long activeId);

    /**
     * 活动编辑
     *
     * @param id
     * @return
     */
    ActiveInfoVo activeAudit(long id, Integer type);

    /**
     * 活动上线
     *
     * @param id
     */
    void onLine(long id);

    /**
     * 活动下线
     *
     * @param id
     */
    void offLine(long id);

    /**
     * 配置表获取活动链接
     *
     * @param key
     * @return
     */
    String getConfig(String key);

    /**
     * 活动信息获取
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
     * 参与记录导出数据获取
     *
     * @param params
     * @param dykId
     * @return
     */
    List<MarketRecordListVo> getExportRecordList(MarketRecordListQueryVo params, String dykId);

    /**
     * 添加系统消息
     *
     * @param message
     */
    void addSysMessage(TSysMessage message);

    /**
     * 查询所有营销活动是否有进行中的活动
     *
     * @param appId
     * @return
     */
    List<String> getMarketActives(String appId);

    /**
     * 获取积分/现金消耗
     * @param params
     * @param dykId
     * @return
     */
    Map<String, Object> getConsumption(MarketRecordListQueryVo params, String dykId);
}
