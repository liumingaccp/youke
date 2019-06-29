package youke.service.market.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.TIntegralActive;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.IntegralActiveDetailVo;
import youke.common.model.vo.result.IntegralActiveVo;
import youke.common.model.vo.result.IntegralOrderVo;
import youke.common.model.vo.result.SubFansIntegralVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:56
 */
public interface IIntegraWealBiz {
    PageInfo<IntegralActiveVo> getActiveList(String title, int state, int page, int limit, String youkeId);

    int addIntegraActive(TIntegralActive model);

    PageInfo<IntegralOrderVo> getOrderList(WealOrderQueryVo params);

    long getTotalIntegral(String dykId);

    PageInfo<SubFansIntegralVo> getIntegralList(WealIntegralQueryVo params);

    void updateActice(TIntegralActive info);

    IntegralActiveDetailVo getActice(int activeId, String appId);

    Long getIntegralCount(WealIntegralQueryVo params);

    Long getIntegralOrderCount(WealOrderQueryVo params);
}
