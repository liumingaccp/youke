package youke.common.exception;

import net.sf.json.JSONObject;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;

import java.io.Serializable;

/** 这里继承RuntimeException异常 **/
public class BusinessException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2332608236621015980L;
    /** 错误码 **/

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}