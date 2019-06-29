package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import youke.common.bean.JsonResult;
import youke.facade.wxper.provider.IWxPerFriendService;
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
@RequestMapping("autoaddfans")
public class WxperFriendAction extends BaseAction{

    @Resource
    private IWxPerFriendService wxperFriendService;

    /**
     * 编辑/批量 被添加好友任务
     * @return
     */
    @RequestMapping("auditaddedtask")
    @ResponseBody
    public JsonResult updateAddedConfig() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("ids",params.getString("ids"));
        condition.put("autoPass",params.getInt("autoPass"));
        condition.put("dailyLimit",params.getInt("dailyLimit"));
        condition.put("autoReply",params.getInt("autoReply"));
        condition.put("content",params.getString("content"));
        condition.put("contentType", params.getString("contentType"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        String msg = wxperFriendService.updateAddedConfig(condition);
        return new JsonResult(msg);
    }

    /**
     * 开启/关闭 用户去重设置
     * @return
     */
    @RequestMapping("updatedistinctconfig")
    @ResponseBody
    public JsonResult updateConfig() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("open", params.getInt("open"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperFriendService.updatedConfig(condition);
        return new JsonResult();
    }

    /**
     * 设置每日添加好友上限
     * @return
     */
    @RequestMapping("updatedailylimitconfig")
    @ResponseBody
    public JsonResult updateConfig2() {
        JSONObject params = getParams();
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("num", params.getInt("num"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperFriendService.updatedConfig(condition);
        return new JsonResult();
    }

    /**
     * 获取每日添加好友上限
     * @return
     */
    @RequestMapping("getdailylimit")
    @ResponseBody
    public JsonResult getDailyLimit() {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Integer dailyLimit = wxperFriendService.getDailyLimit(condition);
        return new JsonResult(dailyLimit);
    }

    /**
     * 获取被添加设置开关
     * @return
     */
    @RequestMapping("getdistinctconfig")
    @ResponseBody
    public JsonResult getDistinctConfig() {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Integer distinct = wxperFriendService.getDistinctConfig(condition);
        return new JsonResult(distinct);
    }

    /**
     * 创建自动添加好友任务
     * @return
     */
    @RequestMapping("createaddfanstask")
    @ResponseBody
    public JsonResult createAddFansTask() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();

        condition.put("id", params.getLong("id"));
        condition.put("mobiles", params.getString("mobiles"));
        condition.put("url", params.getString("url"));
        condition.put("verifyinfo", params.getString("verifyInfo"));
        condition.put("deviceids", params.getString("deviceIds"));

        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeid", AuthUser.getUser().getDykId());
        wxperFriendService.createAddFansTask(condition);
        return new JsonResult();
    }

    /**
     * 获取被添加好友任务编辑数据
     * @return
     */
    @RequestMapping("getofaddedtaskauditdata")
    @ResponseBody
    public JsonResult getOfAddTaskConfig() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("id", params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Map<String, Object> info  = wxperFriendService.getAddedTaskData(condition);
        return new JsonResult(info);
    }

    /**
     * 删除添加好友任务
     * @return
     */
    @RequestMapping("deladdfanstask")
    @ResponseBody
    public JsonResult delAddFansTask() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("id", params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperFriendService.delAddFansTask(condition);
        return new JsonResult();
    }

    /**
     * 任务优先执行
     * @return
     */
    @RequestMapping("prioritizetasks")
    @ResponseBody
    public JsonResult prioritizeTask() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("id", params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        wxperFriendService.updatePrioritizeTask(condition);
        return new JsonResult();
    }

    /**
     * 获取添加好友任务编辑数据
     * @return
     */
    @RequestMapping("getaddtaskauditdata")
    @ResponseBody
    public JsonResult getaddtaskauditdata() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("id", params.getLong("id"));
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        Map<String, Object> data = wxperFriendService.getAddTaskData(condition);
        return new JsonResult(data);
    }


    /**
     * 获取被添加好友任务列表
     * @return
     */
    @RequestMapping("getaddedtasklist")
    @ResponseBody
    public JsonResult getAddedTaskList() {
        HashMap<String, Object> condition = new HashMap<>();
        JSONObject params = getParams();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        condition.put("page", params.getInt("page"));
        condition.put("limit", params.getInt("limit"));
        PageInfo<HashMap<String, Object>> list = wxperFriendService.getAddedTaskList(condition);
        return new JsonResult(list);
    }

    /**
     * 获取添加好友任务列表
     * @return
     */
    @RequestMapping("getaddtasklist")
    @ResponseBody
    public JsonResult getAddTaskList() {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        condition.put("page", getParams().getInt("page"));
        condition.put("limit", getParams().getInt("limit"));
        PageInfo<HashMap<String, Object>> list = wxperFriendService.getAddTaskList(condition);
        return new JsonResult(list);
    }

    /**
     * 获取被添加好友任务列表
     * @return
     */
    @RequestMapping("parsetask")
    @ResponseBody
    public JsonResult updateTask() {
        HashMap<String, Object> condition = new HashMap<>();
        condition.put("appId", AuthUser.getUser().getAppId());
        condition.put("youkeId", AuthUser.getUser().getDykId());
        condition.put("taskId", getParams().getLong("taskId"));
        condition.put("state", getParams().getInt("state"));
        wxperFriendService.updateTaskState(condition);
        return new JsonResult();
    }
}
