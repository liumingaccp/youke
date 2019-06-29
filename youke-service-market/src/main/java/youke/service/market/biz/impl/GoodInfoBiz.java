package youke.service.market.biz.impl;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.SkuReadService.Sku;
import com.jd.open.api.sdk.request.ware.SkuReadFindSkuByIdRequest;
import com.jd.open.api.sdk.response.seller.SellerVenderInfoGetResponse;
import com.jd.open.api.sdk.response.seller.VenderInfoResult;
import com.jd.open.api.sdk.response.ware.SkuReadFindSkuByIdResponse;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanItemGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemGetResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.IShopDao;
import youke.common.exception.BusinessException;
import youke.common.model.TShop;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.ParamUtil;
import youke.common.utils.StringUtil;
import youke.facade.market.vo.GoodInfoVo;
import youke.facade.shop.provider.IShopService;
import youke.facade.shop.util.JDConstants;
import youke.facade.shop.util.PDDUtil;
import youke.facade.shop.util.YZConstants;
import youke.service.market.biz.IGoodInfoBiz;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GoodInfoBiz extends Base implements IGoodInfoBiz {

    @Resource
    private IShopService shopService;
    @Resource
    private IShopDao shopDao;

    @Override
    public GoodInfoVo getTBGoodInfo(String goodUrl, int shopId) {
        String URL = "http://taobao.ecbao.cn/wechat/cm_details_api?item_id=ITEM_ID";

        GoodInfoVo vo = new GoodInfoVo();
        String goodId = null;
        String skuId = null;
        if (goodUrl.startsWith("https://detail.tmall.com/item.htm") || goodUrl.startsWith("https://item.taobao.com/item.htm")) {
            Map<String, String> strMap = ParamUtil.URLRequest(goodUrl);
            goodId = strMap.get("id");
            skuId = strMap.get("skuid");
            if (goodId == null || goodId.equals(""))
                throw new BusinessException("宝贝链接地址有误");
        }
        vo.setGoodId(goodId);
        String httpUrl = URL.replace("ITEM_ID", goodId);
        String resStr = HttpConnUtil.doHttpRequest(httpUrl);
        if (empty(resStr) || !resStr.contains("{"))
            throw new BusinessException("暂不支持获取该宝贝信息");
        JSONObject resObj = JSONObject.fromObject(resStr);
        vo.setGoodTitle(resObj.getString("title"));
        JSONObject price_info = resObj.getJSONObject("price_info");
        if (StringUtil.hasLength(skuId)) {
            if (price_info != null) {
                JSONArray jsonArray = price_info.getJSONObject("sku_price_list").getJSONArray("sku_price_item");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject sku = jsonArray.getJSONObject(i);
                        String sku_id = sku.getString("sku_id");
                        if (skuId.equals(sku_id)) {
                            String price = sku.getJSONObject("price").getString("price");
                            vo.setPrice(price);
                            JSONObject promotion_price = sku.getJSONObject("promotion_price");
                            try{
                                if(promotion_price != null) {
                                    String price1 = promotion_price.getString("price");
                                    if (price1 != null && !price1.trim().equals("")) {
                                        vo.setPrice(price1);
                                    }
                                }
                            }catch (JSONException e){}
                            break;
                        }
                    }
                }
            }
        } else {
            String price = resObj.getString("price");
            if (price == null || !"".equals(price)) {
                try {
                    double v = Double.parseDouble(price);
                    vo.setPrice(v + "");
                } catch (Exception e) {
                    vo.setPrice(price.substring(0, price.indexOf(".") + 2));
                }
            }
            try {
                String promotion_price = resObj.getString("promotion_price");
                if (promotion_price == null || !"".equals(promotion_price)) {
                    try {
                        double v = Double.parseDouble(promotion_price);
                        vo.setPrice(v + "");
                    } catch (Exception e) {
                        vo.setPrice(promotion_price.substring(0, price.indexOf(".") + 2));
                    }
                }
            }catch (JSONException e){}

        }

        vo.setShopName(resObj.getString("shop_name"));
        JSONArray pics = resObj.getJSONArray("pics");
        if (pics != null && pics.size() > 0) {
            for (int i = 0; i < pics.size(); i++) {
                vo.getPicUrls().add(pics.getJSONObject(i).getString("src"));
            }
        }

        JSONArray descs = resObj.getJSONArray("desc");
        if (descs != null && descs.size() > 0) {
            String contents = "";
            for (int i = 0; i < descs.size(); i++) {
                JSONObject desc = descs.getJSONObject(i);
                String label = desc.getString("label");
                String content = desc.getString("content");
                if ("img".equals(label)) {
                    contents += "<img src=\"" + content + "\" />";
                }
            }
            vo.setContent(contents);
        }

        return vo;
    }

    @Override
    public GoodInfoVo getJDGoodInfo(String goodUrl, int shopId) {
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if (empty(shop))
            throw new BusinessException("店铺信息获取失败,请联系客服处理");
        if(shop.getType()!=2)
            throw new BusinessException("商品链接为京东商品，请选择京东店铺");
        Map<String, Object> map;
        String ret;
        Pattern compile = Pattern.compile("https://item.jd.com/([0-9]+).html");
        Matcher matcher = compile.matcher(goodUrl);
        String goodid;
        if (matcher.find()) {
            goodid = matcher.group(1);
        } else {
            throw new BusinessException("无效的商品链接");
        }
        GoodInfoVo info = new GoodInfoVo();
        String token = shopDao.selectAccessTokenByPrimaryKey(shopId);
        SkuReadFindSkuByIdRequest request = new SkuReadFindSkuByIdRequest();
        request.setSkuId(Long.valueOf(goodid));
        request.setField("jdPrice,skuName,wareTitle,wareId,logo");
        JdClient client = new DefaultJdClient(JDConstants.APIURL, token, JDConstants.OPENAPPKEY, JDConstants.OPENAPPSECRET);
        try {
            SkuReadFindSkuByIdResponse response = client.execute(request);
            if (notEmpty(response)) {
                Sku sku = response.getSku();
                if (notEmpty(sku)) {
                    info.setPrice(sku.getJdPrice().toString());
                    info.setGoodTitle(sku.getWareTitle());
                    info.setGoodId(sku.getWareId()+"");
                    info.setPicUrls(Collections.singletonList(JDConstants.IMAGE_PREFIX + sku.getLogo()));
                    info.setPromotionPrice(sku.getJdPrice().toString());
                }else{

                }
            }else {
                throw new BusinessException("商品信息获取失败");
            }
        } catch (JdException e) {
            e.printStackTrace();
        }
        map = new HashMap<>();
        map.put("token", token);
        ret = HttpConnUtil.doPostJson(JDConstants.GET_SHOP_URL, com.alibaba.fastjson.JSONObject.toJSONString(map));
        if (ret.startsWith("{")) {
            SellerVenderInfoGetResponse VenderResponse = com.alibaba.fastjson.JSONObject.parseObject(ret, SellerVenderInfoGetResponse.class);
            VenderInfoResult vender = VenderResponse.getVenderInfoResult();
            if (notEmpty(vender)) {
                info.setShopName(vender.getShopName());
                info.setShopId(vender.getShopId());
            }
        }
        return info;
    }

    @Override
    public GoodInfoVo getPDDGoodInfo(String goodUrl, int shopId) {
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if (empty(shop)) {
            throw new BusinessException("店铺信息获取失败,请联系客服处理");
        }
        if(shop.getType()!=3){
            throw new BusinessException("商品链接为拼多多商品，请选择拼多多店铺");
        }
        // 拼多多
        Map<String, String> strMap = ParamUtil.URLRequest(goodUrl);
        String goodId = strMap.get("goods_id");
        String sku_id = strMap.get("sku_id");
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goodId);
        params.put("access_token",shopService.getPDDToken(shopId));
        params.put("type", "pdd.goods.detail.get");

        JSONObject res = PDDUtil.request(params);
        JSONObject jsonRes = res.getJSONObject("goods_detail_get_response");

        if(jsonRes.containsKey("error_response")){
            JSONObject response = jsonRes.getJSONObject("error_response");
            throw new BusinessException(response.getInt("error_code")+":"+response.getString("error_msg"));
        }

        GoodInfoVo info = new GoodInfoVo();
        info.setShopName(shop.getTitle());
        info.setShopId(Long.parseLong(shop.getDianid()));
        JSONArray sku_list = jsonRes.getJSONArray("sku_list");
        for(int i=0; i< sku_list.size(); i++){
            JSONObject jsonObject = sku_list.getJSONObject(i);
            String sku_id1 = jsonObject.getString("sku_id");
            Integer price = jsonObject.getInt("price");
            Integer multi_price = jsonObject.getInt("multi_price");
            if(sku_id == null){
                info.setPrice(price + "");
                info.setPromotionPrice(multi_price + "");
                break;
            }else{
                if(sku_id1.equals(sku_id)){
                    info.setPrice(price + "");
                    info.setPromotionPrice(multi_price + "");
                    break;
                }
            }
        }
        info.setGoodTitle(jsonRes.getString("goods_name"));
        info.setGoodId(Long.parseLong(goodId)+"");
        JSONArray imgs = jsonRes.getJSONArray("carousel_gallery_list");
        if(imgs != null && imgs.size() >0){
            for(int i=0; i< imgs.size(); i++){
                info.getPicUrls().add(imgs.getString(i));
            }
        }
        return info;
    }

    @Override
    public GoodInfoVo doGetYZGoodInfo(String goodUrl, int shopId) {
        TShop shop = shopDao.selectByPrimaryKey(shopId);
        if (empty(shop))
            throw new BusinessException("店铺信息获取失败,请联系客服处理");
        if(shop.getType()!=4)
            throw new BusinessException("商品链接为有赞商品，请选择有赞店铺");
        Map<String, Object> map;
        String ret;
        String skuId = "";
        String goodId = "";
        if(goodUrl.contains("alias=")){
            Map<String, String> strMap = ParamUtil.URLRequest(goodUrl);
            goodId = strMap.get("alias");
        }else if(goodUrl.contains("goods/")){
            if(goodUrl.contains("?")){
                goodId = goodUrl.substring(goodUrl.lastIndexOf("/")+1,goodUrl.indexOf("?"));
            }else{
                goodId = goodUrl.substring(goodUrl.lastIndexOf("/")+1);
            }
        }
        if(goodUrl.contains("sku_id")){
            Map<String, String> strMap = ParamUtil.URLRequest(goodUrl);
            skuId = strMap.get("sku_id");
        }
        if(empty(goodId))
           throw new BusinessException("宝贝地址解析失败，请检查地址是否有效");
        GoodInfoVo info = new GoodInfoVo();
        String token = doGetYZToken(shopId);
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        YouzanItemGetParams youzanItemGetParams = new YouzanItemGetParams();
        youzanItemGetParams.setAlias(goodId);
        YouzanItemGet youzanItemGet = new YouzanItemGet();
        youzanItemGet.setAPIParams(youzanItemGetParams);
        YouzanItemGetResult result = client.invoke(youzanItemGet);
        if(result!=null) {
            YouzanItemGetResult.ItemDetailOpenModel detail = result.getItem();
            if(detail!=null){
                info.setGoodTitle(detail.getTitle());
                info.setGoodId(goodId);
                List<String> imgs = Arrays.stream(detail.getItemImgs()).map(YouzanItemGetResult.ItemImageOpenModel::getMedium).collect(Collectors.toList());
                info.setPicUrls(imgs);
                if(empty(skuId)) {
                    info.setPrice(detail.getPrice()+"");
                    info.setPromotionPrice(detail.getPrice()+"");
                }else if(detail.getSkus().length>0){
                    for(YouzanItemGetResult.ItemSkuOpenModel sku: detail.getSkus()){
                        if(sku.getSkuId().equals(skuId)){
                            info.setPrice(sku.getPrice()+"");
                            info.setPromotionPrice(sku.getPrice()+"");
                            break;
                        }
                    }
                }
                if(empty(info.getPrice())){
                    info.setPrice(detail.getPrice()+"");
                    info.setPromotionPrice(detail.getPrice()+"");
                }
                return info;
            }
        }
        throw new BusinessException("宝贝地址解析失败，请检查地址是否有效");
    }

    public String doGetYZToken(int shopId) {
        String ACCESSTOKENKEY = YZConstants.CLENTID + "-access-token-";
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

    public static void main(String[] args) {

    }
}
