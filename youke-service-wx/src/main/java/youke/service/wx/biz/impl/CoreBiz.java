package youke.service.wx.biz.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TSubscr;
import youke.common.model.TSubscrConfig;
import youke.common.model.TTag;
import youke.common.model.TTagRule;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.queue.message.InitCodeMessage;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.WxSignature;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class CoreBiz extends Base implements ICoreBiz {

    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private ITagDao tagDao;
    @Resource
    private ITagGroupDao tagGroupDao;
    @Resource
    private ITagRuleDao tagRuleDao;
    @Resource
    private QueueSender queueSender;


    private static final String OPENTOKENKEY = Constants.OPENAPPID+"-component-access-token";
    private static final String PREAUTHCODEKEY = Constants.OPENAPPID+"-pre-auth-code-";
    private static final String ACCESSTOKENKEY = Constants.OPENAPPID+"-authorizer-access-token-";
    private static final String ACCESSTICKETKEY = Constants.OPENAPPID+"-authorizer-jsapi-ticket-";

    /**
     * 获取开放平台token
     * @return
     */
    public String getOpenToken() {
        String val = (String) RedisSlaveUtil.get(OPENTOKENKEY);
        if(empty(val))
        {
            JSONObject obj = new JSONObject();
            obj.put("component_appid",Constants.OPENAPPID);
            obj.put("component_appsecret",Constants.OPENAPPSECRET);
            obj.put("component_verify_ticket",getVerifyTicket());
            String res = HttpConnUtil.httpsRequest(Constants.BASEURL+"component/api_component_token",obj.toString());
            WxCurlUtil.ret(res);
            JSONObject jsonRes = JSONObject.fromObject(res);
            val = jsonRes.getString("component_access_token");
            RedisUtil.set(OPENTOKENKEY, val, Constants.EFFECTIVE_TIME);

        }
        return val;
    }

    public String getPreAuthCode(String dykId){
        JSONObject obj = new JSONObject();
        obj.put("component_appid",Constants.OPENAPPID);
        String res = HttpConnUtil.httpsRequest(Constants.BASEURL+"component/api_create_preauthcode?component_access_token="+getOpenToken(),obj.toString());
        JSONObject jsonRes = JSONObject.fromObject(res);
        return jsonRes.getString("pre_auth_code");
    }

    public String saveAuthInfo(String dykId, String authCode) {
        JSONObject obj = new JSONObject();
        obj.put("component_appid",Constants.OPENAPPID);
        obj.put("authorization_code",authCode);
        String res = HttpConnUtil.doPostJson(Constants.BASEURL+"component/api_query_auth?component_access_token="+getOpenToken(),obj.toString());
        JSONObject jsonRes = JSONObject.fromObject(res);
        JSONObject authInfo = jsonRes.getJSONObject("authorization_info");
        String appId = authInfo.getString("authorizer_appid");
        String accessToken = authInfo.getString("authorizer_access_token");
        //检测有客Id是否已绑定公众号
        TSubscr oldSub = subscrDao.selectByYoukeId(dykId);
        if(notEmpty(oldSub)&&!oldSub.getAppid().equals(appId)){
            return "授权失败，该账户只可重新授权公众号【"+oldSub.getNickname()+"】";
        }

        //检测有客Id是否已绑定
        String youkeId = subscrDao.selectDyk(appId);
        if(notEmpty(youkeId)&&!dykId.equals(youkeId))
        {
            String resMsg = saveSubscr(youkeId, appId);
            if(notEmpty(resMsg))
                return resMsg;
            return "授权失败，公众号已授权其它店有客账户";
        }
        String resMsg = saveSubscr(dykId, appId);
        if(notEmpty(resMsg))
            return resMsg;
        RedisUtil.set(ACCESSTOKENKEY+appId,accessToken,Constants.EFFECTIVE_TIME);
        doFansRule(appId,dykId);
        //发送同步已关注粉丝任务
        queueSender.send("syncsubscr.queue",appId);
        return "恭喜！微信公众号授权成功";
    }


    /**
     * 初始化购物粉丝规则
     */
    public void doFansRule(String appId,String dykId){
        if(tagDao.selectSystagCount(appId)==0) {
            if(tagDao.selectSystagCount(dykId)==0) {
                String tagConfig0 = configDao.selectVal("sys_tag_rule0");
                String tagConfig1 = configDao.selectVal("sys_tag_rule1");
                String tagConfig2 = configDao.selectVal("sys_tag_rule2");
                saveFansRule(tagConfig0.split(","), 0, appId);
                saveFansRule(tagConfig1.split(","), 1, appId);
                saveFansRule(tagConfig2.split(","), 2, appId);
            }else{
                //同步
                tagDao.updateAppIdFrom(appId,dykId);
                tagGroupDao.updateAppIdFrom(appId,dykId);
                tagRuleDao.updateAppIdFrom(appId,dykId);
            }
        }
    }

    private void saveFansRule(String[] tagConfigs,int type,String appId){
        for (int i=0;i<tagConfigs.length;i++) {
            String[] kv = tagConfigs[i].split("-");
            TTag tag = new TTag();
            tag.setAppid(appId);
            tag.setTitle(kv[0]);
            tag.setGroupid(0);
            tag.setRuletype(type);
            tagDao.insertSelective(tag);
            TTagRule tagRule = new TTagRule();
            tagRule.setAppid(appId);
            tagRule.setSerialnum(i+1);
            tagRule.setTagid(tag.getId());
            tagRule.setThannum(Integer.parseInt(kv[1]));
            tagRule.setType(type);
            tagRuleDao.insertSelective(tagRule);
        }
    }

    public String saveSubscr(String dykId,String appId){
        //获取公众号信息
        JSONObject obj = new JSONObject();
        obj.put("component_appid",Constants.OPENAPPID);
        obj.put("authorizer_appid",appId);
        String res = HttpConnUtil.doPostJson(Constants.BASEURL+"component/api_get_authorizer_info?component_access_token="+getOpenToken(),obj.toString());
        logger.info(res);
        JSONObject jsonRes2 = JSONObject.fromObject(res);
        JSONObject authorizerInfo = jsonRes2.getJSONObject("authorizer_info");
        int serviceType =authorizerInfo.getJSONObject("service_type_info").getInt("id");
        int verifyType = authorizerInfo.getJSONObject("verify_type_info").getInt("id");
        if(serviceType<2)
           return "只支持已认证服务号授权绑定";
        if(verifyType!=0)
           return "只支持已认证服务号授权绑定";
        JSONObject authorizationInfo = jsonRes2.getJSONObject("authorization_info");
        StringBuilder funcBuilder = new StringBuilder();
        String funcStr = "";
        JSONArray funcInfos = authorizationInfo.getJSONArray("func_info");
        for (int i = 0; i < funcInfos.size(); i++) {
            JSONObject funcObj = funcInfos.getJSONObject(i);
            funcBuilder.append(","+funcObj.getJSONObject("funcscope_category").getInt("id"));
        }
        if(funcBuilder.length()>0)
            funcStr = funcBuilder.toString().substring(1);
        //保存公众号信息
        TSubscr tSubscr = new TSubscr();
        tSubscr.setAppid(appId);
        tSubscr.setNickname(authorizerInfo.getString("nick_name"));
        if(authorizerInfo.containsKey("head_img"))
        tSubscr.setHeadimg(authorizerInfo.getString("head_img"));
        if(authorizerInfo.containsKey("alias"))
        tSubscr.setAlias(authorizerInfo.getString("alias"));
        if(authorizerInfo.containsKey("business_info"))
        tSubscr.setBusinessinfo(authorizerInfo.getJSONObject("business_info").toString());
        if(authorizerInfo.containsKey("principal_name"))
        tSubscr.setPrincipalname(authorizerInfo.getString("principal_name"));
        //更新二维码图片
        String qrcode = authorizerInfo.getString("qrcode_url");
        try {
            qrcode = FileUpOrDwUtil.uploadNetStream(HttpConnUtil.getStreamFromNetByUrl(qrcode), "jpg/" + UUID.randomUUID().toString() + ".jpg");
        }catch (Exception e){}
        tSubscr.setQrcodeurl(qrcode);
        tSubscr.setRefreshtoken(authorizationInfo.getString("authorizer_refresh_token"));
        tSubscr.setServicetype(serviceType);
        tSubscr.setVerifytype(verifyType);
        tSubscr.setWxid(authorizerInfo.getString("user_name"));
        tSubscr.setCreatetime(new Date());
        tSubscr.setFuncinfo(funcStr);
        tSubscr.setState(1);
        tSubscr.setYoukeid(dykId);
        if(subscrDao.selectCount(appId)>0)
            subscrDao.updateByPrimaryKeySelective(tSubscr);
        else {
            subscrDao.insertSelective(tSubscr);
            TSubscrConfig config = new TSubscrConfig();
            config.setAppid(appId);
            subscrConfigDao.insertSelective(config);
            //初始化公众号短信模板
            InitCodeMessage codeMessage = new InitCodeMessage(appId, ApiCodeConstant.DEFAULT_CODE_MSG,tSubscr.getNickname());
            queueSender.send("initCodeTemp.queue",codeMessage);
        }
        return null;
    }

    public void updateSubscrState(String appId, int state) {
        subscrDao.updateState(appId,state);
    }

    private String getVerifyTicket(){
        //return RedisSlaveUtil.get(VERIFYTICKETKEY).toString();
        return configDao.selectByPrimaryKey("component_verify_ticket").getVal();
    }

    public void saveVerifyTicket(String ticket) {
        //RedisUtil.set(VERIFYTICKETKEY,ticket);
        configDao.updateConfig("component_verify_ticket",ticket);
    }

    public String getToken(String appId) {
        String key = ACCESSTOKENKEY+appId;
        String val = (String) RedisSlaveUtil.get(key);
        if(empty(val))
        {
            JSONObject obj = new JSONObject();
            obj.put("component_appid",Constants.OPENAPPID);
            obj.put("authorizer_appid",appId);
            obj.put("authorizer_refresh_token",subscrDao.selectRefreshToken(appId));
            String res = HttpConnUtil.doPostJson(Constants.BASEURL+"component/api_authorizer_token?component_access_token="+getOpenToken(),obj.toString());
            WxCurlUtil.ret(res);
            JSONObject jsonRes = JSONObject.fromObject(res);
            val = jsonRes.getString("authorizer_access_token");
            RedisUtil.set(key,val,Constants.EFFECTIVE_TIME);
            //保存refreshToken
            String refreshToken = jsonRes.getString("authorizer_refresh_token");
            subscrDao.updateRefreshToken(appId,refreshToken);
        }
        return val;
    }

    public String getJSTicket(String appId) {
        String key = ACCESSTICKETKEY+appId;
        String val = (String) RedisSlaveUtil.get(key);
        if(empty(val))
        {
            String res = HttpConnUtil.doHttpRequest(Constants.BASEURL+"ticket/getticket?access_token="+getToken(appId)+"&type=jsapi");
            JSONObject jsonRes = JSONObject.fromObject(res);
            val = jsonRes.getString("ticket");
            RedisUtil.set(key,val,Constants.EFFECTIVE_TIME);
        }
        return val;
    }


    public String getShortUrl(String appId, String longUrl) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("action","long2short");
        jsonObj.put("long_url",longUrl);
        String res = HttpConnUtil.doPostJson(Constants.BASEURL+"shorturl?access_token="+getToken(appId),jsonObj.toString());
        WxCurlUtil.ret(res);
        JSONObject jsonRes = JSONObject.fromObject(res);
        return jsonRes.getString("short_url");
    }

    public String getQrcodeTmp(String appId, String sceneType, String sceneStr, int expire) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("expire_seconds",expire);
        jsonObj.put("action_name","QR_STR_SCENE");
        jsonObj.put("action_info",JSONObject.fromObject("{\"scene\": {\"scene_str\": \""+sceneType+sceneStr+"\"}}"));
        String res = HttpConnUtil.doPostJson(Constants.BASEURL+"qrcode/create?access_token="+getToken(appId),jsonObj.toString());
        WxCurlUtil.ret(res);
        JSONObject jsonRes = JSONObject.fromObject(res);
        return jsonRes.getString("url");
    }

    public String getQrcode(String appId, String sceneType, String sceneStr) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("action_name","QR_LIMIT_STR_SCENE");
        jsonObj.put("action_info",JSONObject.fromObject("{\"scene\": {\"scene_str\": \""+sceneType+sceneStr+"\"}}"));
        String res = HttpConnUtil.doPostJson(Constants.BASEURL+"qrcode/create?access_token="+getToken(appId),jsonObj.toString());
        WxCurlUtil.ret(res);
        JSONObject jsonRes = JSONObject.fromObject(res);
        return jsonRes.getString("url");
    }

    @Override
    public WxSignature getJSSignature(String appId, String url) {
        String nonceStr = "Wm3WZYTPz0wzccnW";
        String timestamp = System.currentTimeMillis()+"";
        String mSign = "jsapi_ticket="+getJSTicket(appId)+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
        String signature = DigestUtils.sha1Hex(mSign);
        WxSignature wxSignature = new WxSignature();
        wxSignature.setAppId(appId);
        wxSignature.setNonceStr(nonceStr);
        wxSignature.setSignature(signature);
        wxSignature.setTimestamp(timestamp);
        return wxSignature;
    }

}
