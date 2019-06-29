package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TMaterialImage;

import java.util.List;

public interface IMaterialImageDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMaterialImage record);

    TMaterialImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMaterialImage record);

    @Select("select * from t_material_image where appId=#{appId} and state = 0")
    List<TMaterialImage> selectList(@Param(value = "appId") String appId);

    @Select("select mediaId from t_material_image where id=#{id}")
    String selectMediaIdById(@Param(value = "id") int id);

    List<TMaterialImage> selectMediaidById(@Param("ids") List<Integer> ids, @Param("appId") String appId);

    void deleteBacthById(@Param("ids") List<Integer> ids, @Param("appId") String appId);

    @Select("select * from t_material_image where appId=#{appId} and id=#{id}")
    TMaterialImage select(@Param("id") int id, @Param("appId") String appId);

    @Select("select * from t_material_image where appId=#{appId} and mediaId=#{mediaId}")
    TMaterialImage selectMaterialByMediaId(@Param("mediaId") String mediaId, @Param("appId") String appId);

}