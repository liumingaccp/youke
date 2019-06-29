package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TRebateActiveOrder;
import youke.common.model.vo.param.helper.RebateOrderExamineParam;
import youke.common.model.vo.param.helper.RebateOrderQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.helper.RebateOrderDetailRetVo;
import youke.common.model.vo.result.helper.RebateOrderQueryRetVo;

import java.util.List;
import java.util.Map;

public interface IRebateActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TRebateActiveOrder record);

    TRebateActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRebateActiveOrder record);

    List<RebateOrderQueryRetVo> queryList(RebateOrderQueryVo params);

    RebateOrderDetailRetVo selectExamineDetail(@Param("id") Integer orderId, @Param("appId") String appId);

    void updateStateBatch(RebateOrderExamineParam param);

    TRebateActiveOrder selectByOpenId(@Param("openId") String openId, @Param("activeId")int activeId);

    @Select("select COUNT(id) from t_rebate_active_order where orderno = #{orderNo} and youkeId = #{dyk}")
    Integer selectCount(@Param("orderNo") String orderNo, @Param("dyk") String dyk);

    @Select("SELECT active.goodsId, active.fansLimit, torder.state, torder.openId FROM t_rebate_active_order torder JOIN t_rebate_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    Map<String,Object> selectOrderDetail(Long orderId);

    @Update("UPDATE t_rebate_active_order a INNER JOIN t_rebate_active b ON a.`activeId`=b.`id` SET a.state=1 WHERE a.`activeId`=b.`id` AND TIMESTAMPDIFF(Minute,a.`createTime`,NOW())>=b.`limitMinute` AND a.`state`=0")
    int updateAutoExpireState();

    int updateBatchState(@Param("orderIds")List<Long> orderIds, @Param("state")int state);

    @Select("SELECT id,orderno FROM t_rebate_active_order WHERE state=#{state} AND orderno IS NOT NULL")
    List<Map> selectOrderMapByState(@Param("state") int state);

    @Select("SELECT * FROM t_rebate_active_order WHERE state=#{state}")
    List<TRebateActiveOrder> selectOrderByState(@Param("state") int state);

    @Update("UPDATE t_rebate_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId")Long orderId, @Param("state")int state);

    @Update("UPDATE t_rebate_active_order a INNER JOIN t_rebate_active b ON a.`activeId`=b.`id` SET a.state=4 WHERE a.`activeId`=b.`id` AND TIMESTAMPDIFF(DAY,a.`orderTime`,NOW())>=b.`waitDay` AND a.`state`=3")
    int updateAutoExamineState();

    @Select("select COUNT(id) from t_rebate_active_order where openId = #{openId} and activeId= #{activeId}")
    Integer selectCountByActiveIdAndOpenId(@Param("openId") String openId, @Param("activeId") Integer activeId);

    @Select("SELECT SUM(backMoney) FROM t_rebate_active_order WHERE backMoney IS NOT NULL AND state=7 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId")String youkeId);

    @Select("SELECT id,openId, #{comeType} AS comeType,backMoney AS money, appId, youkeId FROM t_rebate_active_order  WHERE backMoney IS NOT NULL AND state=7 AND youkeId='dykw1wF3Xy'")
    List<ActivePayVo> selectActivePayVo(@Param("comeType")int comeType, @Param("youkeId")String youkeId);

    RebateOrderQueryRetVo selectOrderInfo(@Param("appId") String appId, @Param("openId") String openId, @Param("orderId") long orderId);
}