package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.ShopFansImportVo;
import youke.common.model.vo.result.ShopFansVo;
import youke.facade.fans.provider.IShopFansService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/3
 * Time: 11:23
 */
@RestController
@RequestMapping("shopfans")
public class ShopFansAction extends BaseAction {

    @Resource
    private IShopFansService shopFansService;

    /**
     * 分页获取购物粉丝列表
     *
     * @return
     */
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public JsonResult getList() {
        JsonResult result = new JsonResult();
        ShopFansQueryVo qo = getParams(ShopFansQueryVo.class);
        qo.setYoukeId(AuthUser.getUser().getDykId());
        PageInfo<ShopFansVo> pageInfo = shopFansService.getShopfansList(qo);
        result.setData(pageInfo);
        return result;
    }


    /**
     * 购物粉丝删除标签
     *
     * @return
     */
    @RequestMapping(value = "deltags", method = RequestMethod.POST)
    public JsonResult delTags() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String fansIdStr = params.getString("fansId");
        String tags = params.getString("tags");
        tags = StringUtils.hasLength(tags) ? tags : null;
        long fansId = Long.parseLong(fansIdStr);
        shopFansService.deleteTags(fansId, tags);
        return result;
    }

    /**
     * 购物粉丝增加标签
     *
     * @return
     */
    @RequestMapping(value = "addtags", method = RequestMethod.POST)
    public JsonResult addTags() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String fansIds = params.getString("fansId");
        String tags = params.getString("tags");
        tags = StringUtils.hasLength(tags) ? tags : null;
        shopFansService.addTags(fansIds, tags, AuthUser.getUser().getDykId());
        return result;
    }

    /**
     * 购物粉丝导入
     *
     * @return
     */
    @RequestMapping(value = "import")
    public JsonResult importFans(HttpServletRequest request) {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(
                request.getServletContext());
        if (empty(cmr)) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "参数错误");
        }
        Integer shopId = Integer.parseInt(request.getParameter("shopid"));
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            if (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                //处理
                SerializeMultipartFile seriFile = null;
                try {
                    seriFile = new SerializeMultipartFile(mFile.getName(), mFile.getOriginalFilename(),
                            mFile.getContentType(), mFile.isEmpty(), mFile.getSize(), mFile.getBytes());
                    shopFansService.saveImportFansFromFile(shopId, seriFile, AuthUser.getUser().getDykId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new JsonResult();
    }

    /**
     * 购物粉丝添加备注
     *
     * @return
     */
    @RequestMapping(value = "saveremark", method = RequestMethod.POST)
    public JsonResult saveRemark() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String fansIdStr = params.getString("fansId");
        String remarkStr = params.getString("remark");
        remarkStr = StringUtils.hasLength(remarkStr) ? remarkStr : null;
        long fansId = Long.parseLong(fansIdStr);
        shopFansService.saveRemark(fansId, remarkStr);
        return result;
    }


    /**
     * 购物粉丝添分页获取导入记录
     *
     * @return
     */
    @RequestMapping(value = "getimportlist", method = RequestMethod.POST)
    public JsonResult getImportList() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<ShopFansImportVo> info = shopFansService.getImportList(page, limit, AuthUser.getUser().getDykId());
        result.setData(info);
        return result;
    }

    /**
     * 购物粉丝获取同步时间
     *
     * @return
     */
    @RequestMapping(value = "getsynctime", method = RequestMethod.POST)
    public JsonResult getSyncTime() {
        JsonResult result = new JsonResult();
        String date = shopFansService.getSyncTime();
        result.setData(date);
        return result;
    }

    /**
     * 购物粉丝同步
     *
     * @return
     */
    @RequestMapping(value = "sync", method = RequestMethod.POST)
    public JsonResult sync() {
        JsonResult result = new JsonResult();
        //shopFansService.sync();
        return result;
    }

    /**
     * 编辑粉丝
     *
     * @return
     */
    @RequestMapping(value = "savefans", method = RequestMethod.POST)
    public JsonResult saveFans() {
        JsonResult result = new JsonResult();
        ShopFansVo fans = getParams(ShopFansVo.class);
        shopFansService.saveFans(fans);
        return result;
    }

    /**
     * 批量修改粉丝状态 1:加入黑名单 0:移除黑名单
     *
     * @return
     */
    @RequestMapping(value = "openfans", method = RequestMethod.POST)
    public JsonResult openFans() {
        JsonResult result = new JsonResult();
        shopFansService.openOrdeleteFans(getParams().getString("ids"), 0);
        return result;
    }

    /**
     * 批量修改粉丝状态 1:加入黑名单 0:移除黑名单
     *
     * @return
     */
    @RequestMapping(value = "deleteshopfans", method = RequestMethod.POST)
    public JsonResult deleteShopfans() {
        JsonResult result = new JsonResult();
        shopFansService.openOrdeleteFans(getParams().getString("ids"), 1);
        return result;
    }
}
