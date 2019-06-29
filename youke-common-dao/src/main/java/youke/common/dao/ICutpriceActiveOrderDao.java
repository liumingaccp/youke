package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TCutpriceActiveOrder;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.helper.CutPriceOrderRetVo;

import java.util.List;
import java.util.Map;

public interface ICutpriceActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(TCutpriceActiveOrder record);

    int insertSelective(TCutpriceActiveOrder record);

    TCutpriceActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCutpriceActiveOrder record);

    int updateByPrimaryKey(TCutpriceActiveOrder record);

    List<CutPriceOrderRetVo> selectOrderList(CutPriceOrderQueryVo params);

    @Select("SELECT * from t_cutprice_active_order where appId = #{appId} and openId =#{openId}  ORDER BY createTime DESC")
    List<TCutpriceActiveOrder> selectMyOrderList(@Param("appId") String appId, @Param("openId") String openId);

    @Update("UPDATE t_cutprice_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId") Long orderId, @Param("state") int state);

    @Select("SELECT a.id,a.orderno FROM t_cutprice_active_order a INNER JOIN t_cutprice_active b ON a.`activeId`=b.`id` WHERE a.state=#{state} AND a.orderno IS NOT NULL AND b.waitDay=0")
    List<Map> selectOrderMapByState(@Param("state") int state);

    @Update("UPDATE t_cutprice_active_order a INNER JOIN t_cutprice_active b ON a.`activeId`=b.`id` SET a.state=4 WHERE a.`activeId`=b.`id` AND ABS(TIMESTAMPDIFF(SECOND,a.`cutEndTime`,NOW())) >= (b.`waitPayMinute` * 60) AND a.`state`=2")
    int updateTimeOutState();

    @Select("SELECT * FROM t_cutprice_active_order WHERE state=#{state}")
    List<TCutpriceActiveOrder> selectOrderByState(@Param("state") int state);

    @Update("UPDATE t_cutprice_active_order a INNER JOIN t_cutprice_active b ON a.`activeId`=b.`id` SET a.state=5 WHERE a.`activeId`=b.`id` AND ABS(DATEDIFF(a.`orderTime`,NOW())) >= b.`waitDay` AND a.`state`=3")
    int updateTimeOutRebate();

    int updateBatchState(@Param("orderIds") List<Long> orderIds,@Param("state") int state);

    @Select("SELECT * from t_cutprice_active_order where appId = #{appId} and openId =#{openId} and activeId=#{activeId} and state = 0  ORDER BY createTime DESC")
    List<TCutpriceActiveOrder> selectMyOrder(@Param("appId") String appId,@Param("openId") String openId,@Param("activeId") Integer activeId);

    @Update("UPDATE t_cutprice_active_order SET state=1 WHERE NOW() > cutEndTime AND state<2")
    int updateTimeOverState();

    @Select("SELECT IFNULL(count(id),0) from t_cutprice_active_order where orderno=#{orderno}")
    int selectOrderByOrderNo(@Param("orderno") String orderno);
}