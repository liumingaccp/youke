package youke.web.pc.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.model.vo.result.TagListVo;
import youke.common.model.vo.result.TagRuleStateVo;
import youke.common.model.vo.result.TagRuleVo;
import youke.common.model.vo.result.TagVo;
import youke.facade.fans.provider.IFansTagService;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 粉丝标签相关
 */
@RestController
@RequestMapping("fanstag")
public class FansTagAction extends BaseAction {

    @Resource
    private IFansTagService fansTagService;

    /**
     * 新增标签
     *
     * @return
     */
    @RequestMapping(value = "addtag", method = RequestMethod.POST)
    public JsonResult addFansTag() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        int groupId = params.getInt("groupId");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        fansTagService.addTag(groupId, title, appId);
        return result;
    }

    /**
     * 更新标签
     *
     * @return
     */
    @RequestMapping(value = "savetag", method = RequestMethod.POST)
    public JsonResult saveTag() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        JsonResult result = new JsonResult();
        fansTagService.saveTag(id, title, appId);
        return result;
    }

    /**
     * 删除标签
     *
     * @return
     */
    @RequestMapping(value = "deltag", method = RequestMethod.POST)
    public JsonResult deleteTag() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        int id = params.getInt("id");
        fansTagService.delTag(id, youkeId, appId);
        return result;
    }

    /**
     * 新增标签组
     *
     * @return
     */
    @RequestMapping(value = "addgroup", method = RequestMethod.POST)
    public JsonResult addGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        fansTagService.addGroup(title, appId);
        return result;
    }

    /**
     * 更新标签组
     *
     * @return
     */
    @RequestMapping(value = "savegroup", method = RequestMethod.POST)
    public JsonResult saveGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        int id = params.getInt("id");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        fansTagService.saveGroup(id, title, appId);
        return result;
    }

    /**
     * 删除标签组
     *
     * @return
     */
    @RequestMapping(value = "delgroup", method = RequestMethod.POST)
    public JsonResult delGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        int id = params.getInt("id");
        String youkeId = user.getDykId();
        fansTagService.deleteGroup(id, appId, youkeId);
        return result;
    }

    /**
     * 打开或关闭规则
     *
     * @return
     */
    @RequestMapping(value = "openrule", method = RequestMethod.POST)
    public JsonResult delRule() {
        JSONObject params = getParams();
        int type = params.getInt("type");
        boolean open = params.getBoolean("open");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"尚未绑定公众号,该功能未开启");
        }
        fansTagService.updateStateWithTagRule(open, type, appId);
        return new JsonResult();
    }

    /**
     * 保存标签规则
     *
     * @return
     */
    @RequestMapping(value = "saverule", method = RequestMethod.POST)
    public JsonResult saveRule() {
        JSONArray paramArrs = getParamArrs();
        int size = paramArrs.size();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"尚未绑定公众号,该功能未开启");
        }
        JsonResult result = new JsonResult();
        if (size > 0) {
            fansTagService.saveTagRule(paramArrs, appId);
        } else {
            throw new BusinessException("当前保存的标签规则为空");
        }
        return result;
    }

    /**
     * 获取标签规则
     *
     * @return
     */
    @RequestMapping(value = "getrules", method = RequestMethod.POST)
    public JsonResult getRules() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        int type = params.getInt("type");
        List<TagRuleVo> rules = fansTagService.getRules(type, appId);
        Map<String, Object> map = new HashMap<String, Object>();
        TagRuleStateVo ruleState;
        if(empty(appId)){
            ruleState = new TagRuleStateVo();
            ruleState.setOpenTagRule0(0);
            ruleState.setOpenTagRule1(0);
            ruleState.setOpenTagRule2(0);
        }else {
            ruleState = fansTagService.getRuleState(appId);
        }
        map.put("openTagRule0",ruleState.getOpenTagRule0());
        map.put("openTagRule1",ruleState.getOpenTagRule1());
        map.put("openTagRule2",ruleState.getOpenTagRule2());
        map.put("items",rules);
        result.setData(map);
        return result;
    }

    /**
     * 获取所有对应appid下的标签
     *
     * @return
     */
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public JsonResult getTagList() {
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        List<TagListVo> list = fansTagService.getList(appId);
        result.setData(list);
        return result;
    }

    /**
     * 获取 appid 的所有微信标签
     *
     * @return
     */
    @RequestMapping(value = "getwxlist", method = RequestMethod.POST)
    public JsonResult getWxTagList() {
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId))
            appId = user.getDykId();
        List<TagVo> list = fansTagService.getWxList(appId);
        result.setData(list);
        return result;
    }
}
