package youke.order.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.order.common.model.TShopFans;

public interface IShopFansDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopFans record);

    TShopFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TShopFans record);

    @Select("select * from t_shop_fans where nickname=#{nickname}")
    TShopFans selectbyNickname(@Param("nickname") String nickname);

    @Select("select * from t_shop_fans where mobile=#{mobile} and shopId=#{shopId}")
    TShopFans selectbyMobileAndShopId(@Param("mobile")String mobile, @Param("shopId")Integer shopId);

    @Select("select count(1) from t_shop_fans where mobile=#{mobile} and shopId=#{shopId}")
    int selectCountByMobileAndShopId(@Param("mobile")String mobile, @Param("shopId")Integer shopId);
}