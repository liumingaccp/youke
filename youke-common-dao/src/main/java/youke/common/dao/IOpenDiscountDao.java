package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TOpenDiscount;

public interface IOpenDiscountDao {
    int deleteByPrimaryKey(Integer id);
    int insertSelective(TOpenDiscount record);

    TOpenDiscount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOpenDiscount record);

    @Update("update t_open_discount set state=1 where id=#{id}")
    int updateStateUsed(@Param("id")int id);

    @Select("select * from t_open_discount where youkeId=#{youkeId} and state=0 order by money desc limit 1")
    TOpenDiscount selectDiscountByYoukeId(@Param("youkeId")String youkeId);

    @Select("select * from t_open_discount where youkeId=#{youkeId} and state=0 and money<#{price} order by money desc limit 1")
    TOpenDiscount selectDiscountByPriceLimit(@Param("youkeId")String youkeId,@Param("price")int price);

    @Update("update t_open_discount set state=2 where state=0 and expireTime is not null and expireTime<Now()")
    int updateExpireState();

    @Select("select id from t_open_discount where state=0 and expireTime is not null and expireTime>Now() and money=#{discount} limit 1")
    Integer selectDiscountIdByMoney(@Param("discount") Integer discount);
}