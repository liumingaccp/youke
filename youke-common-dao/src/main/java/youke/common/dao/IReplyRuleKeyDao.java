package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.dao.vo.TMediaVo;
import org.apache.ibatis.annotations.Param;
import youke.common.model.TReplyRuleKey;

import java.util.List;

public interface IReplyRuleKeyDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TReplyRuleKey record);

    TReplyRuleKey selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TReplyRuleKey record);

    /**
     * 获取关键词匹配的规则
     * @param key
     * @param appId
     * @return
     */
    @Select("SELECT b.mediaType,b.`materialId`,b.`content` FROM t_reply_rule_key a INNER JOIN t_reply_rule b ON a.`ruleId`=b.`id` WHERE b.`appId`=#{appId} AND b.state=0 AND ((a.`key`=#{key} AND b.keyMatch=0) OR (a.`key` LIKE CONCAT('%',#{key},'%') AND b.keyMatch=1)) ORDER BY b.rank LIMIT 1")
    TMediaVo selectRuleByKey(@Param(value="key") String key, @Param(value="appId") String appId);

    String hasReplyRuleKey(@Param("appId") String appid, @Param("key") String key);

    void deleteByRuleId(@Param("ruleId") Integer ruleId, @Param("appId") String appId);

    List<Integer> selectRuleIdLikeKey(@Param("appId") String appId, @Param("key") String key);

    List<String> selectKeysByRuleId(Integer id);
}