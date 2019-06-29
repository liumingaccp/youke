package youke.common.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TWxPerFans;
import youke.common.model.vo.param.wxper.WxPerFansQueryVo;

import java.util.HashMap;
import java.util.List;

public interface IWxPerFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TWxPerFans record);

    int insertSelective(TWxPerFans record);

    TWxPerFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TWxPerFans record);

    int updateByPrimaryKey(TWxPerFans record);

    List<HashMap<String, Object>> queryList(WxPerFansQueryVo qo);

    List<Long> selectFansIdsByTagIds(@Param("tagIds") List<Integer> tagIds, @Param("accountId") Long accountId, @Param("tagCount") Integer tagCount);

    HashMap<String, Object> selectById(@Param("id") Long id, @Param("appId") String appId, @Param("youkeId") String youkeId);

    @Select("SELECT id from t_wxper_fans where ownWeChatNum=#{weChatNum}")
    List<Long> selectFansByOwnWeChatNum(@Param("weChatNum") String weChatNum);

    @Select("SELECT * from t_wxper_fans where weChatNum=#{weChatNum} and ownWeChatNum = #{ownWeChatNum}")
    TWxPerFans selectFansByWeChatNum(@Param("weChatNum") String weChatNum, @Param("ownWeChatNum") String ownWeChatNum);

    @Select("SELECT id from t_wxper_fans where nickName = #{name} and mobile is null limit 1")
    TWxPerFans selectFansByNickName(@Param("name") String name);

    @Select("SELECT id, mobile, nickName, appId, youkeId from t_wxper_fans where mobile is not null and shopId = -1 and youkeId is not null")
    List<TWxPerFans> selectFansWithBindMobileAndShopIdIsEmpty();

    @Select("SELECT id,nickName,mobile,appId from t_wxper_fans where appId is not null and mobile is not null and subState = 1")
    List<TWxPerFans> selectFansWithAppIdIsNotEmpty();

    @Delete("DELETE from t_wxper_fans where wxAccountId = #{wxAccountId}")
    int deleteFansByAccountId(@Param("wxAccountId") Long wxaccountid);
}