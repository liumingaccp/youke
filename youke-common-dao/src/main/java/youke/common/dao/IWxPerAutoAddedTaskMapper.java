package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerAutoaddedTask;

import java.util.HashMap;
import java.util.List;

public interface IWxPerAutoAddedTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoaddedTask record);

    int insertSelective(TWxPerAutoaddedTask record);

    TWxPerAutoaddedTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoaddedTask record);

    int updateByPrimaryKey(TWxPerAutoaddedTask record);

    /**
     *
     * @param model 的id
     *  如果是 -1 则针对所有任务
     *  如果大于0 则指定任务
     * @param model priority
     *  优先级
     */
    void updatePriority(TWxPerAutoaddedTask model);

    List<HashMap<String,Object>> queryList(String youkeId);

    HashMap<String,Object> selectById(@Param("id") Long id, @Param("youkeId") String youkeid);

    @Update("update t_wxper_autoadded_task set state = #{state} where id = #{taskId}")
    void updateStateByTaskId(@Param("taskId") Long taskId, @Param("state") int state);


    @Select("select * from t_wxper_autoadded_task where deviceId = #{deviceId} and wxAccountId = #{wxAccountId} and ( state = 0  or state = 1 )and groupId > 0")
    List<TWxPerAutoaddedTask> selectTaskByDeviceId(@Param("deviceId") Long deviceId, @Param("wxAccountId") Long wxaccountid);

    @Select("select * from t_wxper_autoadded_task where groupId = #{taskId}")
    List<TWxPerAutoaddedTask> selectByGroupId(Long taskId);

}