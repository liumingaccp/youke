package youke.service.shop.biz.impl;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.seller.SellerVenderInfoGetRequest;
import com.jd.open.api.sdk.request.ware.WareReadFindWareByIdRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.seller.SellerVenderInfoGetResponse;
import com.jd.open.api.sdk.response.ware.WareReadFindWareByIdResponse;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.TShop;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.facade.shop.util.JDConstants;
import youke.service.shop.biz.IJdCoreBiz;
import youke.service.shop.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class JdCoreBiz extends Base implements IJdCoreBiz {

    private static final String ACCESSTOKENKEY = JDConstants.OPENAPPKEY + "-access-token-";

    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    public void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date begTime, Date endTime) {
        TShop shop = shopDao.selectByDianId(uid, 2);
        if (shop == null)
            shop = new TShop();
        shop.setYoukeid(dykId);
        shop.setDianid(uid);
        shop.setDianname(userNick);
        shop.setTitle(userNick);
        shop.setAuthtime(begTime);
        shop.setExptime(endTime);
        shop.setUpdatetime(begTime);
        shop.setCreatetime(new Date());
        shop.setState(1);
        shop.setType(2);
        shop.setAccesstoken(accessToken);
        shop.setRefreshtoken(refreshToken);
        shop.setTitle(getShopTitle(accessToken));
        if (shop.getId() == null) {
            shopDao.insertSelective(shop);
            queueSender.send("initSyncJDOrder.queue", shop.getId());
        } else {
            shopDao.updateByPrimaryKeySelective(shop);
        }
        RedisUtil.set(ACCESSTOKENKEY + shop.getId(), accessToken, (endTime.getTime() - new Date().getTime()) / 1000);
    }

    private String getShopTitle(String token) {
        JdClient client = new DefaultJdClient(JDConstants.APIURL, token, JDConstants.OPENAPPKEY, JDConstants.OPENAPPSECRET);
        SellerVenderInfoGetRequest request = new SellerVenderInfoGetRequest();
        try {
            SellerVenderInfoGetResponse response = client.execute(request);
            return response.getVenderInfoResult().getShopName();
        } catch (JdException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getToken(int shopId) {
        String val = (String) RedisSlaveUtil.get(ACCESSTOKENKEY + shopId);
        if (empty(val)) {
            throw new BusinessException("店铺授权已过期，请重新授权");
        }
        return val;
    }

    public static void getOrders() {
        JdClient client = new DefaultJdClient(JDConstants.APIURL, JDConstants.access_token2, JDConstants.OPENAPPKEY, JDConstants.OPENAPPSECRET);

        OrderSearchRequest request = new OrderSearchRequest();
        request.setOrderState("FINISHED_L");
        request.setPage("1");
        request.setPageSize("50");

        try {
            OrderSearchResponse response = client.execute(request);
            System.out.println(JSONArray.fromObject(response.getOrderInfoResult()));
        } catch (JdException e) {
            e.printStackTrace();
        }
    }
}
