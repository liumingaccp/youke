package youke.core.scheduler.service;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.model.vo.param.KeyValVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.core.scheduler.utils.QueueSender;
import youke.common.queue.message.WxExpireMessage;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.mass.vo.ExpireMsgVo;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class YoukeService extends Base {

    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IOpenDiscountDao openDiscountDao;
    @Resource
    private ISysMessageDao sysMessageDao;
    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IWeixinMessageService weixinMessageService;
    @Resource
    private IMobmsgService mobmsgService;
    @Resource
    private QueueSender queueSender;

    /**
     * 实时更新账户过期状态
     */
    public void doYoukeState(){
        if(youkeDao.updateExpireState()>0){
           List<String> youkeIds = youkeDao.selectExpireIds(DateUtil.getDate());
           if(youkeIds!=null) {
               for (String youkeId : youkeIds) {
                   sysMessageDao.addMessage("会员过期提醒","你好，你的账户会员已欠费过期，请及时续费。",youkeId);
               }
           }
        }
        if(shopDao.updateExpireState()>0){
            List<String> youkeIds = shopDao.selectExpireYoukeIds(DateUtil.getDate("yyyy-MM-dd HH:mm:00"),DateUtil.getDate("yyyy-MM-dd HH:mm:59"));
            if(youkeIds!=null) {
                for (String youkeId : youkeIds) {
                    sysMessageDao.addMessage("店铺授权过期提醒","你好，你的店铺授权已过期，请及时订购授权。",youkeId);
                }
            }
        }
        openDiscountDao.updateExpireState();
    }

    public Date getLastDate() {
        Object obj = RedisSlaveUtil.get("SYNC-YOUKESUCC-TIME");
            if(obj==null) {
            Date newDate = new Date(1527782400000L);
            RedisUtil.set("SYNC-YOUKESUCC-TIME",newDate);
            return newDate;
        }
        return (Date)obj;
    }

    public void setLastDate(Date date) {
        RedisUtil.set("SYNC-YOUKESUCC-TIME",date);
    }

    /**
     * 账户审核通过推送
     */
    public void doYoukeSuccPush(){
        List<Map> maps = youkeDao.selectFirstSuccUser(getLastDate());

        if(maps!=null&&maps.size()>0){
            setLastDate((Date)maps.get(0).get("createTime"));
            List<String> mobiles = new ArrayList<>();
            for (Map map:maps) {
                mobiles.add(map.get("mobile").toString());
                if(notEmpty(map.get("followOpenId")))
                    try {
                        queueSender.send("wxsendyoukesucc.queue",map.get("followOpenId").toString());
                    } catch (Exception e) {}
            }
            mobmsgService.sendYoukeSucc(mobiles);
         }
    }

    /**
     * 账户即将过期推送
     */
    public void doExpirePush(){
        Date curDate = new Date();
        //查找账户过期前N天的账户
        String time15 = DateUtil.formatDate(DateUtil.addDays(curDate,15));
        String time07 = DateUtil.formatDate(DateUtil.addDays(curDate,7));
        String time03 = DateUtil.formatDate(DateUtil.addDays(curDate,3));

        List<ExpireMsgVo> expireMsgVos = new ArrayList<>();
        List<WxExpireMessage> wxExpireVos = new ArrayList<>();

        List<Map> map15 = youkeDao.selectExpireUser(time15);
        for (int i=0;i<map15.size();i++){
            sysMessageDao.addMessage("会员即将过期提醒","你好，你的账户会员15天后过期，过期时间"+time15+"，请及时处理！", (String) map15.get(i).get("youkeId"));
            expireMsgVos.add(new ExpireMsgVo(map15.get(i).get("mobile").toString(),15,time15,(String)map15.get(i).get("company")));
            if(notEmpty(map15.get(i).get("followOpenId")))
               wxExpireVos.add(new WxExpireMessage(map15.get(i).get("followOpenId").toString(),map15.get(i).get("mobile").toString(),map15.get(i).get("company").toString(),15,time15));
        }
        List<Map> map07 = youkeDao.selectExpireUser(time07);
        for (int i=0;i<map07.size();i++){
            sysMessageDao.addMessage("会员即将过期提醒","你好，你的账户会员7天后过期，过期时间"+time07+"，请及时处理！", (String) map07.get(i).get("youkeId"));
            expireMsgVos.add(new ExpireMsgVo(map07.get(i).get("mobile").toString(),7,time07,(String)map07.get(i).get("company")));
            if(notEmpty(map07.get(i).get("followOpenId")))
               wxExpireVos.add(new WxExpireMessage(map07.get(i).get("followOpenId").toString(),map07.get(i).get("mobile").toString(),map07.get(i).get("company").toString(),7,time07));
        }
        List<Map> map03 = youkeDao.selectExpireUser(time03);
        for (int i=0;i<map03.size();i++){
            sysMessageDao.addMessage("会员即将过期提醒","你好，你的账户会员3天后过期，过期时间"+time03+"，请及时处理！", (String) map03.get(i).get("youkeId"));
            expireMsgVos.add(new ExpireMsgVo(map03.get(i).get("mobile").toString(),3,time03,(String)map03.get(i).get("company")));
            if(notEmpty(map03.get(i).get("followOpenId")))
               wxExpireVos.add(new WxExpireMessage(map03.get(i).get("followOpenId").toString(),map15.get(i).get("mobile").toString(),map03.get(i).get("company").toString(),3,time03));
        }
        //短信推送
        if(expireMsgVos!=null&&expireMsgVos.size()>0) {
            mobmsgService.sendExpireMsg(expireMsgVos,0);
        }
        //公众号推送
        if(wxExpireVos!=null&&wxExpireVos.size()>0) {
            for (WxExpireMessage expireVo : wxExpireVos) {
                try {
                    queueSender.send("wxsendsysexpire.queue",expireVo);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 店铺即将过期推送
     */
    public void doShopExpirePush(){
        Date curDate = new Date();
        //查找账户过期前N天的账户
        String time15 = DateUtil.formatDate(DateUtil.addDays(curDate,15));
        String time07 = DateUtil.formatDate(DateUtil.addDays(curDate,7));
        String time03 = DateUtil.formatDate(DateUtil.addDays(curDate,3));

        List<ExpireMsgVo> expireMsgVos = new ArrayList<>();
        List<WxExpireMessage> wxExpireVos = new ArrayList<>();

        try {
            List<Map> map15 = shopDao.selectExpireUser(DateUtil.parseDate(time15+" 00:00:00"),DateUtil.parseDate(time15+" 23:59:59"));
            for (int i = 0; i < map15.size(); i++) {
                sysMessageDao.addMessage("店铺订购即将过期提醒","你好，你的店铺授权15天后过期，过期时间"+time03+"，请及时处理！", (String) map15.get(i).get("youkeId"));
                expireMsgVos.add(new ExpireMsgVo((String)map15.get(i).get("mobile"), 15, time15,(String)map15.get(i).get("title")));
                if (notEmpty(map15.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map15.get(i).get("followOpenId").toString(), map15.get(i).get("mobile").toString(), map15.get(i).get("title").toString(), 15, time15));
            }
            List<Map> map07 = shopDao.selectExpireUser(DateUtil.parseDate(time07+" 00:00:00"),DateUtil.parseDate(time07+" 23:59:59"));
            for (int i = 0; i < map07.size(); i++) {
                sysMessageDao.addMessage("店铺订购即将过期提醒","你好，你的店铺授权7天后过期，过期时间"+time03+"，请及时处理！", (String) map07.get(i).get("youkeId"));

                expireMsgVos.add(new ExpireMsgVo((String)map07.get(i).get("mobile"), 7, time07,(String)map07.get(i).get("title")));
                if (notEmpty(map07.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map07.get(i).get("followOpenId").toString(), map07.get(i).get("mobile").toString(), map07.get(i).get("title").toString(), 7, time07));
            }
            List<Map> map03 = shopDao.selectExpireUser(DateUtil.parseDate(time03+" 00:00:00"),DateUtil.parseDate(time03+" 23:59:59"));
            for (int i = 0; i < map03.size(); i++) {
                sysMessageDao.addMessage("店铺订购即将过期提醒","你好，你的店铺授权3天后过期，过期时间"+time03+"，请及时处理！", (String) map03.get(i).get("youkeId"));
                expireMsgVos.add(new ExpireMsgVo((String)map03.get(i).get("mobile"), 3, time03,(String)map03.get(i).get("title")));
                if (notEmpty(map03.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map03.get(i).get("followOpenId").toString(), map15.get(i).get("mobile").toString(), map03.get(i).get("title").toString(), 3, time03));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //短信推送
        if(expireMsgVos!=null&&expireMsgVos.size()>0) {
            mobmsgService.sendExpireMsg(expireMsgVos,1);
        }
        //公众号推送
        if(wxExpireVos!=null&&wxExpireVos.size()>0) {
            for (WxExpireMessage expireVo : wxExpireVos) {
                try {
                    queueSender.send("wxsendsyserexpire.queue",expireVo);
                    } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 证书即将过期推送
     */
    public void doCertExpirePush(){
        Date curDate = new Date();
        //查找账户过期前N天的账户
        String time15 = DateUtil.formatDate(DateUtil.addDays(curDate,15));
        String time07 = DateUtil.formatDate(DateUtil.addDays(curDate,7));
        String time03 = DateUtil.formatDate(DateUtil.addDays(curDate,3));

        List<WxExpireMessage> wxExpireVos = new ArrayList<>();

        try {
            List<Map> map15 = subscrConfigDao.selectExpireCert(time15);
            for (int i = 0; i < map15.size(); i++) {
                sysMessageDao.addMessage("支付证书即将过期提醒","你好，你的支付证书15天后过期，过期时间"+time15+"，请及时处理！", (String) map15.get(i).get("youkeId"));
                if (notEmpty(map15.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map15.get(i).get("followOpenId").toString(), map15.get(i).get("payNumber").toString(), map15.get(i).get("title").toString(), 15, time15));
            }
            List<Map> map07 = subscrConfigDao.selectExpireCert(time07);
            for (int i = 0; i < map07.size(); i++) {
                sysMessageDao.addMessage("支付证书即将过期提醒","你好，你的支付证书7天后过期，过期时间"+time07+"，请及时处理！", (String) map07.get(i).get("youkeId"));
                if (notEmpty(map07.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map07.get(i).get("followOpenId").toString(), map07.get(i).get("payNumber").toString(), map07.get(i).get("title").toString(), 7, time07));
            }
            List<Map> map03 = subscrConfigDao.selectExpireCert(time03);
            for (int i = 0; i < map03.size(); i++) {
                sysMessageDao.addMessage("支付证书即将过期提醒","你好，你的支付证书3天后过期，过期时间"+time03+"，请及时处理！", (String) map03.get(i).get("youkeId"));
                if (notEmpty(map03.get(i).get("followOpenId")))
                    wxExpireVos.add(new WxExpireMessage(map03.get(i).get("followOpenId").toString(), map15.get(i).get("payNumber").toString(), map03.get(i).get("title").toString(), 3, time03));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //公众号推送
        if(wxExpireVos!=null&&wxExpireVos.size()>0) {
            for (WxExpireMessage expireVo : wxExpireVos) {
                try {
                    queueSender.send("wxsendsyscert.queue",expireVo);
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 短信余额不足每天推送
     */
    public void doMobCodePush(){
        List<Map> maps = mobcodeDao.selectMonitorCount(ApiCodeConstant.MONITOR_MAX_MOBCODE);
        List<KeyValVo> mobVos = new ArrayList<>();
        if(maps!=null&&maps.size()>0){
            for (Map map:maps){
               String mobile = map.get("mobile").toString();
               String count = map.get("count").toString();
               mobVos.add(new KeyValVo(mobile,count));
               if(notEmpty(map.get("followOpenId"))){
                   String jmobile = mobile.replace(mobile.substring(3,6),"****");
                   try {
                       weixinMessageService.sendSysBalance((String) map.get("followOpenId"), "您的短信账户余额不足，为避免影响短信功能使用，请及时充值", jmobile, count + "条");
                   }catch (Exception e){
                       logger.error(e.getMessage());
                   }
                   sysMessageDao.addMessage("短信余额不足提醒","您的短信账户余额不足，仅剩"+count+"条，为避免影响短信功能使用，请及时充值。",(String) map.get("youkeId"));
               }
            }
            try {
                mobmsgService.sendMobCodeCountMsg(mobVos);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
         }
    }

}
