package youke.web.spread.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class RequestUtil {

    public static JSONArray getParamArrs(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf8");
            BufferedReader br = request.getReader();
            StringBuffer json = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            JSONArray jsonArray = JSONArray.fromObject(json.toString());
            return jsonArray;
        } catch (Exception e) {
            throw new JSONException();
        }
    }

    public static JSONObject getParams(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf8");
            BufferedReader br = request.getReader();
            StringBuffer json = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            if (StringUtil.isBlank(json)) {
                json.append("{}");
            }
            JSONObject jsonObject = JSONObject.fromObject(json.toString());
            return jsonObject;
        } catch (Exception e) {
            throw new JSONException();
        }
    }

    public static <T> T getParams(HttpServletRequest request, Class<T> t) {
        try {
            request.setCharacterEncoding("utf8");
            BufferedReader br = request.getReader();
            StringBuffer json = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            return (T)JSONObject.toBean(JSONObject.fromObject(json.toString()), t);
        } catch (Exception e) {
            throw new JSONException();
        }
    }

    public static String getParamsJsonStr(HttpServletRequest request) {
        try {
            request.getParameterMap();
            request.setCharacterEncoding("utf8");
            BufferedReader br = request.getReader();
            StringBuffer json = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            if (StringUtil.isBlank(json)) {
                json.append("{}");
            }

            return json.toString();
        } catch (Exception e) {
            throw new JSONException();
        }
    }

    public static String getBrowserName(String agent) {
        if(agent.indexOf("msie 6")>0){
            return "ie6";
        }else if(agent.indexOf("msie 7")>0){
            return "ie7";
        }else if(agent.indexOf("msie 8")>0){
            return "ie8";
        }else if(agent.indexOf("msie 9")>0){
            return "ie9";
        }else if(agent.indexOf("msie 10")>0){
            return "ie10";
        }else if(agent.indexOf("msie")>0){
            return "ie";
        }else if(agent.indexOf("opera")>0){
            return "opera";
        }else if(agent.indexOf("opera")>0){
            return "opera";
        }else if(agent.indexOf("firefox")>0){
            return "firefox";
        }else if(agent.indexOf("webkit")>0){
            return "webkit";
        }else if(agent.indexOf("gecko")>0 && agent.indexOf("rv:11")>0){
            return "ie11";
        }else{
            return "Others";
        }
    }

}
