package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.param.helper.FollowOrderPosterVo;
import youke.common.model.vo.param.helper.FollowOrderQueryVo;
import youke.common.model.vo.param.helper.FollowQueryVo;
import youke.common.model.vo.result.helper.FollowOrderQueryRetVo;
import youke.common.model.vo.result.helper.FollowQueryRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.facade.helper.provider.IFollowService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("follow")
public class FollowAction extends BaseAction {

    @Resource
    private IFollowService followService;

    @RequestMapping(value = "getfollowlist")
    public JsonResult getList() {
        FollowQueryVo params = getParams(FollowQueryVo.class);
        params.setType("H5");
        PageInfo<FollowQueryRetVo> info = followService.queryList(params, null);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getfolloworderlist")
    public JsonResult getFollowList() {
        FollowOrderQueryVo params = getParams(FollowOrderQueryVo.class);
        PageInfo<FollowOrderQueryRetVo> info = followService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "savefolloworder")
    public JsonResult saveFollowOrderPoster() {
        FollowOrderPosterVo params = getParams(FollowOrderPosterVo.class);
        long id = followService.saveFollowOrderPoster(params);
        return new JsonResult();
    }

    /**
     * 生成海报
     * @param id
     *      活动id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/poster/{id}")
    public void poster(@PathVariable int id, HttpServletRequest request, HttpServletResponse response)
    {

        String openId = request.getParameter("openId");
        String path = followService.createPoter(id, openId);
        if(path != null){
            response.setHeader("content-type", "image/jpeg");
            InputStream in = null;
            try {
                in = HttpConnUtil.getStreamFromNetByUrl(path);
                FileUpOrDwUtil.deleteFile(path.replace(ApiCodeConstant.AUTH_DOMAIN + "/", ""));
                OutputStream out = response.getOutputStream();
                int length = 0;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
