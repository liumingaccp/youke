package youke.facade.market.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.IntegralActiveOrderVo;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.*;
import youke.facade.market.vo.IntegralActiveParamVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:48
 */
public interface IIntegralWealService {

    final String JI_FEN_SET_KEY = "integral-set-key";

    PageInfo<IntegralActiveVo> getIntegralActiveList(String title, int state, int page, int limit, String youkeId);

    PageInfo<IntegralActiveVo> getIntegralActiveList(String appId, int page, int limit);

    int addIntegraActive(IntegralActiveParamVo params);

    PageInfo<IntegralOrderVo> getIntegralOrderList(WealOrderQueryVo params);

    long getTotalIntegral(WealIntegralQueryVo param);

    PageInfo<SubFansIntegralVo> getIntegralList(WealIntegralQueryVo params);

    String getPageUrl();

    void updateActice(int id, String tkl);

    IntegralActiveDetailVo getActice(int activeId, String appId);

    void deleteActive(int activeId);

    IntegralOrderRetVo submitOrder(IntegralActiveOrderVo params);

    IntegralActiveRetVo getactiveinfo(int activeId, String appId, String youkeId);

    PageInfo<IntegralOrderRetVo> getOrderListByOpenId(String appId, String openId, int page, int limit);

    void saveOrderNum(String appId, String openId, Long orderId, String orderNo);

    /**
     * 配置表获取活动链接
     *
     * @return
     */
    String getConfig();

    void updateState(Integer activeId, String dykId, Integer state);

    /**
     * 获取订单详情
     * @param orderId
     * @param appId
     * @param openId
     * @return
     */
    IntegralOrderRetVo getOrderInfo(int orderId, String appId, String openId);

    /**
     * 查询兑换明细消耗的积分
     * @param params
     * @return
     */
    long getIntegralOrderCount(WealOrderQueryVo params);

    /**
     * 获取积分设置
     * @return
     */
    IntegralSetVo getIntegralSet(String dykId);

    void saveIntegralSet(IntegralSetVo setVo, String dykId);
}
