package youke.service.pay.provider;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IConfigDao;
import youke.common.model.TConfig;
import youke.common.model.TOpenPackage;
import youke.common.model.TYouke;
import youke.facade.pay.provider.IOrderService;
import youke.facade.pay.util.OrderType;
import youke.facade.pay.vo.OpenOrderVo;
import youke.facade.pay.vo.OrderPayVo;
import youke.facade.pay.vo.ShopPackageVo;
import youke.service.pay.biz.IOrderBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService extends Base implements IOrderService {

    @Resource
    private IOrderBiz orderBiz;

    public String createOrder(Integer packageId, String orderType, Integer num, Integer userId, String youkeId) {
        String oid = "";
        if (OrderType.MOBC.equals(orderType))
            oid = orderBiz.addMobcodeOrder(packageId, num, userId, youkeId);
        else if (OrderType.OPEN.equals(orderType))
            oid = orderBiz.addOpenOrder(packageId, num, userId, youkeId);
        else if (OrderType.SHOP.equals(orderType))
            oid = orderBiz.addShopOrder(userId, youkeId);
        return oid;
    }

    public String createYoukeOrder(Integer price, Integer userId, String youkeId) {
        return orderBiz.addYoukeOrder(price, userId, youkeId);
    }

    public int getOrderState(String oid, String orderType) {
        int state = 0;
        if (OrderType.OPEN.equals(orderType) || OrderType.SHOP.equals(orderType))
            state = orderBiz.getOpenOrderState(oid);
        else if (OrderType.MOBC.equals(orderType))
            state = orderBiz.getMobcodeOrderState(oid);
        else if (OrderType.ACCO.equals(orderType))
            state = orderBiz.getYoukeOrderState(oid);
        return state;
    }

    public void updateOrderPayed(String oid, Integer price, String payType, String orderType, String ranstr) {
        if (OrderType.MOBC.equals(orderType)) {
            orderBiz.updateMobcodeOrder(oid, price, payType, ranstr);
        } else if (OrderType.OPEN.equals(orderType)) {
            orderBiz.updateOpenOrder(oid, price, payType, ranstr);
        } else if (OrderType.ACCO.equals(orderType)) {
            orderBiz.updateYoukeOrder(oid, price, payType, ranstr);
        } else if (OrderType.SHOP.equals(orderType)) {
            orderBiz.updateShopOrder(oid, price, payType, ranstr);
        }
    }

    public OrderPayVo getOrderPayVo(String oid, String orderType) {
        OrderPayVo  orderPayVo = orderBiz.getOrderPayVo(oid,orderType);
        return orderPayVo;
    }

    public List<TOpenPackage> getOpenPackages() {
        return orderBiz.getOpenPackages();
    }

    @Override
    public ShopPackageVo getShopPackage(String dykId) {
        return orderBiz.getShopPackage(dykId);
    }

    public OpenOrderVo getOpenOrder(String oid) {
        return orderBiz.getOpenOrderVo(oid);
    }

    @Override
    public String createUpOpenOrder(int packageId, Integer userId, String dykId) {
        return orderBiz.addUpOpenOrder(packageId,userId,dykId);
    }


}
