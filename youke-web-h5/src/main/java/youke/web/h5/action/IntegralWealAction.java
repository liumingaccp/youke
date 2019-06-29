package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TIntegralActive;
import youke.common.model.vo.param.IntegralActiveOrderVo;
import youke.common.model.vo.param.helper.FollowOrderPosterVo;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.IntegralActiveRetVo;
import youke.common.model.vo.result.IntegralActiveVo;
import youke.common.model.vo.result.IntegralOrderRetVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.IFollowService;
import youke.facade.market.provider.IIntegralWealService;

import javax.annotation.Resource;

@RestController
@RequestMapping("weal")
public class IntegralWealAction extends BaseAction {

    @Resource
    private IIntegralWealService integralWealService;

    @RequestMapping(value = "getactivelist")
    public JsonResult getList() {
        JSONObject params = getParams();
        String appId = params.getString("appId");
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<IntegralActiveVo> infos = integralWealService.getIntegralActiveList(appId, page, limit);
        return new JsonResult(infos);
    }

    @RequestMapping(value = "submitorder")
    public JsonResult submitOrder() {
        IntegralActiveOrderVo params = getParams(IntegralActiveOrderVo.class);
        IntegralOrderRetVo id = integralWealService.submitOrder(params);
        return new JsonResult(id);
    }

    @RequestMapping(value = "getactiveinfo")
    public JsonResult getActiveInfo() {
        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        String appId = params.getString("appId");
        IntegralActiveRetVo vo = integralWealService.getactiveinfo(activeId, appId, null);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "getorderinfo")
    public JsonResult getOrderInfo() {
        JSONObject params = getParams();
        int orderId = params.getInt("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        IntegralOrderRetVo vo = integralWealService.getOrderInfo(orderId, appId, openId);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "getorderlist")
    public JsonResult getOrderList() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        int page = params.getInt("page");
        if(page <= 0){
            page = 1;
        }
        int limit = params.getInt("limit");
        if(limit <= -1){
            limit = 20;
        }
        PageInfo<IntegralOrderRetVo> info = integralWealService.getOrderListByOpenId(appId, openId, page, limit);
        return new JsonResult(info);
    }

    @RequestMapping(value = "saveordernum")
    public JsonResult saveOrderNum() {
        JSONObject params = getParams();
        Long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        String orderNo = StringUtil.hasLength(params.getString("orderNo"))?params.getString("orderNo"):null;
        integralWealService.saveOrderNum(appId, openId, orderId, orderNo);
        return new JsonResult();
    }
}
