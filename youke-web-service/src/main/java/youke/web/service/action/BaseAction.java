package youke.web.service.action;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.utils.JsonUtils;
import youke.web.service.interceptor.AuthorizeInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务基本Action，所有Action都要继承本类
 */
public class BaseAction extends Base {

    protected String getBasePath(HttpServletRequest request) {
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
//            try {
//                msg = new String(msg.getBytes("gbk"),"utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            if(msg.startsWith(busExp))
            {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,msg.substring(busExp.length(),msg.indexOf("\n")));
            }else if(msg.startsWith(wxExp)){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"WeChat通讯错误",msg.substring(wxExp.length(),msg.indexOf("\n")));
            }
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,msg);
        }else if(ex instanceof JSONException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"JSON参数错误",ex.getMessage());
        }else{
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"亲,服务出了问题...",ex.getMessage());
        }
    }

}
