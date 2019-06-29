package youke.facade.shop.vo;

import java.io.Serializable;

public class AuthShopVo implements Serializable {

    public AuthShopVo() {
    }

    /**
     *
     * @param id
     * @param title
     * @param state
     * @param type
     * @param authTime
     * @param expTime
     */
    public AuthShopVo(int id, String title, int state, int type, String authTime, String expTime) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.type = type;
        this.authTime = authTime;
        this.expTime = expTime;
    }

    private int id;
    private String title;
    private int state;
    private int type;
    private String authTime;
    private String expTime;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }
}
