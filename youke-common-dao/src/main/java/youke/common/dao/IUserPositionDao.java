package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TPosition;
import youke.common.model.TUserPosition;
import youke.common.model.vo.param.KeyValVo;

import java.util.List;
import java.util.Map;

public interface IUserPositionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TUserPosition record);

    int insertSelective(TUserPosition record);

    TUserPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUserPosition record);

    int updateByPrimaryKey(TUserPosition record);

    @Delete("delete from t_user_position where positionId=#{positionId} and userId=#{userId}")
    int deleteUserPosition(@Param("positionId")int positionId, @Param("userId")int userId);

    @Select("SELECT b.* FROM t_user_position a INNER JOIN t_position b ON a.`positionId`=b.`id` WHERE a.userId=#{userId} order by b.createTime desc")
    List<TPosition> selectByUserId(@Param("userId") int userId);

    @Select("SELECT c.id as `key`,c.title as val FROM t_user_position a INNER JOIN t_position_func b ON a.`positionId`=b.`positionId` INNER JOIN t_func c ON b.`funcId`=c.`id` WHERE a.userId=#{userId}")
    List<KeyValVo> selectFuncsByUserId(@Param("userId") int userId);
}