package youke.facade.shop.util;

import net.sf.json.JSONObject;
import youke.common.exception.BusinessException;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.MD5Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PDDUtil {

    /**
     * params 无需传client_id
     * @param params
     * @return
     */
    public static JSONObject request(Map<String,String> params){
        if(params==null)
            return null;
        params.put("client_id", PDDConstants.CLENTID);
        params.put("timestamp",System.currentTimeMillis()+"");

        StringBuilder sb = new StringBuilder(PDDConstants.CLENTSECRET);
        List<Map.Entry<String, String>> mes = sortMap(params);
        for (Map.Entry<String, String> me:mes){
            sb.append(me.getKey()+me.getValue());
        }
        sb.append(PDDConstants.CLENTSECRET);
        //加密
        String sign = MD5Util.MD5(sb.toString()).toUpperCase();
        params.put("sign",sign);

        String res = HttpConnUtil.doHttpRequest(PDDConstants.APIURL,params);
        JSONObject jsonRes = JSONObject.fromObject(res);

        if(jsonRes.containsKey("error_response")){
            JSONObject response = jsonRes.getJSONObject("error_response");
            throw new BusinessException(response.getInt("error_code")+":"+response.getString("error_msg"));
        }

       return jsonRes;
    }


    //Map按字母升序
    private static List<Map.Entry<String, String>> sortMap(final Map<String, String> map) {
        final List<Map.Entry<String, String>> infos = new ArrayList<>(map.entrySet());
        // 重写集合的排序方法：按字母顺序
        Collections.sort(infos, (o1, o2) -> (o1.getKey().toString().compareTo(o2.getKey())));
        return infos;
    }
}
