package youke.web.pc.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.utils.JsonUtils;
import youke.facade.wx.provider.IWeixinMenuService;
import youke.facade.wx.vo.menu.ButtonVo;
import youke.facade.wx.vo.menu.MenuVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 17:58
 */
@RestController
@RequestMapping("wxmenu")
public class WxMenuAction extends BaseAction {

    @Resource
    private IWeixinMenuService weiXinMenuService;

    /**
     * 保存自定义菜单
     *
     * @return
     */
    @RequestMapping(value = "savemenus", method = RequestMethod.POST)
    public JsonResult saveMenus() {
        JSONObject params = getParams();
        int ruleId = params.getInt("ruleId");
        JSONArray data = params.getJSONArray("data");

        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        for (int i = 0; i < data.size(); i++) {
                MenuVo menuVo = (MenuVo) JSONObject.toBean(data.getJSONObject(i), MenuVo.class);
                menuVos.add(menuVo);
        }
        weiXinMenuService.saveMenus(menuVos, AuthUser.getUser().getAppId(), ruleId);
        return new JsonResult();
    }

    /**
     * 获取菜单数据
     *
     * @return
     */
    @RequestMapping(value = "getmenus", method = RequestMethod.POST)
    public JsonResult getMenus() {
        JSONObject params = getParams();
        int ruleId = params.getInt("ruleId");
        List<MenuVo> menus = weiXinMenuService.getMenus(AuthUser.getUser().getAppId(), ruleId);
        return new JsonResult(menus);
    }

    /**
     * 保存发布
     *
     * @return
     */
    @RequestMapping(value = "postmenus", method = RequestMethod.POST)
    public JsonResult postMenus() {
        JSONObject params = getParams();
        JSONArray data = params.getJSONArray("data");
        int ruleId = params.getInt("ruleId");

        List<MenuVo> menuVos = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MenuVo menuVo = new MenuVo();
            JSONObject obj = data.getJSONObject(i);
            ButtonVo button = (ButtonVo)JSONObject.toBean(obj.getJSONObject("button"), ButtonVo.class);
            menuVo.setButton(button);
            JSONArray subButtons = obj.getJSONArray("subButtons");
            if(subButtons != null && subButtons.size() > 0){
                for(int j=0; j<subButtons.size(); j++){
                    ButtonVo sub = (ButtonVo)JSONObject.toBean(subButtons.getJSONObject(j), ButtonVo.class);
                    menuVo.getSubButtons().add(sub);
                }
            }
            menuVos.add(menuVo);
        }
        weiXinMenuService.postMenus(menuVos, AuthUser.getUser().getAppId(), ruleId);
        return new JsonResult();
    }



    public static void main(String[] args) {
        String json = "{" +
                "    \"button\":{" +
                "        \"id\":209," +
                "        \"title\":\"诺仁科技\"," +
                "        \"type\":null," +
                "        \"mediatype\":null," +
                "        \"materialid\":null," +
                "        \"url\":null," +
                "        \"content\":null," +
                "        \"pageappid\":null," +
                "        \"pagepath\":null" +
                "    }," +
                "    \"subButtons\":[" +
                "        {" +
                "            \"id\":210," +
                "            \"title\":\"社会我明哥\"," +
                "            \"type\":\"click\"," +
                "            \"mediatype\":\"img\"," +
                "            \"materialid\":160," +
                "            \"url\":null," +
                "            \"content\":null," +
                "            \"pageappid\":null," +
                "            \"pagepath\":null" +
                "        }," +
                "        {" +
                "            \"id\":211," +
                "            \"title\":\"社会我涛哥\"," +
                "            \"type\":\"click\"," +
                "            \"mediatype\":\"img\"," +
                "            \"materialid\":162," +
                "            \"url\":null," +
                "            \"content\":null," +
                "            \"pageappid\":null," +
                "            \"pagepath\":null" +
                "        }" +
                "    ]" +
                "}";

        try {
            MenuVo menuVo = JsonUtils.JsonToBean(json, MenuVo.class);
            System.out.println(menuVo.getButton().getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
