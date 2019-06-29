package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.facade.cloudcode.provider.IVipKefuService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("vipkefu")
public class VipKefuAction extends BaseAction {

    @Resource
    private IVipKefuService vipKefuService;

    /**
     * 保存云码
     *
     * @return
     */
    @RequestMapping(value = "savekefuqcode", method = RequestMethod.POST)
    public JsonResult saveCloud() {
        JSONObject params = getParams();
        String url = params.getString("url");
        String remark = params.getString("remark");
        vipKefuService.saveKefuQcode(url, remark, AuthUser.getUser().getAppId(), AuthUser.getUser().getDykId());
        return new JsonResult();
    }

    @RequestMapping(value = "getkefuurl", method = RequestMethod.POST)
    public JsonResult getKefuInfo() {
        JSONObject params = getParams();
        Map url = vipKefuService.getQcodeUrl(AuthUser.getUser().getAppId());
        return new JsonResult(url);
    }

    @RequestMapping(value = "deletekefuurl", method = RequestMethod.POST)
    public JsonResult delKefuInfo() {
        JSONObject params = getParams();
        vipKefuService.delQcodeUrl(AuthUser.getUser().getAppId());
        return new JsonResult();
    }
}
