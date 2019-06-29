package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerDeviceGrow;

public interface IWxPerDeviceGrowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerDeviceGrow record);

    int insertSelective(TWxPerDeviceGrow record);

    TWxPerDeviceGrow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerDeviceGrow record);

    int updateByPrimaryKey(TWxPerDeviceGrow record);

    @Select("SELECT * from t_wxper_device_grow where deviceId=#{deviceId} AND youkeId=#{youkeId}")
    TWxPerDeviceGrow selectGrowConfigByDeviceId(@Param("deviceId") Long deviceId, @Param("youkeId") String youkeId);

    @Delete("DELETE from t_wxper_device_grow where deviceId=#{deviceId}")
    int delectTaskByDeviceId(@Param("deviceId") Long deviceId);
}