package youke.common.dao;

import org.apache.ibatis.annotations.Select;
import youke.common.model.TMaterialText;

import java.util.List;

public interface IMaterialTextDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TMaterialText record);

    TMaterialText selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TMaterialText record);

    int updateByPrimaryKeyWithBLOBs(TMaterialText record);

    int updateByPrimaryKey(TMaterialText record);

    List<TMaterialText> selectList(String appId);
}