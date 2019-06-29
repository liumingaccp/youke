package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.WxFansVo;
import youke.facade.fans.provider.IWxFansService;
import youke.facade.wx.provider.IWeixinFansService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 18:51
 */
@RestController
@RequestMapping("wxfans")
public class WxFansAction extends BaseAction {

    @Resource
    private IWxFansService wxFansService;
    @Resource
    private IWeixinFansService weixinFansService;

    /**
     * 批量拉黑
     *
     * @return
     */
    @RequestMapping(value = "batchblack", method = RequestMethod.POST)
    public JsonResult batchBlack() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openIds = params.getString("openId");
        openIds =  StringUtils.hasLength(openIds) ? openIds : null;
        String[] splits = openIds.split(",");
        //本地修改
        wxFansService.batchBlack(Arrays.asList(splits), AuthUser.getUser().getAppId());
        //同步微信
        weixinFansService.updateFansInBlack(Arrays.asList(splits), AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 批量拉白,哈哈
     *
     * @return
     */
    @RequestMapping(value = "batchoutblack", method = RequestMethod.POST)
    public JsonResult batchOutBlack() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openIds = params.getString("openId");
        openIds = StringUtils.hasLength(openIds) ? openIds : null;
        String[] splits = openIds.split(",");
        wxFansService.batchOutBlack(Arrays.asList(splits), AuthUser.getUser().getAppId());
        weixinFansService.updateFansOutBlack(Arrays.asList(splits), AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 微信粉丝分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public JsonResult getList() {
        JsonResult result = new JsonResult();
        WxFansQueryVo qo = getParams(WxFansQueryVo.class);
        qo.setAppId(AuthUser.getUser().getAppId());
        PageInfo<WxFansVo> info = wxFansService.getList(qo);
        result.setData(info);
        return result;
    }

    /**
     * 微信粉丝分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getcount", method = RequestMethod.POST)
    public JsonResult getCount() {
       return new JsonResult(0);
    }

    /**
     * 添加备注
     *
     * @return
     */
    @RequestMapping(value = "saveremark", method = RequestMethod.POST)
    public JsonResult saveRemark() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openId = params.getString("openId");
        openId = StringUtils.hasLength(openId) ? openId : null;
        String remark = params.getString("remark");
        remark = StringUtils.hasLength(remark) ? remark : null;
        wxFansService.saveRemark(openId, remark);
        return result;

    }

    /**
     * 获取小黑屋列表
     *
     * @return
     */
    @RequestMapping(value = "getblacklist", method = RequestMethod.POST)
    public JsonResult getBlackList() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String nickname = params.getString("nickname");
        nickname = StringUtils.hasLength(nickname) ? nickname : null;
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<WxFansVo> pageInfo = wxFansService.getListBlack(nickname, page, limit,AuthUser.getUser().getAppId());
        result.setData(pageInfo);
        return result;
    }

    /**
     * 微信粉丝删除标签
     *
     * @return
     */
    @RequestMapping(value = "deltags", method = RequestMethod.POST)
    public JsonResult delTags() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openId = params.getString("openId");
        openId = StringUtils.hasLength(openId) ? openId : null;
        String tags = params.getString("tags");
        wxFansService.deleteTags(openId, tags);
        weixinFansService.deleteOpenIdTags(openId, tags, AuthUser.getUser().getAppId());
        return result;

    }

    /**
     * 微信粉丝增加标签
     *
     * @return
     */
    @RequestMapping(value = "addtags", method = RequestMethod.POST)
    public JsonResult addTags() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openIds = params.getString("openIds");
        String tags = params.getString("tags");
        wxFansService.addTags(openIds, tags);
        return result;
    }

    /**
     * 微信粉丝批量增加标签
     *
     * @return
     */
    @RequestMapping(value = "batchtags", method = RequestMethod.POST)
    public JsonResult batchtags() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String openIds = params.getString("openIds");
        String tags = params.getString("tags");
        wxFansService.batchtags(openIds, tags);
        return result;
    }

    /**
     * 同本本地标签至微信
     *
     * @return
     */
    @RequestMapping(value = "uptags", method = RequestMethod.POST)
    public JsonResult upTags() {
        JsonResult result = new JsonResult();
        weixinFansService.upTags(AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 同本本地标签至微信
     *
     * @return
     */
    @RequestMapping(value = "downtags", method = RequestMethod.POST)
    public JsonResult downTags() {
        JsonResult result = new JsonResult();
        weixinFansService.downTags(AuthUser.getUser().getAppId());
        return result;
    }
}
