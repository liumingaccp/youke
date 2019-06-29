package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.helper.*;
import youke.facade.helper.vo.CutPriceActiveVo;

import java.util.List;

public interface ICutPriceService {
    /**
     * 保存砍价活动,返回活动id
     *
     * @param params
     * @param dykId
     * @param appId
     * @return
     */
    Integer saveActive(CutPriceActiveVo params, String dykId, String appId);


    /**
     * PC获取砍价活动列表
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<CutPriceActiveRetVo> getActiveList(CutPriceActiveQueryVo params, String dykId);

    /**
     * 砍价活动记录分页获取
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<CutPriceOrderRetVo> getOrderList(CutPriceOrderQueryVo params, String dykId);

    /**
     * 获取t_cofig表对应key的数据
     *
     * @param key
     * @return
     */
    String getConfig(String key);

    /**
     * 参与纪录导出数据获取
     *
     * @param params
     * @param dykId
     * @return
     */
    List<CutPriceOrderRetVo> getExportOrderList(CutPriceOrderQueryVo params, String dykId);

    /**
     * 查看记录审核详情
     *
     * @param orderId
     * @param dykId
     * @return
     */
    CutPriceOrderDetailRetVo getExamineDetail(Long orderId, String dykId);

    /**
     * 获取删除
     *
     * @param dykId
     * @param activeId
     */
    void deleteActive(String dykId, Integer activeId);

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
    void toUpOrDown(Integer activeId, Integer state, String dykId);

    /**
     * H5获取砍价活动列表
     *
     * @param appId
     * @param page
     * @param limit
     * @return
     */
    PageInfo<H5CutPriceActiveRetVo> getActiveList(String appId, Integer page, Integer limit);

    /**
     * H5获取我的参加活动
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
    Integer submitCut(String appId, String openId, Long orderId);

    /**
     * H5获取砍价详情
     *
     * @param appId
     * @param openId
     * @param orderId
     * @param activeId
     * @return
     */
    H5CutPriceOrderDetailRetVo getOrderDetail(String appId, String openId, Long orderId, Integer activeId);

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
     * H5提交订单号
     *
     * @param orderno
     * @param orderId
     * @param openId
     */
    JsonResult submitOrderno(String orderno, Long orderId, String openId);

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
