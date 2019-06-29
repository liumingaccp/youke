package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import youke.common.model.TTagRule;
import youke.common.model.vo.result.TagRuleVo;

import java.util.List;

public interface ITagRuleDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TTagRule record);

    TTagRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTagRule record);

    /**
     * 通过规则类型,appid和标签id去查询对应的规则
     *
     * @param type
     * @param appId
     * @param tagId
     * @return
     */
    TTagRule selectRuleByTypeAndAppIdANdTagId(@Param("type") Integer type, @Param("appId") String appId, @Param("tagId") Integer tagId);

    /**
     * 通过规则类型和编号和appId删除对应的规则
     * @param type
     * @param appId
     */
    void deleteRuleByTypeAndAppId(@Param("type") Integer type, @Param("appId") String appId);

    /**
     * 获取对应类型的标签规则
     * @param type
     * @param appId
     * @return
     */
    List<TagRuleVo> getRules(@Param("type") int type,@Param("appId") String appId);

    /**
     * 获取对应appid下的所有标签规则
     * @param appId
     * @return
     */
    List<TagRuleVo> getAllRules(String appId);

    @Select("SELECT tagId FROM t_tag_rule WHERE appId=#{appId} AND `type`=#{type}")
    List<Integer> selectTagIdByTypeAndAppId(@Param("type")int type, @Param("appId")String appId);

    @Update("update t_tag_rule set appId=#{appId} where appId=#{dykId}")
    int updateAppIdFrom(@Param("appId")String appId, @Param("dykId")String dykId);
}