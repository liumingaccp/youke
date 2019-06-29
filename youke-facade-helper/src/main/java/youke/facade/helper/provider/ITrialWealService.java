package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.TrialActiveRetVo;
import youke.common.model.vo.result.helper.H5TtrialOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderDetailRetVo;
import youke.common.model.vo.result.helper.TrialWealOrderQueryRetVo;
import youke.common.model.vo.result.helper.TrialWealQueryRetVo;
import youke.facade.helper.vo.DemoPics;
import youke.facade.helper.vo.TrialWealActiveVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 16:03
 */
public interface ITrialWealService {
    int addActive(TrialWealActiveVo params);

    void deleteActive(Integer activeId);

    PageInfo<TrialWealOrderQueryRetVo> queryOrderList(TrialWealOrderQueryVo params);

    PageInfo<TrialWealQueryRetVo> queryList(TrialWealQueryVo params, String codePath);

    void examinBatch(TrialWealOrderExamineParam params);

    TrialWealOrderDetailRetVo getExamineDetail(Long orderId, String appId);

    String doImportOrder(TrialWealOrderQueryVo params);

    TrialActiveRetVo getDetail(String appId, Integer id, String dykId);

    List<DemoPics> getTrialExamplePic(H5QueryPicVo params);

    void saveOrderNum(String appId, String openId, Long orderId, String orderNo);

    /**
     * 获取已提交的示例截图
     * @param appId
     * @param id
     * @return
     */
    H5TtrialOrderDetailRetVo getTrialOrderDetail(String appId, Long id);

    long saveAccountPic(Long activeId, String appId, String opneId, String fileUrl);

    long saveTrialOrder(HelperOrderVo params);

    void saveTrialOrderPic(TrialOrderPicsVo params);

    String getAccountExaminPic(long activeId, String openId, String appId);

    void updateState(Integer activeId, String dykId, Integer state);

    TrialWealOrderQueryRetVo getOrderInfo(long orderId, String openId, String appId);
}
