package youke.facade.user.provider;

import youke.common.model.vo.result.SystemNoticeVo;

public interface ISystemNoticeService {
    /**
     * 获取系统公告
     *
     * @return
     */
    SystemNoticeVo selectSystemNotice();
}
