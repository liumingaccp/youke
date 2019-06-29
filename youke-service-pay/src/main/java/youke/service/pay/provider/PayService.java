package youke.service.pay.provider;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.facade.pay.provider.IPayServcie;
import youke.facade.pay.util.OrderType;
import youke.facade.pay.vo.OrderPayVo;
import youke.service.pay.biz.IAlipayBiz;
import youke.service.pay.biz.IOrderBiz;
import youke.service.pay.biz.IWxpayBiz;
import youke.service.pay.biz.IYoukePayBiz;

import javax.annotation.Resource;

@Service
public class PayService extends Base implements IPayServcie {

    @Resource
    private IWxpayBiz wxpayBiz;
    @Resource
    private IAlipayBiz alipayBiz;
    @Resource
    private IOrderBiz orderBiz;
    @Resource
    private IYoukePayBiz youkePayBiz;

    public String getWxpayCodeUrl(String oid, String orderType) {
        OrderPayVo orderPayVo = orderBiz.getOrderPayVo(oid,orderType);
        return wxpayBiz.getScanPayUrl(orderType+orderPayVo.getOid(),orderPayVo.getBody(),orderPayVo.getTotalFee());
    }

    public String getAlipayCodeUrl(String oid, String orderType) {
        OrderPayVo orderPayVo = orderBiz.getOrderPayVo(oid,orderType);
        return alipayBiz.getScanPayUrl(orderType+orderPayVo.getOid(),orderPayVo.getBody(),orderPayVo.getTotalFee());
    }

    @Override
    public String doValidSSL(String mchId, String mchKey, String filecert) {
        return youkePayBiz.doValidSSL(mchId,mchKey,filecert);
    }


}
