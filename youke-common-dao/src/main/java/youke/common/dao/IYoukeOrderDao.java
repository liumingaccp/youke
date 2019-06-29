package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TYoukeOrder;

import java.util.Date;

public interface IYoukeOrderDao {
    int deleteByPrimaryKey(String oid);

    int insertSelective(TYoukeOrder record);

    TYoukeOrder selectByPrimaryKey(String oid);

    int updateByPrimaryKeySelective(TYoukeOrder record);

    @Select("select state from t_youke_order where oid=#{oid}")
    int selectOrderState(@Param("oid") String oid);

    @Update("update t_youke_order set randomstr=#{ranstr},state=1,payTime=#{payTime},payType=#{payType} where oid=#{oid} and totalPrice=#{payPrice}")
    int updateOrderPayed(@Param("oid")String oid, @Param("payPrice")Integer payPrice, @Param("payTime")Date payTime, @Param("payType")String payType, @Param("ranstr")String ranstr);

}