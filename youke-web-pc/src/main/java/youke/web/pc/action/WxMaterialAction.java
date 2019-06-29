package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
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
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.EncodeUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.WxCurlUtil;
import youke.facade.wx.provider.IWeixinMaterialService;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.vo.material.*;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 10:11
 */
@RestController
@RequestMapping("material")
public class WxMaterialAction extends BaseAction {

    @Resource
    private IWeixinMaterialService wxMetiaService;
    @Resource
    private IWeixinService weixinService;
    /**
     * 增加图片素材
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "addImg", method = RequestMethod.POST)
    public JsonResult addImg(MultipartFile file) {
        try {
            if(file.getSize()>2000000){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"图片不能超过2MB");
            }
            String fileName = file.getOriginalFilename().toLowerCase();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg")|| fileName.endsWith(".jpeg")|| fileName.endsWith(".gif")|| fileName.endsWith(".bmp")) {
                SerializeMultipartFile serializeMultipartFile = new SerializeMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), file.isEmpty(), file.getSize(), file.getBytes());
                int i = wxMetiaService.addImg(serializeMultipartFile, AuthUser.getUser().getUserId(), AuthUser.getUser().getAppId());
                return new JsonResult(i);
            }else{
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"只支持bmp/png/jpeg/jpg/gif格式");
            }
        }catch (Exception e){
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS,e.getMessage());
        }
    }

    @RequestMapping(value = "gettoken")
    public JsonResult gettoken() {
        JsonResult result = new JsonResult();
        result.setData(weixinService.getToken(AuthUser.getUser().getAppId()));
        return result;
    }

    @RequestMapping(value = "uploadNewsImg")
    public Object uploadNewsImg(HttpServletRequest request){
        Map result = new HashMap();
        result.put("errno",ApiCodeConstant.COMMON_ERROR_SYS);
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (empty(cmr)) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "参数错误");
        }
        List<String> fileList = new ArrayList<String>();
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile file = mRequest.getFile(files.next());
                if(file.getSize()>1048570){
                    result.put("errmsg","图片不能超过1MB");
                }else{
                    String fileName = file.getOriginalFilename().toLowerCase();
                    if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                        String ret = WxCurlUtil.postFile("https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+weixinService.getToken(AuthUser.getUser().getAppId()),file);
                        JSONObject resObj = JSONObject.fromObject(ret);
                        if(resObj.containsKey("url")){
                            result.put("errno",0);
                            fileList.add(resObj.getString("url"));
                            result.put("data",fileList);
                        }else{
                            result.put("errmsg",resObj.getString("errmsg"));
                        }
                    } else {
                        result.put("errmsg","只支持png/jpg格式图片");
                    }
                }
            }
        }
        return result;
    }

    /**
     * 批量删除图片素材
     *
     * @return
     */
    @RequestMapping(value = "delimg", method = RequestMethod.POST)
    public JsonResult delimg() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String ids = params.getString("ids");
        wxMetiaService.deleteImg(ids, AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 分页获取图片素材
     *
     * @return
     */
    @RequestMapping(value = "getimglist", method = RequestMethod.POST)
    public JsonResult getimglist() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<ImageVo> info = wxMetiaService.getImgList(page, limit, AuthUser.getUser().getAppId());
        result.setData(info);
        return result;
    }

    /**
     * 获取单个图片
     *
     * @return
     */
    @RequestMapping(value = "getimg", method = RequestMethod.POST)
    public JsonResult getimg() {

        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        ImageVo imageVo = wxMetiaService.getImg(id, AuthUser.getUser().getAppId());
        result.setData(imageVo);
        return result;
    }

