package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.JDConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 京东授权业务
 */
@Controller
@RequestMapping("/jd")
public class JDAction extends BaseAction {

    @Resource
    private IShopService shopService;


    @RequestMapping(value="/authback")
    public String authback(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        String error = request.getParameter("error");
        if(notEmpty(error)) {
            request.setAttribute("message","店铺授权失败["+error+"]");
        }else {
            String code = request.getParameter("code");
            String dykId = request.getParameter("state");
            String res = HttpConnUtil.doPostJson("https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id=" + JDConstants.OPENAPPKEY + "&redirect_uri=http://jdser.dianyk.com/jd/authback&code=" + code + "&state=" + dykId + "&client_secret=" + JDConstants.OPENAPPSECRET, "");
            JSONObject resJson = JSONObject.fromObject(res);
            if (resJson.getInt("code") == 0) {
                Long begTime = resJson.getLong("time");
                Long expires = resJson.getLong("expires_in") * 1000; //转毫秒
                String uid = resJson.getString("uid");
                String userNick = resJson.getString("user_nick");
                String accessToken = resJson.getString("access_token");
                String refreshToken = resJson.getString("refresh_token");
                shopService.saveJDAuth(dykId, uid, userNick, accessToken, refreshToken, new Date(begTime), new Date(begTime + expires));
                request.setAttribute("message", "恭喜！店铺授权成功");
            }
        }
        return "tbauth";
    }

}
