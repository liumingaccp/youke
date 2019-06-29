package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TMassTaskOpenId;
import youke.common.model.vo.result.MassFansVo;

import java.util.List;

public interface IMassTaskOpenIdDao {
    int deleteByPrimaryKey(Long id);

    int insert(TMassTaskOpenId record);

    int insertSelective(TMassTaskOpenId record);

    TMassTaskOpenId selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMassTaskOpenId record);

    int updateByPrimaryKey(TMassTaskOpenId record);

    @Select("select openid from t_mass_task_openId where taskId = #{taskId} and appId = #{appId}")
    List<String> selectByTaskId(@Param("taskId") Integer id, @Param("appId") String appId);

    List<MassFansVo> selectFansByTaskId(@Param("taskId") Integer id, @Param("appId") String appId);

    @Update("update t_mass_task_openId set state =#{state} where openId=#{openId} and taskId=#{taskId}")
    void updateByTaskAndOpendId(@Param("openId") String openId, @Param("taskId") int taskId, @Param("state") int state);

    @Update("update t_mass_task_openId set state =#{state} where appId=#{appId} and taskId=#{taskId}")
    void updateStateByTaskId(@Param("taskId") int taskId, @Param("appId") String appId,  @Param("state") int state);
}