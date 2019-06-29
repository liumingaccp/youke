package youke.web.spread.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.web.spread.common.base.Base;
import youke.web.spread.controller.interceptor.AuthorizeInterceptor;

import javax.servlet.http.HttpServletRequest;

public class BaseController extends Base {

    protected String getBasePath(HttpServletRequest request) {
        int port = request.getServerPort();
        return "http://" + request.getServerName()+(port==80?"":port)+request.getContextPath() + "/";
    }

    protected Document getParamXML(){
        try {
            return DocumentHelper.parseText(AuthorizeInterceptor.getParameterJson());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected JSONArray getParamArrs() {
        return JSONArray.fromObject(AuthorizeInterceptor.getParameterJson());
    }

    protected JSONObject getParams() {
        return JSONObject.fromObject(AuthorizeInterceptor.getParameterJson());
    }

    protected String getParamsStr() {
        return AuthorizeInterceptor.getParameterJson();
    }

    protected <T> T getParams(Class<T> t) {
        try {
            return (T)JSONObject.toBean(JSONObject.fromObject(AuthorizeInterceptor.getParameterJson()), t);
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

}