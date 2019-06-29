package youke.service.wx.biz.impl;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.model.TMaterialImage;
import youke.common.model.TMaterialNews;
import youke.common.model.TSubscrMenu;
import youke.common.model.TSubscrMenuRule;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.JsonUtils;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.util.Constants;
import youke.facade.wx.vo.menu.ButtonVo;
import youke.facade.wx.vo.menu.MenuVo;
import youke.service.wx.biz.ICoreBiz;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.biz.IMenuBiz;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By Kinsey
 * Date: 2017/12/20
 * Time: 9:24
 */
@Service(value = "menuBiz")
public class MenuBiz extends Base implements IMenuBiz {

    protected String tokenKey = "wechat-token-";

    @Autowired
    private ICoreBiz coreBiz;
    @Resource
    private IAreaDao iAreaDao;
    @Resource
    private ISubscrMenuDao iSubscrMenuDao;
    @Resource
    private ISubscrMenuRuleDao iSubscrMenuRuleDao;
    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private IMaterialImageDao materialImageDao;
    @Resource
    private IMaterialNewsDao materialNewsDao;

    public void clearMenuCache(String appId) {
        String ret = HttpConnUtil.httpsRequest("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + coreBiz.getToken(appId));
        System.err.println(ret);
    }


    /**
     * 保存个性化菜单规则(用于发布)
     *
     * @param rule
     * @param appId
     */
    public void saveMenuRule(TSubscrMenuRule rule, String appId) {
        rule.setAppid(appId);
        rule.setCreatetime(new Date());
        iSubscrMenuRuleDao.insertSelective(rule);
    }


    /**
     * 删除数据库个性化菜单规则
     *
     * @param ruleId
     * @param appId
     */
    public void deleteMenuRule(int ruleId, String appId) {
        //删除规则的时候,使用这个规则的菜单也要删除
        iSubscrMenuDao.deleteByRuleId(appId, ruleId);
        iSubscrMenuRuleDao.deleteByPrimaryKey(ruleId);
    }

    /**
     * 获取所有的规则(没有menuid的规则),用于筛选
     *
     * @param appId
     * @return
     */
    public List<TSubscrMenuRule> getMenuRules(String appId) {
        return iSubscrMenuRuleDao.selectByAppId(appId);
    }

    public List<MenuVo> getMenus(String appId, int ruleId) {
        List<TSubscrMenu> menus = iSubscrMenuDao.getMenus(appId, ruleId);
        List<MenuVo> menuVos = new ArrayList<>();
        //首先迭代出父级菜单
        if (menus != null && menus.size() > 0) {
            //填充子菜单
            for (TSubscrMenu menu : menus) {
                MenuVo menuVo = new MenuVo();
                ButtonVo buttonVo = this.getButtonVo(menu);
                menuVo.setButton(buttonVo);
                List<TSubscrMenu> subMenus = iSubscrMenuDao.getSubMenus(appId, buttonVo.getId(), ruleId);
                List<ButtonVo> buttonVos = new ArrayList<>();
                if (subMenus != null && subMenus.size() > 0) {
                    for (TSubscrMenu subMenu : subMenus) {
                        buttonVos.add(this.getButtonVo(subMenu));
                    }
                }
                menuVo.setSubButtons(buttonVos);
                menuVos.add(menuVo);
            }
        }

        return menuVos;
    }

    /**
     * 接收前台数据,保存进数据库,用于发布
     *
     * @param menus
     * @param appId
     * @param ruleId 0默认菜单
     */
    public void saveMenus(List<MenuVo> menus, String appId, int ruleId) {

        //根据发布的时传入的 ruleId,删除对应的菜单,防止候不明确
        iSubscrMenuDao.deleteByRuleId(appId, ruleId);

        TSubscrMenu tmenu = null;
        Date createTime = new Date();
        //加入新的菜单(包括对应的子菜单)
        for (int i = 0; i < menus.size(); i++) {
            tmenu = getTSubscrMenu(menus.get(i).getButton(), 0);
            //设置父子公共域
            tmenu.setCreatetime(createTime);
            tmenu.setAppid(appId);
            tmenu.setRuleid(ruleId);
            //以传入的顺序排序
            tmenu.setSort((byte) i);

            iSubscrMenuDao.insertSelective(tmenu);
            //只能这样获取!
            int parentId = tmenu.getId();

            //插入子菜单
            List<ButtonVo> subButtons = menus.get(i).getSubButtons();
            if (subButtons.size() > 0) {
                for (int j = 0; j < subButtons.size(); j++) {
                    tmenu = getTSubscrMenu(subButtons.get(j), parentId);
                    tmenu.setSort((byte) j);
                    tmenu.setCreatetime(createTime);
                    tmenu.setAppid(appId);
                    tmenu.setRuleid(ruleId);

                    iSubscrMenuDao.insertSelective(tmenu);
                }
            }
        }
    }

    /**
     * 发布菜单
     *
     * @param menus
     * @param appId
     * @param ruleId 0默认菜单
     */
    public void doMenus(List<MenuVo> menus, String appId, int ruleId) {
        //本地保存操作
        this.saveMenus(menus, appId, ruleId);
        menus = getMenus(appId, ruleId);

        //拼接json
        StringBuffer sb_json = new StringBuffer();
        sb_json.append("{" + "\"button\": [");
        List<String> subList = new ArrayList<String>();
        //内部拼接
        for (MenuVo menu : menus) {
            ButtonVo button = menu.getButton();
            //拼接一个菜单
            Map<String, String> supJsonMap = getMenuJson(button, null);
            String supJsonStr = JSON.toJSONString(supJsonMap);

            //拼接子菜单
            List<ButtonVo> subButtons = menu.getSubButtons();
            List<Map<String, String>> sub = new ArrayList<Map<String, String>>();
            if (subButtons != null && subButtons.size() > 0) {
                for (ButtonVo vo : subButtons) {
                    Map<String, String> json = getMenuJson(vo, null);
                    sub.add(json);
                }
                String subJsonStr = JSON.toJSONString(sub);
                supJsonStr = supJsonStr.substring(0, supJsonStr.length() - 1) + "," + "\"sub_button\":" + subJsonStr + "}";
            }

            subList.add(supJsonStr);
        }

        //准备多个菜单
        StringBuffer temp = new StringBuffer();
        for (String str : subList) {
            temp = temp.append(str).append(",");
        }

        //拼接所有菜单
        sb_json.append(temp.toString().substring(0, temp.length() - 1));

        if (ruleId == 0) {
            //发布默认菜单
            sb_json.append("]}");

            String jsonParam = sb_json.toString().replace("\\", "");
            //调用发布接口
            String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "menu/create?access_token=" + coreBiz.getToken(appId), jsonParam);
            WxCurlUtil.ret(ret);

        } else {
            //发布个性化菜单
            //查询规则
            TSubscrMenuRule rule = iSubscrMenuRuleDao.selectByPrimaryKey(ruleId);
            //拼接个性化
            String ruleJson = JSON.toJSONString(getRuleJson(rule));
            //调用发布接口
            sb_json.append("],");
            sb_json.append("\"matchrule\":" + ruleJson);
            sb_json.append("}");
            //自动加入了 "\\"
            String jsonParam = sb_json.toString().replace("\\", "");

            String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "menu/addconditional?access_token=" + coreBiz.getToken(appId), jsonParam);
            WxCurlUtil.ret(ret);
            //要保存对应的生成的menuid
            JSONObject jsonObject = JSONObject.fromObject(ret);
            String menuid = String.valueOf(jsonObject.get("menuid"));
            TSubscrMenuRule tSubscrMenuRule = new TSubscrMenuRule();
            tSubscrMenuRule.setId(ruleId);
            tSubscrMenuRule.setMenuid(menuid);
            iSubscrMenuRuleDao.updateByPrimaryKeySelective(tSubscrMenuRule);

            //修改菜单对应 rule 值
            TSubscrMenu tSubscrMenu = new TSubscrMenu();
            tSubscrMenu.setRuleid(ruleId);
            for (MenuVo menuVo : menus) {
                ButtonVo button = menuVo.getButton();
                tSubscrMenu.setId(button.getId());
                iSubscrMenuDao.updateByPrimaryKeySelective(tSubscrMenu);
                List<ButtonVo> subButtons = menuVo.getSubButtons();
                if (subButtons.size() > 0) {
                    for (ButtonVo vo : subButtons) {
                        tSubscrMenu.setId(vo.getId());
                        iSubscrMenuDao.updateByPrimaryKeySelective(tSubscrMenu);
                    }
                }

            }
        }

    }

    /**
     * 删除公众号的菜单(本地数据库也要被删掉)
     *
     * @param appId
     * @param ruleId 0 : 默认菜单(会删除默认菜单和所有的个性化菜单)
     */
    public void deleteMenus(String appId, int ruleId) {
        if (ruleId == 0) {
            String ret = HttpConnUtil.httpsRequest(Constants.BASEURL + "menu/delete?access_token=" + coreBiz.getToken(appId));
            WxCurlUtil.ret(ret);
            //删除数据库中所有的规则和菜单
            iSubscrMenuDao.deleteAllByAppId(appId);
            iSubscrMenuRuleDao.deleteAllByAppId(appId);

        } else {
            //取得 rule 对应的 menuid
            String menuid = iSubscrMenuRuleDao.getMenuid(ruleId);
            //$data = '{"menuid":"413705287"}';
            menuid = "{" + "\"menuid\":" + "\"" + menuid + "\"" + "}";
            String ret = HttpConnUtil.doPostJson(Constants.BASEURL + "menu/delconditional?access_token=" + coreBiz.getToken(appId), menuid);
            WxCurlUtil.ret(ret);

            //删除数据库中 对应的一套菜单
            iSubscrMenuDao.deleteByRuleId(appId, ruleId);
            iSubscrMenuRuleDao.deleteByPrimaryKey(ruleId);
        }
    }

    @Override
    public void doSyncMenu(String appId) {
        JSONObject menu;
        JSONArray menus;
        JSONObject subMenu;
        JSONArray subMenus;
        JSONObject subButton;
        TSubscrMenu subscrMenu;
        List<TSubscrMenu> menuList;
        String token = coreBiz.getToken(appId);
        String content = HttpConnUtil.httpsRequest(Constants.BASEURL + "get_current_selfmenu_info?access_token=" + token);
        WxCurlUtil.ret(content);
        if (!content.contains("click")) {
            menus = JsonUtils.getJsonObject(content).getJSONObject("selfmenu_info").getJSONArray("button");
            if (menus != null && menus.size() > 0) {
                menuList = iSubscrMenuDao.getMenus(appId, 0);
                if (menuList.size() == 0) {
                    for (int i = 0; i < menus.size(); i++) {
                        int pid = 0;
                        menu = JsonUtils.getJsonObject(menus.get(i).toString());//父菜单
                        subscrMenu = parseMenuJson(menu, pid, appId, i);
                        pid = subscrMenu.getId();
                        subButton = menu.getJSONObject("sub_button");
                        if (subButton != null && subButton.size() > 0) {
                            subMenus = subButton.getJSONArray("list");
                            if (subMenus != null && subMenus.size() > 0) {
                                for (int j = 0; j < subMenus.size(); j++) {
                                    subMenu = JsonUtils.getJsonObject(subMenus.get(j).toString());//子菜单
                                    parseMenuJson(subMenu, pid, appId, j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private TSubscrMenu parseMenuJson(JSONObject menu, int pid, String appId, int sort) {
        String materiaId = "";
        String oss_url = "";
        String postUrl;
        String ret = "";
        String type = "";
        TMaterialImage image;
        InputStream inputStream;
        List<TMaterialNews> news;
        TSubscrMenu subscrMenu = new TSubscrMenu();
        subscrMenu.setPid(pid);
        subscrMenu.setRuleid(0);
        subscrMenu.setAppid(appId);
        subscrMenu.setCreatetime(new Date());
        if (menu.has("type"))
            subscrMenu.setMediatype(menu.getString("type"));
        if (menu.has("name"))
            subscrMenu.setTitle(menu.getString("name"));
        subscrMenu.setSort(Byte.valueOf(sort + ""));
        if (menu.getJSONObject("sub_button").size() == 0) {
            materiaId = menu.getString("value");
            type = menu.getString("type");
            switch (type) {
                case "view":
                    subscrMenu.setType("view");
                    subscrMenu.setUrl(menu.getString("url"));
                    break;
                case "miniprogram":
                    subscrMenu.setType("miniprogram");
                    break;
                case "text":
                    subscrMenu.setType("click");
                    subscrMenu.setContent(menu.getString("value"));
                    break;
                case "img":
                    if (!"".equals(materiaId)) {
                        image = materialImageDao.selectMaterialByMediaId(materiaId, appId);
                        if (image == null) {
                            inputStream = WxCurlUtil.downloadTempMaterial(coreBiz.getToken(appId), materiaId);
                            oss_url = FileUpOrDwUtil.uploadNetStream(inputStream, "jpg/" + UUID.randomUUID().toString() + ".jpg");
                            postUrl = Constants.BASEURL + "material/add_material?access_token=" + coreBiz.getToken(appId) + "&type=image";
                            ret = WxCurlUtil.postFileFromUrl(postUrl, oss_url);
                            WxCurlUtil.ret(ret);
                            JSONObject obj = JSONObject.fromObject(ret);
                            image = new TMaterialImage();
                            image.setAppid(appId);
                            image.setState(0);
                            image.setCreatetime(new Date());
                            image.setIstemp(1);
                            image.setUrl(oss_url);
                            image.setMediaid(obj.getString("media_id"));
                            image.setWxUrl(obj.getString("url"));
                            materialImageDao.insertSelective(image);
                        }
                        subscrMenu.setType("img");
                        subscrMenu.setMaterialId(image.getId());
                    }
                    break;
                case "voice":
                    subscrMenu.setType("voice");
                    break;
                case "news":
                    if (!"".equals(materiaId)) {
                        image = materialImageDao.selectMaterialByMediaId(materiaId, appId);
                        news = materialNewsDao.selectMaterialByMediaId(materiaId, appId);
                        if (image == null) {
                            materialBiz.doSyncImage(appId);
                        }
                        if (news.size() == 0) {
                            materialBiz.doSyncNews(appId);
                        }
                        subscrMenu.setType("click");
                        subscrMenu.setMaterialId(materialBiz.getMaterByMaterialId(appId, materiaId, subscrMenu.getMediatype()));
                    }
                    break;
                case "video":
                    subscrMenu.setType("click");
                    subscrMenu.setUrl(menu.getString("value"));
                    break;
                default:
                    subscrMenu.setType("click");
                    break;
            }
        }
        iSubscrMenuDao.insertSelective(subscrMenu);
        return subscrMenu;
    }

    /*
     * 以下是辅助方法=====================================================================================================
     *
     */
    public TSubscrMenu getTSubscrMenu(ButtonVo vo, int parentId) {

        TSubscrMenu tmenu = new TSubscrMenu();
        tmenu.setPid(parentId);
        tmenu.setTitle(vo.getTitle());
        tmenu.setType(vo.getType());
        tmenu.setMediatype(vo.getMediatype());
        tmenu.setMaterialId(vo.getMaterialid());
        tmenu.setUrl(vo.getUrl());
        tmenu.setContent(vo.getContent());
        tmenu.setPageappid(vo.getPageappid());
        tmenu.setPagepath(vo.getPagepath());
        return tmenu;
    }

    public ButtonVo getButtonVo(TSubscrMenu tSubscrMenu) {

        ButtonVo buttonVo = new ButtonVo();
        buttonVo.setId(tSubscrMenu.getId());
        buttonVo.setTitle(tSubscrMenu.getTitle());
        buttonVo.setType(tSubscrMenu.getType());
        buttonVo.setMediatype(tSubscrMenu.getMediatype());
        buttonVo.setMaterialid(tSubscrMenu.getMaterialId());
        buttonVo.setUrl(tSubscrMenu.getUrl());
        buttonVo.setContent(tSubscrMenu.getContent());
        buttonVo.setPageappid(tSubscrMenu.getPageappid());
        buttonVo.setPagepath(tSubscrMenu.getPagepath());
        return buttonVo;
    }

    public Map<String, String> getMenuJson(ButtonVo vo, String sub_button) {
        //拼接
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", vo.getTitle());

        if (notEmpty(vo.getType())) {
            map.put("type", vo.getType());
            if (vo.getType().equals("click")) {
                map.put("key", vo.getId() + "");
            }
        }
        if (notEmpty(vo.getUrl())) {
            map.put("url", vo.getUrl());
        }
        if (notEmpty(vo.getMaterialid())) {
            map.put("media_id", vo.getMaterialid().toString());
        }
        if (notEmpty(vo.getPageappid())) {
            map.put("appid", vo.getPageappid());
        }
        if (notEmpty(vo.getPagepath())) {
            map.put("pagepath", vo.getPagepath());
        }
        if (notEmpty(sub_button)) {
            map.put("sub_button", sub_button);
        }

        return map;
    }

    public Map<String, String> getRuleJson(TSubscrMenuRule rule) {
        //拼接
        Map<String, String> map = new HashMap<String, String>();
        if (rule.getTagId() != null) {
            map.put("tag_id", rule.getTagId().toString());
        }
        if (rule.getSex() != null) {
            if (rule.getSex()) {
                map.put("sex", "1");
            } else {
                map.put("sex", "2");
            }
        }
        if (rule.getOs() != null) {
            map.put("client_platform_type", String.valueOf(rule.getOs()));
        }
        if (rule.getCountry() != null) {
            map.put("country", iAreaDao.selectByPrimaryKey(rule.getCountry()).getTitle());
        }
        if (rule.getProvince() != null) {
            map.put("province", iAreaDao.selectByPrimaryKey(rule.getProvince()).getTitle());
        }
        if (rule.getCity() != null) {
            map.put("city", iAreaDao.selectByPrimaryKey(rule.getCity()).getTitle());
        }
        if (rule.getLang() != null) {
            map.put("lang", rule.getLang());
        }
        if (rule.getLanguage() != null) {
            map.put("language", rule.getLanguage());
        }

        return map;
    }

    public static void main(String[] args) {
    }


}
