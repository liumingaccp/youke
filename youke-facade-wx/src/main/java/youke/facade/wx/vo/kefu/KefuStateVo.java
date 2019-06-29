package youke.facade.wx.vo.kefu;

import java.io.Serializable;

public class KefuStateVo implements Serializable {

    private String account;

    private String state;

    private Integer chatNum;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getChatNum() {
        return chatNum;
    }

    public void setChatNum(Integer chatNum) {
        this.chatNum = chatNum;
    }

}
