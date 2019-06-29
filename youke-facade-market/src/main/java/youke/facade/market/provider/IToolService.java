package youke.facade.market.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.result.TaowxPoterVo;
import youke.facade.market.vo.GoodInfoVo;
import youke.facade.market.vo.TaowxVo;

/**
 * 实用工具
 */
public interface IToolService {

    /**
     * 淘宝/天猫url
     * @param url
     * @param appId
     * @param youkeId
     * @return
     */
    TaowxVo doTaowx(String url, String appId,String youkeId);

    /**
     * 获取天猫 商品信息
     *
     * @param goodUrl
     * @param shopId
     * @return
     */
    GoodInfoVo getProductInfo(String goodUrl, int shopId);

    PageInfo<TaowxPoterVo> getTaoWxList(int page, int limit, String timeStart, String timeEnd, String dykId);

    void savePoster(int posterId, String posterUrl, String body);

    TaowxPoterVo select(int id);

    String createPoter(String posterUrl, String body, String shortUrl);

}
