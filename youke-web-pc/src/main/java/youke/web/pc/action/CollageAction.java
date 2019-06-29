package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.CollageOrderQueryVo;
import youke.common.model.vo.param.helper.CollageQueryVo;
import youke.common.model.vo.result.helper.CollageActiveDetailVo;
import youke.common.model.vo.result.helper.CollageOrderDetailRetVo;
import youke.common.model.vo.result.helper.CollageOrderQueryRetVo;
import youke.common.model.vo.result.helper.CollageQueryRetVo;
import youke.facade.helper.provider.ICollageService;
import youke.facade.helper.vo.CollageActiveVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:20
 */
@Controller
@RequestMapping("collage")
public class CollageAction extends BaseAction {

    @Resource
    private ICollageService collageService;
    
    @RequestMapping("saveactive")
    @ResponseBody
    public JsonResult addActive() {
        CollageActiveVo params = getParams(CollageActiveVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        int i = collageService.addActive(params);
        return new JsonResult(i);
    }

    @RequestMapping("getactivelist")
    @ResponseBody
    public JsonResult getActiveList(HttpServletRequest request) {
        CollageQueryVo params = getParams(CollageQueryVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<CollageQueryRetVo> info = collageService.queryList(params, getBasePath(request)+"common/qrcode?d=");
        return new JsonResult(info);
    }

    @RequestMapping("getorderlist")
    @ResponseBody
    public JsonResult getOrderList() {
        CollageOrderQueryVo params = getParams(CollageOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<CollageOrderQueryRetVo> info = collageService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping("exportorder")
    @ResponseBody
    public JsonResult exportOrder() {
        CollageOrderQueryVo params = getParams(CollageOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        String url = collageService.doImportOrder(params);
        return new JsonResult(url);
    }

    @RequestMapping("toupordown")
    @ResponseBody
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        collageService.updateState(activeId, AuthUser.getUser().getAppId() ,AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }

    @RequestMapping("delactive")
    @ResponseBody
    public JsonResult delactive() {

        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        collageService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("getactivedetail")
    @ResponseBody
    public JsonResult getActive() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        CollageActiveDetailVo ativeDetail = collageService.getAtiveDetail(id, appId, dykId);
        return new JsonResult(ativeDetail);
    }

    @RequestMapping("getexaminedetail")
    @ResponseBody
    public JsonResult getOrderDetail(){

        JSONObject params = getParams();
        Long orderId = params.getLong("orderId");
        CollageOrderDetailRetVo info = collageService.getExamineDetail(orderId, AuthUser.getUser().getAppId(), AuthUser.getUser().getDykId());

        return new JsonResult(info);
    }
}
