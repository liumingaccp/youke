package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TShopOrder;

import java.util.List;
import java.util.Map;

public interface IShopOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopOrder record);

    TShopOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TShopOrder record);

    TShopOrder getByOrderNo(@Param("orderno") String orderno, @Param("youkeId") String youkeId);

    @Select("select * from t_shop_order where orderno=#{orderno} limit 1")
    TShopOrder selectByOrderno(@Param("orderno") String orderno);

    @Select("SELECT a.title,b.openId,b.appId FROM t_shop_order a INNER JOIN t_subscr_fans b ON a.`receiveMobile`=b.`mobile` INNER JOIN t_subscr c ON b.`appId`=c.`appId` WHERE a.`youkeId`=#{youkeId} AND c.`youkeId`=#{youkeId} AND a.`orderno`=#{orderno} LIMIT 1")
    List<Map> selectOrder(@Param("orderno") String orderno,@Param("youkeId") String youkeId);

    /**
     * 筛选已收货的订单号
     * @param ordernos
     * @return
     */
    List<String> selectSHOrderNoInOrderno(@Param("ordernos")List<String> ordernos);
    /**
     * 获取粉丝前七日交易额
     *
     * @return
     */
    int getFansPaymentAmountForSevenDays(String dykId);

    /**
     * 获取粉丝前十四日交易额
     *
     * @return
     */
    int getFansPaymentAmountForElevenDays(String dykId);

    /**
     * 查询昨日交易额
     *
     * @param dykId
     * @return
     */
    int getTurnoverForYesterday(String dykId);

    List<String> selectSuccessOrderNoInOrderNo(@Param("ordernos") List<String> ordernos);

    @Select("SELECT totalPrice FROM t_shop_order WHERE buyerName=#{nickname} ORDER BY payTime DESC LIMIT 1")
    Integer selectLatelyPrice(@Param("nickname")String nickname);

    @Select("SELECT b.id,b.goodId,b.shopType,c.title AS shopTitle,b.title,b.picPath,b.price,b.num,b.totalPrice,b.state FROM  t_shop_order b INNER JOIN t_shop c ON b.shopId=c.id WHERE b.receiveMobile=#{mobile} AND b.youkeId=#{youkeId} ORDER BY b.payTime DESC")
    List<Map> selectBuyerOrderList(@Param("mobile")String mobile,@Param("youkeId")String youkeId);

    @Select("SELECT id from t_shop_order where receiveMobile=#{mobile} AND youkeId=#{youkeId} limit 1")
    Long selectOrderByMobile(@Param("mobile") String mobile,@Param("youkeId") String youkeId);

    @Select("SELECT id, num, totalPrice from t_shop_order where orderno = #{orderno}")
    TShopOrder selectOrderByOrderNo(@Param("orderno") String orderno);

    @Select("SELECT id, shopId, goodId from t_shop_order where picPath IS NULL AND shopType = 2 limit 100")
    List<TShopOrder> selectShopOrderWithEmptyImage();

    @Select("SELECT COUNT(1) AS dealNum,SUM(totalPrice) AS dealTotal, FLOOR(AVG(totalPrice)) AS avgDealTotal FROM t_shop_order WHERE  buyerName=#{buyerNick} AND shopId=#{shopId} and state>0")
    Map selectDealStat(@Param("buyerNick")String buyerNick, @Param("shopId")Integer shopId);
}