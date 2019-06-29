package youke.facade.wx.vo.kefu;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class KefuVo implements Serializable {

    private Integer id;

    /**
     * 客服账号
     */
    private String account;

    /**
     * 客服头像
     */
    private String face;

    /**
     * 客服昵称
     */
    private String nickName;

    /**
     * 客服序号
     */
    private Integer kefuNum;

    /**
     * 绑定微信号
     */
    private String wechatId;

    /**
     * 处于邀请时期的微信号
     */
    private String inviteWx;

    /**
     * 绑定状态  0:未绑定，1:已绑定, 2绑定邀请待确认，3已拒绝，4邀请过期
     * 默认是未绑定
     */
    private int state = 0;

    private String stateDisPlay;

    /**
     * 绑定时间字符串 yyyy-MM-dd HH:mm
     */
    private String bingTime;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getKefuNum() {
        return kefuNum;
    }

    public void setKefuNum(Integer kefuNum) {
        this.kefuNum = kefuNum;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBingTime() {
        return bingTime;
    }

    public void setBingTime(String bingTime) {
        this.bingTime = bingTime;
    }

    public void setInviteWx(String inviteWx) {
        this.inviteWx = inviteWx;
    }

    public String getInviteWx() {
        return inviteWx;
    }

    public String getStateDisPlay() {

        switch (this.getState()){
            case 0 :
                return "未绑定";
            case 1 :
                return "已绑定";
            case 2 :
                return "绑定邀请中";
            case 3:
                return "绑定邀请拒绝";
            case 4 :
                return "绑定邀请过期";
            default:
                return "未绑定";

        }
    }

    public KefuVo(){}

    public KefuVo(JSONObject obj){
        if(obj.has("kf_account")){
            this.account = obj.getString("kf_account");
        }
        if(obj.has("kf_headimgurl")){
            this.face = obj.getString("kf_headimgurl");
        }
        if(obj.has("kf_id")){
            this.kefuNum = obj.getInt("kf_id");
        }
        if(obj.has("kf_nick")){
            this.nickName = obj.getString("kf_nick");
        }

        if(obj.has("invite_status")){

            //如果有值,表示有邀请过的微信号
            this.inviteWx = obj.getString("invite_wx");

            String status = obj.getString("invite_status");
            if(status.equals("waiting")){
                this.state = 2;
            }else if(status.equals("rejected")){
                this.state = 3;
            }else if(status.equals("expired")){
                this.state = 4;
            }
        }

        if(obj.has("kf_wx")){
            this.wechatId = obj.getString("kf_wx");
            //如果有值,表示已经绑定
            this.state = 1;
        }

    }
}
