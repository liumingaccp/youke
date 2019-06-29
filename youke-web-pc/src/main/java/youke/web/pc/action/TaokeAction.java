package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.TaokeOrderExamineParam;
import youke.common.model.vo.param.helper.TaokeOrderQueryVo;
import youke.common.model.vo.param.helper.TaokeQueryVo;
import youke.common.model.vo.result.helper.TaokeOrderDetailRetVo;
import youke.common.model.vo.result.helper.TaokeOrderQueryRetVo;
import youke.common.model.vo.result.helper.TaokeQueryRetVo;
import youke.facade.helper.provider.ITaokeService;
import youke.facade.helper.vo.TaokeActiveVo;
import youke.facade.shop.provider.IShopService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:29
 */
@Controller
@RequestMapping("taoke")
public class TaokeAction extends BaseAction {

    @Resource
    private ITaokeService taokeService;
    @Resource
    private IShopService shopService;

    @RequestMapping("addactive")
    @ResponseBody
    public JsonResult addActive() {
        TaokeActiveVo params = getParams(TaokeActiveVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        int i = taokeService.addActive(params);
        return new JsonResult(i);
    }

    @RequestMapping("getactivedetail")
    @ResponseBody
    public JsonResult getActive() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        youke.common.model.vo.result.TaokeActiveVo ativeDetail = taokeService.getAtiveDetail(id, appId, dykId);
        return new JsonResult(ativeDetail);
    }

    @RequestMapping("delactive")
    @ResponseBody
    public JsonResult delactive() {

        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        taokeService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("getactivelist")
    @ResponseBody
    public JsonResult getActiveList(HttpServletRequest request) {
        TaokeQueryVo params = getParams(TaokeQueryVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<TaokeQueryRetVo> info = taokeService.queryList(params, getBasePath(request)+"common/qrcode?d=");
        return new JsonResult(info);
    }

    @RequestMapping("getorderlist")
    @ResponseBody
    public JsonResult getOrderList() {
        TaokeOrderQueryVo params = getParams(TaokeOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<TaokeOrderQueryRetVo> info = taokeService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping("examineorder")
    @ResponseBody
    public JsonResult examineOrder() {
        TaokeOrderExamineParam params = getParams(TaokeOrderExamineParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        taokeService.examinBatch(params);
        return new JsonResult();
    }

    @RequestMapping("getexaminedetail")
    @ResponseBody
    public JsonResult getExamineDetail() {
        JSONObject params = getParams();
        Integer orderId = params.getInt("orderId");
        TaokeOrderDetailRetVo detail = taokeService.getExamineDetail(orderId, AuthUser.getUser().getAppId());
        return new JsonResult(detail);
    }

    @RequestMapping("exportorder")
    @ResponseBody
    public JsonResult exportOrder() {
        TaokeOrderQueryVo params = getParams(TaokeOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        String url = taokeService.doImportOrder(params);
        return new JsonResult(url);
    }

    @RequestMapping("toupordown")
    @ResponseBody
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        taokeService.updateState(activeId, AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }
}
