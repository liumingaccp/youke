package youke.service.helper.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.helper.*;
import youke.facade.helper.provider.ICutPriceService;
import youke.facade.helper.vo.CutPriceActiveVo;
import youke.service.helper.biz.ICutPriceBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CutPriceService extends Base implements ICutPriceService {
    @Resource
    private ICutPriceBiz cutPriceBiz;

    @Override
    public Integer saveActive(CutPriceActiveVo params, String dykId, String appId) {
        if (empty(params) && empty(dykId) && empty(appId) && empty(params.getId())) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        params.setAppId(appId);
        return cutPriceBiz.saveActive(params);
    }

    @Override
    public PageInfo<CutPriceActiveRetVo> getActiveList(CutPriceActiveQueryVo params, String dykId) {
        if (empty(params) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        return cutPriceBiz.getActiveList(params);
    }

    @Override
    public PageInfo<CutPriceOrderRetVo> getOrderList(CutPriceOrderQueryVo params, String dykId) {
        if (empty(params) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        return cutPriceBiz.getOrderList(params);
    }

    @Override
    public List<CutPriceOrderRetVo> getExportOrderList(CutPriceOrderQueryVo params, String dykId) {
        if (empty(params) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        return cutPriceBiz.getExportOrderList(params);
    }

    @Override
    public CutPriceOrderDetailRetVo getExamineDetail(Long orderId, String dykId) {
        if (empty(orderId) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cutPriceBiz.getExamineDetail(orderId, dykId);
    }

    @Override
    public void deleteActive(String dykId, Integer activeId) {
        if (empty(dykId) && empty(activeId)) {
            throw new BusinessException("无效的请求或参数");
        }
        cutPriceBiz.deleteActive(dykId, activeId);
    }

    @Override
    public CutPriceActiveDetailRetVo getActiveDetail(Integer activeId, String dykId) {
        if (empty(activeId) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cutPriceBiz.getActiveDetail(activeId, dykId);
    }

    @Override
    public void toUpOrDown(Integer activeId, Integer state, String dykId) {
        if (empty(activeId) && empty(state) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        cutPriceBiz.updateActiveState(activeId, state, dykId);
    }

    @Override
    public PageInfo<H5CutPriceActiveRetVo> getActiveList(String appId, Integer page, Integer limit) {
        if (empty(appId)) {
            throw new BusinessException("无效的请求或参数");
        }
        if (empty(limit)) {
            limit = 20;
        }
        return cutPriceBiz.getH5ActiveList(appId, page, limit);
    }

    @Override
    public PageInfo<H5CutPriceOrderRetVo> getMyOrderList(String appId, String openId, Integer page, Integer limit) {
        if (empty(appId) && empty(openId)) {
            throw new BusinessException("无效的请求或参数");
        }
        if (empty(limit)) {
            limit = 20;
        }
        return cutPriceBiz.getMyOrderList(appId, openId, page, limit);
    }

    @Override
    public Integer submitCut(String appId, String openId, Long orderId) {
        if (empty(appId) && empty(openId) && empty(orderId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cutPriceBiz.doSubmitCut(appId, openId, orderId);
    }

    @Override
    public H5CutPriceOrderDetailRetVo getOrderDetail(String appId, String openId, Long orderId, Integer activeId) {
        if (empty(appId) && empty(openId) && empty(orderId) && empty(activeId)) {
            throw new BusinessException("无效的参数或请求");
        }
        return cutPriceBiz.createOrGetOrderDetail(appId, openId, orderId, activeId);
    }

    @Override
    public PageInfo<H5CutPriceFans> getFansList(String orderId, Integer page, Integer limit) {
        if (empty(orderId)) {
            throw new BusinessException("无效的请求或参数");
        }
        if (empty(limit)) {
            limit = 20;
        }
        return cutPriceBiz.getFansList(orderId, page, limit);
    }

    @Override
    public JsonResult submitOrderno(String orderno, Long orderId, String openId) {
        if (empty(orderno) && empty(orderId) && empty(openId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cutPriceBiz.doSubmitOrderno(orderno, orderId, openId);
    }

    @Override
    public H5CutPriceOrderDetailRetVo getPreviewDetail(String appId, String openId, Integer activeId) {
        if (empty(appId) && empty(activeId) && empty(openId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cutPriceBiz.getPreviewDetail(appId, openId, activeId);
    }

    @Override
    public String getConfig(String key) {
        return empty(key) ? "" : cutPriceBiz.getConfig(key);
    }
}
