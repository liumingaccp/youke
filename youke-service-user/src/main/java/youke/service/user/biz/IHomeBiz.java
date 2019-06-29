package youke.service.user.biz;

import youke.common.model.vo.result.AccountDataVo;
import youke.facade.user.vo.WaitDataVo;

public interface IHomeBiz {
    /**
     * 获取我的账户统计
     *
     * @param youkeId
     * @return
     */
    AccountDataVo getAccontData(String youkeId);

    /**
     * 获取代办事项数据
     *
     * @return
     */
    WaitDataVo getWaitData(String dykId);
}
