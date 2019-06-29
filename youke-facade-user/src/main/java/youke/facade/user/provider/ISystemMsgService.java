package youke.facade.user.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.SystemMsgQueryVo;

public interface ISystemMsgService {

    /**
     * 系统消息设置已读
     *
     * @param id
     * @param youkeId
     */
    void readMsg(Integer id, String youkeId);

    /**
     * 获取系统未读消息的数量
     *
     * @param youkeId
     * @return
     */
    int getnewnum(String youkeId);

    /**
     * 获取分页系统消息
     *
     * @return
     */
    PageInfo<TSysMessage> getSystemMsgList(SystemMsgQueryVo qo,String youkeId);
}
