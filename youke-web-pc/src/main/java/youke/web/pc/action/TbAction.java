package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.constants.ApiCodeConstant;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.ShopType;
import youke.facade.shop.util.TBConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 店铺授权业务
 */
@Controller
@RequestMapping("/tb")
public class TbAction extends BaseAction {

    @Resource
    private IShopService shopService;


    @RequestMapping("/")
    public String index(){
        return "tb";
    }

    @RequestMapping("authback")
    public String authback(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        String error = request.getParameter("error");
        if(notEmpty(error)) {
            request.setAttribute("message","店铺授权失败["+error+"]");
        }else {
            String code = request.getParameter("code");
            String state = request.getParameter("state");
            if(empty(state)){
                request.setAttribute("message","state 参数错误");
            }else {
                int type = 0;
                String dykId = state;
                if (state.contains("_")) {
                    dykId = state.split("_")[0];
                    type = Integer.parseInt(state.split("_")[1]);
                }
                String url = "https://oauth.taobao.com/token";
                Map<String, String> props = new HashMap<String, String>();
                props.put("grant_type", "authorization_code");
                props.put("code", code);
                props.put("client_id", TBConstants.OPENAPPKEY);
                props.put("client_secret", TBConstants.OPENAPPSECRET);
                props.put("redirect_uri", "http://www.pudada.com/tb/fauthback");
                props.put("view", "web");
                String json = HttpConnUtil.doHttpRequest(url, props);
                JSONObject resObj = JSONObject.fromObject(json);
                //{"w1_expires_in":566544,"refresh_token_valid_time":1517522400000,"taobao_user_nick":"dinghuanxhin","re_expires_in":566544,"expire_time":1517522400000,"token_type":"Bearer","access_token":"6201a025f2ZZ5126e87b421e3317bd233d04ddbc6374458615648409","w1_valid":1517522400000,"refresh_token":"62013029d7ZZ49abef603cb115584efddde4d0db4ea625a615648409","w2_expires_in":1800,"w2_valid":1516957655381,"r1_expires_in":566544,"r2_expires_in":259200,"r2_valid":1517215055381,"r1_valid":1517522400000,"taobao_user_id":"615648409","expires_in":566544}
                shopService.saveTBAuth(dykId,
                        resObj.getString("taobao_user_id"),
                        resObj.getString("taobao_user_nick"),
                        resObj.getString("access_token"),
                        resObj.getString("refresh_token"),
                        new Date(resObj.getLong("expire_time")),
                        resObj.getLong("r2_expires_in"),
                        type);
                request.setAttribute("message", "恭喜！店铺授权成功");
            }
        }
        return "tbauth";
    }

    @RequestMapping(value="/fauthback")
    @ResponseBody
    public String fauthback(HttpServletRequest request,HttpServletResponse response){
        return "success";
    }
}
