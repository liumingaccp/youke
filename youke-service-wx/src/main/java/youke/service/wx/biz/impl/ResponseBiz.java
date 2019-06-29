package youke.service.wx.biz.impl;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.dao.vo.TMediaVo;
import youke.common.model.TMaterialFile;
import youke.common.model.TSubscrConfig;
import youke.common.model.TSubscrFans;
import youke.common.utils.EncodeUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.MD5Util;
import youke.facade.wx.util.*;
import youke.facade.wx.vo.material.*;
import youke.facade.wx.vo.message.Article;
import youke.facade.wx.vo.message.BaseMessage;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.service.wx.biz.*;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ResponseBiz extends Base implements IResponseBiz {

    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private IReplyBiz replyBiz;
    @Resource
    private IKefuMessageBiz kefuMessageBiz;
    @Resource
    private IFansBiz fansBiz;
    @Resource
    private IReplyRuleKeyDao replyRuleKeyDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IMaterialImageDao materialImageDao;
    @Resource
    private IMaterialFileDao materialFileDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private ISubscrMenuDao subscrMenuDao;


    public String doTextResp(String openId, String wxId, String content,String appId) {
        //关键词匹配
        TMediaVo mediaVo = replyRuleKeyDao.selectRuleByKey(content,appId);
        if(notEmpty(mediaVo)){
            return doMediaXML(wxId,openId,mediaVo.getMediaType(),mediaVo.getMaterialId(),mediaVo.getContent(),appId);
        }
        TSubscrConfig subscrConfig = subscrConfigDao.selectByPrimaryKey(appId);
        if(notEmpty(subscrConfig)){
//            if(kefuMessageBiz.isOpen(appId)){
//                //发送转客服消息
//                kefuMessageBiz.sendText(appId,openId,"正在为您转人工客服，请耐心等待...");
//                //转客服
//                BaseMessage message = new BaseMessage();
//                message.setToUserName(openId);
//                message.setFromUserName(wxId);
//                message.setCreateTime(new Date().getTime());
//                message.setFuncFlag(0);
//                message.setMsgType("transfer_customer_service");
//                return MessageUtil.messageToXml(message);
//            }else if(subscrConfig.getOpenautoreply()==1){
                //默认回复
                AutoReplyVo replyVo = replyBiz.getAutoReply(appId);
                if(notEmpty(replyVo)){
                    return doMediaXML(wxId,openId,replyVo.getMediaType(),replyVo.getMaterialId(),replyVo.getContent(),appId);
                }
//            }
        }
        return "success";
    }

    public String doSubscribeResp(String openId, String wxId, String eventKey, String appId) {
        //保存粉丝入库
        boolean isnew = fansBiz.saveFans(openId,appId);
        if(appId.equals(Constants.APPID)){
            userDao.updateFollowSubscr(openId,1);
        }
        //获取粉丝信息
        if(notEmpty(eventKey)){
            String qrkey = eventKey.replace("qrscene_","");
            if(qrkey.startsWith(SceneType.MOBILE))
            {
                String mobile = qrkey.replace(SceneType.MOBILE,"");
                subscrFansDao.updateMobile(openId,mobile);
                if(appId.equals(Constants.APPID)){
                    userDao.updateUserOpenId(mobile,openId);
                }
                if(appId.equals(Constants.NRAPPID)){
                    try {
                        Map<String, String> params = new HashMap<>();
                        params.put("username",mobile);
                        params.put("openId",openId);
                        params.put("sign", MD5Util.MD5(mobile+"@33bf3ee8561c02551b1e901843366275"));
                        String res = HttpConnUtil.doHttpRequest("http://www.nuoren.cn/index.php?s=/Nuoren/giveflowapi/giveflow", params);
                        logger.info(res);
                        JSONObject jsonRes = JSONObject.fromObject(res);
                        if(jsonRes.getInt("code")==200){
                            return doMediaXML(wxId,openId,"text",0,jsonRes.getString("message"),appId);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else if(qrkey.startsWith(SceneType.KEY)) {
                String key = qrkey.replace(SceneType.KEY,"");
                TMediaVo mediaVo = replyRuleKeyDao.selectRuleByKey(key, appId);
                if (notEmpty(mediaVo)) {
                    return doMediaXML(wxId, openId, mediaVo.getMediaType(), mediaVo.getMaterialId(), mediaVo.getContent(), appId);
                } else {
                    AutoReplyVo replyVo = replyBiz.getSubscribeReply(appId);
                    if (notEmpty(replyVo)) {
                        return doMediaXML(wxId, openId, replyVo.getMediaType(), replyVo.getMaterialId(), replyVo.getContent(), appId);
                    }
                }
            }else if(qrkey.startsWith(SceneType.FOLLOW)){
                if(isnew) {
                    String key = qrkey.replace(SceneType.FOLLOW, "");
                    long id = Long.parseLong(key);
                    return replyBiz.doFollowActive(id, wxId, openId);
                }else{
                    return doMediaXML(wxId,openId,"text",0,"您已关注了公众号",appId);
                }
            }else if(qrkey.startsWith(SceneType.CUTPRICE)){
                String key = qrkey.replace(SceneType.CUTPRICE, "");
                long id = Long.parseLong(key);
                return replyBiz.doCutpriceActive(id, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.TUAN)){
                return replyBiz.doCollageActive(qrkey, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.MARKET)){
                String key = qrkey.replace(SceneType.MARKET, "");
                int type = Integer.parseInt(key);
                return replyBiz.doMarketActive(type, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.INTEGRAL)){
                String key = qrkey.replace(SceneType.INTEGRAL, "");
                int id = Integer.parseInt(key);
                return replyBiz.doIntegralActive(id, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.TRIAL)){
                String key = qrkey.replace(SceneType.TRIAL, "");
                int id = Integer.parseInt(key);
                return replyBiz.doTrialActive(id, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.REBATE)){
                String key = qrkey.replace(SceneType.REBATE, "");
                int id = Integer.parseInt(key);
                return replyBiz.doRebateActive(id, wxId, openId,appId);
            }else if(qrkey.startsWith(SceneType.TAOKE)){
                String key = qrkey.replace(SceneType.TAOKE, "");
                int id = Integer.parseInt(key);
                return replyBiz.doTaokeActive(id, wxId, openId,appId);
            }
        }else{
            AutoReplyVo replyVo = replyBiz.getSubscribeReply(appId);
            if(notEmpty(replyVo)){
                return doMediaXML(wxId,openId,replyVo.getMediaType(),replyVo.getMaterialId(),replyVo.getContent(),appId);
            }
        }
        return "success";
    }

    public void doUnSubscribeResp(String openId, String wxId,String appId) {
        TSubscrFans subscrFans = new TSubscrFans();
        subscrFans.setOpenid(openId);
        subscrFans.setSubstate(1);
        subscrFansDao.updateByPrimaryKeySelective(subscrFans);
        if(appId.equals(Constants.APPID)){
           userDao.updateFollowSubscr(openId,2);
        }
    }

    public String doScanResp(String openId, String wxId, String eventKey,String appId) {
        if(notEmpty(eventKey)){
           return doSubscribeResp(openId,wxId,"qrscene_"+eventKey,appId);
        }
        return "success";
    }

    public String doClickResp(String openId, String wxId, String eventKey,String appId) {
        Integer menuId = Integer.parseInt(eventKey);
        TMediaVo mediaVo = subscrMenuDao.selectTMediaVo(menuId);
        return doMediaXML(wxId,openId,mediaVo.getMediaType(),mediaVo.getMaterialId(),mediaVo.getContent(),appId);
    }

    /**
     * 内部封装素材逻辑
     * @param wxId
     * @param openId
     * @param mediaType
     * @param materialId
     * @param content
     * @return
     */
    private String doMediaXML(String wxId,String openId,String mediaType, Integer materialId,String content, String appId){
        if(mediaType.equals(MediaType.TEXT)){
            return MessageResponse.getTextMessage(wxId,openId,content);
        }
        if(mediaType.equals(MediaType.IMG)){
            String mediaId = materialImageDao.selectMediaIdById(materialId);
            return MessageResponse.getImageMessage(wxId,openId,mediaId);
        }
        if(mediaType.equals(MediaType.VOICE)){
            String mediaId = materialFileDao.selectMediaIdById(materialId);
            return MessageResponse.getVoiceMessage(wxId,openId,mediaId);
        }
        if(mediaType.equals(MediaType.VIDEO)){
            TMaterialFile materialFile = materialFileDao.selectByPrimaryKey(materialId);
            return MessageResponse.getVideoMessage(wxId,openId,materialFile.getMediaid(),materialFile.getTitle(),materialFile.getIntroduction());
        }
        if(mediaType.equals(MediaType.NUSIC)){
            TMaterialFile materialFile = materialFileDao.selectByPrimaryKey(materialId);
            return MessageResponse.getMusicMessage(wxId,openId,materialFile.getTitle(),materialFile.getIntroduction(),materialFile.getUrl(),materialFile.getMediaid());
        }
        if(mediaType.equals(MediaType.NEWS)){
            NewsTreeVo treeVo = materialBiz.getNews(materialId, appId);
            List<Article> articleList = new ArrayList<Article>();
            articleList.add(new Article(treeVo.getNews().getTitle(),treeVo.getNews().getIntro(),treeVo.getNews().getWxThumbUrl(),treeVo.getNews().getUrl()));
            if(notEmpty(treeVo.getSubNews())&&treeVo.getSubNews().size()>0)
            {
                for (NewsVo newsVo:treeVo.getSubNews()) {
                    articleList.add(new Article(newsVo.getTitle(),newsVo.getIntro(),newsVo.getWxThumbUrl(),newsVo.getUrl()));
                }
            }
            return MessageResponse.getNewsMessage(wxId,openId,articleList);
        }
        if(mediaType.equals(MediaType.SYSNEWS)){
            SysNewsTreeVo treeVo = materialBiz.getSysnews(materialId, appId);
            List<Article> articleList = new ArrayList<Article>();
            articleList.add(new Article(treeVo.getNews().getTitle(),treeVo.getNews().getDescription(),treeVo.getNews().getPicUrl(),treeVo.getNews().getUrl()));
            if(notEmpty(treeVo.getSubNews())&&treeVo.getSubNews().size()>0)
            {
                for (SysnewsVo newsVo:treeVo.getSubNews()) {
                    articleList.add(new Article(newsVo.getTitle(),newsVo.getDescription(),newsVo.getPicUrl(),newsVo.getUrl()));
                }
            }
            return MessageResponse.getNewsMessage(wxId,openId,articleList);
        }
        return "";
    }

    public static void main(String[] args) {
        String m = "\u7b7e\u540d\u9519\u8bef";
        System.out.println(EncodeUtil.urlDecode(m));

    }

}
