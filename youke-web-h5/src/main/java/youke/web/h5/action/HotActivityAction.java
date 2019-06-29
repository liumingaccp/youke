package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ComeType;
import youke.common.model.vo.param.HotActivityParamVo;
import youke.common.model.vo.result.market.*;
import youke.facade.market.provider.IHotActivityService;

import javax.annotation.Resource;

@RestController
@RequestMapping("market")
public class HotActivityAction extends BaseAction {

    @Resource
    private IHotActivityService hotActivityService;

    @RequestMapping(value = "joinsdactive", method = RequestMethod.POST)
    public JsonResult sdActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(0);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joinzpactive", method = RequestMethod.POST)
    public JsonResult zpActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(1);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joinstactive", method = RequestMethod.POST)
    public JsonResult stActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(2);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joinssfactive", method = RequestMethod.POST)
    public JsonResult ssfActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(3);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "jointpactive", method = RequestMethod.POST)
    public JsonResult tpActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(4);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joinsbactive", method = RequestMethod.POST)
    public JsonResult sbActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(5);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joinqdactive", method = RequestMethod.POST)
    public JsonResult qdActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(6);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "joincjactive", method = RequestMethod.POST)
    public JsonResult cjActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        LuckyLotteryRetVo prize = hotActivityService.lottery(param);
        return new JsonResult(prize);
    }

    @RequestMapping(value = "joinhbactive", method = RequestMethod.POST)
    public JsonResult hbActive() {
        HotActivityParamVo param = getParams(HotActivityParamVo.class);
        param.setType(8);
        BaseMarketActiveRetVo vo = hotActivityService.takePartInActivity(param);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "getsdactive", method = RequestMethod.POST)
    public JsonResult getSdActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.SHAI_DAN_YOU_LI);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "gettpactive", method = RequestMethod.POST)
    public JsonResult getTpActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.TOU_PIAO_CE_KUAN);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getzpactive", method = RequestMethod.POST)
    public JsonResult getZpActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.ZHUI_PING_YOU_LI);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getstactive", method = RequestMethod.POST)
    public JsonResult getStActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.SHAI_TU_YOU_LI);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getssfactive", method = RequestMethod.POST)
    public JsonResult getSsfActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.SUI_SHI_FAN);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getqdactive", method = RequestMethod.POST)
    public JsonResult getQdActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.MEI_RI_QIAN_DAO);
        return new JsonResult(rule);
    }


    @RequestMapping(value = "getsbactive", method = RequestMethod.POST)
    public JsonResult getSbActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.SHOU_BANG_YOU_LI);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "gethbactive", method = RequestMethod.POST)
    public JsonResult getHbActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.FEN_XIANG_HAI_BAO);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getcjactive", method = RequestMethod.POST)
    public JsonResult getCjActive() {
        JSONObject params = getParams();
        ActivityRuleH5Vo rule = hotActivityService.getActivityRule(params.getString("appId"), params.getLong("activeId"), params.getString("openId"), ComeType.XING_YUN_CHOU_JIANG);
        return new JsonResult(rule);
    }

    @RequestMapping(value = "getqdrunday", method = RequestMethod.POST)
    public JsonResult getQdRunday() {
        JSONObject params = getParams();
        int runDay = hotActivityService.getRunDay(params.getString("appId"), params.getString("openId"));
        return new JsonResult(runDay);
    }

    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getorderlist() {
        JSONObject params = getParams();
        PageInfo<ActivityRecordVo> info = hotActivityService.getRecordList(params.getString("openId"), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    @RequestMapping(value = "getrecorddetails", method = RequestMethod.POST)
    public JsonResult getRecordDetails() {
        JSONObject params = getParams();
        ActivityRecordDetailsVo details = hotActivityService.getRecordDetails(params.getString("openId"), params.getLong("recordId"));
        return new JsonResult(details);
    }


    @RequestMapping(value = "getrecorddetaillist", method = RequestMethod.POST)
    public JsonResult getrecorddetaillist() {
        JSONObject params = getParams();
        PageInfo<ActivityRecordVo> info = hotActivityService.getRecordDetailList(params.getString("openId"), params.getInt("type"), params.getInt("page"), params.getInt("limit"));
        return new JsonResult(info);
    }

    @RequestMapping(value = "getremainingtimes", method = RequestMethod.POST)
    public JsonResult getRemainingTimes() {
        JSONObject params = getParams();
        return new JsonResult(hotActivityService.getRemainingTimes(params.getString("openId"), params.getLong("activeId")));
    }
}
