package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerAutoAddedConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IWxPerAutoAddedConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAutoAddedConfig record);

    int insertSelective(TWxPerAutoAddedConfig record);

    TWxPerAutoAddedConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAutoAddedConfig record);

    int updateByPrimaryKey(TWxPerAutoAddedConfig record);

    Map<String,Object> selectByDeviceId(@Param("id") Long id, @Param("youkeId") String youkeid);

    List<HashMap<String,Object>> queryList(String youkeId);

    @Select("SELECT * from t_wxper_autoadded_config where deviceId=#{deviceId}")
    TWxPerAutoAddedConfig selectConfigByDeviceId(Long deviceId);

    @Delete("DELETE from t_wxper_autoadded_config where deviceId=#{deviceId}")
    int deleteConfigByDeviceId(Long deviceId);

    List<Map<String,Object>> selectAddedFriendDevices();

    Map<String,Object> selectAccountById(Long id);
}