    /**
     * 修改图片素材标题
     *
     * @return
     */
    @RequestMapping(value = "saveImgTitle", method = RequestMethod.POST)
    public JsonResult saveImgTitle() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        String title = params.getString("title");
        title = StringUtils.hasLength(title) ? title : null;
        wxMetiaService.updateImgTitle(id, title, AuthUser.getUser().getAppId());
        return result;
    }


    /**
     * 图文素材删除
     *
     * @return
     */
    @RequestMapping(value = "delnews", method = RequestMethod.POST)
    public JsonResult delnews() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        wxMetiaService.delNews(id, AuthUser.getUser().getAppId());
        return result;
    }

    /**
     * 图文分页获取
     *
     * @return
     */
    @RequestMapping(value = "getnewslist", method = RequestMethod.POST)
    public JsonResult getnewslist() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<NewsTreeVo> info = wxMetiaService.getNewsList(page, limit, AuthUser.getUser().getAppId());
        result.setData(info);
        return result;
    }


    /**
     * 图文单个获取
     *
     * @return
     */
    @RequestMapping(value = "getnews", method = RequestMethod.POST)
    public JsonResult getnews() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        NewsTreeVo news = wxMetiaService.getNews(id, AuthUser.getUser().getAppId());
        result.setData(news);
        return result;

    }


    /**
     * 新增图文素材
     *
     * @return
     */
    @RequestMapping(value = "addnews", method = RequestMethod.POST)
    public JsonResult addnews() {
        JsonResult result = new JsonResult();

        JSONObject params = getParams();
        int id = params.getInt("id");
        JSONArray articles = params.getJSONArray("articles");
        List<NewsVo> news = new ArrayList<NewsVo>();
        for (int i = 0; i < articles.size(); i++) {
            news.add((NewsVo) JSONObject.toBean(articles.getJSONObject(i), NewsVo.class));
        }
        int i = wxMetiaService.addNews(news, AuthUser.getUser().getAppId(), AuthUser.getUser().getUserId(), id);
        result.setData(i);
        return result;
    }

    /**
     * 图文素材保存 先删除,后添加
     *
     * @return
     */
    @RequestMapping(value = "savenews", method = RequestMethod.POST)
    public JsonResult savenews() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        JSONArray articles = params.getJSONArray("articles");
        List<NewsVo> news = new ArrayList<NewsVo>();
        for (int i = 0; i < articles.size(); i++) {
            news.add((NewsVo) JSONObject.toBean(articles.getJSONObject(i), NewsVo.class));
        }
        wxMetiaService.addNews(news, AuthUser.getUser().getAppId(), AuthUser.getUser().getUserId(), id);
        return result;
    }

    /**
     * 获取单个系统图文
     *
     * @return
     */
    @RequestMapping(value = "getsysnews", method = RequestMethod.POST)
    public JsonResult getsysnews() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        SysNewsTreeVo sysNews = wxMetiaService.getSysnews(id, AuthUser.getUser().getAppId());
        result.setData(sysNews);
        return result;
    }

    /**
     * 系统图文分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getsysnewslist", method = RequestMethod.POST)
    public JsonResult getsysnewslist() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<SysNewsTreeVo> info = wxMetiaService.getSysnewsList(page, limit, AuthUser.getUser().getAppId());
        result.setData(info);
        return result;
    }

    /**
     * 系统图文素材新增addsysnews
     *
     * @return
     */
    @RequestMapping(value = "addsysnews", method = RequestMethod.POST)
    public JsonResult addsysnews() {
        JSONObject params = getParams();
        JSONArray news = params.getJSONArray("news");
        List<SysnewsVo> acticles = parseSysnewVo(news);
        int id = wxMetiaService.addOrSaveSysnews(acticles, null, AuthUser.getUser().getAppId());

        return new JsonResult(id);
    }

    /**
     * 系统图文素材保存addsysnews
     *
     * @return
     */
    @RequestMapping(value = "savesysnews", method = RequestMethod.POST)
    public void saveSysnews() {
        JSONObject params = getParams();
        int id = params.getInt("id");
        JSONArray news = params.getJSONArray("news");
        List<SysnewsVo> acticles = parseSysnewVo(news);
        wxMetiaService.addOrSaveSysnews(acticles, id, AuthUser.getUser().getAppId());
    }

    /**
     * 编辑图文预览
     */
    @RequestMapping(value = "preeditnews", method = RequestMethod.POST)
    public JsonResult preEditNews(HttpServletRequest request) {
        String key = IDUtil.getRandomId();
        RedisUtil.set("PreEditNews-"+key,getParamsStr(),3600);//一小时内有效
        return new JsonResult(getBasePath(request) + "common/qrcode?d=" + EncodeUtil.urlEncode(getBasePath(request)+"common/pnews/"+key));
    }

    /**
     * 单图文预览
     */
    @RequestMapping(value = "previewnews", method = RequestMethod.POST)
    public JsonResult previewnews(HttpServletRequest request) {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String url = "";
        //获取图文信息
        NewsTreeVo news = wxMetiaService.getNews(id, AuthUser.getUser().getAppId());
        if(news != null){
            url = news.getNews().getUrl();
        }
        try {
            String shortUrl = weixinService.getShortUrl(AuthUser.getUser().getAppId(), url);
            url = URLEncoder.encode(shortUrl, "utf-8");
        } catch (Exception e) {

        }
        return new JsonResult(getBasePath(request) + "common/qrcode?d=" + url);
    }

    /**
     * 单系统图文预览
     */
    @RequestMapping(value = "previewsysnews", method = RequestMethod.POST)
    public JsonResult previewsysnews(HttpServletRequest request) {
        JSONObject params = getParams();
        int id = params.getInt("id");
        //获取图文信息
        SysNewsTreeVo sysnews = wxMetiaService.getSysnews(id, AuthUser.getUser().getAppId());
        String url = sysnews.getNews().getUrl();
        try {
            String shortUrl = weixinService.getShortUrl(AuthUser.getUser().getAppId(), url);
            url = URLEncoder.encode(shortUrl, "utf-8");
        } catch (Exception e) {

        }
        return new JsonResult(getBasePath(request) + "common/qrcode?d=" + url);
    }

    /**
     * 获取单个用户系统图文
     *
     * @return
     */
    @RequestMapping(value = "getmarketnews", method = RequestMethod.POST)
    public JsonResult getMarketNews() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        SysNewsTreeVo sysNews = wxMetiaService.getSysnews(id, AuthUser.getUser().getAppId());
        result.setData(sysNews);
        return result;
    }

    /**
     * 系统营销图文分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "getmarketnewslist", method = RequestMethod.POST)
    public JsonResult getMarketNewsList() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        PageInfo<SysNewsTreeVo> info = wxMetiaService.getMarketNewsList(page, limit, AuthUser.getUser().getAppId());
        result.setData(info);
        return result;
    }

    private List<SysnewsVo> parseSysnewVo(JSONArray news) {
        List<SysnewsVo> acticles = new ArrayList<SysnewsVo>();
        ;
        if (news != null && news.size() > 0) {

            for (int i = 0; i < news.size(); i++) {
                JSONObject obj = news.getJSONObject(i);
                SysnewsVo vo = new SysnewsVo();
                vo.setDescription(obj.getString("intro"));
                vo.setPicUrl(obj.getString("thumbUrl"));
                vo.setUrl(obj.getString("url"));
                vo.setTitle(obj.getString("title"));
                acticles.add(vo);
            }
        }
        return acticles;
    }
}
