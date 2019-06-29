package youke.service.wx.biz;


import youke.facade.wx.vo.WxSignature;

/**
 * 基础核心接口
 */
public interface ICoreBiz {

    /**
     * 获取开放平台Token
     * @return
     */
    String getOpenToken();

    /**
     * 获取预授权码
     * @return
     */
    String getPreAuthCode(String dykId);

    /**
     * 获取公众号的token
     * @param appId
     * @return
     */
     String getToken(String appId);

    /**
     * 设置verifyTicket
     * @param ticket
     */
     void saveVerifyTicket(String ticket);

    /**
     * 获取短连接
     * @param longUrl
     * @return
     */
     String getShortUrl(String appId,String longUrl);

    /**
     * 保存获取授权回调的公众号信息
     * @param dykId
     * @param authCode
     */
     String saveAuthInfo(String dykId, String authCode);

     String saveSubscr(String dykId,String appId);

     void doFansRule(String appId,String dykId);

     void updateSubscrState(String appId, int state);

    /**
     * 获取临时二维码
     * @param appId
     * @param sceneType  SceneType常量类
     * @param sceneStr
     * @param expire  30~2592000
     * @return
     */
     String getQrcodeTmp(String appId, String sceneType, String sceneStr, int expire);

    /**
     * 获取永久二维码
     * @param appId
     * @param sceneType SceneType常量类
     * @param sceneStr
     * @return
     */
     String getQrcode(String appId, String sceneType, String sceneStr);

     WxSignature getJSSignature(String appId, String url);
}
