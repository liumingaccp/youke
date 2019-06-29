package youke.web.h5.action;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.facade.cloudcode.provider.IVipKefuService;

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
    @RequestMapping(value = "getkefuurl", method = RequestMethod.POST)
    public JsonResult saveCloud() {
        JSONObject params = getParams();
        String appId = params.getString("appId");
        Map url = vipKefuService.getQcodeUrl(appId);
        return new JsonResult(url);
    }
}
