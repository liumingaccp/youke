package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TRebateActive;
import youke.common.model.vo.param.helper.HelperOrderVo;
import youke.common.model.vo.param.helper.RebateOrderQueryVo;
import youke.common.model.vo.param.helper.RebateQueryVo;
import youke.common.model.vo.result.helper.RebateDetailVo;
import youke.common.model.vo.result.helper.RebateOrderQueryRetVo;
import youke.common.model.vo.result.helper.RebateQueryRetVo;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.IRebateService;

import javax.annotation.Resource;

@RestController
@RequestMapping("rebate")
public class RebateAction extends BaseAction {

    @Resource
    private IRebateService rebateService;

    @RequestMapping(value = "getrebatelist")
    public JsonResult getList() {
        RebateQueryVo params = getParams(RebateQueryVo.class);
        PageInfo<RebateQueryRetVo> info = rebateService.queryList(params, null);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getactivedetail")
    public JsonResult getAtiveDetail() {
        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        String appId = params.getString("appId");
        RebateDetailVo info = rebateService.getActiveDetail(activeId, appId, null);
        return new JsonResult(info);
    }
    @RequestMapping(value = "getrebateorderlist")
    public JsonResult getRebateList() {
        RebateOrderQueryVo params = getParams(RebateOrderQueryVo.class);
        params.setType("h5");
        PageInfo<RebateOrderQueryRetVo> info = rebateService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "saverebateorder")
    public JsonResult saveRebateOrder() {
        HelperOrderVo params = getParams(HelperOrderVo.class);
        long id = rebateService.saveRebateOrder(params);
        return new JsonResult(id);
    }

    @RequestMapping(value = "saveordernum")
    public JsonResult saveOrderNum() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        String orderNo = StringUtil.hasLength(params.getString("orderNo"))?params.getString("orderNo"):null;
        rebateService.saveOrderNum(appId, openId, orderId, orderNo);
        return new JsonResult();
    }

    @RequestMapping(value = "getorderinfo")
    public JsonResult getOrderInfo() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        RebateOrderQueryRetVo info = rebateService.getOrderInfo(appId, openId, orderId);
        return new JsonResult(info);
    }
}
