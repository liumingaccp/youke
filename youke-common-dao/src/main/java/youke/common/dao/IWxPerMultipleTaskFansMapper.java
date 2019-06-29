package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerMultipleTaskFans;

import java.util.List;

public interface IWxPerMultipleTaskFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerMultipleTaskFans record);

    int insertSelective(TWxPerMultipleTaskFans record);

    TWxPerMultipleTaskFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerMultipleTaskFans record);

    int updateByPrimaryKey(TWxPerMultipleTaskFans record);

    void insertBatch(@Param("list") List<TWxPerMultipleTaskFans> bacthList);

    @Update("update t_wxper_multiple_task_fans set state = #{state} where taskId = #{taskId}")
    void updateByTaskId(@Param("taskId") Long id, @Param("state") int state);

    @Select("select t2.nickName from t_wxper_multiple_task_fans t1 join t_wxper_fans t2 on t1.fanId = t2.id where taskId = #{taskId}")
    List<String> selectNickNameByTaskId(Long taskId);
}