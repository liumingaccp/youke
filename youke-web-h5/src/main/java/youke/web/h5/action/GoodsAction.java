package youke.web.h5.action;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.request.WirelessShareTpwdCreateRequest;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.taobao.api.response.WirelessShareTpwdCreateResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.facade.market.provider.IToolService;
import youke.facade.shop.util.TBConstants;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("m")
public class GoodsAction extends BaseAction {

    String URL = "http://taobao.ecbao.cn/wechat/cm_details_api?item_id=ITEM_ID";

    @RequestMapping("{id}")
    public String index(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("basePath", getBasePath(request));
        request.setAttribute("goodsId", id);
        return "goods";
    }

    @RequestMapping("api/{id}")
    @ResponseBody
    public Object api(@PathVariable String id) {
        String resStr = HttpConnUtil.doHttpRequest(URL.replace("ITEM_ID", id));
        if (empty(resStr) || !resStr.contains("{")) {
            return "";
        }
        JSONObject resObj = JSONObject.fromObject(resStr);
        String taocode = getCode("https://detail.m.tmall.com/item.htm?id="+id,resObj.getString("title"));
        resObj.put("taocode",taocode);
        return resObj;
    }

    private String getCode(String url, String text) {
        String appkey = "23593400";
        String secret = "1c4a23b51e3ef20645e69650b9455e89";
//
//        TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", appkey, secret);
//        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
//        req.setText(text);
//        req.setUrl(url);
//        TbkTpwdCreateResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        if(rsp.getData()!=null)
//           return rsp.getData().getModel();
//        return "";




        TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest", appkey, secret);
        WirelessShareTpwdCreateRequest req = new WirelessShareTpwdCreateRequest();
        WirelessShareTpwdCreateRequest.GenPwdIsvParamDto obj = new WirelessShareTpwdCreateRequest.GenPwdIsvParamDto();
        obj.setUrl(url);
        obj.setText(text);
        req.setTpwdParam(obj);
        WirelessShareTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
            return rsp.getModel();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(new GoodsAction().getCode("https://uland.taobao.com/","男装精品品牌"));
    }

}
