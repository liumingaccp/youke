package youke.service.shop.biz.impl;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.seller.SellerVenderInfoGetRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.seller.SellerVenderInfoGetResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.TShop;
import youke.common.model.vo.param.KeyValVo;
import youke.common.model.vo.result.ShopVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.util.JDConstants;
import youke.facade.shop.util.PDDConstants;
import youke.facade.shop.util.PDDUtil;
import youke.service.shop.biz.IPddCoreBiz;
import youke.service.shop.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PddCoreBiz extends Base implements IPddCoreBiz {

    private static final String ACCESSTOKENKEY = PDDConstants.CLENTID + "-access-token-";

    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    @Override
    public void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken,Long expire) {
        TShop shop = shopDao.selectByDianId(uid, 3);
        Date now = new Date();
        if (shop == null)
            shop = new TShop();
        shop.setYoukeid(dykId);
        shop.setDianid(uid);
        shop.setDianname(userNick);
        shop.setTitle(userNick);
        shop.setAuthtime(now);
        shop.setExptime(DateUtil.addYears(now,1)); //默认授权1年
        shop.setUpdatetime(now);
        shop.setCreatetime(new Date());
        shop.setState(1);
        shop.setType(3);
        shop.setAccesstoken(accessToken);
        shop.setRefreshtoken(refreshToken);
        KeyValVo kv = getTitleCover(accessToken);
        shop.setTitle(kv.getKey());
        shop.setCover(kv.getVal());
        if (shop.getId() == null) {
            shopDao.insertSelective(shop);
            queueSender.send("initSyncPDDOrder.queue", shop.getId());
        } else {
            shopDao.updateByPrimaryKeySelective(shop);
        }
        RedisUtil.set(ACCESSTOKENKEY + shop.getId(), accessToken, expire);
    }

    private KeyValVo getTitleCover(String token) {
        Map<String,String> params = new HashMap<>();
        params.put("type","pdd.mall.info.get");
        params.put("access_token",token);
        JSONObject res = PDDUtil.request(params);
        JSONObject info = res.getJSONObject("mall_info_get_response");
        return new KeyValVo(info.getString("mall_name"),info.getString("logo"));
    }

    public String getToken(int shopId) {
        String val = (String) RedisSlaveUtil.get(ACCESSTOKENKEY+shopId);
        if(empty(val))
        {
            TShop shop = shopDao.selectByPrimaryKey(shopId);
            if(empty(shop)) {
                throw new BusinessException("店铺未授权，请店铺授权");
            }
            if(shop.getType()!=3){
                throw new BusinessException("店铺类型不是拼多多店铺");
            }
            JSONObject params = new JSONObject();
            params.put("client_id", PDDConstants.CLENTID);
            params.put("client_secret",PDDConstants.CLENTSECRET);
            params.put("grant_type","refresh_token");
            params.put("refresh_token",shopDao.selectRefreshToken(shopId));
            String res = HttpConnUtil.doPostJson("http://open-api.pinduoduo.com/oauth/token",params.toString());
            JSONObject resJson = JSONObject.fromObject(res);
            if (resJson.containsKey("access_token")) {
                Long expire = resJson.getLong("expires_in");
                val = resJson.getString("access_token");
                String refreshToken = resJson.getString("refresh_token");
                TShop tShop = new TShop();
                tShop.setId(shopId);
                tShop.setAccesstoken(val);
                tShop.setRefreshtoken(refreshToken);
                tShop.setUpdatetime(new Date());
                shopDao.updateByPrimaryKeySelective(tShop);
                RedisUtil.set(ACCESSTOKENKEY + shop.getId(), val, expire);
            }
        }
        return val;
    }
}
