package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TConfig;

public interface IConfigDao {


    TConfig selectByPrimaryKey(String key);

    @Select("select val from t_config where `key`=#{key}")
    String selectVal(@Param("key") String key);

    @Update("update t_config set val=#{val} where `key`=#{key}")
    int updateConfig(@Param(value="key") String key, @Param(value="val") String val);

}