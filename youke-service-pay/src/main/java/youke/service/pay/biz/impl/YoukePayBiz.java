package youke.service.pay.biz.impl;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TYoukeBank;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.PayConfigVo;
import youke.common.queue.message.TempMassMessage;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.*;
import youke.facade.pay.util.WXConstants;
import youke.facade.pay.util.WXPayUtil;
import youke.service.pay.biz.IQueueBiz;
import youke.service.pay.biz.IYoukePayBiz;
import youke.service.pay.queue.producter.QueueSender;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.*;

@Service
public class YoukePayBiz extends Base implements IYoukePayBiz {

    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IYoukeBankDao youkeBankDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;
    @Resource
    private IIntegralOrderDao integralOrderDao;
    @Resource
    private IRebateActiveOrderDao rebateActiveOrderDao;
    @Resource
    private ITrialActiveOrderDao trialActiveOrderDao;
    @Resource
    private ITaokeActiveOrderDao taokeActiveOrderDao;
    @Resource
    private IFollowActiveOrderDao followActiveOrderDao;
    @Resource
    private ICutpriceActiveOrderDao cutpriceActiveOrderDao;
    @Resource
    private ICollageActiveOrderDao collageActiveOrderDao;
    @Resource
    private ISysMessageDao sysMessageDao;
    @Resource
    private IAdminMessageDao adminMessageDao;
    @Resource
    private IQueueBiz queueBiz;
    @Resource
    private QueueSender queueSender;

    @Override
    public boolean doPayMoney(int comeType, int money, int commision, String openId, String appId, String youkeId) {
        PayConfigVo configVo = subscrConfigDao.selectPayConfig(appId);
        if(configVo.getPayState()==0) {
            sendFailMessage(youkeId,"未开通支付");
            return false;
        }if(configVo.getPayBuss()==0&&youkeDao.selectMoney(youkeId)<money+commision) {
            sendFailMessage(youkeId,"账户红包余额不足，请及时充值");
            return false;
        }
        String taokeOpenId = null;
        if(openId.contains(",")){
            taokeOpenId = openId.split(",")[1];
            openId = openId.split(",")[0];
        }
        if(taokeOpenId!=null){
            if(doRedPackage(youkeId,appId,taokeOpenId,commision,ComeType.COME_TYPE.get(comeType))){
                TempMassMessage temp = new TempMassMessage();
                temp.setAppId(appId);
                temp.setOpenId(taokeOpenId);
                temp.setComeType(comeType);
                temp.setTitle(ComeType.COME_TYPE.get(comeType));
                temp.setMoney(commision);
                queueSender.send("integralTemplate.queue", temp);
            }
        }
        if(doRedPackage(youkeId,appId,openId,money,ComeType.COME_TYPE.get(comeType))) {
            //账户扣除金额记录
            TYoukeBank youkeBank = new TYoukeBank();
            youkeBank.setPaytype("wxpay");
            youkeBank.setYoukeid(youkeId);
            youkeBank.setTitle(ComeType.COME_TYPE.get(comeType) + "消耗" + StringUtil.FenToYuan(money + commision) + "元");
            youkeBank.setPrice(money);
            youkeBank.setLeftprice(youkeDao.selectMoney(youkeId));
            youkeBank.setCreatetime(new Date());
            youkeBank.setClassify(ComeType.COME_TYPE.get(comeType) + "消耗");
            if (configVo.getPayBuss() == 0) {
                //更新账户金额
                youkeDao.updateMoney(-(money + commision), youkeId);
            } else {
                youkeBank.setLeftprice(0);
            }
            youkeBankDao.insertSelective(youkeBank);
            TempMassMessage temp = new TempMassMessage();
            temp.setAppId(appId);
            temp.setOpenId(openId);
            temp.setComeType(comeType);
            temp.setTitle(ComeType.COME_TYPE.get(comeType));
            temp.setMoney(money);
            queueSender.send("integralTemplate.queue", temp);
            return true;
        }
        return false;
    }

