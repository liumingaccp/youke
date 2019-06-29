package youke.facade.pay.provider;

import youke.common.model.TOpenPackage;
import youke.facade.pay.vo.OpenOrderVo;
import youke.facade.pay.vo.OrderPayVo;
import youke.facade.pay.vo.ShopPackageVo;

import java.util.List;

public interface IOrderService {

    /**
     * 创建订单
     * @param packageId
     * @param orderType
     * @param num
     * @param userId
     * @param youkeId
     * @return
     */
    String createOrder(Integer packageId,String orderType,Integer num,Integer userId,String youkeId);

    /**
     * 创建充值订单
     * @param price
     * @param userId
     * @param youkeId
     * @return
     */
    String createYoukeOrder(Integer price,Integer userId,String youkeId);

    /**
     * 获取订单状态
     * @param oid
     * @param orderType
     * @return
     */
    int getOrderState(String oid,String orderType);

    /**
     * 更新订单支付状态
     * @param oid
     * @param price
     * @param payType
     * @param orderType
     * @param ranstr
     */
    void updateOrderPayed(String oid,Integer price,String payType,String orderType,String ranstr);

    /**
     * 获取订单支付信息
     * @param oid
     * @param orderType
     * @return
     */
    OrderPayVo getOrderPayVo(String oid, String orderType);

    /**
     * 获取订购服务套餐
     * @return
     */
    List<TOpenPackage> getOpenPackages();

    /**
     * 获取新增店铺套餐
     * @param dykId
     * @return
     */
    ShopPackageVo getShopPackage(String dykId);

    /**
     * 获取订购服务订单
     * @param oid
     * @return
     */
    OpenOrderVo getOpenOrder(String oid);

    /**
     * 创建升级订单
     * @param packageId
     * @param userId
     * @param dykId
     * @return
     */
    String createUpOpenOrder(int packageId, Integer userId, String dykId);
}
