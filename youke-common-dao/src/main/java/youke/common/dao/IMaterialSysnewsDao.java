package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TMaterialSysnews;

import java.util.List;

public interface IMaterialSysnewsDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMaterialSysnews record);

    TMaterialSysnews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMaterialSysnews record);

    List<TMaterialSysnews> selectSubSysNewsById(Integer id);

    TMaterialSysnews selectById(@Param("id") Integer id, @Param("appId") String appId);

    List<TMaterialSysnews> selectList(@Param("appId") String appId);

    List<TMaterialSysnews> selectByMediaId(@Param("appId")String appId);

    void deleteNewsById(Integer id);

    List<TMaterialSysnews> selectSysList(@Param("appId")String appId);
}