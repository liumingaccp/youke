package youke.service.user.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.ISysMessageDao;
import youke.common.exception.BusinessException;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.SystemMsgQueryVo;
import youke.service.user.biz.ISystemMsgBiz;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SystemMsgBiz implements ISystemMsgBiz {

    @Resource
    private ISysMessageDao sysMessageDao;

    public void updateMsg(Integer id, String youkeId) {
        int count = sysMessageDao.updateSystemMsgState(id, youkeId);
        if (count == 0) {
            throw new BusinessException("消息状态更新失败或不存在该消息");
        }
    }

    public int getNewNum(String youkeId) {
        return sysMessageDao.getUnReadMsgNum(youkeId);
    }

    public PageInfo<TSysMessage> getSystemMsgList(SystemMsgQueryVo qo, String youkeId) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<TSysMessage> list = sysMessageDao.getSystemMsgList(qo.getKeyword(), youkeId);
        if (list.size() == 0) {
            return new PageInfo<TSysMessage>(Collections.EMPTY_LIST);
        }
        int totalPage = list.size() % qo.getLimit() == 0 ? list.size() / qo.getLimit() : list.size() / qo.getLimit() + 1;
        PageInfo<TSysMessage> info = new PageInfo<TSysMessage>(list, totalPage);
        return info;
    }
}
