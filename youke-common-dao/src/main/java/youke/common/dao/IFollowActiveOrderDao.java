package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TFollowActiveOrder;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.result.ActivePayVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;

import java.util.List;

public interface IFollowActiveOrderDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TFollowActiveOrder record);

    TFollowActiveOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TFollowActiveOrder record);

    List<FollowOrderQueryRetVo> queryList(FollowOrderQueryVo params);

    @Select("SELECT SUM(backMoney) FROM t_follow_active_order WHERE activeId = #{activeId} AND appId = #{appId}")
    Integer querySum(@Param("activeId") Integer id, @Param("appId") String appId);

    @Update("UPDATE t_follow_active_order set state=#{state},backTime=NOW() where id=#{orderId}")
    int updateState(@Param("orderId")Long orderId, @Param("state")int state);

    @Select("SELECT SUM(backMoney) FROM t_follow_active_order WHERE backMoney IS NOT NULL AND state=4 AND youkeId=#{youkeId}")
    Integer selectTotalFailMoney(@Param("youkeId")String youkeId);

    @Select("SELECT id,openId, #{comeType} AS comeType,backMoney AS money, appId,youkeId FROM t_follow_active_order  WHERE backMoney IS NOT NULL AND state=4 AND youkeId=#{youkeId}")
    List<ActivePayVo> selectActivePayVo(@Param("comeType")int comeType, @Param("youkeId")String youkeId);
}