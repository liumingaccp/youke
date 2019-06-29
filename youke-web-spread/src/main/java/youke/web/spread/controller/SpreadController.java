package youke.web.spread.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.facade.user.vo.UserVo;
import youke.web.spread.bean.JsonResult;
import youke.web.spread.bean.PageBean;
import youke.web.spread.common.conts.ApiCodeConstant;
import youke.web.spread.common.utils.DateUtil;
import youke.web.spread.controller.interceptor.AuthUser;
import youke.web.spread.entity.TUserSpread;
import youke.web.spread.service.biz.CommonBiz;
import youke.web.spread.service.biz.UserSpreadBiz;

import java.util.Map;

@RestController
@RequestMapping("spread")
public class SpreadController extends BaseController {

    @Autowired
    private UserSpreadBiz userSpreadBiz;
    @Autowired
    private CommonBiz commonBiz;


    @RequestMapping(value = "getinfo",method = RequestMethod.POST)
    public JsonResult getinfo(){
        UserVo userVo = AuthUser.getUser();
        String id = userSpreadBiz.doInit(userVo.getUserId());
        Map<String,Object> map = userSpreadBiz.getInfo(id);
        return new JsonResult(map);
    }

    @RequestMapping(value = "getsubdata",method = RequestMethod.POST)
    public JsonResult getsubdata(){
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        String flag = params.getString("flag");
        String timeBeg = params.getString("doTimeBeg");
        String timeEnd = params.getString("doTimeEnd");
        if(notEmpty(timeBeg)){
            if(DateUtil.parseDate(timeBeg)==null||DateUtil.parseDate(timeEnd)==null){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"时间格式错误");
            }
        }
        UserVo userVo = AuthUser.getUser();
        String id = userSpreadBiz.doInit(userVo.getUserId());
        PageBean<Map> pageBean = userSpreadBiz.getSubData(page,limit,id,flag,timeBeg,timeEnd);
        return new JsonResult(pageBean);
    }

    @RequestMapping(value = "getsublist",method = RequestMethod.POST)
    public JsonResult getsublist(){
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        String timeBeg = params.getString("doTimeBeg");
        String timeEnd = params.getString("doTimeEnd");
        if(notEmpty(timeBeg)){
            if(DateUtil.parseDate(timeBeg)==null||DateUtil.parseDate(timeEnd)==null){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"时间格式错误");
            }
        }
        UserVo userVo = AuthUser.getUser();
        String id = userSpreadBiz.doInit(userVo.getUserId());
        PageBean<Map> pageBean = userSpreadBiz.getSubList(page,limit,id,timeBeg,timeEnd);
        return new JsonResult(pageBean);
    }

    @RequestMapping(value = "bindalipay",method = RequestMethod.POST)
    public JsonResult bindalipay(){
        JSONObject params = getParams();
        String id = params.getString("id");
        String alipayAccount = params.getString("alipayAccount");
        String alipayName = params.getString("alipayName");
        userSpreadBiz.doBindAlipay(id,alipayAccount,alipayName);
        return new JsonResult();
    }

    @RequestMapping(value = "takemoney",method = RequestMethod.POST)
    public JsonResult takemoney(){
        JSONObject params = getParams();
        String id = params.getString("id");
        int money = params.getInt("money");
        String code = params.getString("code");
        TUserSpread userSpread = userSpreadBiz.getUserSpread(id);
        if(!commonBiz.checkCode(userSpread.getMobile(),code))
        {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"验证码无效");
        }
        if(money>userSpread.getBalance()){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"提现金额超出余额");
        }
        userSpreadBiz.doTakeMoney(id,money);
        return new JsonResult();
    }

    @RequestMapping(value = "takemoneyrecord",method = RequestMethod.POST)
    public JsonResult takemoneyrecord(){
        JSONObject params = getParams();
        int state = params.getInt("state");
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        String timeBeg = params.getString("createTimeBeg");
        String timeEnd = params.getString("createTimeEnd");
        if(notEmpty(timeBeg)){
            if(DateUtil.parseDate(timeBeg)==null||DateUtil.parseDate(timeEnd)==null){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"时间格式错误");
            }
        }
        PageBean<Map> pageBean = userSpreadBiz.getTakeMoneyRecord(page,limit,AuthUser.getUser().getUserId(),state,timeBeg,timeEnd);
        return new JsonResult(pageBean);
    }

}
