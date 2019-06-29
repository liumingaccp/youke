package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TSubscr;

import java.util.List;
import java.util.Map;

public interface ISubscrDao {
    int deleteByPrimaryKey(String appid);

    int insertSelective(TSubscr record);

    TSubscr selectByPrimaryKey(String appid);

    int updateByPrimaryKeySelective(TSubscr record);

    @Select("select appId from t_subscr where youkeId=#{youkeId} and state=1 limit 1")
    String selectLoginAppId(@Param(value = "youkeId") String youkeId);

    @Select("select refreshToken from t_subscr where appId=#{appId}")
    String selectRefreshToken(@Param(value = "appId") String appId);

    @Update("update t_subscr set refreshToken=#{refreshToken} where appId=#{appId}")
    int updateRefreshToken(@Param(value = "appId") String appId, @Param(value = "refreshToken") String refreshToken);

    @Select("select count(1) from t_subscr where appId=#{appId}")
    int selectCount(@Param(value = "appId") String appId);

    @Update("update t_subscr set state=#{state} where appId=#{appId}")
    int updateState(@Param(value = "appId") String appId, @Param(value = "state") int state);

    @Select("select serviceType from t_subscr where appId=#{appId}")
    int getSubScrtype(@Param(value = "appId") String appId);

    @Select("select youkeId from t_subscr where appId=#{appId}")
    String selectDyk(@Param(value = "appId") String appId);

    @Select("select nickName from t_subscr where appId=#{appId}")
    String selectNickname(@Param(value = "appId") String appId);

    TSubscr selectByYoukeId(String id);

    @Select("SELECT appId, youkeId,createTime from t_subscr where state = 1")
    List<Map> selectAll();
}