package youke.service.wx.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.page.PageModel;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.utils.StringUtil;
import youke.facade.wx.util.MessageResponse;
import youke.facade.wx.util.SceneType;
import youke.facade.wx.vo.message.Article;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.facade.wx.vo.reply.KeyReplyVo;
import youke.service.wx.biz.IKefuMessageBiz;
import youke.service.wx.biz.IReplyBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReplyBiz implements IReplyBiz {

    @Resource
    private IKefuMessageBiz kefuMessageBiz;
    @Resource
    private QueueSender queueSender;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IReplyAutoDao replyAutoDao;
    @Resource
    private IReplyRuleDao replyRuleDao;
    @Resource
    private IReplyRuleKeyDao replyRuleKeyDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IFollowActiveDao followActiveDao;
    @Resource
    private IFollowActivePosterDao followActivePosterDao;
    @Resource
    private IFollowActiveOrderDao followActiveOrderDao;
    @Resource
    private IIntegralActiveDao integralActiveDao;
    @Resource
    private ITrialActiveDao trialActiveDao;
    @Resource
    private IRebateActiveDao rebateActiveDao;
    @Resource
    private ITaokeActiveDao taokeActiveDao;

    public void saveSubscribeReply(String content, String mediaType, int materialId, String appId) {
        TReplyAuto info = new TReplyAuto();
        info.setAppid(appId);
        info.setContent(content);
        info.setCreatetime(new Date());
        info.setMaterialid(materialId);
        info.setMediatype(mediaType);
        info.setUpdatetime(new Date());
        info.setType(0);
        replyAutoDao.deleteByAppid(appId, 0);
        replyAutoDao.insertSelective(info);
    }

    public void delSubscribeReply(String appId) {
        replyAutoDao.deleteByAppid(appId, 0);
    }

    public AutoReplyVo getSubscribeReply(String appId) {
        TReplyAuto reply = replyAutoDao.selectByAppid(appId, 0);
        if(reply==null)
            return null;
        return new AutoReplyVo(reply.getContent(), reply.getMediatype(), reply.getMaterialid());
    }

    public void saveAutoReply(String content, String mediaType, int materialId, String appId) {

        //先删除
        replyAutoDao.deleteByAppid(appId, 1);

        TReplyAuto info = new TReplyAuto();
        info.setAppid(appId);
        info.setContent(content);
        info.setCreatetime(new Date());
        info.setMaterialid(materialId);
        info.setMediatype(mediaType);
        info.setType(1);
        info.setUpdatetime(new Date());
        replyAutoDao.insertSelective(info);
    }

    public void delAutoReply(String appId) {
        replyAutoDao.deleteByAppid(appId, 1);
    }

    public AutoReplyVo getAutoReply(String appId) {
        TReplyAuto reply = replyAutoDao.selectByAppid(appId, 1);
        if(reply==null)
            return null;
        return new AutoReplyVo(reply.getContent(), reply.getMediatype(), reply.getMaterialid());
    }

    public Integer addKeyReply(String title, List<String> keys, String mediaType, String content, int materialId,Integer keyMatch, String appId) {

        /*if (hasReplyRule(appId, title)) {
            throw new BusinessException("已存在[" + title + "]标题");
        }*/
        List<String> echoKeys = new ArrayList<String>();
        for (String key : keys) {
            if (hasRuleKey(appId, key)) {
                throw new BusinessException("["+ key +"]" + "已存在");
            }else{
                if(!echoKeys.contains(key)){
                    echoKeys.add(key);
                }
            }
        }
        if (echoKeys.size() <=  0) {
            throw new BusinessException("传入的关键字不能为空");
        }

        //保存规则
        TReplyRule rule = new TReplyRule();
        rule.setTitle(title);
        rule.setAppid(appId);
        rule.setContent(content);
        rule.setCreatetime(new Date());
        rule.setKeymatch(0);
        rule.setMaterialid(materialId);
        rule.setMediatype(mediaType);
        rule.setRank(0);
        rule.setState(0);
        rule.setUpdatetime(new Date());

        replyRuleDao.insertSelective(rule);
        int ruleId = rule.getId();

        for (String key : keys) {
            //批量插入key
            TReplyRuleKey ruleKey = new TReplyRuleKey();
            ruleKey.setAppid(appId);
            ruleKey.setRuleid(ruleId);
            ruleKey.setKey(key);
            replyRuleKeyDao.insertSelective(ruleKey);
        }

        return ruleId;
    }
    public void saveKeyReply(Integer id, String title, List<String> keys, String mediaType, String content, int materialId, Integer keyMatch, String appId) {
       /* if (hasReplyRule(appId, title)) {
            throw new BusinessException("已存在[" + title + "]标题");
        }*/
        replyRuleKeyDao.deleteByRuleId(id, appId);

        List<String> echoKeys = new ArrayList<String>();
        for (String key : keys) {
            if (hasRuleKey(appId, key)) {
                throw new BusinessException("["+ key +"]" + "已存在");
            }else{
                if(!echoKeys.contains(key)){
                    echoKeys.add(key);
                }
            }
        }
        if (echoKeys.size() <= 0) {
            throw new BusinessException("传入的关键字不能为空");
        }

        TReplyRule rule = replyRuleDao.selectByPrimaryKey(id);
        rule.setTitle(title);
        rule.setMediatype(mediaType);
        rule.setMaterialid(materialId);
        rule.setContent(content);
        rule.setKeymatch(keyMatch);
        rule.setUpdatetime(new Date());

        replyRuleDao.updateByPrimaryKeySelective(rule);

        for (String key : keys) {
            //批量插入key
            TReplyRuleKey ruleKey = new TReplyRuleKey();
            ruleKey.setAppid(appId);
            ruleKey.setRuleid(id);
            ruleKey.setKey(key);
            replyRuleKeyDao.insertSelective(ruleKey);
        }

    }

    public void delKeyReply(Integer id, String appId) {
        replyRuleDao.deleteById(id, appId);
        replyRuleKeyDao.deleteByRuleId(id, appId);
    }

    public PageInfo<KeyReplyVo> getKeyReplys(String title, String key, String appId, int page, int limit) {
        List<KeyReplyVo> list = new ArrayList<KeyReplyVo>();
        if(key == null){
            key = "%%";
        }else {
            key = "%" + key + "%";
        }
        if(title == null){
            title = "%%";
        }else{
            title = "%"+ title +"%";
        }
        PageHelper.startPage(page,limit);
        List<TReplyRule> rules = replyRuleDao.selectLikeTitle(appId, title, key);
        PageModel<KeyReplyVo> keyReplyVos = new PageModel<KeyReplyVo>((Page) rules);
        for(TReplyRule rule : rules){
            KeyReplyVo vo = new KeyReplyVo();
            vo.setId(rule.getId());
            vo.setAutoType(rule.getMediatype());
            vo.setContent(rule.getContent());
            vo.setTitle(rule.getTitle());
            vo.setMediaType(rule.getMediatype());
            vo.setMaterialId(rule.getMaterialid());
            vo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            List<String> keys = replyRuleKeyDao.selectKeysByRuleId(rule.getId());
            vo.setKeys(keys);
            keyReplyVos.add(vo);
        }

        return new PageInfo<KeyReplyVo>(keyReplyVos);
    }

    public void do_on_ofReply(int type, String appId) {
        subscrConfigDao.updateByAppid(type, appId);
    }

    @Override
    public String doFollowActive(long id,String wxId, String openId) {
        TFollowActivePoster poster = followActivePosterDao.selectByPrimaryKey(id);
        String msg = "本次推广关注活动已过期！";
        if(poster==null)
            return MessageResponse.getTextMessage(wxId,openId,msg);
        TFollowActive active = followActiveDao.selectByPrimaryKey(poster.getActiveid());
        if(active==null)
            return MessageResponse.getTextMessage(wxId,openId,msg);
        else if(active.getState()==2)
            return MessageResponse.getTextMessage(wxId,openId,msg);
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(poster.getOpenid());
        //插入记录
        TFollowActiveOrder activeOrder = new TFollowActiveOrder();
        activeOrder.setActiveid(poster.getActiveid());
        activeOrder.setAppid(poster.getAppid());
        activeOrder.setBackmoney(active.getBackMoney());
        activeOrder.setCreatetime(new Date());
        activeOrder.setFollowopenid(openId);
        activeOrder.setFollowname(subscrFansDao.selectNickname(openId));
        activeOrder.setOpenid(subscrFans.getOpenid());
        activeOrder.setMobile(subscrFans.getMobile());
        activeOrder.setWxfansname(subscrFans.getNickname());
        activeOrder.setTitle(active.getTitle());
        activeOrder.setYoukeid(active.getYoukeid());
        followActiveOrderDao.insertSelective(activeOrder);
        //推送
        String noticemsg = active.getNoticemsg().replace("{微信昵称}",activeOrder.getFollowname()).replace("{本次佣金}", StringUtil.FenToYuan(active.getBackMoney())+"元");
        String followmsg = active.getFollowmsg().replace("{微信昵称}",subscrFans.getNickname());
        kefuMessageBiz.sendText(poster.getAppid(),poster.getOpenid(),noticemsg);
        String resXML = MessageResponse.getTextMessage(wxId,openId,followmsg);
        if(active.getBackMoney()>0){
            ActiveMassMessage massMessage = new ActiveMassMessage();
            massMessage.setRecordId(activeOrder.getId());
            massMessage.setAppId(poster.getAppid());
            massMessage.setComeType(ComeType.TUI_GUANG_GUAN_ZHU);
            massMessage.setMoney(active.getBackMoney());
            massMessage.setOpenId(poster.getOpenid());
            massMessage.setYoukeId(poster.getYoukeid());
            massMessage.setTitle(ComeType.COME_TYPE.get(ComeType.TUI_GUANG_GUAN_ZHU));
            queueSender.send("activemass.queue",massMessage);
        }else{
            followActiveOrderDao.updateState(activeOrder.getId(),3);
        }
        return resXML;
    }

    public boolean hasReplyRule(String appid, String rule) {
        String ret = replyRuleDao.hasReplyRule(appid, rule);
        if (ret != null) {
            return true;
        }
        return false;
    }

    public boolean hasRuleKey(String appid, String key) {
        String ret = replyRuleKeyDao.hasReplyRuleKey(appid, key);
        if (ret != null) {
            return true;
        }
        return false;
    }

    @Override
    public KeyReplyVo getKeyReply(int ruleId) {
        TReplyRule rule = replyRuleDao.selectByPrimaryKey(ruleId);
        if(rule !=  null){
            KeyReplyVo vo = new KeyReplyVo();
            vo.setId(rule.getId());
            vo.setAutoType(rule.getMediatype());
            vo.setContent(rule.getContent());
            vo.setTitle(rule.getTitle());
            vo.setMediaType(rule.getMediatype());
            vo.setMaterialId(rule.getMaterialid());
            vo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            List<String> keys = replyRuleKeyDao.selectKeysByRuleId(rule.getId());
            vo.setKeys(keys);
            return vo;
        }
        return null;
    }

    @Override
    public String doCutpriceActive(long id, String wxId, String openId,String appId) {
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("您正在帮助好友砍价");
        article.setPicUrl(ComeType.COME_TYPE_PIC.get(ComeType.KAN_JIA));
        article.setUrl(ComeType.COME_TYPE_URL.get(ComeType.KAN_JIA).replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doCollageActive(String key, String wxId, String openId,String appId) {
        String url = "";
        if(key.startsWith("tuan-go")){
            url = ComeType.GO_PIN_TUAN_URL;
        }else if(key.startsWith("tuan-do")){
            url = ComeType.DO_PIN_TUAN_URL;
        }else{
            url = ComeType.MY_PIN_TUAN_URL;
        }
        long id = Long.parseLong(key.split("_")[1]);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【拼团活动】点击继续参与");
        article.setPicUrl(ComeType.COME_TYPE_PIC.get(ComeType.PIN_TUAN));
        article.setUrl(url.replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doMarketActive(int type, String wxId, String openId, String appId) {
        int comeType = ComeType.getComeTypeFromActiveType(type);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【"+ComeType.COME_TYPE.get(comeType)+"】点击继续参与");
        article.setPicUrl(ComeType.COME_TYPE_PIC.get(comeType));
        article.setUrl(ComeType.COME_TYPE_URL.get(comeType).replace("{appId}",appId));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doIntegralActive(int id, String wxId, String openId, String appId) {
        TIntegralActive active = integralActiveDao.selectByPrimaryKey(id);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【积分兑换】点击继续参与");
        article.setPicUrl(active.getCover());
        article.setUrl(ComeType.COME_TYPE_URL.get(ComeType.JI_FEN_DUI_HUAN).replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doTrialActive(int id, String wxId, String openId, String appId) {
        TTrialActive active = trialActiveDao.selectByPrimaryKey(id);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【试用福利】点击继续参与");
        article.setPicUrl(active.getCover());
        article.setUrl(ComeType.COME_TYPE_URL.get(ComeType.SHI_YONG_FU_LI).replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doRebateActive(int id, String wxId, String openId, String appId) {
        TRebateActive active = rebateActiveDao.selectByPrimaryKey(id);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【购物返利】点击继续参与");
        article.setPicUrl(active.getCover());
        article.setUrl(ComeType.COME_TYPE_URL.get(ComeType.GOU_WU_FAN_LI).replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }

    @Override
    public String doTaokeActive(int id, String wxId, String openId, String appId) {
        TTaokeActive active = taokeActiveDao.selectByPrimaryKey(id);
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setTitle("你正在参加【微淘客】点击继续参与");
        article.setPicUrl(active.getCover());
        article.setUrl(ComeType.COME_TYPE_URL.get(ComeType.WEI_TAO_KE).replace("{appId}",appId).replace("{id}",id+""));
        articles.add(article);
        String resXML = MessageResponse.getNewsMessage(wxId,openId,articles);
        return resXML;
    }


}
