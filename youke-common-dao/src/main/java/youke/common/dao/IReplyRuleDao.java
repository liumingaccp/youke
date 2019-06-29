package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import youke.common.model.TReplyRule;

import java.util.List;

public interface IReplyRuleDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TReplyRule record);

    TReplyRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TReplyRule record);

    int updateByPrimaryKeyWithBLOBs(TReplyRule record);

    int updateByPrimaryKey(TReplyRule record);

    String hasReplyRule(@Param("appId") String appid, @Param("rule") String rule);

    List<TReplyRule> selectLikeTitle(@Param("appId")String appId, @Param("title") String title, @Param("key")String key);

    void deleteById(@Param("id") Integer id, @Param("appId") String appId);
}