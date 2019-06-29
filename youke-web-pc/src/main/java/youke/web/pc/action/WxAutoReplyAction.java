package youke.web.pc.action;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.facade.wx.provider.IWeixinAutoReplyService;
import youke.facade.wx.vo.reply.AutoReplyVo;
import youke.facade.wx.vo.reply.KeyReplayParam;
import youke.facade.wx.vo.reply.KeyReplyVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/5
 * Time: 19:13
 */
@RestController
@RequestMapping("wxreply")
public class WxAutoReplyAction extends BaseAction {

    @Resource
    private IWeixinAutoReplyService wxAutoReplyService;

    /**
     * 自动回复保存
     *
     * @return
     */
    @RequestMapping(value = "savereply", method = RequestMethod.POST)
    public JsonResult savereply() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        JSON.toJSONString(params);
        int type = params.getInt("type");
        String mediaType = params.getString("mediaType");
        int materialId = params.getInt("materialId");
        String content = params.getString("content");
        wxAutoReplyService.saveReply(type, content, mediaType, materialId, AuthUser.getUser().getAppId());

        return result;
    }

    /**
     * 自动回复开启 or 关闭
     *
     * @return
     */
    @RequestMapping(value = "openreply", method = RequestMethod.POST)
    public JsonResult openreply() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int open = params.getInt("open");
        wxAutoReplyService.do_on_ofReply(open, AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 自动回复删除
     *
     * @return
     */
    @RequestMapping(value = "delreply", method = RequestMethod.POST)
    public JsonResult delreply() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int type = params.getInt("type");
        wxAutoReplyService.delReply(type, AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 自动回复获取
     *
     * @return
     */
    @RequestMapping(value = "getreply", method = RequestMethod.POST)
    public JsonResult getreply() {
        AutoReplyVo reply = null;
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int type = params.getInt("type");
        reply = wxAutoReplyService.getReply(type, AuthUser.getUser().getAppId());
        result.setData(reply);
        return result;
    }


    /**
     * 关键词回复分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getkeyreplylist", method = RequestMethod.POST)
    public JsonResult getkeyreplylist() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String title = params.getString("title");
        String key = params.getString("key");
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<KeyReplyVo> info = wxAutoReplyService.getKeyReplys(title, key, AuthUser.getUser().getAppId(), page, limit);
        result.setData(info);
        return result;
    }

    /**
     * 关键词回复获取单个
     *
     * @return
     */
    @RequestMapping(value = "getkeyreplydetail", method = RequestMethod.POST)
    public JsonResult getKeyReplyDetail() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int ruleId = params.getInt("ruleId");
        KeyReplyVo info = wxAutoReplyService.getKeyReply(ruleId);
        result.setData(info);
        return result;
    }

    /**
     * 关键词回复规则添加
     *
     * @return
     */
    @RequestMapping(value = "addkeyreply", method = RequestMethod.POST)
    public JsonResult addkeyreply() {
        JsonResult result = new JsonResult();
        KeyReplayParam params = getParams(KeyReplayParam.class);
        List<String> mkeys = new ArrayList<String>();
        if (params.getKeys() != null) {
            for (int i = 0; i < params.getKeys().size(); i++) {
                mkeys.add(params.getKeys().get(i));
            }
        }
        wxAutoReplyService.addKeyReply(params.getTitle(), mkeys, params.getMediaType(), params.getContent(), Integer.parseInt(params.getMaterialId()), params.getKeyMatch(), AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 关键词回复规则保存
     *
     * @return
     */
    @RequestMapping(value = "savekeyreply", method = RequestMethod.POST)
    public JsonResult savekeyreply() {
        JsonResult result = new JsonResult();

        KeyReplayParam params = getParams(KeyReplayParam.class);
        List<String> mkeys = new ArrayList<String>();
        if (params.getKeys() != null) {
            for (int i = 0; i < params.getKeys().size(); i++) {
                mkeys.add(params.getKeys().get(i));
            }
        }
        wxAutoReplyService.saveKeyReply(params.getId(), params.getTitle(), mkeys, params.getMediaType(), params.getContent(), Integer.parseInt(params.getMaterialId()), params.getKeyMatch(), AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 关键词回复规则删除
     *
     * @return
     */
    @RequestMapping(value = "delkeyreply", method = RequestMethod.POST)
    public JsonResult delkeyreply() {

        JsonResult result = new JsonResult();
        int id = 0;
        JSONObject params = getParams();
        id = params.getInt("id");
        wxAutoReplyService.delKeyReply(id, AuthUser.getUser().getAppId());
        return result;
    }
}
