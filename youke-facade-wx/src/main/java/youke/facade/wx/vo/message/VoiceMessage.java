package youke.facade.wx.vo.message;

/** 
 * 消息基类（普通用户-> 公众帐号）
 *  
 * @author liuming
 */
public class VoiceMessage extends BaseMessage {

    private MediaIdVo Voice;

    public MediaIdVo getVoice() {
        return Voice;
    }

    public void setVoice(MediaIdVo voice) {
        Voice = voice;
    }
}
