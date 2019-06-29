package youke.service.pay.biz.impl;

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
import org.junit.Test;
import youke.common.constants.ApiCodeConstant;
import youke.common.constants.ComeType;
import youke.common.dao.ITaokeActiveOrderDao;
import youke.common.model.TTaokeActiveOrder;
import youke.common.model.vo.result.PayConfigVo;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.TempMassMessage;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.MD5Util;
import youke.facade.pay.util.WXConstants;
import youke.facade.pay.util.WXPayUtil;
import youke.service.pay.biz.IFansMoneyBiz;
import youke.service.pay.biz.IYoukePayBiz;
import youke.service.pay.queue.producter.QueueSender;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.*;

public class YoukePayBizTest extends BaseJunit4Test {

    @Resource
    private IYoukePayBiz youkePayBiz;
    @Resource
    private IFansMoneyBiz fansMoneyBiz;
    @Resource
    private ITaokeActiveOrderDao taokeActiveOrderDao;
    @Resource
    private QueueSender queueSender;

    @Test
    public void doTestPay() {
        //youkePayBiz.doPayMoney(ComeType.SHI_YONG_FU_LI,500,0,"oyxoc1ZVur_3RmQJocfcZuHEUFh4","wxe3e582584ba16db1","dykw1wF3Xy");
        String youkeId="dyk79GMa5o";
        String appId="wxe3f68ea519ac64e9";
        String openId="oPzNE0kIab31w2jFZPm72oK6TNuY";

        String mchId = "1496498832";
        String mchKey = "4277147287d8137f9c6c4893dc8f46b4";
        String filecert = "https://nuoren.oss-cn-shenzhen.aliyuncs.com/p12/20180626 165823.p12";
        String nonce_str = MD5Util.MD5(UUID.randomUUID().toString());
        String ran_str = IDUtil.getRandomId();
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        boolean succ = false;
        packageParams.put("mch_appid", appId);
        packageParams.put("mchid", mchId);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("partner_trade_no", ran_str);
        packageParams.put("openid", openId);
        packageParams.put("check_name", "NO_CHECK");
        packageParams.put("amount", "100");
        packageParams.put("desc", "来自【购物返利测试】的活动红包,祝您生活愉快!");
        packageParams.put("spbill_create_ip", WXConstants.CLIENTIP);
        String sign = WXPayUtil.createSign(packageParams, mchKey);
        packageParams.put("sign", sign);
        String requestXML = WXPayUtil.getRequestXml(packageParams);
        try {
            succ = sendMmpay(youkeId, mchId, filecert, requestXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SSLConnectionSocketFactory getSSLFactory (String mchId, String filecert) throws Exception {
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
                    System.out.println(map.get("return_msg"));
                else if(map.get("result_code").equals("FAIL"))
                    System.out.println(map.get("err_code_des"));
                return false;
            }
        }catch(Exception ex){
            System.out.println("支付证书验证失败，请检查商户名,密钥或证书");
        }
        return false;
    }

    @Test
    public void pushPayMsg(){
        TempMassMessage temp = new TempMassMessage();
        temp.setAppId("wxe3e582584ba16db1");
        temp.setOpenId("oyxoc1ZVur_3RmQJocfcZuHEUFh4");
        temp.setComeType(ComeType.SHI_YONG_FU_LI);
        temp.setTitle("试用福利");
        temp.setMoney(1000);
        queueSender.send("integralTemplate.queue",temp);
    }

    @Test
    public void testPay(){
        List<TTaokeActiveOrder> orders = taokeActiveOrderDao.selectOrderByState(4);
        if (orders != null && orders.size() > 0) {
            for (TTaokeActiveOrder order : orders) {
                ActiveMassMessage massMessage = new ActiveMassMessage();
                if (order.getBackmoney() > 0) {
                    massMessage.setRecordId(order.getId());
                    massMessage.setAppId(order.getAppid());
                    massMessage.setComeType(ComeType.WEI_TAO_KE);
                    massMessage.setMoney(order.getBackmoney());
                    massMessage.setOpenId(order.getBuyeropenid());
                    massMessage.setYoukeId(order.getYoukeid());
                    massMessage.setTitle(order.getTitle());
                    massMessage.setState(1);
                    if (order.getCommision() > 0) {
                        massMessage.setIntegral(order.getCommision());
                        massMessage.setOpenId(order.getBuyeropenid() + "," + order.getTaokeopenid());
                    }
                    if (youkePayBiz.doPayMoney(massMessage.getComeType(), massMessage.getMoney(), massMessage.getIntegral() == null ? 0 : massMessage.getIntegral(), massMessage.getOpenId(), massMessage.getAppId(), massMessage.getYoukeId())) {
                        youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 0);
                        try {
                            fansMoneyBiz.addMoney(massMessage.getComeType(), massMessage.getTitle(), massMessage.getOpenId().split(",")[0], massMessage.getMoney(), massMessage.getAppId(), massMessage.getYoukeId());
                            if (order.getCommision() > 0) {
                                fansMoneyBiz.addMoney(massMessage.getComeType(), massMessage.getTitle(), massMessage.getOpenId().split(",")[1], order.getCommision(), massMessage.getAppId(), massMessage.getYoukeId());
                            }
                        }catch (Exception e){}
                    } else {
                        youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 1);
                    }
                }
            }
        }
    }

}