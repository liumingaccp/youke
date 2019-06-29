package youke.service.helper.provider;

import org.springframework.stereotype.Service;
import youke.facade.helper.provider.ITaocodeService;

@Service
public class TaocodeService implements ITaocodeService {

    @Override
    public String getCode(String url, String text) {
        /*String appkey = "23593400";
        String secret = "1c4a23b51e3ef20645e69650b9455e89";

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
        }*/
        return "";
    }

}
