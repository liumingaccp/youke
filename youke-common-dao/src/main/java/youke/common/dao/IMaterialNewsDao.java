package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMaterialNews;

import java.util.List;

public interface IMaterialNewsDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMaterialNews record);

    TMaterialNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMaterialNews record);

    int updateByPrimaryKeyWithBLOBs(TMaterialNews record);

    int updateByPrimaryKey(TMaterialNews record);

    List<TMaterialNews> selectList(String appId);

    List<TMaterialNews> selectSubNewsById(Integer id);

    TMaterialNews selectById(@Param("id") Integer id, @Param("appId") String appId);

    @Select("SELECT * from t_material_news where appId = #{appId} and mediaId = #{mediaId} ORDER BY createTime")
    List<TMaterialNews> selectMaterialByMediaId(@Param("mediaId") String mediaId, @Param("appId") String appId);

    @Select("SELECT * from t_material_news where appId = #{appId} and thumbMediaId = #{thumbMediaId} and mediaId = #{mediaId} ORDER BY createTime")
    TMaterialNews selectMaterialByThumbMediaId(@Param("thumbMediaId") String thumbMediaId, @Param("mediaId") String mediaId, @Param("appId") String appId);
}