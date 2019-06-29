package youke.web.h5.action;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.MD5Util;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.user.provider.IUserService;
import youke.facade.wx.provider.IWeixinService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@RequestMapping("common")
public class CommonAction extends BaseAction {

    @Resource
    private IWeixinService weixinService;
    @Resource
    private IUserService userService;
    @Resource
    private IMobmsgService mobmsgService;

    /**
     * 发送验证码
     *
     * @return
     */
    @RequestMapping("sendcode")
    public JsonResult sendcode() {
        JSONObject objs = getParams();
        String mobile = objs.getString("mobile");
        String appId = objs.getString("appId");
        //手机号码的本地验证
        mobmsgService.sendMobCode(appId,mobile);
        return new JsonResult();
    }

    @RequestMapping(value = "getqrcode")
    public JsonResult getQrcode(HttpServletRequest request) {
        String qrcode = "";
        JSONObject objs = getParams();
        if (!objs.containsKey("appId"))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "appId参数必传");
        String appId = objs.getString("appId");
        if (objs.containsKey("key")) {
            String key = objs.getString("key");
            String val = objs.getString("val");
            try {
                qrcode = "https://pcapi.dianyk.com/common/qrcode?d=" + URLEncoder.encode(weixinService.getQrcodeTmp(appId, key + "_", val, 3600), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            qrcode = userService.getsubscr(appId).getQrcodeUrl();
        }
        return new JsonResult(qrcode);
    }

    @RequestMapping("getwximage")
    public JsonResult getWxImage() {
        JSONObject objs = getParams();
        String serverId = objs.getString("serverId");
        String appId = objs.getString("appId");
        String downUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + weixinService.getToken(appId) + "&media_id=" + serverId;
        InputStream stream = HttpConnUtil.getStreamFromNetByUrl(downUrl);
        String extensionName = "jpg";
        String key = extensionName + "/" + MD5Util.MD5(UUID.randomUUID().toString()) + "." + extensionName;
        return new JsonResult(FileUpOrDwUtil.uploadNetStream(stream, key));
    }

}
