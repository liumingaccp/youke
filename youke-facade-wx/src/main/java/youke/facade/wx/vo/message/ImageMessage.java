package youke.facade.wx.vo.message;

/** 
 * 消息基类（普通用户-> 公众帐号）
 *  
 * @author liuming
 */
public class ImageMessage extends BaseMessage {

    private MediaIdVo Image;

    public MediaIdVo getImage() {
        return Image;
    }

    public void setImage(MediaIdVo image) {
        Image = image;
    }
}
