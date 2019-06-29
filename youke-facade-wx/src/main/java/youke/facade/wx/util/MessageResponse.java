package youke.facade.wx.util;


import youke.facade.wx.vo.message.*;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author liuming
 */
public class MessageResponse {
	
	/**
	 * 回复文本消息
	 * @param fromUserName
	 * @param toUserName
	 * @param respContent
	 * @return
	 */
	public static String getTextMessage(String fromUserName , String toUserName , String respContent) {
		TextMessage message = new TextMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		message.setFuncFlag(0);
		message.setContent(respContent);
		return MessageUtil.messageToXml(message);
	}
	
	/**
	 * 创建图文消息
	 * @param fromUserName
	 * @param toUserName
	 * @param articleList
	 * @return
	 */
	public static String getNewsMessage(String fromUserName , String toUserName , List<Article> articleList) {
		NewsMessage message = new NewsMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		message.setFuncFlag(0);
		
		// 设置图文消息个数
		message.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		message.setArticles(articleList);

		// 将图文消息对象转换成xml字符串
		return MessageUtil.newsMessageToXml(message);
	}

	/**
	 * 回复图片消息
	 * @param fromUserName
	 * @param toUserName
	 * @param mediaId
	 * @return
	 */
	public static String getImageMessage(String fromUserName , String toUserName, String mediaId) {
		ImageMessage message = new ImageMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
		message.setFuncFlag(0);
		message.setImage(new MediaIdVo(mediaId));
		return MessageUtil.messageToXml(message);
	}

	/**
	 * 回复语音消息
	 * @param fromUserName
	 * @param toUserName
	 * @param mediaId
	 * @return
	 */
	public static String getVoiceMessage(String fromUserName , String toUserName, String mediaId) {
		VoiceMessage message = new VoiceMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
		message.setFuncFlag(0);
		message.setVoice(new MediaIdVo(mediaId));
		return MessageUtil.messageToXml(message);
	}

	/**
	 * 回复视频消息
	 * @param fromUserName
	 * @param toUserName
	 * @param mediaId
	 * @param title
	 * @param description
	 * @return
	 */
	public static String getVideoMessage(String fromUserName , String toUserName, String mediaId,String title,String description) {
		VideoMessage message = new VideoMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
		message.setFuncFlag(0);
		message.setVideo(new VideoVo(mediaId,title,description));
		return MessageUtil.messageToXml(message);
	}

	/**
	 *
	 * @param fromUserName
	 * @param toUserName
	 * @param title
	 * @param description
	 * @param musicUrl
	 * @param thumbMediaId
	 * @return
	 */
	public static String getMusicMessage(String fromUserName , String toUserName, String title,String description, String musicUrl, String thumbMediaId) {
		MusicMessage message = new MusicMessage();
		message.setFromUserName(fromUserName);
		message.setToUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		message.setFuncFlag(0);
		message.setMusic(new MusicVo(title,description,musicUrl,musicUrl,thumbMediaId));
		return MessageUtil.messageToXml(message);
	}
}