package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.MD5Util;
import youke.common.utils.QrCodeUtil;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.user.provider.IAreaService;
import youke.facade.user.provider.IUserService;
import youke.facade.user.vo.AreaVo;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.SceneType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("news")
public class NewsAction extends BaseAction {

    @Resource
    private IAreaService areaService;

    /**
     * 获取资讯类型
     * @return
     */
    @RequestMapping("getnewstype")
    @ResponseBody
    public JsonResult getNewsType() {
        List<TagVo> typeVos = areaService.getNewsType();
        JsonResult result = new JsonResult();
        result.setData(typeVos);
        return result;
    }

    /**
     * 获取资讯列表
     * @return
     */
    @RequestMapping("getnewslist")
    @ResponseBody
    public JsonResult getNewsList(HttpServletRequest request) {
        int typeId =  Integer.parseInt(request.getParameter("typeId"));
        int page =  Integer.parseInt(request.getParameter("page"));
        int limit =  Integer.parseInt(request.getParameter("limit"));
        JsonResult result = new JsonResult();
        PageInfo<NewsVo> newsData = areaService.getNewsList(typeId,page,limit);
        result.setData(newsData);
        return result;
    }

    /**
     * 获取资讯列表
     * @return
     */
    @RequestMapping("getnewsinfo")
    @ResponseBody
    public JsonResult getNewsInfo(HttpServletRequest request) {
        int id =  Integer.parseInt(request.getParameter("id"));
        JsonResult result = new JsonResult();
        NewsVo newsData = areaService.getNewsInfo(id);
        newsData.setContent(StringEscapeUtils.unescapeHtml(newsData.getContent()));
        result.setData(newsData);
        return result;
    }
}
