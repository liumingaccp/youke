package youke.facade.user.provider;

import youke.common.model.vo.result.AccountDataVo;
import youke.facade.user.vo.WaitDataVo;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:33
 */
public interface IHomeService {
    /**
     * 获取待办事项数据
     *
     * @return
     */
    WaitDataVo getWaitData(String dykId);

    /**
     * 获取我的账户统计
     *
     * @param youkeId
     * @return
     */
    AccountDataVo getAccontData(String youkeId);
}
