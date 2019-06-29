package youke.service.user.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.SystemMsgQueryVo;

public interface ISystemMsgBiz {
    /**
     * 系统消息设置已读
     *
     * @param id
     * @param youkeId
     */
    void updateMsg(Integer id, String youkeId);

    /**
     * 获取系统未读消息数量
     *
     * @param youkeId
     * @return
     */
    int getNewNum(String youkeId);

    /**
     * 获取系统分页消息
     *
     * @param qo
     * @return
     */
    PageInfo<TSysMessage> getSystemMsgList(SystemMsgQueryVo qo, String youkeId);
}
