package youke.web.pc.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.result.*;
import youke.common.redis.RedisSlaveUtil;
import youke.facade.cloudcode.provider.IChatService;
import youke.facade.fans.provider.IFansTagService;
import youke.facade.fans.provider.IShopFansService;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("pcwx")
public class PcwxAction extends BaseAction {

     @Resource
     private IFansTagService fansTagService;
     @Resource
     private IShopFansService shopFansService;
     @Resource
     private IChatService chatService;

    /**
     * 保存聊天记录
     * @return
     */
     @RequestMapping(value="saveChatMessage", method = RequestMethod.POST)
     public JsonResult saveChatMessage() {
         JSONObject parObj = getParams();
         String type = parObj.getString("type");
         String body = parObj.getString("body");
         String friendId = parObj.getString("friendId");
         String weixinId = parObj.getString("weixinId");
         chatService.saveChatMessage(type,body,weixinId,friendId);
         return new JsonResult();
     }

    /**
     * 获取聊天记录
     * @return
     */
    @RequestMapping(value="getChatMessage", method = RequestMethod.POST)
    public JsonResult getChatMessage() {
        JSONObject parObj = getParams();
        String type = parObj.getString("type");
        int sort = parObj.getInt("sort");
        int page = parObj.getInt("page");
        int limit = parObj.getInt("limit");
        String friendId = parObj.getString("friendId");
        String weixinId = parObj.getString("weixinId");
        return new JsonResult(chatService.getChatMessage(type,sort,page,limit,weixinId,friendId));
    }

    /**
     * 获取公共快捷语
     * @return
     */
     @RequestMapping(value="getQuickWord", method = RequestMethod.POST)
     public JsonResult getQuickWord()
     {
         String qwords = (String) RedisSlaveUtil.get("pcwx-quickword");
         if(notEmpty(qwords))
            return new JsonResult(JSONArray.fromObject(qwords));
         String[] quickData = {"您好,欢迎光临,请问有什么可以帮到您的吗?",
                 "您好,很高兴为您服务,您刚才说的商品有货!",
                 "很高兴为您服务。我需要为您看下库存单,麻烦您稍等!",
                 "您可以进群领优惠劵哦可以直接抵现金的哦!",
                 "亲,请问还有什么需要了解的吗?",
                 "亲,请问还有什么可以帮到您的吗?",
                 "亲,非常抱歉,您说的我的确无法办到。希望我下次能帮到您。",
                 "亲,您的眼光真不错,我个人也很喜欢您选的这款,这款是我们店铺热卖产品。",
                 "亲,非常抱歉,价格已经是最优惠的了。",
                 "亲,您真的让我很为难,我请示下店长,看能不能给您折扣,不过估计有点难,您稍等…",
                 "您说的情况需要请示我们经理了,请您稍等下。",
                 "我们尽量当天发出,如遇特殊情况会有延迟,16:30后的订单隔天发出。",
                 "亲,您稍等,我帮您确认一下哪个快递能到。",
                 "亲,您好,是有什么问题让您不满意了吗?",
                 "亲爱会员,双十一来啦!",
                 "双十一狂欢节惊爆疯抢中!",
                 "亲，好久不见，想您了!"
         };
         List<Map<String,String>> maps = new ArrayList<>();
         for (String data:quickData) {
             Map<String,String> map = new HashMap<>();
             map.put("text",data);
             maps.add(map);
         }
         return new JsonResult(maps);
     }

    /**
     * 获取所有标签库
     * @return
     */
    @RequestMapping(value = "getTagList", method = RequestMethod.POST)
    public JsonResult getTagList() {
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        List<TagListVo> list = fansTagService.getList(appId);
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "getFriendTags", method = RequestMethod.POST)
    public JsonResult getFriendTags(HttpServletRequest request)
    {
        JSONObject parObj = getParams();
        UserVo userVo = AuthUser.getUser();
        String friendId = parObj.getString("friendId");
        String weixinId = parObj.getString("weixinId");
        List<TagVo> tagVos = fansTagService.getFriendTags(friendId,userVo.getDykId(),weixinId);
        return new JsonResult(tagVos);
    }

