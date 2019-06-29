package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TPosition;

import java.util.List;

public interface IPositionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TPosition record);

    int insertSelective(TPosition record);

    TPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPosition record);

    int updateByPrimaryKey(TPosition record);

    @Select("select * from t_position where youkeId=#{dykId}")
    List<TPosition> selectPositions(@Param("dykId")String dykId);

    @Select("select count(1) from t_user_position where positionId=#{id}")
    int selectPositionUserCount(@Param("id") int id);
}