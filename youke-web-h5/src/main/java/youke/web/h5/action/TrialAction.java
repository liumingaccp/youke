package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TTrialActive;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.TrialActiveRetVo;
import youke.common.model.vo.result.helper.H5TtrialOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderQueryRetVo;
import youke.common.model.vo.result.helper.TrialWealQueryRetVo;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ITrialWealService;
import youke.facade.helper.vo.DemoPics;

import javax.annotation.Resource;
import java.util.List;

import static java.awt.SystemColor.info;

@RestController
@RequestMapping("trial")
public class TrialAction extends BaseAction {

    @Resource
    private ITrialWealService trialWealService;

    @RequestMapping(value = "getactivelist")
    public JsonResult getList() {
        TrialWealQueryVo params = getParams(TrialWealQueryVo.class);
        params.setType("H5");
        PageInfo<TrialWealQueryRetVo> info = trialWealService.queryList(params, null);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getactivedetail")
    public JsonResult getActiveDetail() {
        JSONObject params = getParams();
        String appId = params.getString("appId");
        int id = params.getInt("id");
        TrialActiveRetVo detail = trialWealService.getDetail(appId, id, null);
        return new JsonResult(detail);
    }


    @RequestMapping(value = "gettrialexamplepic")
    public JsonResult getTrialExamplePic() {
        H5QueryPicVo params = getParams(H5QueryPicVo.class);
        List<DemoPics> demoPics = trialWealService.getTrialExamplePic(params);
        return new JsonResult(demoPics);
    }

    @RequestMapping(value = "getorderlist")
    public JsonResult getTrialOrderList() {
        TrialWealOrderQueryVo params = getParams(TrialWealOrderQueryVo.class);
        params.setType("H5");
        PageInfo<TrialWealOrderQueryRetVo> info = trialWealService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "saveordernum")
    public JsonResult saveOrderNum() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String opneId  = params.getString("openId");
        String orderNo = StringUtil.hasLength(params.getString("orderNo"))?params.getString("orderNo"):null;
        trialWealService.saveOrderNum(appId, opneId, orderId, orderNo);
        return new JsonResult();
    }

    @RequestMapping(value = "gettrialorderdetail")
    public JsonResult getTrialOrderDetail() {
        JSONObject params = getParams();
        String appId = params.getString("appId");
        long id = params.getLong("id");
        H5TtrialOrderDetailRetVo vo = trialWealService.getTrialOrderDetail(appId, id);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "saveaccountpic")
    public JsonResult saveAccountPic() {
        JSONObject params = getParams();
        long activeId = params.getLong("activeId");
        String appId = params.getString("appId");
        String opneId = params.getString("opneId");
        String fileUrl = params.getString("fileUrl");
        long id = trialWealService.saveAccountPic(activeId, appId, opneId, fileUrl);
        return new JsonResult(id);
    }
    @RequestMapping(value = "getaccountexaminpic")
    public JsonResult getAccountExaminPic(){
        JSONObject params = getParams();
        long activeId = params.getLong("activeId");
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        String url = trialWealService.getAccountExaminPic(activeId,openId, appId);
        return new JsonResult(url);
    }

    @RequestMapping(value = "saveactiveorder")
    public JsonResult saveActiveOrder() {
        HelperOrderVo params = getParams(HelperOrderVo.class);
        long id = trialWealService.saveTrialOrder(params);
        return new JsonResult(id);
    }

    @RequestMapping(value = "savetrialorderpic")
    public JsonResult saveTrialOrderPic() {
        TrialOrderPicsVo params = getParams(TrialOrderPicsVo.class);
        trialWealService.saveTrialOrderPic(params);
        return new JsonResult();
    }

    @RequestMapping(value = "getorderinfo")
    public JsonResult getOrderInfo(){
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        TrialWealOrderQueryRetVo info = trialWealService.getOrderInfo(orderId,openId, appId);
        return new JsonResult(info);
    }
}