    public boolean doRedPackage(String youkeId, String appId, String openId,int totalAmount,String comeTypeTitle){
        //排除店有客公众号和体验号
        if(totalAmount>200) {
            if (appId.equals(ApiCodeConstant.APPID) || appId.equals(ApiCodeConstant.APPID_TIYAN) || appId.equals(ApiCodeConstant.APPID_YANFA)) {
                return true;
            }
        }
        PayConfigVo payConfigVo = subscrConfigDao.selectPayConfig(appId);
        String mchId = "";
        String mchKey = "";
        String filecert = "";
        //String wxname = subscrDao.selectNickname(appId);
        if(payConfigVo.getPayState()==1) {
            if (payConfigVo.getPayBuss() == 0) {
                mchId = ApiCodeConstant.PAY_MCHID;
                mchKey = ApiCodeConstant.PAY_MCHKEY;
                filecert = ApiCodeConstant.PAY_FILECERT;
            } else {
                mchId = payConfigVo.getPayNumber();
                mchKey = payConfigVo.getPaycert();
                filecert = payConfigVo.getFilecert();
            }
        }else{
            return false;
        }
        String nonce_str = MD5Util.MD5(UUID.randomUUID().toString());
        String ran_str = IDUtil.getRandomId();
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        boolean succ = false;
//      注销红包发送
//        if(totalAmount<=WXConstants.PAYLIMIT) {
//            packageParams.put("nonce_str", nonce_str);
//            packageParams.put("mch_billno", ran_str);
//            packageParams.put("mch_id", mchId);
//            packageParams.put("wxappid", appId);
//            packageParams.put("send_name", wxname);
//            packageParams.put("re_openid", openId);
//            packageParams.put("total_amount", totalAmount + "");
//            packageParams.put("total_num", "1");
//            packageParams.put("wishing", "感谢您参加" + comeTypeTitle + "活动,祝您生活愉快!");
//            packageParams.put("client_ip", WXConstants.CLIENTIP);
//            packageParams.put("act_name", comeTypeTitle + "活动红包");
//            packageParams.put("remark", "更多红包营销活动，快来参与！");
//            packageParams.put("scene_id", "PRODUCT_1");
//            String sign = WXPayUtil.createSign(packageParams, mchKey);
//            packageParams.put("sign", sign);
//            String requestXML = WXPayUtil.getRequestXml(packageParams);
//            try {
//                succ = sendRedpack(youkeId, mchId, filecert, requestXML);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
            packageParams.put("mch_appid", appId);
            packageParams.put("mchid", mchId);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("partner_trade_no", ran_str);
            packageParams.put("openid", openId);
            packageParams.put("check_name", "NO_CHECK");
            packageParams.put("amount", totalAmount+"");
            packageParams.put("desc", "来自【" + comeTypeTitle + "】的活动红包,祝您生活愉快!");
            packageParams.put("spbill_create_ip", WXConstants.CLIENTIP);
            String sign = WXPayUtil.createSign(packageParams, mchKey);
            packageParams.put("sign", sign);
            String requestXML = WXPayUtil.getRequestXml(packageParams);
            try {
                succ = sendMmpay(youkeId, mchId, filecert, requestXML);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        return succ;
    }

    @Override
    public void doAutoPay() {
        System.out.println("执行了");
        List<String> youkeIds = youkeDao.selectOKPayYoukeId();
        if(youkeIds!=null&&youkeIds.size()>0){
            for (String id:youkeIds) {
                if(getPayFailMoney(id)>0){
                    List<ActivePayVo> payVos = marketActiveRecordDao.selectActivePayVo(id);
                    for(ActivePayVo payVo:payVos)
                    {
                        payVo.setComeType(ComeType.getComeTypeFromActiveType(payVo.getComeType()));
                    }
                    if(doEashPay(payVos)&&
                       doEashPay(integralOrderDao.selectActivePayVo(ComeType.JI_FEN_DUI_HUAN,id))&&
                       doEashPay(followActiveOrderDao.selectActivePayVo(ComeType.TUI_GUANG_GUAN_ZHU,id))&&
                       doEashPay(rebateActiveOrderDao.selectActivePayVo(ComeType.GOU_WU_FAN_LI,id))&&
                       doEashPay(trialActiveOrderDao.selectActivePayVo(ComeType.SHI_YONG_FU_LI,id))&&
                       doEashPay(taokeActiveOrderDao.selectActivePayVo(ComeType.WEI_TAO_KE,id))){
                       //do nothing
                    }
                }
            }
        }
        System.out.println("执行结束");
    }

    private boolean doEashPay(List<ActivePayVo> payVos){
        boolean res = true;
        if(payVos!=null&&payVos.size()>0){
            for (ActivePayVo payVo:payVos){
                if(!doPayMoney(payVo.getComeType(),payVo.getMoney(),payVo.getCommision(),empty(payVo.gettOpenId())?payVo.getOpenId():payVo.getOpenId()+","+payVo.gettOpenId(),payVo.getAppId(),payVo.getYoukeId())){
                    res = false;
                    break;
                }else{
                    //更新活动状态
                    updateActiveState(payVo.getId(),payVo.getComeType(), 0);
                }
            }
        }
        return res;
    }


    @Override
    public String doValidSSL(String mchId, String mchKey, String filecert) {
        String res = "success";
        if(empty(mchId)) return "支付商户名不能为空";
        if(empty(mchKey)) return "支付密钥不能为空";
        if(empty(filecert)) return "请上传支付证书";
        if(!StringUtil.isNumeric(mchId)) return "无效的支付商户名，请检查后重试";
        if(mchKey.length()!=32) return "无效的支付密钥，请检查后重试";
        if(!filecert.endsWith(".p12"))  return "无效的支付证书，请上传.p12格式证书";
        try{
            getSSLFactory(mchId,filecert);
        }catch (Exception e){
            res = "支付证书验证失败，请检查商户名,密钥或证书后重试";
        }
        return res;
    }

    private SSLConnectionSocketFactory getSSLFactory (String mchId,String filecert) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream instream = HttpConnUtil.getStreamFromNetByUrl(filecert);
        // 加载密钥库
        keyStore.load(instream, mchId.toCharArray());
        instream.close();
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return sslsf;
    }

