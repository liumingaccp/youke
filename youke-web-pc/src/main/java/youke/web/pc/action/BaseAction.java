package youke.web.pc.action;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.exception.WxException;
import youke.common.utils.JsonUtils;
import youke.web.pc.interceptor.AuthorizeInterceptor;
import org.dom4j.Document;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 */
public class BaseAction extends Base {

    protected String getBasePath(HttpServletRequest request) {
        int port = request.getServerPort();
        return "https://" + request.getServerName()+request.getContextPath() + "/";
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
            return JsonUtils.JsonToBean(AuthorizeInterceptor.getParameterJson(), t);
        } catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult exp(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        if(ex instanceof RuntimeException) {
            String busExp = "youke.common.exception.BusinessException:";
            String wxExp = "youke.common.exception.WxException:";
            String msg = ex.getMessage();
            if(msg.startsWith(busExp))
            {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,msg.substring(busExp.length(),msg.indexOf("\n")));
            }else if(msg.startsWith(wxExp)){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"WeChat通讯错误",msg.substring(wxExp.length(),msg.indexOf("\n")));
            }
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS, "服务器繁忙，请稍候再试",ex.getMessage());
        }else if(ex instanceof JSONException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"JSON参数错误",ex.getMessage());
        }else{
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"服务器繁忙，请稍候再试",ex.getMessage());
        }
    }

}
