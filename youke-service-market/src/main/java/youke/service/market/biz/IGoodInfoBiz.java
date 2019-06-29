package youke.service.market.biz;

import youke.facade.market.vo.GoodInfoVo;

public interface IGoodInfoBiz {

    GoodInfoVo getTBGoodInfo(String goodUrl, int shopId);

    GoodInfoVo getJDGoodInfo(String goodUrl, int shopId);

    GoodInfoVo getPDDGoodInfo(String goodUrl, int shopId);

    GoodInfoVo doGetYZGoodInfo(String goodUrl, int shopId);
}
