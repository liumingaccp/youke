package youke.service.pay.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.XMLUtil;
import youke.facade.pay.util.WXConstants;
import youke.facade.pay.util.WXPayUtil;
import youke.service.pay.biz.IWxpayBiz;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class WxpayBiz extends Base implements IWxpayBiz {

    public String getScanPayUrl(String tradeNo, String body, int totalFee) {

        String nonce_str = IDUtil.getRandomId();
        String trade_type = "NATIVE";
        Date nowDate = new Date();
        String time_start =  DateUtil.formatDate(nowDate,"yyyyMMddHHmmss");
        String time_expire = DateUtil.formatDate(DateUtil.addHours(nowDate,2),"yyyyMMddHHmmss");
        String ran_str = IDUtil.getRandomId();
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", WXConstants.APPID);
        packageParams.put("mch_id", WXConstants.PARTNET);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body",body);
        packageParams.put("out_trade_no", tradeNo+"_"+ran_str);
        packageParams.put("product_id", tradeNo);
        packageParams.put("total_fee", totalFee+"");
        packageParams.put("spbill_create_ip", WXConstants.CLIENTIP);
        packageParams.put("notify_url", WXConstants.NOTIFYURL);
        packageParams.put("trade_type", trade_type);
        packageParams.put("time_start", time_start);
        packageParams.put("time_expire", time_expire);
        String sign = WXPayUtil.createSign(packageParams,WXConstants.PARTNETKEY);
        packageParams.put("sign", sign);

        String requestXML = WXPayUtil.getRequestXml(packageParams);
        System.out.println("请求xml："+requestXML);

        String resXml = HttpConnUtil.doPostJson(WXConstants.PAY_API, requestXML);
        System.out.println("返回xml："+resXml);

        try {
            Map map = XMLUtil.doXMLParse(resXml);
            String urlCode = (String) map.get("code_url");
            System.out.println("二维码url："+urlCode);
            return urlCode;
        }catch (Exception ex){
            throw new BusinessException("微信支付结果XML解析失败");
        }
    }

    public static void main(String[] args) {
        String ram = IDUtil.getRandomId();
        System.out.println(ram);
        //new WxpayBiz().getScanPayUrl(ram,"测试微信扫码支付"+ IDUtil.getRandomId(),1);
    }
}
