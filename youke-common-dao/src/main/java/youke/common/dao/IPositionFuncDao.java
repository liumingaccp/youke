package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import youke.common.model.TPositionFunc;

public interface IPositionFuncDao {
    int deleteByPrimaryKey(Long id);

    int insert(TPositionFunc record);

    int insertSelective(TPositionFunc record);

    TPositionFunc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TPositionFunc record);

    int updateByPrimaryKey(TPositionFunc record);

    @Delete("delete FROM t_position_func WHERE positionId=#{positionId}")
    int deletePositionFuncs(@Param("positionId") int positionId);
}