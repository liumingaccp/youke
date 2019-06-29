package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMaterialFile;

import java.util.List;

public interface IMaterialFileDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMaterialFile record);

    TMaterialFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMaterialFile record);

    List<TMaterialFile> selectList(String appId);

    @Select("select mediaId from t_material_file where id=#{id}")
    String selectMediaIdById(@Param(value = "id") int id);

    TMaterialFile selectByMateriaId(String materialId);

    @Select("SELECT * from t_material_file where appId=#{appId} and mediaId=#{mediaId}")
    TMaterialFile selectMaterialByMediaId(@Param("mediaId") String mediaId, @Param("appId") String appId);
}