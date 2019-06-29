package youke.facade.wx.provider;

import youke.facade.wx.vo.WxSignature;

import java.util.Map;

public interface IWeixinService {

     /**
      * 处理微信XML解析得到的HashMap
      * @param map
      * @return
      */
     String doService(Map<String,String> map);

     /**
      * 微信生成短链接
      * @param appId
      * @param longUrl
      * @return
      */
     String getShortUrl(String appId,String longUrl);

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

    String getOpenToken();

    String getToken(String appId);

    /**
     * 获取预授权码
     * @return
     */
    String getPreAuthCode(String dykId);

    /**
     * 处理授权回调
     * @param dykId
     * @param authCode
     */
    String doAuthBack(String dykId,String authCode);

    void saveVerifyTicket(String componentVerifyTicket);

    /**
     * 公众号取消授权
     * @param appId
     */
    void doUnauthorized(String appId);

    /**
     * 公众号取消或更新授权
     * @param appId
     * @param authCode
     */
    void doAuthorized(String appId, String authCode);

    /**
     * 获取JSSDK 签名
     * @param appId
     * @param url
     * @return
     */
    WxSignature getJSSignature(String appId, String url);
}
