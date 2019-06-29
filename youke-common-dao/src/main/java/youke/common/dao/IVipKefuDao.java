package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TVipKefu;

import java.util.Map;

public interface IVipKefuDao {
    int deleteByPrimaryKey(Long id);

    int insert(TVipKefu record);

    int insertSelective(TVipKefu record);

    TVipKefu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TVipKefu record);

    int updateByPrimaryKey(TVipKefu record);

    @Select("select url,remark from t_vip_kefu where appId = #{appId}")
    Map selectUrlByAppId(@Param("appId") String appId);

    @Delete("delete from t_vip_kefu where appId = #{appId}")
    void deleteByAppId(@Param("appId") String appId);
}