package youke.service.user.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.SystemMsgQueryVo;
import youke.facade.user.provider.ISystemMsgService;
import youke.service.user.biz.ISystemMsgBiz;

import javax.annotation.Resource;

@Service
public class SystemMsgService extends Base implements ISystemMsgService {

    @Resource
    private ISystemMsgBiz systemMsgBiz;

    public void readMsg(Integer id, String youkeId) {
        if (notEmpty(id) && notEmpty(youkeId)) {
            systemMsgBiz.updateMsg(id, youkeId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public int getnewnum(String youkeId) {
        if (notEmpty(youkeId)) {
            return systemMsgBiz.getNewNum(youkeId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public PageInfo<TSysMessage> getSystemMsgList(SystemMsgQueryVo qo, String youkeId) {
        if (empty(youkeId))
            throw new BusinessException("无效的请求或参数");
        return systemMsgBiz.getSystemMsgList(qo, youkeId);
    }
}

