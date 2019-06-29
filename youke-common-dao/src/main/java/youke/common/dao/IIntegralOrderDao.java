package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TIntegralOrder;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.IntegralOrderRetVo;
import youke.common.model.vo.result.IntegralOrderVo;

import java.util.List;
import java.util.Map;

public interface IIntegralOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TIntegralOrder record);

    TIntegralOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TIntegralOrder record);

    List<IntegralOrderVo> queryList(WealOrderQueryVo params);

    long countIntegral(String dykId);

    List<IntegralOrderRetVo> queryListByOpenId(@Param("appId") String appId, @Param("openId") String openId);

    @Select("SELECT active.goodsId FROM t_integral_active_order torder JOIN t_integral_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    String selectGoodId(@Param("orderId")Long orderId);

    @Update("UPDATE t_integral_order a INNER JOIN t_integral_active b ON a.`activeId`=b.`id` SET a.state=5 WHERE a.`activeId`=b.`id` AND TIMESTAMPDIFF(HOUR,a.`createTime`,NOW())>=b.`validHour` AND a.`state`=0")
    Integer updateAutoExpireState();

    Integer updateBatchState(@Param("orderIds")List<Long> orderIds, @Param("state")int state);

    @Select("SELECT active.goodsId, torder.state, torder.openId FROM t_integral_order torder JOIN t_integral_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    Map<String, Object> selectOrderDetail(Long orderId);

    @Select("SELECT id,orderno FROM t_integral_order WHERE state=#{state} AND orderno IS NOT NULL")
    List<Map> selectOrderMapByState(@Param("state") int state);

    @Select("select COUNT(id) from t_integral_order where orderno = #{orderNo} and appId = #{appId}")
    Integer selectCount(@Param("orderNo") String orderNo, @Param("appId") String appId);

    @Update("update t_integral_order set state=#{state},backTime=NOW() where id=#{id}")
    int updateState(@Param("id") long id, @Param("state") int state);

    @Select("SELECT SUM(backMoney) FROM t_integral_order WHERE backMoney IS NOT NULL AND state=4 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId")String youkeId);

    @Select("SELECT id,openId, #{comeType} as comeType,backMoney AS money,appId,youkeId FROM t_integral_order  WHERE backMoney IS NOT NULL AND state=4 AND youkeId=#{youkeId}")
    List<ActivePayVo> selectActivePayVo(@Param("comeType")int comeType,@Param("youkeId")String youkeId);

    IntegralOrderRetVo selectOrderInfo(@Param("orderId") int orderId, @Param("appId") String appId, @Param("openId") String openId);

    Long selectInttegralCount(WealOrderQueryVo params);
}