    /**
     * 新增标签组
     * @return
     */
    @RequestMapping(value = "addTagGroup", method = RequestMethod.POST)
    public JsonResult addTagGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        fansTagService.addGroup(title, appId);
        return result;
    }

    /**
     * 更新标签组
     * @return
     */
    @RequestMapping(value = "saveTagGroup", method = RequestMethod.POST)
    public JsonResult saveTagGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        int id = params.getInt("id");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        fansTagService.saveGroup(id, title, appId);
        return result;
    }

    /**
     * 删除标签组
     * @return
     */
    @RequestMapping(value = "delTagGroup", method = RequestMethod.POST)
    public JsonResult delTagGroup() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        int id = params.getInt("id");
        String youkeId = user.getDykId();
        fansTagService.deleteGroup(id, appId, youkeId);
        return result;
    }

    /**
     * 新增标签
     * @return
     */
    @RequestMapping(value = "addTag", method = RequestMethod.POST)
    public JsonResult addTag() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        int groupId = params.getInt("groupId");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        fansTagService.addTag(groupId, title, appId);
        return result;
    }

    /**
     * 更新标签
     * @return
     */
    @RequestMapping(value = "saveTag", method = RequestMethod.POST)
    public JsonResult saveTag() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String title = params.getString("title");
        UserVo user = AuthUser.getUser();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        JsonResult result = new JsonResult();
        fansTagService.saveTag(id, title, appId);
        return result;
    }

    /**
     * 删除标签
     * @return
     */
    @RequestMapping(value = "delTag", method = RequestMethod.POST)
    public JsonResult delTag() {
        JSONObject params = getParams();
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        String appId = user.getAppId();
        if(empty(appId)){
            appId = user.getDykId();
        }
        Integer id = params.getInt("id");
        fansTagService.delTag(id, youkeId, appId);
        return result;
    }

    /**
     * 绑定好友标签
     * @return
     */
    @RequestMapping(value = "bindFriendTag", method = RequestMethod.POST)
    public JsonResult bindFriendTag() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        Integer tagId = params.getInt("tagId");
        String friendId = params.getString("friendId");
        String weixinId = params.getString("weixinId");
        fansTagService.bindFriendTag(tagId,friendId,weixinId,youkeId);
        return new JsonResult();
    }

    @RequestMapping(value = "delFriendTag", method = RequestMethod.POST)
    public JsonResult delFriendTag(HttpServletRequest request)
    {
        JSONObject parObj = getParams();
        UserVo userVo = AuthUser.getUser();
        Integer tagId = parObj.getInt("tagId");
        String friendId = parObj.getString("friendId");
        String weixinId = parObj.getString("weixinId");
        fansTagService.delFriendTag(tagId,friendId,weixinId,userVo.getDykId());
        return new JsonResult();
    }

    /**
     * 绑定购物账号
     * @return
     */
    @RequestMapping(value = "bindBuyerName", method = RequestMethod.POST)
    public JsonResult bindBuyerName() {
        return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ,"请升级最新版本");
    }

    /**
     * 绑定手机号码
     * @return
     */
    @RequestMapping(value = "bindMobile", method = RequestMethod.POST)
    public JsonResult bindMobile() {
        JSONObject params = getParams();
        String friendId = params.getString("friendId");
        String mobile = params.getString("mobile");
        String weixinId = params.getString("weixinId");
        shopFansService.bindMobile(friendId,mobile,weixinId);
        return new JsonResult();
    }

    /**
     * 获取标签下好友Ids
     * @return
     */
    @RequestMapping(value = "getTagFriendIds", method = RequestMethod.POST)
    public JsonResult getTagFriendIds() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        String tagIds = params.getString("tagIds");
        String weixinId = params.getString("weixinId");
        if(empty(tagIds)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"tagIds不能为空");
        }
        String[] tagIdArr = tagIds.split(",");
        List<FriendIdVo> friendIdVos = fansTagService.getTagFriendIds(tagIdArr,weixinId,youkeId);
        return new JsonResult(friendIdVos);
    }

    /**
     * 获取所选微信号已绑定的标签
     * @return
     */
    @RequestMapping(value = "getWeixinTags", method = RequestMethod.POST)
    public JsonResult getWeixinTags() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        String weixinId = params.getString("weixinId");
        if(empty(weixinId)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"weixinId不能为空");
        }
        List<TagVo> tagVos = fansTagService.getWeixinTags(weixinId);
        return new JsonResult(tagVos);
    }

    /**
     * 获取所选好友客户信息
     * @return
     */
    @RequestMapping(value = "getShopFansInfo", method = RequestMethod.POST)
    public JsonResult getShopFansInfo() {
        JSONObject params = getParams();
        String friendId = params.getString("friendId");
        String weixinId = params.getString("weixinId");
        if(empty(friendId)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"friendId不能为空");
        }
        BuyerVo buyerVo = shopFansService.getFriendBuyerInfo(friendId,weixinId,AuthUser.getUser().getDykId());
        return new JsonResult(buyerVo);
    }

    /**
     * 获取所选好友客户订单信息
     * @return
     */
    @RequestMapping(value = "getShopOrderList", method = RequestMethod.POST)
    public JsonResult getShopOrderList() {
        JSONObject params = getParams();
        String friendId = params.getString("friendId");
        String weixinId = params.getString("weixinId");
        if(empty(friendId)){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"friendId不能为空");
        }
        List<BuyerOrderVo> buyerOrderVos = shopFansService.getShopOrderList(friendId,weixinId,AuthUser.getUser().getDykId());
        return new JsonResult(buyerOrderVos);
    }

}
