package youke.service.shop.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.model.TShop;
import youke.common.model.vo.result.ShopVo;
import youke.common.utils.DateUtil;
import youke.facade.shop.vo.AuthShopVo;
import youke.service.shop.biz.IShopBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("shopBiz")
public class ShopBiz extends Base implements IShopBiz {

    @Resource
    private IShopDao shopDao;

    public List<ShopVo> getShopList(String dykId) {
        return shopDao.selectShopListByYoukeId(dykId);
    }

    public List<AuthShopVo> getAuthShops(String title, int state, int type, String dykId) {
        title = empty(title) ? null : title;
        List<AuthShopVo> authShopVos = new ArrayList<AuthShopVo>();
        List<TShop> shops = shopDao.selectAuthShops(title, state, type, dykId);
        if (shops.size() > 0) {
            for (TShop shop : shops) {
                String authTime = "";
                String expTime = "";
                if (shop.getAuthtime() != null)
                    authTime = DateUtil.formatDate(shop.getAuthtime(), "yyyy-MM-dd HH:mm");
                if (shop.getExptime() != null)
                    expTime = DateUtil.formatDate(shop.getExptime(), "yyyy-MM-dd HH:mm");
                authShopVos.add(new AuthShopVo(shop.getId(), shop.getTitle(), shop.getState(), shop.getType(), authTime, expTime));
            }
        }
        return authShopVos;
    }

    public void updateExpState(String dykId) {
        shopDao.updateExpState(dykId, new Date());
    }

}