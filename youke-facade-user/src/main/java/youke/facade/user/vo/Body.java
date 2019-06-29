package youke.facade.user.vo;

import java.io.Serializable;

public class Body implements Serializable {

    public Body(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
