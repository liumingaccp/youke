package youke.web.pc.action;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TOpenPackage;
import youke.facade.pay.provider.IOrderService;
import youke.facade.pay.provider.IPayServcie;
import youke.facade.pay.util.AliConstants;
import youke.facade.pay.util.PayType;
import youke.facade.pay.util.WXConstants;
import youke.facade.pay.vo.OpenOrderVo;
import youke.facade.pay.vo.ShopPackageVo;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 支付授权业务
 */
@RestController
@RequestMapping("/pay")
public class PayAction extends BaseAction {

    @Resource
    private IPayServcie payServcie;
    @Resource
    private IOrderService orderService;

    /**
     * 获取订购服务套餐
     * @return
     */
    @RequestMapping(value = "/getopenpackages")
    public JsonResult getOpenPackages(){
        List<TOpenPackage> packages = orderService.getOpenPackages();
        return new JsonResult(packages);
    }


    /**
     * 获取新增店铺套餐
     * @return
     */
    @RequestMapping(value = "/getshoppackage")
    public JsonResult getShopPackage(){
        String dykId = AuthUser.getUser().getDykId();
        ShopPackageVo packageVo = orderService.getShopPackage(dykId);
        return new JsonResult(packageVo);
    }

    /**
     * 获取订购服务订单
     * @return
     */
    @RequestMapping(value = "/getopenorder")
    public JsonResult getOpenOrder(){
        JSONObject params = getParams();
        String oid = params.getString("oid");
        OpenOrderVo orderVo = orderService.getOpenOrder(oid);
        return new JsonResult(orderVo);
    }


    /**
     * 创建订单
     * @return
     */
    @RequestMapping(value = "/createorder")
    public JsonResult createOrder(){
        JSONObject params = getParams();
        int packageId = params.getInt("packageId");
        int num = params.getInt("num");
        String orderType = params.getString("type");
        UserVo userVo = AuthUser.getUser();
        String oid = orderService.createOrder(packageId,orderType,num,userVo.getUserId(),userVo.getDykId());
        return new JsonResult(oid);
    }

    /**
     * 升级订购套餐
     * @return
     */
    @RequestMapping(value = "/upopenorder")
    public JsonResult createUpOrder(){
        JSONObject params = getParams();
        int packageId = params.getInt("packageId");
        UserVo userVo = AuthUser.getUser();
        String oid = orderService.createUpOpenOrder(packageId,userVo.getUserId(),userVo.getDykId());
        return new JsonResult(oid);
    }

    /**
     * 创建充值订单
     * @return
     */
    @RequestMapping(value = "/createyoukeorder")
    public JsonResult createYoukeOrder(){
        JSONObject params = getParams();
        int price = params.getInt("price");
        UserVo userVo = AuthUser.getUser();
        String oid = orderService.createYoukeOrder(price,userVo.getUserId(),userVo.getDykId());
        return new JsonResult(oid);
    }

    /**
     * 获取订单状态
     * @return
     */
    @RequestMapping(value = "/getorderstate")
    public JsonResult getOrderState(){
        JSONObject params = getParams();
        String oid = params.getString("oid");
        String orderType = params.getString("type");
        return new JsonResult(orderService.getOrderState(oid,orderType));
    }

    /**
     * 获取订单支付信息
     * @return
     */
    @RequestMapping(value = "/getorderpay")
    public JsonResult getOrderPay(){
        JSONObject params = getParams();
        String oid = params.getString("oid");
        String orderType = params.getString("type");
        return new JsonResult(orderService.getOrderPayVo(oid,orderType));
    }

    /**
     * 获取微信支付二维码
     * @param request
     * @return
     */
    @RequestMapping(value = "/getwxpaycode")
    public JsonResult getWxpayCode(HttpServletRequest request){
        JSONObject params = getParams();
        String oid = params.getString("oid");
        String orderType = params.getString("type");
        return new JsonResult(getBasePath(request) + "common/qrcode?d="+payServcie.getWxpayCodeUrl(oid,orderType));
    }

    /**
     * 获取支付宝支付二维码
     * @param request
     * @return
     */
    @RequestMapping(value = "/getalipaycode")
    public JsonResult getAlipayCode(HttpServletRequest request){
        JSONObject params = getParams();
        String oid = params.getString("oid");
        String orderType = params.getString("type");
        return new JsonResult(getBasePath(request) + "common/qrcode?d="+payServcie.getAlipayCodeUrl(oid,orderType));
    }

    /**
     * 扫码支付回调
     * @return
     */
    @RequestMapping(value = "/wxpayback")
    public String wxpayback(){
        String resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>;";
        try {
            Document encDoc = getParamXML();
            //解密推送信息
            if (encDoc != null) {
                Element element = getParamXML().getRootElement();
                if("SUCCESS".equals(element.elementText("result_code"))&&
                   WXConstants.PARTNET.equals(element.elementText("mch_id"))){
                    String out_trade_no = element.elementText("out_trade_no");
                    Integer total_fee = Integer.valueOf(element.elementText("total_fee"));
                    String oidstr = out_trade_no.split("_")[0];
                    String ranstr = out_trade_no.split("_")[1];
                    String orderType = oidstr.substring(0,4);
                    String oid = oidstr.substring(4);
                    orderService.updateOrderPayed(oid,total_fee, PayType.WXPAY,orderType,ranstr);
                    resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
                }
            }
            return resXml;
        }catch (Exception ex){
            ex.printStackTrace();
            return resXml;
        }
    }

    /**
     * 支付宝支付回调
     * @return
     */
    @RequestMapping(value = "/alipayback")
    public String alipayback(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AliConstants.ALIPAY_PUBLIC_KEY, AliConstants.CHARSET, AliConstants.SIGN_TYPE); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(!signVerified)
            return "fail";

        //商户订单号
        //String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no = request.getParameter("out_trade_no");
        //交易额
        String total_amount = request.getParameter("total_amount");
        //交易状态
        String trade_status = request.getParameter("trade_status");

        if(trade_status.equals("TRADE_FINISHED")){
            //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
        }else if (trade_status.equals("TRADE_SUCCESS")){
            //付款完成后，支付宝系统发送该交易状态通知
            String oidstr = out_trade_no.split("_")[0];
            String ranstr = out_trade_no.split("_")[1];
            String orderType = oidstr.substring(0,4);
            String oid = oidstr.substring(4);
            int total_fee = (int)(Double.valueOf(total_amount)*100);
            orderService.updateOrderPayed(oid,total_fee, PayType.ALIPAY,orderType,ranstr);
        }
        return "success";
    }

}
