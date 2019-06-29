package youke.service.user.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.dao.IAdminNoticeDao;
import youke.common.model.TAdminNotice;
import youke.common.model.vo.result.SystemNoticeVo;
import youke.common.utils.DateUtil;
import youke.service.user.biz.ISystemNoticeBiz;

import javax.annotation.Resource;

@Service
public class SystemNoticeBiz implements ISystemNoticeBiz {
    @Resource
    private IAdminNoticeDao adminNoticeDao;

    @Override
    public SystemNoticeVo selectSystemNotice() {
        TAdminNotice notice = adminNoticeDao.selectSystemNotices();
        SystemNoticeVo noticeVo;
        if (notice != null) {
            if (DateUtil.compareDate(notice.getEndTime())) {
                noticeVo = new SystemNoticeVo();
                noticeVo.setContent(notice.getContent());
                noticeVo.setStartTime(DateUtil.formatDateTime(notice.getStartTime()));
                noticeVo.setEndTime(DateUtil.formatDateTime(notice.getEndTime()));
                return noticeVo;
            }
        }
        return null;
    }
}
