package youke.service.wx.biz;

import youke.common.model.TSubscrMenuRule;
import youke.facade.wx.vo.menu.MenuVo;

import java.util.List;

/**
 * 自定义菜单配置接口
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1434698695
 */
public interface IMenuBiz {

    String MENUKEY = "wechat-menu-";

    /**
     * 清除redis缓存
     *
     * @param appId
     */
    void clearMenuCache(String appId);

    /**
     * 创建菜单规则
     *
     * @param appId
     * @return
     */
    void saveMenuRule(TSubscrMenuRule rule, String appId);

    /**
     * 删除菜单规则(同时删除关联菜单)
     *
     * @param appId
     * @return
     */
    void deleteMenuRule(int ruleId, String appId);

    /**
     * 获取菜单规则集
     *
     * @param appId
     * @return
     */
    List<TSubscrMenuRule> getMenuRules(String appId);

    /**
     * 获取菜单
     *
     * @param appId
     * @param ruleId 0默认菜单
     * @return
     */
    List<MenuVo> getMenus(String appId, int ruleId);

    /**
     * 保存菜单
     *
     * @param menus
     * @param appId
     * @param ruleId 0默认菜单
     */
    void saveMenus(List<MenuVo> menus, String appId, int ruleId);

    /**
     * 发布菜单
     *
     * @param menus
     * @param appId
     * @param ruleId 0默认菜单
     */
    void doMenus(List<MenuVo> menus, String appId, int ruleId);

    /**
     * 删除菜单
     *
     * @param appId
     * @param ruleId 0默认菜单
     */
    void deleteMenus(String appId, int ruleId);

    /**
     * 同步订单
     *
     * @param appId
     */
    void doSyncMenu(String appId);
}
