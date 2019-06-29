package youke.service.shop.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanShopGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.TShop;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.facade.shop.util.YZConstants;
import youke.service.shop.biz.IYzCoreBiz;
import youke.service.shop.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class YzCoreBiz extends Base implements IYzCoreBiz {

    private static final String ACCESSTOKENKEY = YZConstants.CLENTID + "-access-token-";

    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    @Override
    public void saveAuthInfo(String dykId, String accessToken, String refreshToken,Long expire) {
        //获取id，name,logo
        YZClient client = new DefaultYZClient(new Token(accessToken)); //new Sign(appKey, appSecret)
        YouzanShopGetParams youzanShopGetParams = new YouzanShopGetParams();
        YouzanShopGet youzanShopGet = new YouzanShopGet();
        youzanShopGet.setAPIParams(youzanShopGetParams);
        YouzanShopGetResult result = client.invoke(youzanShopGet);

        TShop shop = shopDao.selectByDianId(result.getId()+"", 4);
        Date now = new Date();
        if (shop == null)
            shop = new TShop();
        shop.setYoukeid(dykId);
        shop.setDianid(result.getId()+"");
        shop.setDianname(result.getName());
        shop.setTitle(result.getName());
        shop.setAuthtime(now);
        shop.setExptime(DateUtil.addYears(now,1)); //默认授权1年
        shop.setUpdatetime(now);
        shop.setCreatetime(new Date());
        shop.setState(1);
        shop.setType(4);
        shop.setAccesstoken(accessToken);
        shop.setRefreshtoken(refreshToken);
        shop.setTitle(result.getName());
        shop.setCover(result.getLogo());
        if (shop.getId() == null) {
            shopDao.insertSelective(shop);
            queueSender.send("initSyncYZOrder.queue", shop.getId());
        } else {
            shopDao.updateByPrimaryKeySelective(shop);
        }
        RedisUtil.set(ACCESSTOKENKEY + shop.getId(), accessToken, expire-60);
    }

    public String getToken(int shopId) {
        String val = (String) RedisSlaveUtil.get(ACCESSTOKENKEY+shopId);
        if(empty(val))
        {
            TShop shop = shopDao.selectByPrimaryKey(shopId);
            if(empty(shop)) {
                throw new BusinessException("店铺未授权，请店铺授权");
            }
            if(shop.getType()!=4){
                throw new BusinessException("店铺类型不是有赞店铺");
            }
            JSONObject params = new JSONObject();
            params.put("client_id", YZConstants.CLENTID);
            params.put("client_secret",YZConstants.CLENTSECRET);
            params.put("grant_type","refresh_token");
            params.put("refresh_token",shopDao.selectRefreshToken(shopId));
            String res = HttpConnUtil.doPostJson("https://open.youzan.com/oauth/token",params.toString());
            JSONObject resJson = JSONObject.parseObject(res);
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
