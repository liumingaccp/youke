package youke.web.spread.common.exception;

import java.io.Serializable;

/** 这里继承RuntimeException异常 **/
public class TokenException extends RuntimeException implements Serializable {

    /** 错误码 **/

    public TokenException(Throwable cause) {
        super(cause);
    }

    public TokenException(String message) {
        super(message);
    }
}