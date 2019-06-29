package youke.service.market.provider;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.model.vo.result.TaowxPoterVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.EncodeUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.ParamUtil;
import youke.facade.market.provider.IToolService;
import youke.facade.market.vo.GoodInfoVo;
import youke.facade.market.vo.TaowxVo;
import youke.service.market.biz.IGoodInfoBiz;
import youke.service.market.biz.ITaowxBiz;
import youke.service.market.utils.PoterImgUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
public class ToolService extends Base implements IToolService {

    @Resource
    private IGoodInfoBiz goodInfoBiz;
    @Resource
    private ITaowxBiz taowxBiz;

    public TaowxVo doTaowx(String url, String appId, String youkeId) {
        String wxUrl = "";
        if (url.contains("taobao.com") || url.contains("tmall.com")) {
            if (url.startsWith("https://detail.tmall.com/item.htm") || url.startsWith("https://item.taobao.com/item.htm")) {
                String itemId = ParamUtil.URLRequest(url).get("id");
                if (empty(itemId))
                    throw new BusinessException("宝贝链接地址有误");
                url = ParamUtil.UrlPage(url) + "?id=" + itemId;
                wxUrl = ApiCodeConstant.WX_TAO + itemId;
            } else {
                wxUrl = "http://t.ecbao.cn/taobao_shop.php?uri=" + EncodeUtil.urlEncode(url.substring(0, url.indexOf(".com") + 4));
            }
            JSONObject parObj = new JSONObject();
            parObj.put("longUrl", wxUrl);
            parObj.put("appId", appId);
            String resStr = HttpConnUtil.doPostJson(ApiCodeConstant.DOMAIN_PCAPI + "common/getshorturl", parObj.toString());
            JSONObject resObj = JSONObject.fromObject(resStr);
            String shortUrl = resObj.getString("data");
            int posterId = taowxBiz.addTaowxPoster(url, shortUrl, youkeId);
            TaowxVo taowxVo = new TaowxVo();
            taowxVo.setCodeUrl(ApiCodeConstant.DOMAIN_PCAPI + "common/qrcode?d=" + EncodeUtil.urlEncode(shortUrl));
            taowxVo.setPosterId(posterId);
            taowxVo.setShortUrl(shortUrl);
            return taowxVo;
        }
        throw new BusinessException("只支持淘宝或天猫宝贝地址");
    }

    public GoodInfoVo getProductInfo(String goodUrl, int shopId) {
        if (goodUrl.contains("taobao.com") || goodUrl.contains("tmall.com"))
            return goodInfoBiz.getTBGoodInfo(goodUrl,shopId);
        if (goodUrl.contains("jd.com"))
            return goodInfoBiz.getJDGoodInfo(goodUrl, shopId);
        if(goodUrl.contains("yangkeduo.com"))
            return goodInfoBiz.getPDDGoodInfo(goodUrl,shopId);
        if(goodUrl.contains("youzan.com"))
            return goodInfoBiz.doGetYZGoodInfo(goodUrl,shopId);
        throw new BusinessException("只支持淘宝,天猫，京东，拼多多,有赞商品地址");
    }


    public PageInfo<TaowxPoterVo> getTaoWxList(int page, int limit, String timeStart, String timeEnd, String dykId) {
        if (page < 0) {
            page = 1;
        }
        if (limit < 0) {
            limit = 20;
        }
        timeStart = StringUtils.hasLength(timeStart) ? timeStart : null;
        timeEnd = StringUtils.hasLength(timeEnd) ? timeEnd : null;
        PageInfo<TaowxPoterVo> pageInfo = taowxBiz.getTaoWxList(page, limit, timeStart, timeEnd, dykId);
        List<TaowxPoterVo> list = pageInfo.getList();
        if (list != null && list.size() > 0) {
            for (TaowxPoterVo vo : list) {
                vo.setCodeUrl(ApiCodeConstant.DOMAIN_PCAPI + "common/qrcode?t=1&d=" + EncodeUtil.urlEncode(vo.getShortUrl()));
            }
        }
        return pageInfo;
    }

    public void savePoster(int posterId, String posterUrl, String body) {
        if (posterId < 0) {
            throw new BusinessException("参数错误");
        }
        taowxBiz.savePoter(posterId, posterUrl, body);
    }

    public TaowxPoterVo select(int id) {
        if (id > 0) {
            return taowxBiz.select(id);
        }
        return null;
    }

    public String createPoter(String posterUrl, String body, String shortUrl) {
        if (posterUrl == null) {
            throw new BusinessException("posterUrl 不能为空");
        }
        InputStream posterStream = HttpConnUtil.getStreamFromNetByUrl(posterUrl + "?x-oss-process=image/resize,m_fill,h_600,w_600");
        InputStream fingerStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.OSS_BASE + "png/add94cd21712e55836a6aba74f8a3ac6.png");
        InputStream codeStream = HttpConnUtil.getStreamFromNetByUrl(ApiCodeConstant.DOMAIN_PCAPI + "common/qrcode?d=" + URLEncoder.encode(shortUrl));
        File resultFile = null;
        try {
            resultFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            PoterImgUtil.graphicsGeneration(body, posterStream, fingerStream, codeStream, resultFile);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        } finally {
            if (posterStream != null) {
                try {
                    posterStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fingerStream != null) {
                try {
                    fingerStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (codeStream != null) {
                try {
                    codeStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String key = "haibao/" + UUID.randomUUID().toString() + ".jpg";
        return FileUpOrDwUtil.uploadLocalFile(resultFile, key);
    }

}
