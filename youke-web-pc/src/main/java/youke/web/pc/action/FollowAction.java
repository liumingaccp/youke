package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.TFollowActive;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;
import youke.facade.helper.provider.IFollowService;
import youke.facade.helper.vo.FollowActiveVo;
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
@RequestMapping("follow")
public class FollowAction extends BaseAction {

    @Resource
    private IFollowService followService;

    @RequestMapping("addactive")
    @ResponseBody
    public JsonResult addActive() {
        FollowActiveVo params = getParams(FollowActiveVo.class);
        params.setYoukeId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        int i = followService.addActive(params);
        return new JsonResult(i);
    }

    @RequestMapping("delactive")
    @ResponseBody
    public JsonResult delactive() {

        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        followService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("getactivelist")
    @ResponseBody
    public JsonResult getActiveList(HttpServletRequest request) {
        FollowQueryVo params = getParams(FollowQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<FollowQueryRetVo> info = followService.queryList(params, getBasePath(request)+"common/qrcode?d=");
        return new JsonResult(info);
    }

    @RequestMapping("getactivedetail")
    @ResponseBody
    public JsonResult getActiveDetail(HttpServletRequest request) {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        TFollowActive info = followService.getActiveDetail(id, appId, dykId);
        return new JsonResult(info);
    }

    @RequestMapping("getorderlist")
    @ResponseBody
    public JsonResult getOrderList() {
        FollowOrderQueryVo params = getParams(FollowOrderQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        params.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<FollowOrderQueryRetVo> info = followService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping("toupordown")
    @ResponseBody
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        followService.updateState(activeId, AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }
}
