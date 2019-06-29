package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TTaokeActive;
import youke.common.model.vo.page.PageResult;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.helper.*;
import youke.facade.helper.vo.TaokeActiveVo; /**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 16:03
 */
public interface ITaokeService {
    int addActive(TaokeActiveVo params);

    void deleteActive(Integer activeId);

    PageInfo<TaokeOrderQueryRetVo> queryOrderList(TaokeOrderQueryVo params);

    PageResult<H5TaokeOrderQueryRetVo> queryOrderList(H5TaokeOrderQueryVo params);

    PageInfo<TaokeQueryRetVo> queryList(TaokeQueryVo params, String codePath);

    void examinBatch(TaokeOrderExamineParam params);

    TaokeOrderDetailRetVo getExamineDetail(Integer orderId, String appId);

    String doImportOrder(TaokeOrderQueryVo params);

    youke.common.model.vo.result.TaokeActiveVo getAtiveDetail(Integer activeId, String appId, String youkeId);

    long saveTaokeOrder(HelperOrderVo params);

    PageInfo<H5TaokeOrderDetailQueryRetVo> queryDetailList(H5TaokeDetailQueryVo params);

    void saveOrderNum(String appId, String openId, Long orderId, String orderNo);

    /**
     * 生成海报
     * @param activeId
     * @param openId
     * @return
     */
    String createPoter(Integer activeId, String openId);

    void updateState(Integer activeId, String dykId, Integer state);

    H5TaokeOrderQueryRetVo getOrderInfo(String appId, String openId, long orderId);
}
