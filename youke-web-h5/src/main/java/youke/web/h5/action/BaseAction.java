package youke.web.h5.action;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.utils.JsonUtils;
import youke.web.h5.interceptor.AuthorizeInterceptor;

import javax.servlet.http.HttpServletRequest;

public class BaseAction extends Base {

    protected String getBasePath(HttpServletRequest request) {
        int port = request.getServerPort();
        return "https://" + request.getServerName()+request.getContextPath() + "/";
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
        if (ex instanceof RuntimeException) {
            String wxExp = "youke.common.exception.WxException:";
            String busExp = "youke.common.exception.BusinessException:";
            String h5MobileExp = "youke.common.exception.H5MobileException:";
            String h5SubscrExp = "youke.common.exception.H5SubscrException:";
            String msg = ex.getMessage();
            if (msg.startsWith(busExp)) {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, msg.substring(busExp.length(), msg.indexOf("\n")));
            } else if (msg.startsWith(wxExp)) {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "WeChat通讯错误", msg.substring(wxExp.length(), msg.indexOf("\n")));
            } else if (msg.startsWith(h5MobileExp)) {
                return new JsonResult(ApiCodeConstant.COMMON_H5_MOBILE, "请绑定手机号码");
            } else if (msg.startsWith(h5SubscrExp)) {
                return new JsonResult(ApiCodeConstant.COMMON_H5_SUBSCR, "请先关注公众号");
            }
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS, "服务器繁忙，请稍候再试",ex.getMessage());
        }else if(ex instanceof JSONException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"JSON参数错误",ex.getMessage());
        }else{
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"服务器繁忙，请稍候再试",ex.getMessage());
        }
    }

}
