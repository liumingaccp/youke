package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.param.cloudcode.CloudCodeQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeRecordQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeSaveVo;
import youke.common.model.vo.result.ActiveLinkVo;
import youke.common.model.vo.result.cloudcode.CloudCodeAuditRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeQueryRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRecordQueryRetVo;
import youke.common.utils.EncodeUtil;
import youke.facade.cloudcode.provider.ICloudCodeService;
import youke.facade.cloudcode.vo.CloudCodeConstant;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("cloudcode")
public class CloudCodeAction extends BaseAction {

    @Resource
    private ICloudCodeService cloudCodeService;

    /**
     * 保存云码
     *
     * @return
     */
    @RequestMapping(value = "savecloudcode", method = RequestMethod.POST)
    public JsonResult saveCloud(HttpServletRequest request) {
        CloudCodeSaveVo params = getParams(CloudCodeSaveVo.class);
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if (empty(appId)){
            appId = ApiCodeConstant.APPID;
        }
        Long id = cloudCodeService.saveCloudCode(params, user.getDykId(), appId);
        ActiveLinkVo link = getActiveUrl(appId, id, request);
        return new JsonResult(link.getQrdUrl());
    }

    /**
     * 获取云码列表
     *
     * @return
     */
    @RequestMapping(value = "getcloudcodelist", method = RequestMethod.POST)
    public JsonResult getCloudCodeList(HttpServletRequest request) {
        CloudCodeQueryVo params = getParams(CloudCodeQueryVo.class);
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if (empty(appId)){
            appId = ApiCodeConstant.APPID;
        }
        PageInfo<CloudCodeQueryRetVo> info = cloudCodeService.getCloudCodeList(params, user.getDykId());
        for (CloudCodeQueryRetVo vo : info.getList()) {
            ActiveLinkVo link = getActiveUrl(appId, vo.getId(), request);
            vo.setPreUrl(link.getUrl());
            vo.setPreCodeUrl(link.getQrdUrl());
        }
        return new JsonResult(info);
    }


    /**
     * 二维码编辑|查看 页面数据获取
     *
     * @return
     */
    @RequestMapping(value = "auditcloudcode", method = RequestMethod.POST)
    public JsonResult auditCloudCode() {
        JSONObject params = getParams();
        CloudCodeAuditRetVo vo = cloudCodeService.auditCloudCode(params.getLong("id"));
        return new JsonResult(vo);
    }

    /**
     * 云码作废
     *
     * @return
     */
    @RequestMapping(value = "dropcloudcode", method = RequestMethod.POST)
    public JsonResult dropCloudCode() {
        JSONObject params = getParams();
        cloudCodeService.dropCloudCode(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 云码删除
     *
     * @return
     */
    @RequestMapping(value = "deletecloudcode", method = RequestMethod.POST)
    public JsonResult deleteCloudCode() {
        JSONObject params = getParams();
        cloudCodeService.deleteCloudCode(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 获取扫码记录
     *
     * @return
     */
    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getRecordList() {
        CloudCodeRecordQueryVo params = getParams(CloudCodeRecordQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<CloudCodeRecordQueryRetVo> info = cloudCodeService.getRecordList(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 获取活动地址
     *
     * @param appId
     * @param activeId
     * @param request
     * @return
     */
    private ActiveLinkVo getActiveUrl(String appId, Long activeId, HttpServletRequest request) {
        ActiveLinkVo link = new ActiveLinkVo();
        link.setUrl(getConfig(CloudCodeConstant.CC).replace("{appId}", appId + "&id=" + activeId));
        link.setQrdUrl(getBasePath(request) + "common/qrcode?d=" + EncodeUtil.urlEncode(link.getUrl()));
        return link;
    }

    /**
     * 获取对应配置表数据
     *
     * @param key
     * @return
     */
    private String getConfig(String key) {
        return cloudCodeService.getConfig(key);
    }
}
