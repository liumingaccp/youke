package youke.common.dao;

import youke.common.model.TSubscrMenuRule;

import java.util.List;

public interface ISubscrMenuRuleDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TSubscrMenuRule record);

    TSubscrMenuRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSubscrMenuRule record);

    /**
     * 获取对应 ruleId 的 menuid
     * @param ruleId
     */
    String getMenuid(int ruleId);

    /**
     * 删除 appid下对应的全部的规则
     * @param appId
     */
    void deleteAllByAppId(String appId);

    /**
     * 获取appid对应下的所有规则
     * @param appId
     * @return
     */
    List<TSubscrMenuRule> selectByAppId(String appId);
}