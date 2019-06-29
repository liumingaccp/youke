package youke.web.service.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.facade.wxper.provider.IWxPerCoreService;
import youke.facade.wxper.vo.WxPerFansSaveVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@RestController
@RequestMapping(value = "wxper")
public class WxPerAction extends BaseAction {

    @Resource
    private IWxPerCoreService wxPerCoreService;

    /**
     * 设备激活回执
     */
    @RequestMapping(value = "activation", method = RequestMethod.POST)
    public JsonResult activation() {
        JSONObject params = getParams();
        wxPerCoreService.doActivation(params.getString("imel"), params.getString("cid"), params.getString("phoneModel"));
        return new JsonResult();
    }

    /**
     * 设备连接回执
     */
    @RequestMapping(value = "connection", method = RequestMethod.POST)
    public JsonResult connection() {
        JSONObject params = getParams();
        wxPerCoreService.doConnection(params.getString("cid"), params.getString("wechatnum"));
        return new JsonResult();
    }

    /**
     * 设备断开回执
     */
    @RequestMapping(value = "disconnect", method = RequestMethod.POST)
    public JsonResult disconnect() {
        JSONObject params = getParams();
        wxPerCoreService.doDisconnect(params.getString("cid"));
        return new JsonResult();
    }

    /**
     * 获取微信基本信息回执
     */
   /* @RequestMapping(value = "wxGetAll", method = RequestMethod.POST)
    public JsonResult wxGetAll() {
        WxInfoVo params = getParams(WxInfoVo.class);
        WxPerFansSaveVo vo = new WxPerFansSaveVo();
        vo.setCid(params.getCid());
        vo.setNickname(params.getNickname());
        vo.setWechat(params.getWechat());
        vo.setJson(com.alibaba.fastjson.JSONArray.toJSONString(params.getAllcontact()));
        wxPerCoreService.doWxGetAll(vo);
        return new JsonResult();
    }*/

    /**
     * 朋友圈评论回执
     */
    @RequestMapping(value = "addComment", method = RequestMethod.POST)
    public JsonResult addComment() {
        JSONObject params = getParams();
        wxPerCoreService.addComment(params.getString("cid"), params.getLong("taskId"), params.getString("comment"), params.getString("nickName"));
        return new JsonResult();
    }

    /**
     * 朋友圈点赞回执
     *
     * @return
     */
    @RequestMapping(value = "addLike", method = RequestMethod.POST)
    public JsonResult addLike() {
        JSONObject params = getParams();
        wxPerCoreService.addLike(params.getString("cid"), params.getLong("taskId"), params.getString("nickName"));
        return new JsonResult();
    }

    /**
     * 自动点赞任务回执
     *
     * @return
     */
    @RequestMapping(value = "updateLikeTask", method = RequestMethod.POST)
    public JsonResult updateLikeTask() {
        JSONObject params = getParams();
        wxPerCoreService.updateLikeTask(params.getString("cid"), params.getLong("taskId"), params.getInt("likeNum"));
        return new JsonResult();
    }

    /**
     * 朋友圈推送回执
     *
     * @return
     */
    @RequestMapping(value = "updateCircleTask", method = RequestMethod.POST)
    public JsonResult updateCircleTask() {
        JSONObject params = getParams();
        wxPerCoreService.updateCircleTask(params.getString("cid"), params.getLong("taskId"), params.getInt("state"));
        return new JsonResult();
    }

    /**
     * 好友群发任务回执
     */
    @RequestMapping(value = "updateMultipleTask", method = RequestMethod.POST)
    public JsonResult updateMutipleTask() {
        JSONObject params = getParams();
        wxPerCoreService.updateMutipleTask(params.getString("cid"), params.getLong("taskId"), params.getInt("state"));
        return new JsonResult();
    }

    /**
     * 自动加好友任务回执
     */
    @RequestMapping(value = "updateAutoAddTask", method = RequestMethod.POST)
    public JsonResult updateAutoAddTask() {
        JSONObject params = getParams();
        String mobile = null;
        String name = null;
        try {
            mobile = params.getString("mobile");
            name = params.getString("name");
        } catch (Exception e) {
            //
        }
        wxPerCoreService.updateAutoAddTask(params.getString("cid"), params.getLong("taskId"), params.getInt("state"), mobile, name);
        return new JsonResult();
    }

    /**
     * 自动加好友任务回执
     */
    @RequestMapping(value = "updateAutoPassTask", method = RequestMethod.POST)
    public JsonResult updateAutoPassTask() {
        JSONObject params = getParams();
        JSONArray nickNames = params.getJSONArray("nickName");
        String names = "";
        if (nickNames != null) {
            for (int i = 0; i < nickNames.size(); i++) {
                names = names + nickNames.getString(i) + ",";
            }
        }
        wxPerCoreService.updateAutoPassTask(params.getString("cid"), params.getLong("taskId"), params.getInt("passNum"), names);
        return new JsonResult();
    }

    /**
     * 自动养号回执
     *
     * @return
     */
    @RequestMapping(value = "updateGrowTask", method = RequestMethod.POST)
    public JsonResult updateGrowTask() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        wxPerCoreService.updateGrowTask(params.getString("cid"), params.getLong("taskId"));
        return result;
    }


    /**
     * 获取微信基本信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("wxGetAll")
    public JsonResult uploadFile(HttpServletRequest request) throws Exception {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (empty(cmr)) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "参数错误");
        }
        String cid = request.getParameter("cid");
        String wechat = request.getParameter("wechat");
        String nickname = request.getParameter("nickname");
        String str = "";
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null && mFile.getSize() > 0) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(mFile.getInputStream(), StandardCharsets.UTF_8)); // 实例化输入流，并获取网页代码
                    String s; // 依次循环，至到读的值为空
                    StringBuilder sb = new StringBuilder();
                    while ((s = reader.readLine()) != null) {
                        sb.append(s);
                    }
                    reader.close();
                    str = sb.toString();
                }
            }
            logger.error(str);
            if (notEmpty(str)) {
                com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(str);
                WxPerFansSaveVo vo = new WxPerFansSaveVo();
                vo.setJson(array);
                vo.setCid(cid);
                vo.setNickname(nickname);
                vo.setWechat(wechat);
                wxPerCoreService.doWxGetAll(vo);
            }
        }
        return new JsonResult();
    }
}
