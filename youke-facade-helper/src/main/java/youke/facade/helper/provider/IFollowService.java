package youke.facade.helper.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TFollowActive;
import youke.common.model.TFollowActivePoster;
import youke.common.model.vo.param.helper.FollowOrderPosterVo;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;
import youke.facade.helper.vo.FollowActiveVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/3
 * Time: 16:03
 */
public interface IFollowService {
    int addActive(FollowActiveVo params);

    void deleteActive(Integer activeId);

    PageInfo<FollowOrderQueryRetVo> queryOrderList(FollowOrderQueryVo params);

    PageInfo<FollowQueryRetVo> queryList(FollowQueryVo params, String codePath);

    long saveFollowOrderPoster(FollowOrderPosterVo params);

    String createPoter(Integer activeId, String openId);

    TFollowActivePoster selectPoter(Integer activeId, String openId);

    TFollowActive getActiveDetail(Integer id, String appId, String dykId);

    void updateState(Integer activeId,String youkeId, Integer state);
}
