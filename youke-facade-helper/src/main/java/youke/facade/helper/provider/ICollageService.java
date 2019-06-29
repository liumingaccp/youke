package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.helper.CollageOrderQueryVo;
import youke.common.model.vo.param.helper.CollageQueryVo;
import youke.common.model.vo.param.helper.TuanVo;
import youke.common.model.vo.result.helper.*;
import youke.facade.helper.vo.CollageActiveVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/12
 * Time: 9:27
 */
public interface ICollageService {
    
    int addActive(CollageActiveVo params);

    PageInfo<CollageQueryRetVo> queryList(CollageQueryVo params, String codePath);

    PageInfo<CollageOrderQueryRetVo> queryOrderList(CollageOrderQueryVo params);

    String doImportOrder(CollageOrderQueryVo params);

    void deleteActive(Integer activeId);

    void updateState(Integer activeId, String appId, String dykId, Integer state);

    CollageActiveDetailVo getAtiveDetail(Integer id, String appId, String dykId);

    /**
     *  --------------------H5
     */


    /**
     * 可参与拼团的活动列表
     * @param params
     * @return
     */
    PageInfo<CollageQueryRetVoH5> queryListForH5(CollageQueryVo params);

    /**
     * 个人拼团活动列表
     * @param params
     * @return
     */
    PageInfo<CollageQueryRetVoByOpenId> queryListForOpenId(CollageQueryVo params);

    /**
     * 获取个人拼团详情
     * @param appId
     * @param openId
     * @param tuanId
     * @return
     */
    TuanDetailVo getTuanDetailByOpenId(String appId, String openId, Integer tuanId);

    /**
     * 获取拼团活动单个信息(加上拼团数)
     * @param appId
     * @param openId
     * @param activeId
     * @return
     */
    CollageActiveDetailAndTuanVo getActiveDetailWithTuanDetail(String appId, String openId, Integer activeId);

    /**
     * 发起拼团
     * @param vo
     * @return
     */
    Long submitTuan(TuanVo vo);

    CollageOrderDetailRetVo getExamineDetail(Long orderId, String appId, String dykId);
}
