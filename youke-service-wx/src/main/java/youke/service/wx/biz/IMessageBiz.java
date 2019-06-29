package youke.service.wx.biz;


import youke.facade.wx.vo.message.Article;

import java.util.List;

/**
 * 客服消息接口(48小时内有效，发送失败抛出BusinessException)
 */
public interface IMessageBiz {

    /**
     * 发文本
     * @param appId
     * @param openId
     * @param content
     */
    String sendText(String appId,String openId,String content);

    /**
     * 发图片
     * @param appId
     * @param openId
     * @param mediaId
     */
    String sendImage(String appId,String openId, String mediaId);

    /**
     * 发语音
     * @param appId
     * @param openId
     * @param mediaId
     */
    String sendVoice(String appId,String openId, String mediaId);

    /**
     * 发送视频
     * @param appId
     * @param openId
     * @param mediaId
     * @param title
     * @param desc
     */
    String sendVideo(String appId,String openId, String mediaId,String thumb_media_id, String title,String desc);

    /**
     * 发送音乐
     * @param appId
     * @param openId
     * @param thumbMediaId
     * @param title
     * @param desc
     * @param musicurl
     * @param hqmusicurl
     */
    String sendMusic(String appId,String openId, String thumbMediaId,String title,String desc,String musicurl,String hqmusicurl);

    /**
     * 发送图文
     * @param appId
     * @param openId
     * @param articles
     */
    String sendNews(String appId, String openId, List<Article> articles);

    /**
     * 发送图文
     * @param appId
     * @param openId
     * @param mediaId
     */
    String sendNews(String appId, String openId, String mediaId);

    String getVideoMediaId(String mediaId, String title, String desc, String appId);
}
