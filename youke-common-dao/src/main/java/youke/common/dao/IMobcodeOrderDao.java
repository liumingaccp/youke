package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMobcodeOrder;

import java.util.Date;
import java.util.List;

public interface IMobcodeOrderDao {
    int deleteByPrimaryKey(String oid);

    int insertSelective(TMobcodeOrder record);

    TMobcodeOrder selectByPrimaryKey(String oid);

    int updateByPrimaryKeySelective(TMobcodeOrder record);

    @Select("select state from t_mobcode_order where oid=#{oid}")
    int selectOrderState(@Param("oid") String oid);

    @Update("update t_mobcode_order set randomstr=#{ranstr},state=1,payTime=#{payTime},payType=#{payType} where oid=#{oid} and totalPrice=#{payPrice}")
    int updateOrderPayed(@Param("oid") String oid, @Param("payPrice") Integer payPrice, @Param("payTime") Date payTime, @Param("payType") String payType, @Param("ranstr") String ranstr);

    @Select("select totalCount from t_mobcode_order where oid=#{oid}")
    int selectTotalCount(@Param("oid") String oid);

    /**
     * 获取充值记录
     *
     * @param dykId
     * @return
     */
    List<TMobcodeOrder> getOrderList(String dykId);
}