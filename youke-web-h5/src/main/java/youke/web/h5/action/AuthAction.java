package youke.web.h5.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.IDUtil;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
@RequestMapping("auth")
public class AuthAction extends BaseAction {

    @Resource
    private IWeixinService weixinService;

    @RequestMapping(value = "{type}/{appid}")
    public String userinfo(@PathVariable String type, @PathVariable String appid, HttpServletRequest request, HttpServletResponse response) {
         String reurl = request.getParameter("reurl");
         if(empty(reurl)){
             request.setAttribute("message","reurl不能为空");
             return "error";
         }
         String rankey = UUID.randomUUID().toString();
         RedisUtil.set(rankey,reurl,600);
         return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appid + "&redirect_uri=" + URLEncoder.encode(getBasePath(request)+"auth/callback")+"&response_type=code&scope=snsapi_"+type+"&state="+type+"_"+rankey+"&component_appid="+Constants.OPENAPPID+"#wechat_redirect";
    }

    @RequestMapping(value = "callback")
    public String callback(HttpServletRequest request) {
         if(empty(request.getParameter("code"))){
             request.setAttribute("message","用户禁止授权");
             return "error";
         }
         String code = request.getParameter("code");
         String state = request.getParameter("state");
         String appid = request.getParameter("appid");
         String json = HttpConnUtil.doHttpRequest("https://api.weixin.qq.com/sns/oauth2/component/access_token?appid="+appid+"&code="+code+"&grant_type=authorization_code&component_appid="+Constants.OPENAPPID+"&component_access_token="+weixinService.getOpenToken());
         JSONObject object = JSONObject.fromObject(json);
         if(object.containsKey("openid")) {
            String openid = object.getString("openid");
            String access_token = object.getString("access_token");
            String type = state.split("_")[0];
            String ranKey = state.split("_")[1];
            String reurl = (String) RedisSlaveUtil.get(ranKey);
             try {
                 reurl =URLDecoder.decode(reurl,"utf-8");
             } catch (Exception e) {
             }
             RedisUtil.del(ranKey);
            if (type.equals("userinfo")) {
                //拉取用户信息
                String userinfo = HttpConnUtil.doHttpRequest("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN");
                JSONObject userObj = JSONObject.fromObject(userinfo);
                if (userObj.containsKey("openid")) {
                    String nickname = userObj.getString("nickname");
                    try {
                        nickname = URLEncoder.encode(nickname,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return "redirect:"+reurl+"?openid="+openid+"&nickname="+nickname+"&headimgurl="+userObj.getString("headimgurl").replace("http://","https://");
                }
            }
            return "redirect:"+reurl+"?openid="+openid;
         }
        request.setAttribute("message",object.toString());
        return "error";
    }

    @RequestMapping(value = "getsignature",  method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getsignature(HttpServletRequest request) {
        JSONObject params = getParams();
        String appId = params.getString("appId");
        String url = params.getString("url");
        return new JsonResult(weixinService.getJSSignature(appId,url));

    }

}
