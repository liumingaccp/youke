package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerMultipleRecord;
import youke.common.model.vo.param.wxper.MassTaskRecordQueryVo;

import java.util.List;
import java.util.Map;

public interface IWxPerMultipleRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerMultipleRecord record);

    int insertSelective(TWxPerMultipleRecord record);

    TWxPerMultipleRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerMultipleRecord record);

    int updateByPrimaryKey(TWxPerMultipleRecord record);

    List<Map<String,Object>> selectList(MassTaskRecordQueryVo params);

    @Update("update t_wxper_multiple_record set state = #{state} where taskId = #{taskId}")
    void updateByTaskId(@Param("taskId") Long id, @Param("state") int state);

    @Select("select * from t_wxper_multiple_record where taskId = #{taskId}")
    TWxPerMultipleRecord getByTaskId(long taskId);

    @Update("update t_wxper_multiple_record set state = 4 where taskId in (select taskId from t_wxper_op_log where opType = 0 and state = 8) and state != 4")
    void updateReturn();
}