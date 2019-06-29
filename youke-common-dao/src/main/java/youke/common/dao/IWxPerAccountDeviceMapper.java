package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerAccountDevice;

import java.util.ArrayList;
import java.util.HashMap;

public interface IWxPerAccountDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerAccountDevice record);

    int insertSelective(TWxPerAccountDevice record);

    TWxPerAccountDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerAccountDevice record);

    int updateByPrimaryKey(TWxPerAccountDevice record);

    /**
     * 通过设备id查询中间表
     *
     * @param deviceId
     * @return
     */
    TWxPerAccountDevice selectByDeviceId(@Param("deviceId") long deviceId, @Param("state") Integer state);

    /**
     * 通过设备id查询中间表
     *
     * @param accountId
     * @return
     */
    @Select("SELECT * from t_wxper_account_device where accountId = #{accountId} AND state = 1")
    TWxPerAccountDevice selectByAccountId(@Param("accountId") Long accountId);

    ArrayList<HashMap<String, Object>> selectAccountAndDevices(@Param("appId") String appId, @Param("youkeId") String youkeId);

    @Delete("DELETE from t_wxper_account_device where deviceId = #{deviceId} and wxAccountId = #{accountId}")
    int deleteAccountDeviceByPrimaryKey(@Param("deviceId") Long deviceId, @Param("accountId") Long accountId);
}