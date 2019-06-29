package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import youke.common.bean.JsonResult;
import youke.common.model.TMobcodeDetail;
import youke.common.model.TMobcodeTemplete;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.MassRecordRetVo;
import youke.common.model.vo.result.MassTempListVo;
import youke.common.model.vo.result.MobcodeRecordVo;
import youke.common.utils.JsonUtils;
import youke.facade.mass.provider.IMassSMSService;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 短信群发
 */
@RestController
@RequestMapping("mass")
public class MassSMSAction extends BaseAction {
    @Resource
    private IMassSMSService massSMSService;

    /**
     * 分页获取模板
     *
     * @return
     */
    @RequestMapping(value = "gettempletelist", method = RequestMethod.POST)
    public JsonResult getTemplateList() {
        JsonResult result = new JsonResult();
        MassSMSQueryVo params = getParams(MassSMSQueryVo.class);
        String dykId = AuthUser.getUser().getDykId();
        PageInfo<TMobcodeTemplete> info = massSMSService.getTemplateList(params, dykId);
        result.setData(info);
        return result;
    }


    /**
     * 获取模板名称列表
     *
     * @return
     */
    @RequestMapping(value = "gettempletenames", method = RequestMethod.POST)
    public JsonResult getTemplateNames() {
        MassSMSQueryVo params = getParams(MassSMSQueryVo.class);
        String dykId = AuthUser.getUser().getDykId();
        List<String> names = massSMSService.getTemplateNames(params, dykId);
        return new JsonResult(names);
    }


    /**
     * 短信群发记录分页获取
     *
     * @return
     */
    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getRecordList() {
        MassSMSQueryVo params = getParams(MassSMSQueryVo.class);
        String dykId = AuthUser.getUser().getDykId();
        PageInfo<MobcodeRecordVo> info = massSMSService.getRecordList(params, dykId);
        return new JsonResult(info);
    }

    /**
     * 会员群发获取临时发送名单
     *
     * @return
     */
    @RequestMapping(value = "getshopsendlist", method = RequestMethod.POST)
    public JsonResult getshopsendlist() {
        MassSMSParamVo params = getParams(MassSMSParamVo.class);
        String dykId = AuthUser.getUser().getDykId();
        String appId = AuthUser.getUser().getAppId();
        PageInfo<MassTempListVo> info = massSMSService.getShopSendList(params, dykId, appId);
        return new JsonResult(info);
    }

    /**
     * 用户模板保存
     *
     * @return
     */
    @RequestMapping(value = "savetemplete", method = RequestMethod.POST)
    public JsonResult savetemplete() {
        MassTemplateParamVo params = getParams(MassTemplateParamVo.class);
        String dykId = AuthUser.getUser().getDykId();
        massSMSService.saveTemplate(params, dykId);
        return new JsonResult();
    }

    /**
     * 短信发送记录明细分页获取
     *
     * @return
     */
    @RequestMapping(value = "getrecorddetail", method = RequestMethod.POST)
    public JsonResult getrecorddetail() {
        MassRecordDetailQueryVo params = getParams(MassRecordDetailQueryVo.class);
        String dykId = AuthUser.getUser().getDykId();
        PageInfo<TMobcodeDetail> info = massSMSService.getRecordDetailList(params, dykId);
        return new JsonResult(info);
    }

    /**
     * 普通短信发送保存任务
     *
     * @return
     */
    @RequestMapping(value = "savesendtask", method = RequestMethod.POST)
    public JsonResult savesendtask() {
        MassSMSParamVo params = getParams(MassSMSParamVo.class);
        String dykId = AuthUser.getUser().getDykId();
        String appId = AuthUser.getUser().getAppId();
        massSMSService.saveSendTask(params, dykId, appId);
        return new JsonResult();
    }

    /**
     * 会员短信发送保存任务
     *
     * @return
     */
    @RequestMapping(value = "saveshopsendtask", method = RequestMethod.POST)
    public JsonResult saveshopsendtask() {
        MassSMSParamVo params = getParams(MassSMSParamVo.class);
        String dykId = AuthUser.getUser().getDykId();
        String appId = AuthUser.getUser().getAppId();
        massSMSService.saveShopSendTask(params, dykId, appId);
        return new JsonResult();
    }

    /**
     * 删除短信模板
     *
     * @return
     */
    @RequestMapping(value = "deleteTemplate", method = RequestMethod.POST)
    public JsonResult deleteTemplate() {
        JSONObject params = getParams();
        massSMSService.deleteTemplate(params.getInt("id"));
        return new JsonResult();
    }

    /**
     * 删除短信记录
     *
     * @return
     */
    @RequestMapping(value = "deleteRecord", method = RequestMethod.POST)
    public JsonResult deleteRecord() {
        JSONObject params = getParams();
        massSMSService.deleteRecord(params.getInt("id"));
        return new JsonResult();
    }

    /**
     * 获取短信记录
     *
     * @return
     */
    @RequestMapping(value = "getmasslist", method = RequestMethod.POST)
    public JsonResult getMassList() {
        MassQueryVo params = getParams(MassQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<MassRecordRetVo> info = massSMSService.getMassList(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 取消短信发送
     *
     * @return
     */
    @RequestMapping(value = "cancelsend", method = RequestMethod.POST)
    public JsonResult cancelsend() {
        JSONObject params = getParams();
        massSMSService.doCancelSend(params);
        return new JsonResult();
    }

    /**
     * 短信回调
     *
     * @param MultiRequest
     */
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public void callback(MultipartHttpServletRequest MultiRequest) {
        DefaultMultipartHttpServletRequest defaultRequest = (DefaultMultipartHttpServletRequest) MultiRequest;
        Map<String, String[]> params = defaultRequest.getParameterMap();
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            object.put(key, value);
        }
        Message message = null;
        try {
            String json = object.toString();
            logger.info(json);
            message = JsonUtils.JsonToBean(json, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        massSMSService.callback(message);
    }
}
