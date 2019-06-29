package youke.web.h5.action;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.result.cloudcode.H5CloudCodeQrCodeRetVo;
import youke.facade.cloudcode.provider.ICloudCodeService;

import javax.annotation.Resource;

@RestController
@RequestMapping("cloudcode")
public class CloudCodeAction extends BaseAction {

    @Resource
    private ICloudCodeService cloudCodeService;

    /**
     * 获取二维码
     *
     * @return
     */
    @RequestMapping(value = "getcloudcode", method = RequestMethod.POST)
    public JsonResult getCloudCode() {
        JSONObject params = getParams();
        H5CloudCodeQrCodeRetVo vo = cloudCodeService.getCloudCode(params.getLong("id"), params.getString("openId"));
        return new JsonResult(vo);
    }

}
