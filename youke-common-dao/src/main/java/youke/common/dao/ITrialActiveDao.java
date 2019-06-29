package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTrialActive;
import youke.common.model.vo.param.helper.TrialWealQueryVo;
import youke.common.model.vo.result.TrialActiveRetVo;
import youke.common.model.vo.result.helper.TrialWealQueryRetVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITrialActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTrialActive record);

    TTrialActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTrialActive record);

    @Delete("delete from t_trial_active where id = #{activeId} and state = 0")
    void deleteByKey(Integer activeId);

    List<TrialWealQueryRetVo> queryList(TrialWealQueryVo params);

    @Select("SELECT state FROM t_trial_active where id = #{activeId}")
    Integer selectState(Integer activeId);

    @Update("update t_trial_active set state = 3 where id = #{activeId}")
    void updateState(Integer activeId);

    @Select("SELECT id FROM t_trial_active where state IN (0,1) AND startTime <= #{st} AND endTime >= #{et} group by id")
    List<Integer> checkActive(@Param("st") Date starttime, @Param("et") Date endtime);

    List<Map<String,Date>> getActiveTimeList(@Param("startTime") Date starttime, @Param("endTime")Date endTime);

    @Update("UPDATE t_trial_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_trial_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    TrialActiveRetVo selectByPrimaryKeyAndYoukeId(@Param("id") Integer id, @Param("dyk") String dyk);

    @Select("select count(id) from t_trial_active where youkeId = #{appId}")
    Long selectActiveCount(String appId);
}