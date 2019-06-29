package youke.web.pc.action;

import it.sauronsoftware.jave.Encoder;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.MD5Util;
import youke.common.utils.QrCodeUtil;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.user.provider.IAreaService;
import youke.facade.user.provider.IUserService;
import youke.facade.user.vo.AreaVo;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.SceneType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.*;

@Controller
@RequestMapping("common")
public class CommonAction extends BaseAction {


    @Resource
    private IUserService userService;
    @Resource
    private IAreaService areaService;
    @Resource
    private IMobmsgService mobmsgService;
    @Resource
    private IWeixinService weixinService;
    @Resource
    private IWeixinMessageService weixinMessageService;

    /**
     * 发送验证码
     *
     * @return
     */
    @RequestMapping("sendcode")
    @ResponseBody
    public JsonResult sendcode() {
        JsonResult result = new JsonResult();
        JSONObject objs = getParams();
        String mobile = objs.getString("mobile");
        //手机号码的本地验证
        mobmsgService.sendMobCode(ApiCodeConstant.APPID, mobile);
        return result;
    }

    /**
     * 验证码校验
     *
     * @return
     */
    @RequestMapping("checkCode")
    @ResponseBody
    public JsonResult checkCode() {
        JSONObject objs = getParams();
        String mobile = objs.getString("mobile");
        String code = objs.getString("code");
        JsonResult result = new JsonResult();
        if (!mobmsgService.checkMobCode(mobile, code)) {
            throw new BusinessException("验证码过期或有误");
        }
        return result;
    }

