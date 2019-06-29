package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.TrialWealOrderExamineParam;
import youke.common.model.vo.param.helper.TrialWealOrderQueryVo;
import youke.common.model.vo.param.helper.TrialWealQueryVo;
import youke.common.model.vo.result.TrialActiveRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderQueryRetVo;
import youke.common.model.vo.result.helper.TrialWealQueryRetVo;
import youke.facade.helper.provider.ITrialWealService;
import youke.facade.helper.vo.TrialWealActiveVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:29
 */
@Controller
@RequestMapping("trial")
public class TrialWealAction extends BaseAction {

    @Resource
    private ITrialWealService trialWealService;

    @RequestMapping("addactive")
    @ResponseBody
    public JsonResult addActive() {
        TrialWealActiveVo params = getParams(TrialWealActiveVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        int i = trialWealService.addActive(params);
        return new JsonResult(i);
    }

    @RequestMapping("delactive")
    @ResponseBody
    public JsonResult delactive() {

        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        trialWealService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("getactivelist")
    @ResponseBody
    public JsonResult getActiveList(HttpServletRequest request) {
        TrialWealQueryVo params = getParams(TrialWealQueryVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<TrialWealQueryRetVo> info = trialWealService.queryList(params, getBasePath(request)+"common/qrcode?d=");
        return new JsonResult(info);
    }

    @RequestMapping(value = "getactivedetail")
    @ResponseBody
    public JsonResult getActiveDetail() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        TrialActiveRetVo info = trialWealService.getDetail(appId, id, dykId);
        return new JsonResult(info);
    }

    @RequestMapping("getorderlist")
    @ResponseBody
    public JsonResult getOrderList() {
        TrialWealOrderQueryVo params = getParams(TrialWealOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<TrialWealOrderQueryRetVo> info = trialWealService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping("examineorder")
    @ResponseBody
    public JsonResult examineOrder() {
        TrialWealOrderExamineParam params = getParams(TrialWealOrderExamineParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        trialWealService.examinBatch(params);
        return new JsonResult();
    }

    @RequestMapping("getexaminedetail")
    @ResponseBody
    public JsonResult getExamineDetail() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        TrialWealOrderDetailRetVo detail = trialWealService.getExamineDetail(orderId, AuthUser.getUser().getAppId());
        return new JsonResult(detail);
    }

    @RequestMapping("exportorder")
    @ResponseBody
    public JsonResult exportOrder() {
        TrialWealOrderQueryVo params = getParams(TrialWealOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        String url = trialWealService.doImportOrder(params);
        return new JsonResult(url);
    }

    @RequestMapping("toupordown")
    @ResponseBody
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        trialWealService.updateState(activeId, AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }
}
