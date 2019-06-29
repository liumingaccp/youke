package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TCutpriceActiveFans;

import java.util.List;

public interface ICutpriceActiveFansDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCutpriceActiveFans record);

    int insertSelective(TCutpriceActiveFans record);

    TCutpriceActiveFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCutpriceActiveFans record);

    int updateByPrimaryKey(TCutpriceActiveFans record);

    @Select("SELECT IFNULL(count(id),0) from t_cutprice_active_fans where openId = #{openId} and orderId = #{orderId}")
    int selectFansRecordNum(@Param("openId") String openId, @Param("orderId") Long orderId);

    @Select("SELECT COUNT(IFNULL(tcaf.id,0)) FROM t_cutprice_active_fans tcaf WHERE TO_DAYS(createTime) = TO_DAYS(NOW()) AND tcaf.openId = #{openId} and orderId NOT IN (SELECT id from t_cutprice_active_order WHERE openId=#{openId})")
    int selectJoinNum(@Param("openId") String openId);

    @Select("SELECT * from t_cutprice_active_fans where orderId = #{orderId} ORDER BY createTime DESC")
    List<TCutpriceActiveFans> selectFansListByOrderId(@Param("orderId") String orderId);
}