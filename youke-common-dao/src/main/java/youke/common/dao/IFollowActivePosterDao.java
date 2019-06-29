package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.model.TFollowActivePoster;

public interface IFollowActivePosterDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TFollowActivePoster record);

    TFollowActivePoster selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TFollowActivePoster record);

    @Select("SELECT COUNT(id) FROM t_follow_active_poster  WHERE activeId = #{activeId} AND appId =#{appId}")
    int queryCountByFollowId(@Param("activeId") Integer id, @Param("appId") String appId);

    @Select("SELECT * FROM t_follow_active_poster  WHERE activeId = #{activeId} AND openId =#{openId}")
    TFollowActivePoster selectInfo(@Param("activeId") Integer activeId, @Param("openId") String openId);
}