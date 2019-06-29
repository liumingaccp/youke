package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TFunc;
import youke.common.model.vo.param.KeyValVo;

import java.util.List;

public interface IFuncDao {
    int deleteByPrimaryKey(String id);

    int insert(TFunc record);

    int insertSelective(TFunc record);

    TFunc selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TFunc record);

    int updateByPrimaryKey(TFunc record);

    @Select("SELECT a.id AS `key`,a.title AS `val` FROM t_position_func b INNER JOIN t_func a ON b.funcId=a.id WHERE b.positionId=#{positionId}")
    List<KeyValVo> selectPositionFuncs(@Param("positionId") Integer positionId);

    @Select("SELECT id AS `key`,title AS val FROM t_func WHERE vips LIKE #{vip} order by sort")
    List<KeyValVo> selectFuncs(@Param("vip") String vip);

    @Select("SELECT id AS `key`,title AS val FROM t_func WHERE state=0 and vips LIKE #{vip} order by sort")
    List<KeyValVo> selectDefaultFuncs(@Param("vip") String vip);

    @Select("SELECT * FROM t_func WHERE vips LIKE #{vip} order by sort")
    List<TFunc> selectFuncsByVip(@Param("vip") String vip);
}