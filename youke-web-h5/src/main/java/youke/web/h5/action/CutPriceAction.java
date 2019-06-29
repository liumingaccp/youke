package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.result.helper.H5CutPriceActiveRetVo;
import youke.common.model.vo.result.helper.H5CutPriceFans;
import youke.common.model.vo.result.helper.H5CutPriceOrderDetailRetVo;
import youke.common.model.vo.result.helper.H5CutPriceOrderRetVo;
import youke.facade.helper.provider.ICutPriceService;

import javax.annotation.Resource;

@RestController
@RequestMapping("cutprice")
public class CutPriceAction extends BaseAction {
    @Resource
    private ICutPriceService cutPriceService;

    /**
     * H5获取砍价活动列表
     *
     * @return
     */
    @RequestMapping(value = "getactivelist", method = RequestMethod.POST)
    public JsonResult getactivelist() {
        JSONObject params = getParams();
        PageInfo<H5CutPriceActiveRetVo> info = cutPriceService.getActiveList(params.getString("appId"), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    /**
     * H5获取我的砍价活动列表
     *
     * @return
     */
    @RequestMapping(value = "getmyorderlist", method = RequestMethod.POST)
    public JsonResult getMyOrderList() {
        JSONObject params = getParams();
        PageInfo<H5CutPriceOrderRetVo> info = cutPriceService.getMyOrderList(params.getString("appId"), params.getString("openId"), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    /**
     * 砍价
     *
     * @return
     */
    @RequestMapping(value = "submitCut", method = RequestMethod.POST)
    public JsonResult submitCut() {
        JSONObject params = getParams();
        Integer price = cutPriceService.submitCut(params.getString("appId"), params.getString("openId"), params.getLong("orderId"));
        return new JsonResult(price);
    }

    /**
     * 获取砍价商品信息
     *
     * @return
     */
    @RequestMapping(value = "getorderdetail", method = RequestMethod.POST)
    public JsonResult getOrderDetail() {
        JSONObject params = getParams();
        H5CutPriceOrderDetailRetVo vo = cutPriceService.getOrderDetail(params.getString("appId"), params.getString("openId"), params.getLong("orderId"), params.getInt("activeId"));
        return new JsonResult(vo);
    }

    /**
     * 获取砍价粉丝列表
     *
     * @return
     */
    @RequestMapping(value = "getfanslist", method = RequestMethod.POST)
    public JsonResult getFansList() {
        JSONObject params = getParams();
        PageInfo<H5CutPriceFans> info = cutPriceService.getFansList(params.getString("orderId"), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    /**
     * 提交订单号
     *
     * @return
     */
    @RequestMapping(value = "submitOrderno", method = RequestMethod.POST)
    public JsonResult submitOrderno() {
        JSONObject params = getParams();
        return cutPriceService.submitOrderno(params.getString("orderNo"), params.getLong("orderId"), params.getString("openId"));
    }

    /**
     * 砍价页面预览
     *
     * @return
     */
    @RequestMapping(value = "getpreviewdetail", method = RequestMethod.POST)
    public JsonResult getpreviewdetail() {
        JSONObject params = getParams();
        H5CutPriceOrderDetailRetVo vo = cutPriceService.getPreviewDetail(params.getString("appId"), params.getString("openId"), params.getInt("activeId"));
        return new JsonResult(vo);
    }
}