    private boolean sendRedpack(String youkeId,String mchId,String filecert,String requestXML){
        try{
            CloseableHttpClient httpclient = HttpClients.custom()
                        .setSSLSocketFactory(getSSLFactory(mchId, filecert))
                        .build();
            Map<String, String> map = null;
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
            StringEntity reqEntity = new StringEntity(requestXML, "utf-8");
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if (entity != null) {
                map = new HashMap<>();
                // 从request中取得输入流
                InputStream inputStream = entity.getContent();
                // 读取输入流
                SAXReader reader = new SAXReader();
                Document document = reader.read(inputStream);
                // 得到xml根元素
                Element root = document.getRootElement();
                // 得到根元素的所有子节点
                List<Element> elementList = root.elements();
                // 遍历所有子节点
                for (Element e : elementList)
                    map.put(e.getName(), e.getText());
                // 释放资源
                inputStream.close();
            }
            EntityUtils.consume(entity);
            response.close();
            httpclient.close();
            if (map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
                return true;
            } else {
                sendFailMessage(youkeId,map.get("err_code_des"));
                return false;
            }
        }catch(Exception ex){
            sendFailMessage(youkeId,"支付证书验证失败，请检查商户名,密钥或证书");
        }
        return false;
    }

    private boolean sendMmpay(String youkeId,String mchId,String filecert,String requestXML){
        try{
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(getSSLFactory(mchId, filecert))
                    .build();
            Map<String, String> map = null;
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
            StringEntity reqEntity = new StringEntity(requestXML, "utf-8");
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if (entity != null) {
                map = new HashMap<>();
                // 从request中取得输入流
                InputStream inputStream = entity.getContent();
                // 读取输入流
                SAXReader reader = new SAXReader();
                Document document = reader.read(inputStream);
                // 得到xml根元素
                Element root = document.getRootElement();
                // 得到根元素的所有子节点
                List<Element> elementList = root.elements();
                // 遍历所有子节点
                for (Element e : elementList)
                    map.put(e.getName(), e.getText());
                // 释放资源
                inputStream.close();
            }
            EntityUtils.consume(entity);
            response.close();
            httpclient.close();
            if (map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
                return true;
            } else {
                if(map.get("return_code").equals("FAIL"))
                   sendFailMessage(youkeId,map.get("return_msg"));
                else if(map.get("result_code").equals("FAIL"))
                   sendFailMessage(youkeId,map.get("err_code_des"));
                return false;
            }
        }catch(Exception ex){
            sendFailMessage(youkeId,"支付证书验证失败，请检查商户名,密钥或证书");
        }
        return false;
    }

