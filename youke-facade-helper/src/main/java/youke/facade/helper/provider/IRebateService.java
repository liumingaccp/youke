package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TRebateActive;
import youke.common.model.vo.param.helper.HelperOrderVo;
import youke.common.model.vo.param.helper.RebateOrderExamineParam;
import youke.common.model.vo.param.helper.RebateOrderQueryVo;
import youke.common.model.vo.param.helper.RebateQueryVo;
import youke.common.model.vo.result.helper.RebateDetailVo;
import youke.common.model.vo.result.helper.RebateOrderDetailRetVo;
import youke.common.model.vo.result.helper.RebateOrderQueryRetVo;
import youke.common.model.vo.result.helper.RebateQueryRetVo;
import youke.facade.helper.vo.RebateActiveVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/2
 * Time: 12:12
 */
public interface IRebateService {
    int addActive(RebateActiveVo vo);

    void deleteActive(Integer activeId);

    PageInfo<RebateQueryRetVo> queryList(RebateQueryVo params, String codePath);

    PageInfo<RebateOrderQueryRetVo> queryOrderList(RebateOrderQueryVo params);

    void examinBatch(RebateOrderExamineParam param);

    String doImportOrder(RebateOrderQueryVo params);

    RebateOrderDetailRetVo getExamineDetail(Integer orderId, String appId);

    RebateDetailVo getActiveDetail(Integer activeId, String appId, String dykId);

    long saveRebateOrder(HelperOrderVo params);

    void saveOrderNum(String appId,String openId, Long orderId, String orderNo);

    void updateState(Integer activeId, String dykId, Integer state);

    RebateOrderQueryRetVo getOrderInfo(String appId, String openId, long orderId);
}