    /**
     * 批量上传文件到阿里OSS
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadfile")
    @ResponseBody
    public JsonResult uploadFile(HttpServletRequest request) throws Exception {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (empty(cmr)) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "参数错误");
        }
        List<String> fileList = new ArrayList<String>();
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null && mFile.getSize() > 0) {

                    String originalFilename = mFile.getOriginalFilename();
                    String extensionName = FileUpOrDwUtil.getExtensionName(originalFilename);
                    //获取扩展名当做前缀
                    String filename = extensionName + "/" + MD5Util.MD5(UUID.randomUUID().toString()) + "." + extensionName;
                    String url = FileUpOrDwUtil.uploadNetStream(mFile.getInputStream(), filename);
                    //可访问路径
                    fileList.add(url);
                }
            }
        }
        return new JsonResult(fileList);
    }

    /**
     * 批量上传视频到阿里OSS
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadvideo")
    @ResponseBody
    public JsonResult uploadVideo(HttpServletRequest request) throws Exception {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (empty(cmr)) {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "参数错误");
        }
        List<Map> fileList = new ArrayList<Map>();
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null && mFile.getSize() > 0) {
                    String originalFilename = mFile.getOriginalFilename();
                    String extensionName = FileUpOrDwUtil.getExtensionName(originalFilename);
                    //获取扩展名当做前缀
                    String filename = extensionName + "/" + MD5Util.MD5(UUID.randomUUID().toString()) + "." + extensionName;
                    String url = FileUpOrDwUtil.uploadNetStream(mFile.getInputStream(), filename);
                    //可访问路径
                    Map map = new HashMap();
                    map.put("url",url);
                    map.put("duration",getVideoDuration(mFile));
                    fileList.add(map);
                }
            }
        }
        return new JsonResult(fileList);
    }

    private long getVideoDuration(MultipartFile file){
        FileChannel fc= null;
        long ls = 0;
        try {
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File source = fi.getStoreLocation();
            Encoder encoder = new Encoder();
            it.sauronsoftware.jave.MultimediaInfo
                    m = encoder.getInfo(source);
            ls = m.getDuration();
        } catch (Exception e) {

            e.printStackTrace();

        }finally {
            if (null!=fc){
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ls/1000;
        }
    }

    @RequestMapping("uploadEditorFile")
    @ResponseBody
    public Object uploadEditorFile(HttpServletRequest request) throws Exception {
        Map result = new HashMap();
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (empty(cmr)) {
            result.put("errno", ApiCodeConstant.COMMON_ERROR_PARAM);
            return result;
        }
        List<String> fileList = new ArrayList<String>();
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null && mFile.getSize() > 0) {
                    String originalFilename = mFile.getOriginalFilename();
                    String extensionName = FileUpOrDwUtil.getExtensionName(originalFilename);
                    //获取扩展名当做前缀
                    String filename = extensionName + "/" + MD5Util.MD5(UUID.randomUUID().toString()) + "." + extensionName;
                    String url = FileUpOrDwUtil.uploadNetStream(mFile.getInputStream(), filename);
                    //可访问路径
                    fileList.add(url);
                }
            }
            result.put("errno", 0);
            result.put("data", fileList);
        }
        return result;
    }


    /**
     * 生成二维码
     *
     * @param request
     * @param response
     */
    @RequestMapping("qrcode")
    @ResponseBody
    public void qrCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            int size = 10;
            String url = null;
            int type = 0;
            if (notEmpty(request.getParameter("s")))
                size = Integer.parseInt(request.getParameter("s"));
            if (notEmpty(request.getParameter("t"))) {
                type = Integer.parseInt(request.getParameter("t"));
            }
            url = request.getParameter("d");
            url = URLDecoder.decode(url, "utf-8");
            if (notEmpty(url)) {
                if (type == 0) {
                    response.setContentType("image/jpeg");
                    QrCodeUtil.encode(url, response.getOutputStream(), size * 30, size * 30);
                }
                if (type == 1) {
                    // 设置输出的格式
                    response.reset();
                    response.setContentType("multipart/form-data");
                    response.addHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID() + ".jpg");
                    QrCodeUtil.encode(url, response.getOutputStream(), size * 30, size * 30);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取短链接
     *
     * @return
     */
    @RequestMapping("getshorturl")
    @ResponseBody
    public JsonResult getShortUrl() {
        JSONObject jsonObj = getParams();
        String appId = jsonObj.getString("appId");
        String longUrl = jsonObj.getString("longUrl");
        String url = weixinService.getShortUrl(appId, longUrl);
        return new JsonResult(url);
    }

    @RequestMapping("getareas")
    @ResponseBody
    public JsonResult getAreas() {

        JSONObject params = getParams();
        int pid = params.getInt("pid");
        List<AreaVo> areas = areaService.getAreas(pid);
        return new JsonResult(areas);
    }

    /**
     * 预览图文
     *
     * @param request
     */
    @RequestMapping("pnews/{key}")
    public String pnews(@PathVariable String key, HttpServletRequest request) {
        String newObj = (String) RedisSlaveUtil.get("PreEditNews-" + key);
        if (empty(newObj)) {
            request.setAttribute("express", true);
            request.setAttribute("title", "预览图文已过期");
        } else {
            JSONObject object = JSONObject.fromObject(newObj);
            request.setAttribute("express", false);
            request.setAttribute("title", object.getString("title"));
            request.setAttribute("author", object.getString("author"));
            request.setAttribute("date", DateUtil.getDate("yyyy-MM-dd"));
            request.setAttribute("content", object.getString("content"));
        }
        return "pnews";
    }

    @RequestMapping(value = "nrqrcode")
    @ResponseBody
    public JsonResult nrQrCode(HttpServletRequest request) {
        String mobile = request.getParameter("username");
        if (empty(mobile))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM, "username不能为空");
        String codeUrl = weixinService.getQrcodeTmp(Constants.NRAPPID, SceneType.MOBILE, mobile, 3600);
        return new JsonResult(getBasePath(request) + "common/qrcode?d=" + codeUrl);
    }


    @RequestMapping(value = "applyagency", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult applyAgency(HttpServletRequest request) {
        String name = request.getParameter("name");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String mobile = request.getParameter("mobile");

        if(empty(name))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"姓名不能为空");
        if(empty(province)||empty(city))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"请选择城市");
        if(empty(mobile))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"手机不能为空");

        userService.saveApplyAgencyInfo(name, province, city, mobile);

        try {
            Map<String,String> map = new HashMap<>();
            map.put("first","您好，有新用户在店有客官网申请加盟合作啦！");
            map.put("keyword1", name+"("+mobile+")-"+province+city);
            map.put("keyword2", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm"));
            map.put("remark","请登录业务后台跟进处理。");
            String appId = "wxb90758151405384b";
            String openId = "osbOk1KBPhh5lJnR7qfBpBGOeimo";
            String tempId = "-_WTOd34ODrlRTja9LBPOGZ9zlc-DbZi-8RG_8KnQZg";
            String url="https://sys.dianyk.com";
            weixinMessageService.sendTempMess(appId,openId,tempId,url,map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult();
    }
}
