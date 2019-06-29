package youke.service.market.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IConfigDao;
import youke.common.dao.ISysMessageDao;
import youke.common.exception.BusinessException;
import youke.common.model.TConfig;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.market.MarketActivitySaveVo;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.*;
import youke.common.model.vo.result.market.MarketActivityVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;
import youke.facade.market.provider.IMarketActivityService;
import youke.service.market.biz.IMarketActivityBiz;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class MarketActivityService extends Base implements IMarketActivityService {
    @Resource
    private IConfigDao configDao;
    @Resource
    private ISysMessageDao sysMessageDao;
    @Resource
    private IMarketActivityBiz marketActivityBiz;

    public String getTemplate(String key) {
        TConfig tConfig = configDao.selectByPrimaryKey(key);
        if (notEmpty(configDao)) {
            return tConfig.getVal();
        } else {
            return null;
        }
    }

    public PageInfo<MarketActivityVo> getActivityList(MarketQueryVo params) {
        return marketActivityBiz.getActivityList(params);
    }

    public PageInfo<MarketRecordListVo> getRecordList(MarketRecordListQueryVo params, String dykId) {
        if (notEmpty(params) && notEmpty(dykId)) {
            params.setYoukeId(dykId);
            return marketActivityBiz.getRecordList(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public MarketExamineDetailVo getExamineDetail(int recordId, String dykId) {
        if (empty(recordId) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        return marketActivityBiz.getExamineDetail(recordId, dykId);
    }

    public void batchAudit(String recordIds, Integer state, String failReason, String dykId, Integer userId) {
        if (empty(recordIds) && empty(state) && empty(dykId) && empty(userId))
            throw new BusinessException("无效的请求或参数");
        marketActivityBiz.updateState(recordIds, state, failReason, dykId, userId);
    }

    public List<ResultOfVotingVo> gettpdatas(Long activeId, String dykId) {
        if (empty(activeId) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        return marketActivityBiz.gettpdatas(activeId, dykId);
    }

    public void deleteActive(long id) {
        if (empty(id))
            throw new BusinessException("请选择要删除的活动");
        marketActivityBiz.deleteActive(id);
    }

    @Override
    public void saveImportOrders(long activeId, String csvUrl, String dykId) {
        if (empty(activeId) && empty(csvUrl) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        marketActivityBiz.saveImportOrders(activeId, csvUrl, dykId);
    }

    @Override
    public void saveInputOrders(MarketActivitySaveVo params, String dykId, Long activeId) {
        if (empty(params) && empty(dykId) && empty(activeId))
            throw new BusinessException("无效的请求或参数");
        params.setDykId(dykId);
        marketActivityBiz.saveInputOrders(params, activeId);
    }

    @Override
    public void onLine(long id) {
        if (empty(id))
            throw new BusinessException("请选择要上线的活动");
        marketActivityBiz.updateActiveStateForOnLine(id);
    }

    @Override
    public void offLine(long id) {
        if (empty(id))
            throw new BusinessException("请选择要下线的活动");
        marketActivityBiz.updateActiveStateForOffLine(id);
    }

    @Override
    public String getConfig(String key) {
        return configDao.selectByPrimaryKey(key).getVal();
    }

    @Override
    public void addSysMessage(TSysMessage message) {
        sysMessageDao.addMessage(message.getTitle(), message.getContent(), message.getYoukeid());
    }

    @Override
    public List<String> getMarketActives(String appId) {
        return marketActivityBiz.getMarketActives(appId);
    }

    @Override
    public ActiveDataVo getActiveData(long activeId) {
        return marketActivityBiz.getActiveData(activeId);
    }

    @Override
    public List<ActiveChartVo> getActiveChart(long activeId, String begDate, String endDate) {
        if (empty(activeId))
            throw new BusinessException("无效的请求或参数");
        return marketActivityBiz.getActiveChart(activeId, begDate, endDate);
    }

    @Override
    public List<ActiveTranChartVo> getActiveTranChart(long activeId, String begDate, String endDate) {
        if (empty(activeId))
            throw new BusinessException("无效的请求或参数");
        return marketActivityBiz.getActiveTranChart(activeId, begDate, endDate);
    }

    @Override
    public List<MarketRecordListVo> getExportRecordList(MarketRecordListQueryVo params, String dykId) {
        if (empty(params) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        params.setYoukeId(dykId);
        return marketActivityBiz.getExportRecordList(params);
    }

    @Override
    public Map<String, Object> getConsumption(MarketRecordListQueryVo params, String dykId) {
        if (notEmpty(params) && notEmpty(dykId)) {
            params.setYoukeId(dykId);
            return marketActivityBiz.getConsumption(params);
        }
        throw new BusinessException("无效的请求或参数");
    }

    @Override
    public ActiveInfoVo activeAudit(long id, Integer type) {
        if (empty(id) && empty(type))
            throw new BusinessException("请选择要编辑的活动");
        switch (type) {
            case 0:
                return marketActivityBiz.getSdActiveInfo(id);
            case 1:
                return marketActivityBiz.getZpActiveInfo(id);
            case 2:
                return marketActivityBiz.getStActiveInfo(id);
            case 3:
                return marketActivityBiz.getSsfActiveInfo(id);
            case 4:
                return marketActivityBiz.getTpActiveInfo(id);
            case 5:
                return marketActivityBiz.getSbActiveInfo(id);
            case 6:
                return marketActivityBiz.getQdActiveInfo(id);
            case 7:
                return marketActivityBiz.getCjActiveInfo(id);
            case 8:
                return marketActivityBiz.getHbActiveInfo(id);
            default:
                return null;
        }
    }

    @Override
    public Long saveActive(MarketActivitySaveVo params, String dykId) {
        if (empty(params) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        params.setDykId(dykId);
        switch (params.getType()) {
            case 0:
                return marketActivityBiz.savesdActive(params);
            case 1:
                return marketActivityBiz.saveZPActive(params);
            case 2:
                return marketActivityBiz.savestActive(params);
            case 3:
                return marketActivityBiz.saveflActive(params);
            case 4:
                return marketActivityBiz.savetpActive(params);
            case 5:
                return marketActivityBiz.savesbActive(params);
            case 6:
                return marketActivityBiz.saveqdActive(params);
            case 7:
                return marketActivityBiz.saveCjActive(params);
            case 8:
                return marketActivityBiz.savehbActive(params);
            default:
                return null;
        }
    }
}
