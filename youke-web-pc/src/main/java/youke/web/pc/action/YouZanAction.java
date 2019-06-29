package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.YZConstants;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 有赞授权业务
 */
@Controller
@RequestMapping("/youzan")
public class YouZanAction extends BaseAction {

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
            params.put("client_id", YZConstants.CLENTID);
            params.put("client_secret",YZConstants.CLENTSECRET);
            params.put("grant_type","authorization_code");
            params.put("code",code);
            params.put("redirect_uri","https://pcapi.dianyk.com/youzan/authback");
            String res = HttpConnUtil.doPostJson("https://open.youzan.com/oauth/token",params.toString());
            JSONObject resJson = JSONObject.fromObject(res);
            Long expire = resJson.getLong("expires_in");
            String accessToken = resJson.getString("access_token");
            String refreshToken = resJson.getString("refresh_token");
            shopService.saveYZAuth(dykId, accessToken, refreshToken, expire);
            request.setAttribute("message", "恭喜！店铺授权成功");
        }
        return "tbauth";
    }

    @RequestMapping(value="/push")
    @ResponseBody
    public String push(HttpServletRequest request,HttpServletResponse response){
        JSONObject params = getParams();
        if(params.containsKey("client_id")&&params.getString("client_id").equals(YZConstants.CLENTID)){
            String[] status = {"WAIT_SELLER_SEND_GOODS","WAIT_BUYER_CONFIRM_GOODS","TRADE_SUCCESS"};
            boolean isOK = Arrays.stream(status).anyMatch(sta -> sta.equals(params.getString("status")));
            if(isOK){
                String orderStr = params.getString("msg");
                String dianId = params.getString("kdt_id");
                shopService.doYZOrderPush(dianId,orderStr);
            }
        }
        return "ok";
    }


    public static void main(String[] args) {

    }

}
