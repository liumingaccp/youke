package youke.service.market.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.result.TaowxPoterVo;

public interface ITaowxBiz {

    int addTaowxPoster(String url, String shortUrl,String youkeId);

    PageInfo<TaowxPoterVo> getTaoWxList(int page, int limit, String timeStart, String timeEnd, String dykId);

    void savePoter(int posterId, String posterUrl, String body);

    TaowxPoterVo select(int id);
}
