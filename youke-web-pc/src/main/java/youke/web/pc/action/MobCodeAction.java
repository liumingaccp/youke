package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.TMobcodeOrder;
import youke.common.model.TMobcodePackage;
import youke.common.model.vo.param.QueryObjectVO;
import youke.facade.mass.provider.IMobCodeService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信中心
 */
@RestController
@RequestMapping("mobcode")
public class MobCodeAction extends BaseAction {

    @Resource
    private IMobCodeService mobCodeService;

    /**
     * 获取剩余短信数
     *
     * @return
     */
    @RequestMapping(value = "getcodecount", method = RequestMethod.POST)
    public JsonResult getcodecount() {
        JsonResult result = new JsonResult();
        String dykId = AuthUser.getUser().getDykId();
        Integer count = mobCodeService.getCodeCount(dykId);
        result.setData(count);
        return result;
    }

    /**
     * 充值记录分页获取
     *
     * @return
     */
    @RequestMapping(value = "getorderlist", method = RequestMethod.POST)
    public JsonResult getorderlist() {
        JsonResult result = new JsonResult();
        QueryObjectVO params = getParams(QueryObjectVO.class);
        String dykId = AuthUser.getUser().getDykId();
        PageInfo<TMobcodeOrder> info = mobCodeService.getOrderList(params, dykId);
        result.setData(info);
        return result;
    }

    /**
     * 获取短信充值套餐
     *
     * @return
     */
    @RequestMapping(value = "getpackages", method = RequestMethod.POST)
    public JsonResult getpackages() {
        JsonResult result = new JsonResult();
        List<TMobcodePackage> list = mobCodeService.getPackages();
        result.setData(list);
        return result;
    }
}
