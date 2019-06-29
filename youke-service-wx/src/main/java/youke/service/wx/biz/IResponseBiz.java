package youke.service.wx.biz;

public interface IResponseBiz {

    /**
     * 处理微信文本
     * @param openId
     * @param wxId
     * @param content
     * @param appId
     * @return
     */
    String doTextResp(String openId, String wxId, String content,String appId);

    /**
     * 处理微信关注
     * @param openId
     * @param wxId
     * @param eventKey  事件KEY值，qrscene_为前缀，后面为二维码的参数值
     * @param appId
     * @return
     */
    String doSubscribeResp(String openId, String wxId, String eventKey,String appId);

    /**
     * 处理取消关注
     * @param openId
     * @param wxId
     * @param appId
     */
    void doUnSubscribeResp(String openId, String wxId,String appId);

    /**
     * 处理扫码进入
     * @param openId
     * @param wxId
     * @param eventKey  事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
     * @param appId
     */
    String doScanResp(String openId, String wxId, String eventKey,String appId);

    /**
     * 处理点击事件
     * @param openId
     * @param wxId
     * @param eventKey
     * @param appId
     * @return
     */
    String doClickResp(String openId, String wxId, String eventKey,String appId);
}
