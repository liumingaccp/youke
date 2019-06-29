package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TRebateActive;
import youke.common.model.vo.param.helper.RebateQueryVo;
import youke.common.model.vo.result.helper.RebateQueryRetVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRebateActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TRebateActive record);

    TRebateActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRebateActive record);

    void deleteByKey(Integer activeId);

    List<RebateQueryRetVo> queryList(RebateQueryVo params);

    @Select("SELECT state FROM t_rebate_active where id = #{activeId}")
    Integer selectState(Integer activeId);

    @Update("update t_rebate_active set state = 3 where id = #{activeId}")
    void updateState(Integer activeId);

    List<Map<String,Date>> getActiveTimeList(@Param("startTime") Date starttime, @Param("endTime")Date endTime);

    @Update("UPDATE t_rebate_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_rebate_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    @Select("select * from t_rebate_active where id = #{id} and youkeId = #{dyk}")
    TRebateActive selectByPrimaryKeyAndDyk(@Param("id") Integer activeId, @Param("dyk") String dyk);

    @Select("select count(id) from t_rebate_active where youkeId = #{appId}")
    Long selectActiveCount(String appId);
}