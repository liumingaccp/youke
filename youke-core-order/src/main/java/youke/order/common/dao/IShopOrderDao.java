package youke.order.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.order.common.model.TShopOrder;

import java.util.Map;

public interface IShopOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopOrder record);

    TShopOrder selectByPrimaryKey(Long id);

    @Select("select * from t_shop_order where orderno=#{orderno} limit 1")
    TShopOrder selectByOrderno(@Param("orderno") String orderno);

    int updateByPrimaryKeySelective(TShopOrder record);

    @Select("SELECT COUNT(1) AS dealNum,SUM(totalPrice) AS dealTotal, FLOOR(AVG(totalPrice)) AS avgDealTotal FROM t_shop_order WHERE  buyerName=#{buyerNick} AND shopId=#{shopId} and state>0")
    Map selectDealStat(@Param("buyerNick")String buyerNick, @Param("shopId")Integer shopId);
}