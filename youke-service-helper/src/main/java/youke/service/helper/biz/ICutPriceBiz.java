package youke.service.helper.biz;

import com.github.pagehelper.PageInfo;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.helper.*;
import youke.facade.helper.vo.CutPriceActiveVo;

import java.util.List;

public interface ICutPriceBiz {
    /**
     * 保存砍价活动,返回活动id
     *
     * @param params
     * @return
     */
    Integer saveActive(CutPriceActiveVo params);

    /**
     * 获取砍价活动列表
     *
     * @param params
     * @return
     */
    PageInfo<CutPriceActiveRetVo> getActiveList(CutPriceActiveQueryVo params);

    /**
     * 砍价活动记录分页获取
     *
     * @param params
     * @return
     */
    PageInfo<CutPriceOrderRetVo> getOrderList(CutPriceOrderQueryVo params);

    /**
     * 参与记录导出数据获取
     *
     * @param params
     * @return
     */
    List<CutPriceOrderRetVo> getExportOrderList(CutPriceOrderQueryVo params);

    /**
     * 获取t_config表对应key的值
     *
     * @param key
     * @return
     */
    String getConfig(String key);

    /**
     * 查看记录审核详情
     *
     * @param orderId
     * @param dykId
     * @return
     */
    CutPriceOrderDetailRetVo getExamineDetail(Long orderId, String dykId);

    /**
     * 活动删除
     *
     * @param dykId
     * @param orderId
     */
    void deleteActive(String dykId, Integer orderId);

    /**
     * 活动编辑数据获取
     *
     * @param activeId
     * @param dykId
     * @return
     */
    CutPriceActiveDetailRetVo getActiveDetail(Integer activeId, String dykId);

    /**
     * 活动上下线
     *
     * @param activeId
     * @param state
     * @param dykId
     */
    void updateActiveState(Integer activeId, Integer state, String dykId);

    /**
     * H5获取砍价活动列表
     *
     * @param appId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<H5CutPriceActiveRetVo> getH5ActiveList(String appId, Integer page, Integer limit);

    /**
     * H5获取我参与的砍价活动
     *
     * @param appId
     * @param openId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<H5CutPriceOrderRetVo> getMyOrderList(String appId, String openId, Integer page, Integer limit);

    /**
     * 砍价
     *
     * @param appId
     * @param openId
     * @param orderId
     * @return
     */
    Integer doSubmitCut(String appId, String openId, Long orderId);

    /**
     * H5砍价详情获取
     *
     * @param appId
     * @param openId
     * @param orderId
     * @param activeId
     * @return
     */
    H5CutPriceOrderDetailRetVo createOrGetOrderDetail(String appId, String openId, Long orderId, Integer activeId);

    /**
     * 砍价粉丝列表获取
     *
     * @param orderId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<H5CutPriceFans> getFansList(String orderId, Integer page, Integer limit);

    /**
     * H5上传订单号
     *
     * @param orderno
     * @param orderId
     * @param openId
     */
    JsonResult doSubmitOrderno(String orderno, Long orderId, String openId);

    /**
     * 砍价预览页面数据获取
     *
     * @param appId
     * @param openId
     * @param activeId
     * @return
     */
    H5CutPriceOrderDetailRetVo getPreviewDetail(String appId, String openId, Integer activeId);
}
