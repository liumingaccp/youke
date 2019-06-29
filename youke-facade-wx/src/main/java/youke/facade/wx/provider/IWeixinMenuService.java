package youke.facade.wx.provider;

import youke.common.model.TSubscrMenuRule;
import youke.facade.wx.vo.menu.MenuVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 18:55
 */
public interface IWeixinMenuService {
    /**
     * 创建菜单规则
     * @param appId
     * @return
     */
    void saveMenuRule(TSubscrMenuRule rule, String appId);

    /**
     * 删除菜单规则(同时删除关联菜单)
     * @param appId
     * @return
     */
    void deleteMenuRule(int ruleId,String appId);

    /**
     * 获取菜单规则集
     * @param appId
     * @return
     */
    List<TSubscrMenuRule> getMenuRules(String appId);

    /**
     * 获取菜单
     * @param appId
     * @param ruleId  0默认菜单
     * @return
     */
    List<MenuVo> getMenus(String appId, int ruleId);

    /**
     * 保存菜单
     * @param menus
     * @param appId
     * @param ruleId  0默认菜单
     */
    void saveMenus(List<MenuVo> menus,String appId,int ruleId);

    /**
     * 发布菜单
     * @param menus
     * @param appId
     * @param ruleId  0默认菜单
     */
    void postMenus(List<MenuVo> menus,String appId,int ruleId);
}
