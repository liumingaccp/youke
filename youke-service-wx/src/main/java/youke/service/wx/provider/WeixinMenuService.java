package youke.service.wx.provider;

import org.springframework.stereotype.Service;
import youke.common.model.TSubscrMenuRule;
import youke.facade.wx.provider.IWeixinMenuService;
import youke.facade.wx.vo.menu.MenuVo;
import youke.service.wx.biz.IMenuBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WeixinMenuService implements IWeixinMenuService {

    @Resource
    private IMenuBiz menuBiz;

    public void saveMenuRule(TSubscrMenuRule rule, String appId) {
        menuBiz.saveMenuRule(rule,appId);
    }

    public void deleteMenuRule(int ruleId, String appId) {
        menuBiz.deleteMenuRule(ruleId,appId);
    }

    public List<TSubscrMenuRule> getMenuRules(String appId) {
        return menuBiz.getMenuRules(appId);
    }

    public List<MenuVo> getMenus(String appId, int ruleId) {
        return menuBiz.getMenus(appId,ruleId);
    }

    public void saveMenus(List<MenuVo> menus, String appId, int ruleId) {
        menuBiz.saveMenus(menus, appId, ruleId);
    }

    public void postMenus(List<MenuVo> menus, String appId, int ruleId) {
        menuBiz.doMenus(menus, appId ,ruleId);
    }
}
