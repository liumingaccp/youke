package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.wxper.AutoLikeRecordQueryVo;
import youke.common.model.vo.param.wxper.AutoLikeTaskAuditVo;
import youke.common.model.vo.param.wxper.CircleMarketRecordQueryVo;
import youke.common.model.vo.param.wxper.CircleMessageSaveVo;
import youke.common.model.vo.result.wxper.AutoLikeRecordQueryRetVo;
import youke.common.model.vo.result.wxper.AutoLikeTaskAuditRetVo;
import youke.common.model.vo.result.wxper.AutoLikeTaskVo;
import youke.common.model.vo.result.wxper.CircleMarketRecordQueryRetVo;
import youke.facade.user.vo.UserVo;
import youke.facade.wxper.provider.IWxPerCircleMarketService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "circlemarketing")
public class WxPerCircleAction extends BaseAction {

    @Resource
    private IWxPerCircleMarketService wxPerCircleMarketService;

    @RequestMapping(value = "createmessage", method = RequestMethod.POST)
    public JsonResult createMessage() {
        CircleMessageSaveVo params = getParams(CircleMessageSaveVo.class);
        UserVo user = AuthUser.getUser();
        wxPerCircleMarketService.createMessage(params, user.getDykId());
        return new JsonResult();
    }

    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getRecordList() {
        CircleMarketRecordQueryVo params = getParams(CircleMarketRecordQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<CircleMarketRecordQueryRetVo> info = wxPerCircleMarketService.getRecordList(params, user.getDykId());
        return new JsonResult(info);
    }

    @RequestMapping(value = "repush", method = RequestMethod.POST)
    public JsonResult rePush() {
        JSONObject params = getParams();
        wxPerCircleMarketService.doRePush(params.getLong("id"));
        return new JsonResult();
    }

    @RequestMapping(value = "delrecord", method = RequestMethod.POST)
    public JsonResult delRecord() {
        JSONObject params = getParams();
        wxPerCircleMarketService.delRecord(params.getLong("id"));
        return new JsonResult();
    }

    @RequestMapping(value = "getautoliketasklist", method = RequestMethod.POST)
    public JsonResult getAutoLikeTaskList() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        PageInfo<AutoLikeTaskVo> info = wxPerCircleMarketService.getAutoLikeTaskList(user.getDykId(), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    @RequestMapping(value = "getlikerecordlist", method = RequestMethod.POST)
    public JsonResult getLikeRecordList() {
        AutoLikeRecordQueryVo params = getParams(AutoLikeRecordQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<AutoLikeRecordQueryRetVo> info = wxPerCircleMarketService.getAutoLikeRecordList(params, user.getDykId());
        return new JsonResult(info);
    }

    @RequestMapping(value = "getliketaskauditdata", method = RequestMethod.POST)
    public JsonResult getLikeTaskAuditData() {
        JSONObject params = getParams();
        AutoLikeTaskAuditRetVo vo = wxPerCircleMarketService.getLikeTaskAuditData(params.getLong("id"));
        return new JsonResult(vo);
    }

    @RequestMapping(value = "updateliketask", method = RequestMethod.POST)
    public JsonResult updateLikeTask() {
        UserVo user = AuthUser.getUser();
        AutoLikeTaskAuditVo params = getParams(AutoLikeTaskAuditVo.class);
        String ret = wxPerCircleMarketService.updateLikeTask(params, user.getDykId());
        return new JsonResult(ret);
    }
}
