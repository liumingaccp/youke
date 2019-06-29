package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TFollowActive;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IFollowActiveDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TFollowActive record);

    TFollowActive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TFollowActive record);

    void deleteByKey(Integer activeId);

    List<FollowQueryRetVo> queryList(FollowQueryVo params);

    @Select("SELECT state FROM t_follow_active where id = #{activeId}")
    Integer selectState(Integer activeId);

    @Update("update t_follow_active set state = 3 where id = #{activeId}")
    void updateState(Integer activeId);

    List<Map<String,Object>> getActiveTimeList(@Param("startTime") Date starttime, @Param("endTime")Date endTime, @Param("youkeId") String youkeId);

    @Update("UPDATE t_follow_active SET state=1 WHERE NOW() BETWEEN startTime AND endTime AND state=0")
    int updateStateIng();

    @Update("UPDATE t_follow_active SET state=2 WHERE NOW() > endTime AND state<2")
    int updateStateEnd();

    @Select("select * from t_follow_active where id = #{id} and youkeId = #{dyk}")
    TFollowActive selectByPrimaryKeyAndYoukeId(@Param("id") Integer id, @Param("dyk") String dyk);

    @Select("select count(id) from t_follow_active where appId = #{appId}")
    Long selectActiveCount(String appId);
}