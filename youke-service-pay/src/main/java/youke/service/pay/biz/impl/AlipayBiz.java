package youke.service.pay.biz.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.utils.IDUtil;
import youke.facade.pay.util.AliConstants;
import youke.service.pay.biz.IAlipayBiz;

@Service
public class AlipayBiz extends Base implements IAlipayBiz {


    @Override
    public String getScanPayUrl(String tradeNo, String body, int totalFee) {
        AlipayClient alipayClient = new DefaultAlipayClient(AliConstants.gatewayUrl,AliConstants.APP_ID, AliConstants.APP_PRIVATE_KEY, "json", AliConstants.CHARSET, AliConstants.ALIPAY_PUBLIC_KEY, AliConstants.SIGN_TYPE); //获得初始化的AlipayClient
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
        String ran_str = IDUtil.getRandomId();
        String out_trade_no = tradeNo+"_"+ran_str;
        String total_amount = (Double.valueOf(totalFee+"")/100)+"";
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+out_trade_no+"\"," +
                "    \"total_amount\":\""+total_amount+"\"," +
                "    \"subject\":\""+body+"\"," +
                "    \"timeout_express\":\"90m\"}");//设置业务参数
        request.setNotifyUrl(AliConstants.notify_url);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            String json = response.getBody();
            JSONObject jsonRes = JSONObject.fromObject(json);
            return jsonRes.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("生成支付宝二维码异常");
        }
    }

    public static void main(String[] args) {
    }
}
