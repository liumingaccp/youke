package youke.web.pc.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.TRebateActive;
import youke.common.model.vo.param.helper.RebateOrderExamineParam;
import youke.common.model.vo.param.helper.RebateOrderQueryVo;
import youke.common.model.vo.param.helper.RebateQueryVo;
import youke.common.model.vo.result.helper.RebateDetailVo;
import youke.common.model.vo.result.helper.RebateOrderDetailRetVo;
import youke.common.model.vo.result.helper.RebateOrderQueryRetVo;
import youke.common.model.vo.result.helper.RebateQueryRetVo;
import youke.facade.helper.provider.IRebateService;
import youke.facade.helper.vo.RebateActiveVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:29
 */
@Controller
@RequestMapping("rebate")
public class RebateAction extends BaseAction {

    @Resource
    private IRebateService rebateService;

    @RequestMapping("addactive")
    @ResponseBody
    public JsonResult addActive() {
        RebateActiveVo params = getParams(RebateActiveVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        int i = rebateService.addActive(params);
        return new JsonResult(i);
    }

    @RequestMapping("delactive")
    @ResponseBody
    public JsonResult delactive() {

        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        rebateService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("getactivelist")
    @ResponseBody
    public JsonResult getActiveList(HttpServletRequest request) {
        RebateQueryVo params = getParams(RebateQueryVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<RebateQueryRetVo> info = rebateService.queryList(params, getBasePath(request)+"common/qrcode?d=");
        return new JsonResult(info);
    }

    @RequestMapping("getorderlist")
    @ResponseBody
    public JsonResult getOrderList() {
        RebateOrderQueryVo params = getParams(RebateOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<RebateOrderQueryRetVo> info = rebateService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping("examineorder")
    @ResponseBody
    public JsonResult examineOrder() {
        RebateOrderExamineParam params = getParams(RebateOrderExamineParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        rebateService.examinBatch(params);
        return new JsonResult();
    }

    @RequestMapping("getexaminedetail")
    @ResponseBody
    public JsonResult getExamineDetail() {
        JSONObject params = getParams();
        Integer orderId = params.getInt("orderId");
        RebateOrderDetailRetVo detail = rebateService.getExamineDetail(orderId, AuthUser.getUser().getAppId());
        return new JsonResult(detail);
    }

    @RequestMapping("exportorder")
    @ResponseBody
    public JsonResult exportOrder() {
        RebateOrderQueryVo params = getParams(RebateOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        String url = rebateService.doImportOrder(params);
        return new JsonResult(url);
    }

    @RequestMapping("getactivedetail")
    @ResponseBody
    public JsonResult getActiveDetail(){
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        RebateDetailVo activeDetail = rebateService.getActiveDetail(id, appId, dykId);
        return new JsonResult(activeDetail);
    }

    @RequestMapping("toupordown")
    @ResponseBody
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        rebateService.updateState(activeId, AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }
}
