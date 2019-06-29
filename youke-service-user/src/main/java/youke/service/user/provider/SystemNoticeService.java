package youke.service.user.provider;

import org.springframework.stereotype.Service;
import youke.common.model.vo.result.SystemNoticeVo;
import youke.facade.user.provider.ISystemNoticeService;
import youke.service.user.biz.ISystemNoticeBiz;

import javax.annotation.Resource;

@Service
public class SystemNoticeService implements ISystemNoticeService {

    @Resource
    private ISystemNoticeBiz systemNoticeBiz;

    @Override
    public SystemNoticeVo selectSystemNotice() {
        return systemNoticeBiz.selectSystemNotice();
    }
}
