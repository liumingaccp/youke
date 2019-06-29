package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxPerConfig;

public interface IWxPerConfigMapper {
    int deleteByPrimaryKey(String youkeid);

    int insert(TWxPerConfig record);

    int insertSelective(TWxPerConfig record);

    TWxPerConfig selectByPrimaryKey(String youkeid);

    int updateByPrimaryKeySelective(TWxPerConfig record);

    int updateByPrimaryKey(TWxPerConfig record);

    @Update("update t_wxper_config set openDistinct = #{open} where youkeId =#{youkeId}")
    void updateOpenDistinct(@Param("appId") String appId, @Param("youkeId") String youkeId, @Param("open") Integer open);

    @Update("update t_wxper_config set dailyLimit = #{num} where youkeId =#{youkeId}")
    void updateDailyLimit(@Param("appId") String appId, @Param("youkeId") String youkeId, @Param("num") Integer num);

    @Select("select * from t_wxper_config where youkeId = #{youkeId}")
    TWxPerConfig selectOne(@Param("appId") String appId, @Param("youkeId") String youkeId);
}