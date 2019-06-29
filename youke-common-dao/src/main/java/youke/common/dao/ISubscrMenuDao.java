package youke.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import youke.common.dao.vo.TMediaVo;
import youke.common.model.TSubscrMenu;

import java.util.List;

public interface ISubscrMenuDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TSubscrMenu record);

    TSubscrMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSubscrMenu record);

    /**
     * 根据传入的ruleId删除 原有appId的菜单
     * @param ruleId
     */
    void deleteByRuleId(@Param("appId") String appId, @Param("ruleId") int ruleId);

    /**
     * 获取appid下对应ruleid 规则的菜单
     * @param appId
     * @param ruleId
     * @return
     */
    List<TSubscrMenu> getMenus(@Param("appId") String appId, @Param("ruleId") int ruleId);

    /**
     * 获取appid下对应ruleid 规则的菜单
     * @param appId
     * @param ruleId
     * @return
     */
    List<TSubscrMenu> getSubMenus(@Param("appId") String appId, @Param("pid") int pid, @Param("ruleId") int ruleId);

    /**
     * 删除appid下对应的所有菜单
     * @param appId
     */
    void deleteAllByAppId(String appId);

    @Select("select mediaType,materialId,content from t_subscr_menu where id=#{id}")
    TMediaVo selectTMediaVo(@Param(value="id") Integer id);

    List<Integer> selectMaters(@Param("appId") String appId, @Param("MaterType") String type, @Param("ruleId") int i);
}