package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.result.ShopVo;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.vo.AuthShopVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("shop")
public class ShopAction extends BaseAction {

    @Resource
    private IShopService shopService;

    /**
     * 获取店铺选择列表
     *
     * @return
     */
    @RequestMapping(value = "getshopselect", method = RequestMethod.POST)
    public JsonResult getShopSelect() {
        String dykId = AuthUser.getUser().getDykId();
        JsonResult result = new JsonResult();
        List<ShopVo> shops = shopService.getShopList(dykId);
        result.setData(shops);
        return result;
    }

    /**
     * 获取授权店铺列表
     *
     * @return
     */
    @RequestMapping(value = "getauthshops", method = RequestMethod.POST)
    public JsonResult getAuthShops() {
        JSONObject parObj = getParams();
        String title = parObj.getString("title");
        int state = parObj.getInt("state");
        int type = parObj.getInt("type");
        List<AuthShopVo> shops = shopService.getAuthShops(title,state,type,AuthUser.getUser().getDykId());
        return new JsonResult(shops);
    }
}
