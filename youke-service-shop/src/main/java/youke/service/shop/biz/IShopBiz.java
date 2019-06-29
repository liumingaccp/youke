package youke.service.shop.biz;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import youke.common.model.vo.result.ShopVo;
import youke.facade.shop.vo.AuthShopVo;

import java.util.Date;
import java.util.List;

public interface IShopBiz {

    List<ShopVo> getShopList(String dykId);

    List<AuthShopVo> getAuthShops(String title, int state, int type, String dykId);

    void updateExpState(String dykId);

}