    /**
     * 发送红包失败通知，1天发一次
     * @param youkeId
     * @param reason
     */
    private void sendFailMessage(String youkeId,String reason){
        logger.info(reason);
        Map<String,String> map = youkeDao.selectYoukeOpenMap(youkeId);
        String key = "weixin-payfail-"+youkeId;
        String send = (String) RedisSlaveUtil.get(key);
        if(empty(send)) {
            //发短信
            int money = getPayFailMoney(youkeId);
            try {
                //发短信通知
                queueBiz.sendPayFailMsg(map.get("mobile"),money, reason);
                //发微信通知
                queueBiz.sendSysPayFail(map.get("openId"),money, reason);
                //保存系统通知
                sysMessageDao.addMessage("商户余额不足提醒","您的微信商户发放返利红包失败，失败红包共"+StringUtil.FenToYuan(money)+"元，原因："+reason+"，请及时处理。",youkeId);
                adminMessageDao.addMessage("payfail","微信商户发放返利红包失败，失败红包共"+StringUtil.FenToYuan(money)+"元，原因："+reason+"，请跟进处理。",youkeId);
                RedisUtil.set(key,DateUtil.getDateTime(),43200);
            } catch (Exception e) {
            }
        }
    }

    private int getPayFailMoney(String youkeId){
        //获取失败营销活动
        int money = 0;
        Integer money2=marketActiveRecordDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        money2=integralOrderDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        money2=rebateActiveOrderDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        money2=trialActiveOrderDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        money2=taokeActiveOrderDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        money2=followActiveOrderDao.selectTotalFailMoney(youkeId);
        if(money2!=null)
            money+=money2;
        return money;
    }


    /**
     * 更新活动状态
     * @param recordId
     * @param comeType
     * @param type 0已返利，1返利失败
     */
    public void updateActiveState(Long recordId, int comeType, int type){
        if(ComeType.isMarketActive(comeType)){
            marketActiveRecordDao.updateState(recordId,type==0?3:4);
        }else if(comeType==ComeType.JI_FEN_DUI_HUAN){
            integralOrderDao.updateState(recordId,type==0?3:4);
        }else if(comeType==ComeType.TUI_GUANG_GUAN_ZHU){
            followActiveOrderDao.updateState(recordId,type==0?3:4);
        }else if(comeType==ComeType.GOU_WU_FAN_LI){
            rebateActiveOrderDao.updateState(recordId,type==0?6:7);
        }else if(comeType==ComeType.SHI_YONG_FU_LI){
            trialActiveOrderDao.updateState(recordId,type==0?6:7);
        }else if(comeType==ComeType.WEI_TAO_KE){
            taokeActiveOrderDao.updateState(recordId,type==0?6:7);
        }else if(comeType==ComeType.KAN_JIA){
            cutpriceActiveOrderDao.updateState(recordId,type==0?6:7);
        }else if(comeType==ComeType.PIN_TUAN){
            collageActiveOrderDao.updateState(recordId,type==0?4:5);
        }
    }

}
