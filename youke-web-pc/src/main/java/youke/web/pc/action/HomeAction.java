package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.FansChartParamVo;
import youke.common.model.vo.result.AccountDataVo;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.facade.market.vo.MarketConstant;
import youke.facade.user.provider.IHomeService;
import youke.facade.user.vo.FansChartDateVo;
import youke.facade.user.vo.FansDataVo;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created with IntelliJ IDEA
 * Created By liuming
 * Date: 2018/2/28
 * Time: 11:29
 */
@Controller
@RequestMapping("home")
public class HomeAction extends BaseAction {

    @Resource
    private IHomeService homeService;

    /**
     * 获取社群数据监控
     *
     * @return
     */
    @RequestMapping("getsqdata")
    @ResponseBody
    @SuppressWarnings("all")
    public JsonResult getSqData() {
        UserVo user = AuthUser.getUser();
        Map<String, Object> map = new HashMap<>();
        if (RedisUtil.hasKey(MarketConstant.KEY_HOME_DATA)) {
            Map<String, Object> objectMap = (Map<String, Object>) RedisUtil.hget(MarketConstant.KEY_HOME_DATA, user.getDykId());
            if (objectMap != null) {
                map.put("ZRNewFansNum", objectMap.get("ZRNewFansNum"));
                map.put("ZRActiveFansNum", objectMap.get("ZRActiveFansNum"));
                map.put("WeekREFansNum", objectMap.get("WeekREFansNum"));
                map.put("WeekSTOFansNum", objectMap.get("WeekSTOFansNum"));
                map.put("WeekSaleNum", objectMap.get("WeekSaleNum"));
                map.put("WeekREPercent", objectMap.get("WeekREPercent"));
            }
        } else {
            FansDataVo data = new FansDataVo();
            data.setVal("0");
            data.setType(2);
            data.setPercent("0.00%");
            map.put("ZRNewFansNum", data);
            map.put("ZRActiveFansNum", data);
            map.put("WeekREFansNum", data);
            map.put("WeekSTOFansNum", data);
            map.put("WeekSaleNum", data);
            map.put("WeekREPercent", data);
        }
        return new JsonResult(map);
    }

    /**
     * 获取粉丝统计报表
     *
     * @return
     */
    @RequestMapping("getfanschart")
    @ResponseBody
    @SuppressWarnings("all")
    public JsonResult getFansChart() {
        List<FansChartDateVo> charts;
        Map<String, Object> map = new HashMap<>();
        FansChartParamVo params = getParams(FansChartParamVo.class);
        UserVo user = AuthUser.getUser();
        if (RedisUtil.hasKey(MarketConstant.KEY_HOME_CHART + params.getType())) {
            charts = (List<FansChartDateVo>) RedisUtil.hget(MarketConstant.KEY_HOME_CHART + params.getType(), user.getDykId());
            if (charts != null && charts.size() > 0) {
                List<FansChartDateVo> list = charts.stream().filter(chart -> DateUtil.isBetweenTwoDates(chart.getName(), params.getBegDate(), params.getEndDate())).collect(toList());
                return new JsonResult(list);
            } else {
                charts = new ArrayList<>();
                FansChartDateVo data = new FansChartDateVo();
                String date = DateUtil.formatDate(DateUtil.getDateBefore(new Date(), 1));
                data.setVal(0);
                data.setName(date);
                charts.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + params.getType(), user.getDykId(), charts);
            }
        } else {
            FansChartDateVo data = new FansChartDateVo();
            charts = new ArrayList<>();
            String date = DateUtil.formatDate(DateUtil.getDateBefore(new Date(), 1));
            data.setVal(0);
            data.setName(date);
            charts.add(data);
            map.put(user.getDykId(), charts);
            RedisUtil.hmset(MarketConstant.KEY_HOME_CHART + params.getType(), map);
        }
        return new JsonResult(charts);
    }


    /**
     * 获取待办事项数据
     *
     * @return
     */
    @RequestMapping("getwaitdata")
    @ResponseBody
    public JsonResult getWaitData() {
        return new JsonResult(homeService.getWaitData(AuthUser.getUser().getDykId()));
    }

    /**
     * 获取我的账户统计
     *
     * @return
     */
    @RequestMapping("getaccountdata")
    @ResponseBody
    public JsonResult getAccountData() {
        UserVo user = AuthUser.getUser();
        AccountDataVo accountData = new AccountDataVo();
        if(user.getVip()>0) {
            accountData = homeService.getAccontData(AuthUser.getUser().getDykId());
            JSONObject obj = JSONObject.fromObject(accountData);
            obj.put("openTime",accountData.getOpenTime().getTime());
            obj.put("endTime",accountData.getEndTime().getTime());
            obj.put("appId",empty(user.getAppId())?"":user.getAppId());
            return new JsonResult(obj);
        }
        return new JsonResult(accountData);
    }
}
