package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.facade.wxper.provider.IWxPerMassService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/7/10
 * Time: 12:03
 */
@Controller
@RequestMapping("multiplesend")
public class WxperMassAction extends BaseAction{

    @Resource
    private IWxPerMassService wxperMassService;

    @RequestMapping("createmultiplesendtask")
    @ResponseBody
    public JsonResult addActive() {
        JSONObject params = getParams();
        HashMap condition = new HashMap();
        condition.put("deviceIds",params.getString("deviceIds"));
        condition.put("type", params.getString("type"));
        condition.put("tagIds", params.getString("tagIds"));
        condition.put("pushTime", params.getString("pushTime"));
        condition.put("delay", params.getInt("delay"));
        condition.put("content", params.getString("content"));
        condition.put("mediaUrl", params.getString("mediaUrl"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperMassService.saveTask(condition);
        return new JsonResult();
    }

    @RequestMapping("getsendtasksendnum")
    @ResponseBody
    public JsonResult getNum() {
        JSONObject params = getParams();
        HashMap condition = new HashMap();
        condition.put("deviceIds",params.getString("deviceIds"));
        condition.put("tagIds", params.getString("tagIds"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Long count = wxperMassService.getTaskFansNum(condition);
        return new JsonResult(count);
    }

    @RequestMapping("getrecordlist")
    @ResponseBody
    public JsonResult getRecordList() {
        JSONObject params = getParams();
        HashMap condition = new HashMap();
        condition.put("nickName",params.getString("nickName"));
        condition.put("begCreateTime", params.getString("begCreateTime"));
        condition.put("endCreateTime", params.getString("endCreateTime"));
        condition.put("begSendTime", params.getString("begSendTime"));
        condition.put("endSendTime", params.getString("endSendTime"));
        condition.put("content", params.getString("content"));
        condition.put("sendNumBeg", params.getString("sendNumBeg"));
        condition.put("sendNumEnd", params.getString("sendNumEnd"));
        condition.put("state", params.getInt("state"));
        condition.put("page", params.getInt("page"));
        condition.put("limit", params.getInt("limit"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        PageInfo<Map<String, Object>> infos = wxperMassService.getRecordList(condition);
        return new JsonResult(infos);
    }

    @RequestMapping("parsetask")
    @ResponseBody
    public JsonResult parseTaskState() {
        JSONObject params = getParams();
        HashMap condition = new HashMap();
        condition.put("id",params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperMassService.saveParseTask(condition);
        return new JsonResult();
    }

}
