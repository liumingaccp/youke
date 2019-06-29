package youke.service.wx.provider;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.MessageUtil;
import youke.facade.wx.vo.WxSignature;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IFansBiz;
import youke.service.wx.biz.IResponseBiz;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class WeixinService extends Base implements IWeixinService {

    @Resource
    private IResponseBiz responseBiz;

    @Resource
    private ICoreBiz coreBiz;

    @Resource
    private IFansBiz fansBiz;

    public String doService(Map<String,String> map)
    {
        String respMessage = "success";
        try {
            String appId = map.get("appId");
            // 发送方帐号（open_id）
            String openId = map.get("FromUserName");
            // 公众帐号
            String wxId = map.get("ToUserName");
            // 消息类型
            String msgType = map.get("MsgType");
            // 消息内容
            String content = map.get("Content");
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                fansBiz.updateFansLastTime(openId);
                return responseBiz.doTextResp(openId , wxId , content,appId);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                String eventType = map.get("Event");// 事件类型
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅以及订阅带参数
                    fansBiz.updateFansLastTime(openId);
                    return responseBiz.doSubscribeResp(openId, wxId, map.get("EventKey"), appId);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
                    responseBiz.doUnSubscribeResp(openId,wxId,appId);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
                    fansBiz.updateFansLastTime(openId);
                    return responseBiz.doScanResp(openId,wxId,map.get("EventKey"),appId);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                    fansBiz.updateFansLastTime(openId);
                    return responseBiz.doClickResp(openId,wxId,map.get("EventKey"),appId);
                }else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {// 点击菜单跳转链接时的事件推送
                    fansBiz.updateFansLastTime(openId);
                    return responseBiz.doClickResp(openId,wxId,map.get("EventKey"),appId);
                }
            }
            //开启微信声音识别测试
            else if(msgType.equals("voice"))
            {
                fansBiz.updateFansLastTime(openId);
                String recvMessage = map.get("Recognition");
                return responseBiz.doTextResp(openId , wxId , recvMessage,appId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    public String getShortUrl(String appId,String longUrl){
        return coreBiz.getShortUrl(appId,longUrl);
    }

    public String getQrcodeTmp(String appId,String sceneType,String sceneStr,int expire){
        return coreBiz.getQrcodeTmp(appId,sceneType,sceneStr,expire);
    }

    public String getQrcode(String appId, String sceneType, String sceneStr) {
        return coreBiz.getQrcode(appId,sceneType,sceneStr);
    }

    public String getPreAuthCode(String dykId) {
        return coreBiz.getPreAuthCode(dykId);
    }

    public String getOpenToken() {
        return coreBiz.getOpenToken();
    }

    public String getToken(String appId){
        return coreBiz.getToken(appId);
    }

    public String doAuthBack(String dykId,String authCode) {
        return coreBiz.saveAuthInfo(dykId,authCode);
    }

    public void saveVerifyTicket(String ticket) {
         coreBiz.saveVerifyTicket(ticket);
    }

    public void doUnauthorized(String appId) {
         coreBiz.updateSubscrState(appId,0);
    }

    public void doAuthorized(String appId, String authCode) {
         coreBiz.updateSubscrState(appId,1);
    }

    public WxSignature getJSSignature(String appId, String url) {
        return coreBiz.getJSSignature(appId, url);
    }


}
