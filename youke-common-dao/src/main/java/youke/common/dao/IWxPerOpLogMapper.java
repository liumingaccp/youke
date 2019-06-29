package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TWxperOpLog;

import java.util.List;

public interface IWxPerOpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxperOpLog record);

    int insertSelective(TWxperOpLog record);

    TWxperOpLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxperOpLog record);

    int updateByPrimaryKey(TWxperOpLog record);

    List<TWxperOpLog> selectExecutableOps();

    @Update("update t_wxper_op_log set state = #{state}, realOpTime = now(), updateTime = now() where taskId = #{taskId} and opType = #{opType} and wxAccountId = #{wxAccountId} and deviceId =#{deviceId}")
    void updateStateByTaskId(@Param("wxAccountId")Long wxaccountid, @Param("deviceId") Long deviceId,  @Param("taskId") Long taskId, @Param("state") int state, @Param("opType") int opType);

    List<TWxperOpLog> selectByOpTypeAndTaskId(@Param("opType") Integer addFriend, @Param("taskId") Long id, @Param("states") List<Integer> stateList);

    @Select("select * from t_wxper_op_log where deviceId = #{deviceId} and wxAccountId =#{wxAccountId} and state = 2")
    List<TWxperOpLog> selectNoresOpList(@Param("deviceId") Long id, @Param("wxAccountId") Long wxaccountid);

    @Update("update t_wxper_op_log set state = 8 where state = 2 and abs(TIMESTAMPDIFF(MINUTE,updateTime,NOW())) > 5")
    void updateReturn();

    @Update("update t_wxper_op_log set state = 4 where state = 1 and opType = #{type} and abs (TIMESTAMPDIFF(MINUTE,opTime,NOW())) > 5")
    void updateUnPush(@Param("type") int type);
}