package youke.web.spread.common.exception;

import java.io.Serializable;

/** 这里继承RuntimeException异常 **/
public class BusinessException extends RuntimeException implements Serializable {
    /** 错误码 **/

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}