package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerDevice;
import youke.common.model.vo.param.wxper.DeviceQueryVo;
import youke.common.model.vo.result.wxper.DeviceQueryRetVo;
import youke.common.model.vo.result.wxper.DeviceSelectRetVo;

import java.util.List;
import java.util.Map;

public interface IWxPerDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerDevice record);

    int insertSelective(TWxPerDevice record);

    TWxPerDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerDevice record);

    int updateByPrimaryKey(TWxPerDevice record);

    List<DeviceQueryRetVo> selectDeviceManageList(DeviceQueryVo params);

    @Select("SELECT id, deviceName, state from t_wxper_device where youkeId=#{dykId} AND state!=2")
    List<DeviceSelectRetVo> selectDeviceSelectList(@Param("dykId") String dykId);

    @Select("SELECT clientId from t_wxper_device where id = #{id} AND state!=2")
    String selectClientIdByPrimaryKey(@Param("id") Long id);

    @Select("SELECT IFNULL(count(id),0) from t_wxper_device where youkeId=#{youkeId} AND state!=2")
    int selectUseNum(@Param("youkeId") String youkeId);

    @Select("SELECT * from t_wxper_device where imel=#{imel}")
    TWxPerDevice selectDeviceByImel(@Param("imel") String imel);

    @Select("SELECT wxPersonal from t_wxper_device where id=#{id}")
    String selectWxPersonalByPrimaryKey(@Param("id") Long id);

    @Select("SELECT deviceName from t_wxper_device where id=#{id}")
    String selectDeviceNameByPrimaryKey(@Param("deviceId") Long id);

    @Select("SELECT * from t_wxper_device where clientId = #{clientId}")
    TWxPerDevice selectDeviceByClientId(String clientId);

    @Select("SELECT wxAccountId from t_wxper_device where id=#{deviceId}")
    Long selectAccountIdByPrimaryKey(@Param("deviceId") Long deviceId);

    @Select("SELECT t1.id,t1.wxAccountId,t1.youkeId, t2.addnum,t2.lastAddFriendTime FROM t_wxper_device t1 JOIN t_wxper_account t2 ON t1.wxAccountId = t2.id join t_wxper_config t3 on t1.youkeId = t3.youkeId WHERE t1.state = 1 and t2.wxPersonal is not null and (t2.addNum is null or t2.addNum < t3.dailyLimit)")
    List<Map<String, Object>> selectAddFriendDevices();

    @Select("SELECT * FROM t_wxper_device WHERE state = 1")
    List<TWxPerDevice> selectAvailableDevices();

    @Select("SELECT id, clientId, wxAccountId from t_wxper_device where clientId IS NOT NULL")
    List<TWxPerDevice> selectAllDeviceClientIds();

    @Select("SELECT id, clientId, wxAccountId from t_wxper_device where state = 1 AND clientId IS NOT NULL")
    List<TWxPerDevice> selectOnLineDeviceClientIds();

    @Select("select * from t_wxper_device where state = 1 and youkeId = #{youkeId}")
    List<TWxPerDevice> selectByYoukeId(String youkeId);

    @Select("select * from t_wxper_device where weChatNum = #{weChatNum} limit 1")
    TWxPerDevice selectDeviceByWechatnum(@Param("weChatNum") String wechatnum);
}