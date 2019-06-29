package youke.web.pc.action;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.facade.pay.provider.IOrderService;
import youke.facade.wx.aes.WXBizMsgCrypt;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信授权业务
 */
@Controller
@RequestMapping("/weixin")
public class WeixinAction extends BaseAction {

    @Resource
    private IWeixinService weixinService;

    @RequestMapping("getOpenToken")
    @ResponseBody
    public JsonResult getOpenToken(){
        return new JsonResult(weixinService.getOpenToken());
    }


    @RequestMapping("{dykId}")
    public String index(@PathVariable String dykId,HttpServletRequest request){
        request.setAttribute("dykId",dykId);
        return "weixin";
    }

    /**
     * 跳转授权页面
     * @return
     */
    @RequestMapping(value="{dykId}/goauth")
    public String goauth(@PathVariable String dykId,HttpServletRequest request){
        String preAuthCode = weixinService.getPreAuthCode(dykId);
        return "redirect:https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid="+ Constants.OPENAPPID+"&pre_auth_code="+preAuthCode+"&redirect_uri="+getBasePath(request)+"weixin/"+dykId+"/authback&auth_type=1";
    }

    @RequestMapping(value="{dykId}/authback")
    public String authback(@PathVariable String dykId,HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        String authCode = request.getParameter("auth_code");
        String msg = weixinService.doAuthBack(dykId,authCode);
        request.setAttribute("message",msg);
        return "wxauth";
    }

    /**
     * 消息授权回调通知
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="noticeback")
    @ResponseBody
    public String noticeback(HttpServletRequest request) throws Exception
    {
        String msgSignature = request.getParameter("msg_signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        try {
            Document encDoc = getParamXML();
            //解密推送信息
            if (encDoc != null) {
                Element element = getParamXML().getRootElement();
                WXBizMsgCrypt pc = new WXBizMsgCrypt(Constants.OPENTOKEN, Constants.OPENKEY, Constants.OPENAPPID);
                String encrypt = element.elementText("Encrypt");
                String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%s]]></Encrypt></xml>";
                String fromXML = String.format(format, encrypt);
                String resultXml = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
                logger.info(resultXml);
                Document xmlDoc = DocumentHelper.parseText(resultXml);
                Element xmlEle = xmlDoc.getRootElement();
                //todo 9、推送授权相关通知
                String infotype = xmlEle.elementText("InfoType");
                if (infotype.equals("component_verify_ticket")) {
                    weixinService.saveVerifyTicket(xmlEle.elementText("ComponentVerifyTicket"));
                } else if (infotype.equals("unauthorized")) {
                    weixinService.doUnauthorized(xmlEle.elementText("AuthorizerAppid"));
                } else if (infotype.equals("authorized") || infotype.equals("updateauthorized")) {
                    weixinService.doAuthorized(xmlEle.elementText("AuthorizerAppid"), xmlEle.elementText("AuthorizationCode"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return "success";
        }
    }


    /**
     * 授权公众号交互推送回调
     * @param appId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="{appId}/callback")
    @ResponseBody
    public String callback(@PathVariable String appId, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        String msgSignature = request.getParameter("msg_signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        try {
            Document encDoc = getParamXML();
            //解密推送信息
            if (encDoc != null) {
                Element element = getParamXML().getRootElement();
                WXBizMsgCrypt pc = new WXBizMsgCrypt(Constants.OPENTOKEN, Constants.OPENKEY, Constants.OPENAPPID);
                String encrypt = element.elementText("Encrypt");
                String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%s]]></Encrypt></xml>";
                String fromXML = String.format(format, encrypt);
                String resultXml = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
                logger.info("-------------消息解密XML--------------");
                logger.info(resultXml);
                Document xmlDoc = DocumentHelper.parseText(resultXml);
                List<Element> eles = xmlDoc.getRootElement().elements();
                Map<String, String> map = new HashMap<String, String>();
                map.put("appId", appId);
                for (Element ele : eles) {
                    map.put(ele.getName(), ele.getStringValue());
                }
                String resXML = weixinService.doService(map);
                logger.info(resXML);
                return pc.encryptMsg(resXML,timestamp,nonce);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }


}
