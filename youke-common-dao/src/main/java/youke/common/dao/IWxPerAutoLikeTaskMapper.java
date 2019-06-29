package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import youke.common.model.TWxPerAutoLikeTask;
import youke.common.model.vo.result.wxper.AutoLikeTaskVo;

import java.util.List;

public interface IWxPerAutoLikeTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoLikeTask record);

    int insertSelective(TWxPerAutoLikeTask record);

    TWxPerAutoLikeTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoLikeTask record);

    int updateByPrimaryKey(TWxPerAutoLikeTask record);

    List<AutoLikeTaskVo> selectAutoLikeTaskList(@Param("dykId") String dykId);

    @Delete("DELETE from t_wxper_autolike_task where deviceId=#{deviceId}")
    int deleteTaskByDeviceId(@Param("deviceId") Long deviceId);
}