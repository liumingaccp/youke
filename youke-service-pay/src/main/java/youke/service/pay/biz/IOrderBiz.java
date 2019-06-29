package youke.service.pay.biz;

import youke.common.model.TMobcodeOrder;
import youke.common.model.TOpenPackage;
import youke.facade.pay.vo.OpenOrderVo;
import youke.facade.pay.vo.OrderPayVo;
import youke.facade.pay.vo.ShopPackageVo;

import java.util.List;

public interface IOrderBiz {

     /**
      * 创建充值订单
      * @param packageId
      * @param num
      * @param userId
      * @param youkeId
      * @return
      */
     String addMobcodeOrder(Integer packageId,Integer num,Integer userId,String youkeId);

     /**
      * 创建订购充值订单
      * @param packageId
      * @param num
      * @param userId
      * @param youkeId
      * @return
      */
     String addOpenOrder(Integer packageId, Integer num, Integer userId, String youkeId);

     /**
      * 创建账户充值订单
      * @param price
      * @param userId
      * @param youkeId
      * @return
      */
     String addYoukeOrder(Integer price, Integer userId, String youkeId);
     /**
      * 获取充值订单
      * @param oid
      * @return
      */
     TMobcodeOrder getMobcodeOrder(String oid);

     /**
      * 获取充值订单
      * @param oid
      * @param orderType
      * @return
      */
     OrderPayVo getOrderPayVo(String oid,String orderType);

     /**
      * 获取充值订单状态
      * @param oid
      * @return
      */
     int getMobcodeOrderState(String oid);

     /**
      * 获取订购服务订单状态
      * @param oid
      * @return
      */
     int getOpenOrderState(String oid);

     /**
      * 获取账户充值订单状态
      * @param oid
      * @return
      */
     int getYoukeOrderState(String oid);
     /**
      * 更新充值订单付款状态
      * @param oid
      * @param price
      * @param payType
      * @param ranstr
      */
     void updateMobcodeOrder(String oid,Integer price,String payType,String ranstr);

     /**
      * 更新订购订单付款状态
      * @param oid
      * @param price
      * @param payType
      * @param ranstr
      */
     void updateOpenOrder(String oid,Integer price,String payType,String ranstr);

     /**
      * 更新新增店铺付款状态
      * @param oid
      * @param price
      * @param payType
      * @param ranstr
      */
     void updateShopOrder(String oid, Integer price, String payType, String ranstr);

     /**
      * 更新充值订单付款状态
      * @param oid
      * @param price
      * @param payType
      * @param ranstr
      */
     void updateYoukeOrder(String oid, Integer price, String payType, String ranstr);
     /**
      * 获取订购套餐
      * @return
      */
     List<TOpenPackage> getOpenPackages();

     OpenOrderVo getOpenOrderVo(String oid);

     /**
      * 添加订购升级订单
      * @param packageId
      * @param userId
      * @param dykId
      * @return
      */
     String addUpOpenOrder(int packageId, Integer userId, String dykId);

     /**
      * 添加新增店铺订单
      * @param userId
      * @param youkeId
      * @return
      */
     String addShopOrder(int userId, String youkeId);

     ShopPackageVo getShopPackage(String dykId);
}
