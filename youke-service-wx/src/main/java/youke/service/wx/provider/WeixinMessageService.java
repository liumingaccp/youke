package youke.service.wx.provider;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.constants.ApiCodeConstant;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.model.TShopOrder;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.TemplateUtil;
import youke.service.wx.biz.IKefuMessageBiz;
import youke.service.wx.biz.ITemplateBiz;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WeixinMessageService implements IWeixinMessageService {

    @Resource
    private IKefuMessageBiz kefuMessageBiz;

    @Resource
    private ITemplateBiz templateBiz;

    @Resource
    private ISubscrFansDao subscrFansDao;

    @Resource
    private IConfigDao configDao;

    @Resource
    private IShopOrderDao shopOrderDao;

    @Resource
    private IShopDao shopDao;

    @Override
    public void sendText(String appId, String openId, String message) {
        kefuMessageBiz.sendText(appId,openId,message);
    }

    @Override
    public void sendTempIntegralGain(String appId, String openId, int comeType, String title, int integral) {
        String url = configDao.selectVal("h5_myintegral_page").replace("{appId}",appId);
        Map<String,String> map = new HashMap<>();
        map.put("first","参与活动【"+title+"】获得"+integral+"积分");
        map.put("keyword1",subscrFansDao.selectNickname(openId));
        map.put("keyword2", DateUtil.getDateTime());
        map.put("keyword3", ComeType.COME_TYPE.get(comeType));
        map.put("keyword4",integral+"分");
        map.put("keyword5",subscrFansDao.getIntegral(openId)+"分");
        map.put("remark","点击查看积分详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.INTEGRAL_GAIN,url,map);
    }

    @Override
    public void sendTempIntegralConsume(String appId, String openId, int comeType, String title, int integral) {
        String url = configDao.selectVal("h5_myintegral_page").replace("{appId}",appId);
        Map<String,String> map = new HashMap<>();
        map.put("first","参与活动【"+title+"】消耗"+integral+"积分");
        map.put("keyword1",ComeType.COME_TYPE.get(comeType));
        map.put("keyword2",integral+"分");
        map.put("keyword3",subscrFansDao.getIntegral(openId)+"分");
        map.put("remark","点击查看积分详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.INTEGRAL_CONSUME,url,map);
    }

    @Override
    public void sendTempExamineSuccess(String appId, String openId, int comeType, String title) {
        String key = "";
        if(ComeType.isMarketActive(comeType))
            key = "h5_myactive_page";
        else if(comeType==ComeType.JI_FEN_DUI_HUAN)
            key = "h5_myintegral_page";
        else if(comeType==ComeType.GOU_WU_FAN_LI)
            key = "h5_myrebate_page";
        else if(comeType==ComeType.WEI_TAO_KE)
            key = "h5_mytaoke_page";
        else if(comeType==ComeType.TUI_GUANG_GUAN_ZHU)
            key = "h5_myfollow_page";
        else if(comeType==ComeType.SHI_YONG_FU_LI)
            key = "h5_mytrial_page";
        String url = configDao.selectVal(key).replace("{appId}",appId);
        Map<String,String> map = new HashMap<>();
        map.put("first","参与活动【"+title+"】审核通过");
        map.put("keyword1","成功");
        map.put("keyword2", DateUtil.getDateTime());
        map.put("remark","点击查看详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.EXAMINE_SUCCESS,url,map);
    }

    @Override
    public void sendTempExamineFail(String appId, String openId, int comeType, String title, String reason) {
        String key = "";
        if(ComeType.isMarketActive(comeType))
            key = "h5_myactive_page";
        else if(comeType==ComeType.JI_FEN_DUI_HUAN)
            key = "h5_myintegral_page";
        else if(comeType==ComeType.GOU_WU_FAN_LI)
            key = "h5_myrebate_page";
        else if(comeType==ComeType.WEI_TAO_KE)
            key = "h5_mytaoke_page";
        else if(comeType==ComeType.TUI_GUANG_GUAN_ZHU)
            key = "h5_myfollow_page";
        else if(comeType==ComeType.SHI_YONG_FU_LI)
            key = "h5_mytrial_page";
        String url = configDao.selectVal(key).replace("{appId}",appId);
        Map<String,String> map = new HashMap<>();
        map.put("first","参与活动【"+title+"】审核不通过");
        map.put("keyword1","失败");
        map.put("keyword2", reason);
        map.put("remark","点击查看详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.EXAMINE_FAIL,url,map);
    }

    @Override
    public void sendTempOrderReceive(String appId, String openId, String title, String orderno, int type) {
        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderno);
        if(shopOrder!=null) {
            Map<String, String> map = new HashMap<>();
            map.put("first", "商品【"+title+"】已收货");
            map.put("keyword1", type == 0 ? "淘宝订单" : type == 1 ? "天猫订单" : type == 2 ? "京东订单" : "其他订单");
            map.put("keyword2", orderno);
            map.put("keyword3", "-");
            map.put("keyword4", shopOrder.getTitle());
            map.put("keyword5", shopOrder.getNum()+"");
            map.put("remark", "感谢您的使用");
            templateBiz.doSendTemplete(appId, openId, TemplateUtil.ORDER_RECEIVE, null, map);
        }
    }

    @Override
    public void sendTempOrderPay(String appId, String openId, String title, String orderno, int type) {
        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderno);
        if(shopOrder!=null) {
            Map<String, String> map = new HashMap<>();
            map.put("first",  "商品【"+title+"】付款成功");
            map.put("keyword1", orderno);
            map.put("keyword2", "成功");
            map.put("keyword3", DateUtil.formatDateTime(shopOrder.getPaytime()));
            map.put("keyword4", shopDao.selectTitle(shopOrder.getShopid()));
            map.put("keyword5", StringUtil.FenToYuan(shopOrder.getTotalprice())+"元");
            map.put("remark", "感谢您的使用");
            templateBiz.doSendTemplete(appId, openId, TemplateUtil.ORDER_PAY, null, map);
        }
    }

    @Override
    public void sendTempOrderDeliver(String appId, String openId, String title, String orderno, int type) {
        TShopOrder shopOrder = shopOrderDao.selectByOrderno(orderno);
        System.out.println(shopOrder);
        if(shopOrder!=null) {
            Map<String, String> map = new HashMap<>();
            map.put("first", "商品【"+title+"】已发货");
            map.put("keyword1", shopOrder.getTitle());
            map.put("keyword2", "-");
            map.put("keyword3", "-");
            map.put("keyword4", shopOrder.getReceiveaddress());
            map.put("remark", "感谢您的使用");
            System.out.println("开始发送sendTemplete");
            templateBiz.doSendTemplete(appId, openId, TemplateUtil.ORDER_DELIVER, null, map);
        }
    }

    @Override
    public void sendTempSignNotice(String appId, String openId, String title) {
//        String url = configDao.selectVal("h5_myintegral_page").replace("{appId}",appId);
//        Map<String,String> map = new HashMap<>();
//        map.put("first","参与活动【"+title+"】签到成功");
//        map.put("keyword1",subscrFansDao.selectNickname(openId));
//        map.put("keyword2", "每日签到");
//        map.put("keyword3", DateUtil.getDateTime());
//        map.put("remark","点击查看积分详情");
//        templateBiz.doSendTemplete(appId,openId,TemplateUtil.SIGN_NOTICE,url,map);
    }

    @Override
    public void sendTempMoneyGain(String appId, String openId, Integer comeType, String title, Integer money) {
        Map<String,String> map = new HashMap<>();
        map.put("first","参与活动【"+title+"】零钱到账");
        map.put("keyword1",StringUtil.FenToYuan(money)+"元");
        map.put("keyword2", DateUtil.getDateTime());
        map.put("keyword3", ComeType.COME_TYPE.get(comeType));
        map.put("keyword4", "企业付款零钱");
        map.put("remark","感谢您的使用");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.MONEY_GAIN,null,map);
    }

    @Override
    public void sendTempKanJia(String appId, String openId,long orderId, String title, String friendName, String cutPrice, String leftPrice,String dealPrice,String limitTime) {
        Map<String,String> map = new HashMap<>();

        if(leftPrice.equals("0.00"))
           map.put("first","好厉害！你的好友["+friendName+"]已经帮你砍了"+cutPrice+"元,砍价成功,请在["+limitTime+"]内购买该商品并完成付款，否则无法参加砍价返利");
        else
           map.put("first","好厉害！你的好友["+friendName+"]已经帮你砍了"+cutPrice+"元,还需砍"+leftPrice);
        map.put("keyword1",title);
        map.put("keyword2", dealPrice+"元");
        map.put("remark","点击查看详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.HELPER_KANJIA,ComeType.COME_TYPE_URL.get(ComeType.KAN_JIA).replace("{appId}",appId).replace("{id}",orderId+""),map);
    }

    @Override
    public void sendTempPinTuan(String appId, String openId, String title, long tuanId) {
        Map<String,String> map = new HashMap<>();
        map.put("first","你的拼团成功了["+title+"]");
        map.put("keyword1","满员");
        map.put("keyword2", "成功");
        map.put("remark","点击查看详情");
        templateBiz.doSendTemplete(appId,openId,TemplateUtil.HELPER_PINTUAN,ComeType.COME_TYPE_URL.get(ComeType.PIN_TUAN).replace("{appId}",appId).replace("{id}",tuanId+""),map);

    }

    @Override
    public void sendSysOpenAccount(String openId, int vip, int money, String title, String endTime) {
        Map<String,String> map = new HashMap<>();
        map.put("first","恭喜您已成功开通会员！");
        map.put("keyword1", ApiCodeConstant.VIP_TYPE.get(vip));
        map.put("keyword2", title);
        map.put("keyword3", StringUtil.FenToYuan(money)+"元");
        map.put("keyword4", endTime);
        map.put("remark","即日起，即可享受会员特权。");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_OPEN,null,map);
    }

    @Override
    public void sendSysCert(String openId, String title, String payNumber,int lastDay, String endTime) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您好，您的支付证书"+lastDay+"天后到期！");
        map.put("keyword1", title);
        map.put("keyword2", "微信支付证书");
        map.put("keyword3", payNumber);
        map.put("keyword4", endTime);
        map.put("remark","请及时更新证书。");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_CERT,null,map);
    }

    @Override
    public void sendSysExpire(String openId, String title, int lastDay, String endTime) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您好，您的会员账户"+title+"将于"+lastDay+"天后到期！");
        map.put("name", "账户");
        map.put("expDate", endTime);
        map.put("remark","请登录平台,更多-账号管理-账号信息 进行续费操作");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_EXPIRE,null,map);
    }

    @Override
    public void sendSysRenew(String openId, String title, int money, String endTime) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您的会员账户已续费成功");
        map.put("keyword1", title);
        map.put("keyword2", StringUtil.FenToYuan(money)+"元");
        map.put("keyword3", endTime);
        map.put("remark","感谢您的使用");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_RENEW,null,map);
    }

    @Override
    public void sendSysPayFail(String openId, int money, String reason) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您好，您的账户发放返利红包失败");
        map.put("keyword1", "共"+StringUtil.FenToYuan(money)+"元");
        map.put("keyword2", "发放失败");
        map.put("keyword3", reason);
        map.put("remark","请及时跟进处理");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_PAYFAIL,null,map);
    }

    @Override
    public void sendSysRecharge(String openId, String title,String type, int money) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您好，您已成功进行"+type+"充值。");
        map.put("accountType", "充值账户");
        map.put("account", title);
        map.put("amount", StringUtil.FenToYuan(money)+"元");
        map.put("result", "充值成功");
        map.put("remark","感谢您的使用");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_RECHARGE,null,map);
    }

    @Override
    public void sendSysBalance(String openId, String title, String account, String money) {
        Map<String,String> map = new HashMap<>();
        map.put("first",title);
        map.put("keyword1", account);
        map.put("keyword2", money);
        map.put("remark","感谢您的支持，望生意兴隆");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_BALANCE,null,map);
    }

    @Override
    public void sendSysServer(String openId, String title, String server, int lastDay, String endTime) {
        Map<String,String> map = new HashMap<>();
        map.put("first","您好，您的"+server+"将于"+lastDay+"天后到期！");
        map.put("keyword1", title);
        map.put("keyword2", server);
        map.put("keyword3", endTime);
        map.put("remark","请注意跟进，谢谢");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_SERVER,null,map);
    }

    @Override
    public void sendYoukeSucc(String openId) {
        Map<String,String> map = new HashMap<>();
        map.put("first","尊敬的客户，您注册的店有客账户已经通过审核！");
        map.put("keyword1", "审核通过");
        map.put("keyword2", DateUtil.getDate("yyyy-MM-dd HH:mm"));
        map.put("remark","请及时订购VIP套餐，谢谢");
        templateBiz.doSendMessage(Constants.APPID,openId,TemplateUtil.SYS_TEMPID_YKSUCC,null,map);
    }

    @Override
    public void sendTempMess(String appId, String openId, String tempId, String url,Map<String,String> map) {
        templateBiz.doSendMessage(appId,openId,tempId,url,map);
    }

    @Override
    public void sendUserJoinPush(Map<String, String> map) {
        //推送体验号模板消息
        String appId = "wxb90758151405384b";
        String tempId = "-_WTOd34ODrlRTja9LBPOGZ9zlc-DbZi-8RG_8KnQZg";
        String url="https://sys.dianyk.com";
        String key = "USER-REGISTER-PUSHOPEN";

        if(RedisSlaveUtil.hasKey(key)){
            Map<Object,Object> maps = RedisSlaveUtil.hmget(key);
            if(maps!=null&&maps.size()>0){

                List<JSONObject> objs = new ArrayList<>();
                for (Map.Entry<Object, Object> entry : maps.entrySet()) {
                    objs.add(JSONObject.fromObject(entry.getValue()));
                }
                //时间降序排序
                Collections.sort(objs, (arg0, arg1) -> {
                    long n0 = arg0.getLong("createTime");
                    long n1 = arg1.getLong("createTime");
                    if (n1 > n0) {
                        return 1;
                    } else if (n1 == n0) {
                        return 0;
                    } else {
                        return -1;
                    }
                });
                int count = 0;
                if(RedisUtil.hasKey(key+"-COUNT")){
                    count = (Integer) RedisUtil.get(key+"-COUNT");
                }
                if(count>=objs.size())
                    count = 0;
                JSONObject tarObj = objs.get(count);
                templateBiz.doSendMessage(appId,tarObj.getString("openId"),tempId,url,map);
                //更新次数
                tarObj.put("pushcount",tarObj.getInt("pushcount")+1);
                RedisUtil.hset(key,tarObj.getString("openId"),tarObj.toString());
                count=count+1;
                RedisUtil.set(key+"-COUNT",count);
            }
        }

    }


}
