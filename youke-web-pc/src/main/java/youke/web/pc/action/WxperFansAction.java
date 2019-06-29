package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.facade.wxper.provider.IWxPerFansService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/10
 * Time: 12:03
 */
@Controller
@RequestMapping("wxperfans")
public class WxperFansAction extends BaseAction{

    @Resource
    private IWxPerFansService wxPerFansService;

    @RequestMapping("getwxperfanslist")
    @ResponseBody
    public JsonResult getWxperFansList() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("shopId",params.getInt("shopId"));
        condition.put("subState", params.getInt("subState"));
        condition.put("tagIds", params.getString("tagIds"));
        condition.put("mobile", params.getString("mobile"));
        condition.put("weChatNum", params.getString("weChatNum"));
        condition.put("nickName", params.getString("nickName"));
        condition.put("accountName", params.getString("accountName"));

        condition.put("page", params.getInt("page"));
        condition.put("limit", params.getInt("limit"));
        condition.put("wxAccountId", params.getLong("wxAccountId"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        PageInfo<HashMap<String, Object>> infos = wxPerFansService.getList(condition);
        return new JsonResult(infos);
    }

    @RequestMapping("getfansdetail")
    @ResponseBody
    public JsonResult getFansDetail() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("id", params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Map<String, Object> info = wxPerFansService.getFansDetail(condition);
        return new JsonResult(info);
    }


    @RequestMapping("addtags")
    @ResponseBody
    public JsonResult addTags() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("fansIds", params.getString("fansIds"));
        condition.put("tags", params.getString("tags"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxPerFansService.addTags(condition);
        return new JsonResult();
    }

    @RequestMapping("deltags")
    @ResponseBody
    public JsonResult adelTags() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("fansIds", params.getString("fansIds"));
        condition.put("tags", params.getString("tags"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxPerFansService.delTags(condition);
        return new JsonResult();
    }

    @RequestMapping("getwxaccount")
    @ResponseBody
    public JsonResult getWxAccount() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        List<HashMap<String, Object>> list = wxPerFansService.getAccountAndDevices(condition);
        return new JsonResult(list);
    }

    @RequestMapping("sycfans")
    @ResponseBody
    public JsonResult sycFans(){
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        condition.put("ids", params.getString("ids"));
        wxPerFansService.doSyc(condition);
        return new JsonResult();
    }
}
