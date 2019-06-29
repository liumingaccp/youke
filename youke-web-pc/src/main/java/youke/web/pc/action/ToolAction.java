package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.result.TaowxPoterVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.facade.market.provider.IToolService;
import youke.facade.market.vo.GoodInfoVo;
import youke.facade.market.vo.TaowxVo;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@RestController
@RequestMapping("tool")
public class ToolAction extends BaseAction {

    @Resource
    private IToolService toolService;

    @RequestMapping("/gettaowxres")
    public JsonResult getTaowxRes(HttpServletRequest request) {
        JSONObject parObj = getParams();
        String url = parObj.getString("url");
        UserVo userVo = AuthUser.getUser();
        TaowxVo taowxVo = toolService.doTaowx(url, userVo.getAppId(), userVo.getDykId());
        return new JsonResult(taowxVo);
    }

    @RequestMapping("/gettaowxlist")
    public JsonResult getTaowxlist(HttpServletRequest request) {
        JSONObject parObj = getParams();
        UserVo userVo = AuthUser.getUser();
        int page = parObj.getInt("page");
        int limit = parObj.getInt("limit");
        String timeStart = parObj.getString("timeStart");
        String timeEnd = parObj.getString("timeEnd");
        PageInfo<TaowxPoterVo> info = toolService.getTaoWxList(page, limit, timeStart, timeEnd, userVo.getDykId());
        return new JsonResult(info);
    }

    @RequestMapping("/saveposter")
    public JsonResult savePoster(HttpServletRequest request) {
        JSONObject parObj = getParams();
        UserVo userVo = AuthUser.getUser();
        int posterId = parObj.getInt("posterId");
        String posterUrl = parObj.getString("posterUrl");
        String body = parObj.getString("body");
        toolService.savePoster(posterId, posterUrl, body);
        return new JsonResult();
    }

    @RequestMapping("/poster/{id}")
    public JsonResult poster(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        BufferedInputStream in = null;
        InputStream inputStream = null;
        String tempFileUrl = null;
        try {
            String t = request.getParameter("t");
            int type = 0;
            if (!empty(t)) {
                type = Integer.parseInt(t);
            }
            TaowxPoterVo info = toolService.select(id);
            tempFileUrl = toolService.createPoter(info.getPosterUrl(), info.getBody(), info.getShortUrl());
            inputStream = HttpConnUtil.getStreamFromNetByUrl(tempFileUrl);
            FileUpOrDwUtil.deleteFile(tempFileUrl.replace(ApiCodeConstant.OSS_BASE, "")); //删除阿里上的图片
            if (inputStream == null) {
                return result;
            }
            if (type == 0) {
                in = new BufferedInputStream(inputStream);
                byte[] bb = new byte[1024];// 用来存储每次读取到的字节数组
                int n;// 每次读取到的字节数组的长度
                while ((n = in.read(bb)) != -1) {
                    response.getOutputStream().write(bb, 0, n);// 写入到输出流
                }
            }
            if (type == 1) {
                // 设置输出的格式
                response.reset();
                response.setContentType("multipart/form-data");
                response.addHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".jpg");
                in = new BufferedInputStream(inputStream);
                byte[] bb = new byte[1024];// 用来存储每次读取到的字节数组
                int n;// 每次读取到的字节数组的长度
                while ((n = in.read(bb)) != -1) {
                    response.getOutputStream().write(bb, 0, n);// 写入到输出流
                }
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/gettaogoods")
    public JsonResult getTaoGoods() {
        JSONObject parObj = getParams();
        String url = parObj.getString("url");
        int shopId = parObj.getInt("shopId");
        GoodInfoVo info = toolService.getProductInfo(url, shopId);
        return new JsonResult(info);
    }
}
