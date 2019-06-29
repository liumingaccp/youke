package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerAutoAddedTaskRecord;

import java.util.List;

public interface IWxPerAutoAddedTaskRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoAddedTaskRecord record);

    int insertSelective(TWxPerAutoAddedTaskRecord record);

    TWxPerAutoAddedTaskRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoAddedTaskRecord record);

    int updateByPrimaryKey(TWxPerAutoAddedTaskRecord record);

    @Update("update t_wxper_autoadded_task_record set state = #{state} where taskId = #{taskId}")
    void updateByTaskId(@Param("taskId") Long id, @Param("state") int state);

    @Select("select * from t_wxper_autoadded_task_record where taskId = #{taskId}")
    List<TWxPerAutoAddedTaskRecord> getByTaskId(@Param("taskId") Long taskId);

    @Update("update t_wxper_autoadded_task_record set alreadyExecuteNum = expectExecuteNum - #{count} where taskId = #{taskId}")
    void updateAlreadyExecuteNum(@Param("taskId") long taskId, @Param("count") Integer count);

    @Update("update t_wxper_autoadded_task_record set state = 4 where taskId in (select taskId from t_wxper_op_log where opType = 2 and state = 8) and state != 4")
    void updateReturn();
}