package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.IntegralDetailQueryVo;
import youke.common.model.vo.result.IntegralSetVo;
import youke.common.model.vo.result.SubFansIntegralDetailVo;
import youke.common.utils.BeanUtil;
import youke.common.utils.StringUtil;
import youke.facade.market.provider.IIntegralWealService;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.user.provider.IUserService;
import youke.facade.user.vo.SubFansInfo;
import youke.facade.user.vo.UserVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("member")
public class MemberAction extends BaseAction {

    @Resource
    private IUserService userService;
    @Resource
    private IIntegralWealService integralWealService;
    @Resource
    private IMobmsgService mobmsgService;

    @RequestMapping("getintegral")
    public JsonResult getIntegral() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        Integer data = userService.getIntegral(openId);
        return new JsonResult(data);
    }

    @RequestMapping("getinfo")
    public JsonResult getInfo() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        SubFansInfo info = userService.getMemInfo(appId, openId);
        Map result = new HashMap();
        try {
            BeanUtil.bean2Map(info,result);
            String dykId = userService.getYoukeIdByAppId(appId);
            IntegralSetVo setVo = integralWealService.getIntegralSet(dykId);
            result.put("integralExpTime",setVo.getIntegralExp());
            result.put("openBackIntegral",setVo.getOpenBackIntegral());
            result.put("backIntegralScale",(double)setVo.getNumForIntegral()/setVo.getNumForMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(result);
    }

    @RequestMapping("savemobile")
    public JsonResult saveMobile() {
        JSONObject params = getParams();
        String openId = params.getString("openId");
        String appId = params.getString("appId");
        String mobile = StringUtil.hasLength(params.getString("mobile")) ? params.getString("mobile") : null;
        String code = StringUtil.hasLength(params.getString("code")) ? params.getString("code") : null;
        SubFansInfo info = userService.getMemInfo(appId,openId);
        if (info == null || info.getHasSubscr() == 0) {
            throw new BusinessException("还没有关注公众号");
        }
        if (mobile == null) {
            throw new BusinessException("输入的手机号码为空");
        }
        if (code == null) {
            throw new BusinessException("请输入验证码");
        }
        if (!mobmsgService.checkMobCode(mobile, code)) {
            throw new BusinessException("输入验证码错误");
        }
        userService.saveMobile(openId, mobile, appId);
        return new JsonResult();
    }

    @RequestMapping("getintegraldetail")
    public JsonResult getIntegralDetail() {
        IntegralDetailQueryVo params = getParams(IntegralDetailQueryVo.class);
        PageInfo<SubFansIntegralDetailVo> info = userService.getIntegralDetail(params);
        return new JsonResult(info);
    }

}
