package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.SystemMsgQueryVo;
import youke.common.model.vo.result.SystemNoticeVo;
import youke.facade.user.provider.ISystemMsgService;
import youke.facade.user.provider.ISystemNoticeService;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;

@RestController
@RequestMapping("message")
public class SystemMessageAction extends BaseAction {

    @Resource
    private ISystemMsgService systemMsgService;
    @Resource
    private ISystemNoticeService systemNoticeService;

    /**
     * 读取系统消息
     *
     * @return
     */
    @RequestMapping(value = "setread", method = RequestMethod.POST)
    public JsonResult readMsg() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        JsonResult result = new JsonResult();
        systemMsgService.readMsg(id, youkeId);
        return result;
    }

    /**
     * 获取未读消息的数量
     *
     * @return
     */
    @RequestMapping(value = "getnewnum", method = RequestMethod.POST)
    public JsonResult getnewnum() {
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        JsonResult result = new JsonResult();
        int num = systemMsgService.getnewnum(youkeId);
        result.setData(num);
        return result;
    }

    /**
     * 获取系统分页消息
     * 按时间降序排
     *
     * @return
     */
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public JsonResult getMsgList() {
        SystemMsgQueryVo qo = getParams(SystemMsgQueryVo.class);
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        JsonResult result = new JsonResult();
        PageInfo<TSysMessage> info = systemMsgService.getSystemMsgList(qo, youkeId);
        result.setData(info);
        return result;
    }


    /**
     * 获取系统公告
     *
     * @return
     */
    @RequestMapping(value = "getsystemnotice", method = RequestMethod.POST)
    public JsonResult getsysnotice() {
        SystemNoticeVo vo = systemNoticeService.selectSystemNotice();
        return new JsonResult(vo);
    }
}
