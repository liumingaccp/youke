package youke.web.spread.common.exception;

import java.io.Serializable;

/** 这里继承RuntimeException异常 **/
public class SignException extends RuntimeException implements Serializable {

    /** 错误码 **/

    public SignException(Throwable cause) {
        super(cause);
    }

    public SignException(String message) {
        super(message);
    }

    public SignException() {
        super();
    }
}