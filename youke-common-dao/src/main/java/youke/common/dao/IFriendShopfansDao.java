package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TFriendShopfans;

import java.util.List;
import java.util.Map;

public interface IFriendShopfansDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TFriendShopfans record);

    TFriendShopfans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TFriendShopfans record);

    @Select("select count(1) from t_friend_shopfans where friendId=#{friendId} and buyername IS NOT NULL")
    int selectCount(@Param("friendId")String friendId);

    @Select("select * from t_friend_shopfans where friendId=#{friendId} and weixinId=#{weixinId} limit 1")
    TFriendShopfans selectByFriendId(@Param("friendId")String friendId,@Param("weixinId")String weixinId);

    @Select("SELECT a.buyerName,a.receiveName,a.receiveState,a.totalPrice,a.num,a.payTime,b.city FROM t_shop_order a LEFT JOIN t_shop_fans b ON a.`receiveMobile`=b.mobile AND b.youkeId=#{youkeId} WHERE a.receiveMobile=#{mobile} AND a.youkeId=#{youkeId} ORDER BY a.payTime DESC")
    List<Map> selectFriendOrdersByMobile(@Param("mobile")String mobile, @Param("youkeId")String youkeId);

    @Select("SELECT receiveMobile FROM t_shop_order WHERE buyerName=#{buyerName} AND youkeId=#{youkeId}")
    String selectMobileByBuyerName(@Param("buyerName")String buyerName, @Param("youkeId")String youkeId);
}