package youke.web.pc.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.utils.JsonUtils;
import youke.facade.user.vo.UserVo;
import youke.facade.wx.provider.IWeixinMenuService;
import youke.facade.wx.provider.IWeixinTempService;
import youke.facade.wx.vo.WxTempVo;
import youke.facade.wx.vo.menu.MenuVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("wxtemp")
public class WxTempAction extends BaseAction {

    @Resource
    private IWeixinTempService weixinTempService;

    /**
     * 判断是否开启模板
     *
     * @return
     */
    @RequestMapping(value = "isopen", method = RequestMethod.POST)
    public JsonResult isOpen() {
        String appId = AuthUser.getUser().getAppId();
        return new JsonResult(weixinTempService.isOpen(appId));
    }

    /**
     * 获取微信模板配置
     *
     * @return
     */
    @RequestMapping(value = "gettemplates", method = RequestMethod.POST)
    public JsonResult getTemplates() {
        String appId = AuthUser.getUser().getAppId();
        List<WxTempVo> tempVos = weixinTempService.getTemplates(appId);
        return new JsonResult(tempVos);
    }

    @RequestMapping(value = "opentemplate", method = RequestMethod.POST)
    public JsonResult openTemplate() {
        UserVo userVo = AuthUser.getUser();
        JSONObject params = getParams();
        String templateShort = params.getString("templateShort");
        String templateId = weixinTempService.openTemplate(userVo.getAppId(),userVo.getDykId(),templateShort);
        return new JsonResult(templateId);
    }

    @RequestMapping(value = "closetemplate", method = RequestMethod.POST)
    public JsonResult closeTemplate() {
        String appId = AuthUser.getUser().getAppId();
        JSONObject params = getParams();
        String templateId = params.getString("templateId");
        weixinTempService.closeTemplate(appId,templateId);
        return new JsonResult();
    }

}
