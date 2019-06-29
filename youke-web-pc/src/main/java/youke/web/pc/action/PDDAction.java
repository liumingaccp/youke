package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.JDConstants;
import youke.facade.shop.util.PDDConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拼多多授权业务
 */
@Controller
@RequestMapping("/pinduoduo")
public class PDDAction extends BaseAction {

    @Resource
    private IShopService shopService;

    @RequestMapping(value="/authback")
    public String authback(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        String code = request.getParameter("code");
        String dykId = request.getParameter("state");
        if(empty(code)) {
            request.setAttribute("message","店铺授权失败,code为null");
        }else if(empty(dykId)) {
            request.setAttribute("message", "店铺授权失败,state为null");
        }else{
            JSONObject params = new JSONObject();
            params.put("client_id", PDDConstants.CLENTID);
            params.put("client_secret",PDDConstants.CLENTSECRET);
            params.put("grant_type","authorization_code");
            params.put("code",code);
            params.put("redirect_uri","https://pcapi.dianyk.com/pinduoduo/authback");
            params.put("state",dykId);
            String res = HttpConnUtil.doPostJson("http://open-api.pinduoduo.com/oauth/token",params.toString());
            JSONObject resJson = JSONObject.fromObject(res);
            Long expire = resJson.getLong("expires_in");
            String uid = resJson.getString("owner_id");
            String userNick = resJson.getString("owner_name");
            String accessToken = resJson.getString("access_token");
            String refreshToken = resJson.getString("refresh_token");
            shopService.savePDDAuth(dykId, uid, userNick, accessToken, refreshToken, expire);
            request.setAttribute("message", "恭喜！店铺授权成功");
        }
        return "tbauth";
    }

    public static void main(String[] args) {
        JSONObject params = new JSONObject();
        params.put("client_id", PDDConstants.CLENTID);
        params.put("client_secret",PDDConstants.CLENTSECRET);
        params.put("grant_type","authorization_code");
        params.put("code","a76bc6fed7d548b8904f8f30b9f3ff54ea561dfa");
        params.put("redirect_uri","https://pcapi.dianyk.com/pinduoduo/authback");
        params.put("state","dykb7Yhmay");
        String res = HttpConnUtil.doPostJson("http://open-api.pinduoduo.com/oauth/token",params.toString());
        System.out.println(res);
        JSONObject resJson = JSONObject.fromObject(res);
        String accessToken = resJson.getString("access_token");
        System.out.println(accessToken);
    }

}
