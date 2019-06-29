package youke.web.spread.controller.configs;

import net.sf.json.JSONException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.web.spread.bean.JsonResult;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.exception.BusinessException;
import youke.web.spread.common.exception.SignException;
import youke.web.spread.common.exception.TokenException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class Global500ExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult defaultErrorHandler(HttpServletRequest req,
                                          Exception ex) {
        if(ex instanceof BusinessException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, ex.getMessage());
        }else if(ex instanceof SignException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SING, "签名无效，请检查");
        }else if(ex instanceof TokenException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_LOGIN_TIMEOUT, ex.getMessage());
        }else if(ex instanceof JSONException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"入参错误，请检查",ex.getMessage());
        }else if(ex instanceof HttpRequestMethodNotSupportedException) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"无效请求，请检查",ex.getMessage());
        }else{
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,"服务器繁忙，请稍候再试",ex.getMessage());
        }
    }
}
