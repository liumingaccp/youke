package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.MassFansQueryVo;
import youke.common.model.vo.result.MassFansVo;
import youke.common.model.vo.result.MassRecordVo;
import youke.facade.wx.provider.IWeixinMassFansService;
import youke.facade.wx.vo.mass.TaskParam;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:16
 */
@RestController
@RequestMapping("wxmass")
public class WxMassAction extends BaseAction {

    @Resource
    private IWeixinMassFansService wxMassFansService;

    /**
     * 高级群发获取发送粉丝列表
     *
     * @return
     */
    @RequestMapping(value = "gethighsendlist", method = RequestMethod.POST)
    public JsonResult getHighSendList() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        PageInfo<MassFansVo> info = wxMassFansService.getSendlist(params, 1);
        result.setData(info);
        return result;
    }

    /**
     * 高级群发获取发送人数
     *
     * @return
     */
    @RequestMapping(value = "gethighsendnum", method = RequestMethod.POST)
    public JsonResult getHighSendNum() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        long count = wxMassFansService.selectCount(params, 1);
        result.setData(count);
        return result;
    }

    /**
     * 高级群发保存发送任务
     *
     * @return
     */
    @RequestMapping(value = "savehighsendtask", method = RequestMethod.POST)
    public JsonResult saveHighSendTask() {
        TaskParam params = getParams(TaskParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        wxMassFansService.saveSendTask(params, 1);
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * 普通群发获取发送粉丝列表
     *
     * @return
     */
    @RequestMapping(value = "gettagsendlist", method = RequestMethod.POST)
    public JsonResult getTagSendList() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        PageInfo<MassFansVo> info = wxMassFansService.getSendlist(params, 2);
        result.setData(info);
        return result;
    }

    /**
     * 普通群发保存发送任务
     *
     * @return
     */
    @RequestMapping(value = "savetagsendtask", method = RequestMethod.POST)
    public JsonResult saveTagSendTask() {
        TaskParam params = getParams(TaskParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        wxMassFansService.saveSendTask(params, 2);
        return result;
    }

    /**
     * 普通群发获取发送人数
     *
     * @return
     */
    @RequestMapping(value = "gettagsendnum", method = RequestMethod.POST)
    public JsonResult getTagSendNum() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        long count = wxMassFansService.selectCount(params, 1);
        result.setData(count);
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * 互动群发获取发送粉丝列表
     *
     * @return
     */
    @RequestMapping(value = "getkefusendlist", method = RequestMethod.POST)
    public JsonResult getKefuSendlist() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        PageInfo<MassFansVo> info = wxMassFansService.getSendlist(params, 3);
        result.setData(info);
        return result;
    }

    /**
     * 互动群发获取发送人数
     *
     * @return
     */
    @RequestMapping(value = "getkefusendnum", method = RequestMethod.POST)
    public JsonResult getKefuSendnum() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        long count = wxMassFansService.selectCount(params, 2);
        result.setData(count);
        return result;
    }

    /**
     * 保存互动群发任务
     *
     * @return
     */
    @RequestMapping(value = "savekefusendtask", method = RequestMethod.POST)
    public JsonResult saveKefuSendTask() {
        TaskParam params = getParams(TaskParam.class);
        params.setAppId(AuthUser.getUser().getAppId());
        JsonResult result = new JsonResult();
        wxMassFansService.saveSendTask(params, 3);
        return result;
    }

    /**
     * 群发记录分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getRecordList() {
        MassFansQueryVo params = getParams(MassFansQueryVo.class);
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<MassRecordVo> pageInfo = wxMassFansService.getRecordList(params);
        return new JsonResult(pageInfo);
    }

    /**
     * 群发记录分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getrecordfanslist", method = RequestMethod.POST)
    public JsonResult getRecordFansList() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<MassFansVo> pageInfo = wxMassFansService.getRecordFansList(id, AuthUser.getUser().getAppId(), page, limit);
        return new JsonResult(pageInfo);
    }

    /**
     * 获取公众号类型
     *
     * @return
     */
    @RequestMapping(value = "getsubscrtype", method = RequestMethod.POST)
    public JsonResult getSubScrtype() {
        int type = wxMassFansService.getSubScrtype(AuthUser.getUser().getAppId());
        return new JsonResult(type);
    }
}
