package youke.common.exception;

import java.io.Serializable;

/** 这里继承RuntimeException异常 **/
public class H5SubscrException extends RuntimeException implements Serializable {

    public H5SubscrException() {
        super("");
    }
}