package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerAutoAddedTaskFans;

import java.util.List;

public interface IWxPerAutoAddedTaskFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoAddedTaskFans record);

    int insertSelective(TWxPerAutoAddedTaskFans record);

    TWxPerAutoAddedTaskFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoAddedTaskFans record);

    int updateByPrimaryKey(TWxPerAutoAddedTaskFans record);

    void insertBacth(@Param("fansList") List<TWxPerAutoAddedTaskFans> fansList);

    @Update("update t_wxper_autoadded_task_fans set state = 2 where taskId = #{taskId}")
    void deleteByTaskId(Long taskId);

    void updatePriority(@Param("taskId") Long id, @Param("priority") int priority);

    @Update("update t_wxper_autoadded_task_fans set state = #{state} where taskId = #{taskId}")
    void updateByTaskId(@Param("taskId") Long id, @Param("state") int flag);

    List<TWxPerAutoAddedTaskFans> selectByPriority(@Param("priority") int priority, @Param("limit") int limitFans ,@Param("state")int state ,@Param("ids") List<Long> taskIdList);

    @Select("select * from t_wxper_autoadded_task_fans where state = #{state} and taskId = #{taskId}")
    List<TWxPerAutoAddedTaskFans> selectByTaskIdAndState(@Param("taskId") Long id, @Param("state") int i);

    Integer selectCountByTaskIdAndSatete(@Param("taskId") Long taskId, @Param("state") int state);

    void updateBantch(@Param("fansIds") List<Long> fansIds);

    @Update("update t_wxper_autoadded_task_fans set state = 1, name = #{name} where mobile = #{mobile} and taskId = #{taskId}")
    void updateByMobile(@Param("mobile") String mobile, @Param("name") String name, @Param("taskId") long taskId);

    List<Long> checkFansId(@Param("mobile") String mobile, @Param("list") List<Long> list);

    @Select("SELECT name, mobile from t_wxper_autoadded_task_fans where name = #{nickName} limit 1")
    TWxPerAutoAddedTaskFans selectFansByNickName(@Param("nickName") String nickName);

    @Select("SELECT * from t_wxper_autoadded_task_fans where state < 3 AND name is not null")
    List<TWxPerAutoAddedTaskFans> selectUnSyncFans();
}