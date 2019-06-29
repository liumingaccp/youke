package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TRebateActiveOrder;
import youke.common.model.TTrialActiveOrder;
import youke.common.model.vo.param.helper.TrialWealOrderExamineParam;
import youke.common.model.vo.param.helper.TrialWealOrderQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.helper.TrialWealOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderQueryRetVo;

import java.util.List;
import java.util.Map;

public interface ITrialActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TTrialActiveOrder record);

    TTrialActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTrialActiveOrder record);

    List<TrialWealOrderQueryRetVo> queryList(TrialWealOrderQueryVo params);

    TrialWealOrderDetailRetVo selectExamineDetail(@Param("id") Long orderId, @Param("appId") String appId);

    void updateStateBatch(TrialWealOrderExamineParam param);

    @Select("SELECT active.goodsId,active.fansLimit, torder.state,torder.openId FROM t_trial_active_order torder JOIN t_trial_active active ON torder.activeId = active.id WHERE torder.id = #{orderId}")
    Map<String, Object> selectOrderDetail(Long orderId);

    @Select("select COUNT(id) from t_trial_active_order where orderno = #{orderNo} and youkeId = #{dyk}")
    Integer selectCount(@Param("orderNo") String orderNo, @Param("dyk") String dyk);

    int updateBatchState(@Param("orderIds")List<Long> orderIds, @Param("state")int state);

    @Select("SELECT id,orderno FROM t_trial_active_order WHERE state=#{state} AND orderno IS NOT NULL")
    List<Map> selectOrderMapByState(@Param("state") int state);

    @Select("SELECT * FROM t_trial_active_order WHERE state=#{state}")
    List<TTrialActiveOrder> selectOrderByState(@Param("state") int state);

    @Update("UPDATE t_trial_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId")Long orderId, @Param("state")int state);

    @Update("UPDATE t_trial_active_order a INNER JOIN t_trial_active b ON a.`activeId`=b.`id` SET a.state=4 WHERE a.`activeId`=b.`id` AND TIMESTAMPDIFF(DAY,a.`orderTime`,NOW())>=b.`waitDay` AND a.`state`=3")
    int updateAutoExamineState();

    @Select("SELECT SUM(backReward) FROM t_trial_active_order WHERE rewardType=0 AND backReward IS NOT NULL AND state=7 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId")String youkeId);

    @Select("SELECT id,openId, #{comeType} AS comeType,backReward AS money, appId,youkeId FROM t_trial_active_order  WHERE rewardType=0 AND backReward IS NOT NULL AND state=7 AND youkeId=#{youkeId}")
    List<ActivePayVo> selectActivePayVo(@Param("comeType")int comeType, @Param("youkeId")String youkeId);

    Integer selectJoinCount(@Param("activeId") Integer id, @Param("youkeId") String youkeId, @Param("typeId") Integer i);

    TrialWealOrderQueryRetVo selectOrderInfo(@Param("appId") String appId, @Param("openId") String openId, @Param("orderId") long orderId);
}