package youke.service.user.biz;

import youke.common.model.vo.result.SystemNoticeVo;

public interface ISystemNoticeBiz {
    /**
     * 查询系统消息
     *
     * @return
     */
    SystemNoticeVo selectSystemNotice();
}
