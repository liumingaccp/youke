package youke.service.shop.biz.impl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.JushitaJdpUserAddRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.TopAuthTokenRefreshRequest;
import com.taobao.api.response.JushitaJdpUserAddResponse;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.TopAuthTokenRefreshResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.TShop;
import youke.common.model.vo.param.KeyValVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.facade.shop.util.TBConstants;
import youke.service.shop.biz.ITbCoreBiz;
import youke.service.shop.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

@Service
public class TbCoreBiz extends Base implements ITbCoreBiz {

    private static final String ACCESSTOKENKEY = TBConstants.OPENAPPKEY+"-access-token-";

    @Resource
    private IShopDao shopDao;
    @Resource
    private QueueSender queueSender;

    public void saveAuthInfo(String dykId, String uid, String userNick, String accessToken, String refreshToken, Date expireTime, Long r2Expires, int shopType) {
        TShop shop = shopDao.selectByDianId(uid,shopType);
        if(shop==null)
            shop = new TShop();
        try {
            userNick = URLDecoder.decode(userNick,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Date nowDate = new Date();
        shop.setYoukeid(dykId);
        shop.setDianid(uid);
        shop.setDianname(userNick);
        shop.setAuthtime(nowDate);
        shop.setExptime(expireTime);
        shop.setUpdatetime(new Date());
        shop.setCreatetime(new Date());
        shop.setState(1);
        shop.setType(shopType);
        shop.setAccesstoken(accessToken);
        shop.setRefreshtoken(refreshToken);
        KeyValVo kv = getTitleCover(userNick);
        shop.setTitle(kv.getKey());
        shop.setCover(kv.getVal());
        if(shop.getId()==null){
            shopDao.insertSelective(shop);
            saveToken(shop.getId(),accessToken,r2Expires);
        }else{
            shopDao.updateByPrimaryKeySelective(shop);
            saveToken(shop.getId(),accessToken,r2Expires);
        }
        //开启RDS数据同步
        doRDSPush(shop.getId());
    }

    private void doRDSPush(int shopId){
        TaobaoClient client = new DefaultTaobaoClient(TBConstants.URL, TBConstants.OPENAPPKEY, TBConstants.OPENAPPSECRET);
        JushitaJdpUserAddRequest req = new JushitaJdpUserAddRequest();
        req.setRdsName("rm-vy15000tai9k2838t");
        req.setTopics("trade,item,refund");
        req.setHistoryDays(90L);
        JushitaJdpUserAddResponse rsp = null;
        try {
            rsp = client.execute(req, getToken(shopId));
            System.out.println(rsp.getIsSuccess());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private KeyValVo getTitleCover(String userNick){
        TaobaoClient client = new DefaultTaobaoClient(TBConstants.URL, TBConstants.OPENAPPKEY, TBConstants.OPENAPPSECRET);
        ShopGetRequest req = new ShopGetRequest();
        req.setNick(userNick);
        req.setFields("title,pic_path");
        try {
            ShopGetResponse response = client.execute(req);
            Shop shop = response.getShop();
            return new KeyValVo(shop.getTitle(),shop.getPicPath());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return new KeyValVo();
    }

    private void saveToken(int shopId, String token,Long expires){
        RedisUtil.set(ACCESSTOKENKEY+shopId,token,expires);
        shopDao.updateAccessToken(shopId,token);
    }

    public String getToken(int shopId){
        String val = (String) RedisSlaveUtil.get(ACCESSTOKENKEY+shopId);
        if(empty(val))
        {
            TShop shop = shopDao.selectByPrimaryKey(shopId);
            if(empty(shop.getExptime()))
                throw new BusinessException("店铺未授权，请店铺授权");
            if(shop.getExptime().getTime()<new Date().getTime())
                throw new BusinessException("店铺授权已过期,请重新授权");
            TaobaoClient client = new DefaultTaobaoClient(TBConstants.URL, TBConstants.OPENAPPKEY, TBConstants.OPENAPPSECRET);
            TopAuthTokenRefreshRequest req = new TopAuthTokenRefreshRequest();
            req.setRefreshToken(shop.getRefreshtoken());
            try {
                TopAuthTokenRefreshResponse response = client.execute(req);
                JSONObject jsonRes = JSONObject.fromObject(response.getTokenResult());
                String accessToken = jsonRes.getString("access_token");
                String refreshToken = jsonRes.getString("refresh_token");
                Long r2Expires = jsonRes.getLong("r2_expires_in");
                saveToken(shopId,accessToken,r2Expires);
                shopDao.updateRefreshToken(shopId,refreshToken); //保存刷新token
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return val;
    }

}
