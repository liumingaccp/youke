package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.helper.*;
import youke.common.model.vo.result.helper.*;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ICollageService;
import youke.facade.helper.provider.IRebateService;

import javax.annotation.Resource;

@RestController
@RequestMapping("collage")
public class CollageAction extends BaseAction {

    @Resource
    private ICollageService collageService;

    @RequestMapping(value = "getactivelist")
    public JsonResult getList() {
        CollageQueryVo params = getParams(CollageQueryVo.class);
        PageInfo<CollageQueryRetVoH5> info = collageService.queryListForH5(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getactivedetails")
    public JsonResult getAtiveDetail() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        int activeId = params.getInt("activeId");
        CollageActiveDetailAndTuanVo info = collageService.getActiveDetailWithTuanDetail(appId, openId, activeId);
        return new JsonResult(info);
    }


    @RequestMapping(value = "submitTuan")
    public JsonResult submitTuan() {
        TuanVo params = getParams(TuanVo.class);
        long id = collageService.submitTuan(params);
        return new JsonResult(id);
    }

    @RequestMapping(value = "gettuanlist")
    public JsonResult saveOrderNum() {
        CollageQueryVo params = getParams(CollageQueryVo.class);
        PageInfo<CollageQueryRetVoByOpenId> list = collageService.queryListForOpenId(params);
        return new JsonResult(list);
    }

    @RequestMapping(value = "gettuandetails")
    public JsonResult getTuanDetail() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        int tuanId = params.getInt("tuanId");
        TuanDetailVo info = collageService.getTuanDetailByOpenId(appId, openId, tuanId);
        return new JsonResult(info);
    }
}
