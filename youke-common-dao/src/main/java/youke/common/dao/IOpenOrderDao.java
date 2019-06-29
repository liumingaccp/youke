package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TOpenOrder;

import java.util.Date;

public interface IOpenOrderDao {
    int deleteByPrimaryKey(String oid);

    int insertSelective(TOpenOrder record);

    TOpenOrder selectByPrimaryKey(String oid);

    int updateByPrimaryKeySelective(TOpenOrder record);

    @Update("update t_open_order set randomstr=#{ranstr},state=1,payTime=#{payTime},payType=#{payType} where oid=#{oid} and totalPrice=#{payPrice}")
    int updateOrderPayed(@Param("oid")String oid, @Param("payPrice")Integer payPrice, @Param("payTime")Date payTime, @Param("payType")String payType, @Param("ranstr")String ranstr);

    @Select("select state from t_open_order where oid=#{oid}")
    int selectOrderState(@Param("oid")String oid);
}