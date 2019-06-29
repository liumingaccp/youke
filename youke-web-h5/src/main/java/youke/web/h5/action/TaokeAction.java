package youke.web.h5.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.TTaokeActive;
import youke.common.model.vo.page.PageResult;
import youke.common.model.vo.param.helper.H5TaokeDetailQueryVo;
import youke.common.model.vo.param.helper.H5TaokeOrderQueryVo;
import youke.common.model.vo.param.helper.HelperOrderVo;
import youke.common.model.vo.param.helper.TaokeQueryVo;
import youke.common.model.vo.result.TaokeActiveVo;
import youke.common.model.vo.result.helper.H5TaokeOrderDetailQueryRetVo;
import youke.common.model.vo.result.helper.H5TaokeOrderQueryRetVo;
import youke.common.model.vo.result.helper.TaokeQueryRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.facade.helper.provider.ITaokeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("taoke")
public class TaokeAction extends BaseAction {

    @Resource
    private ITaokeService taokeService;

    @RequestMapping(value = "gettaokelist")
    public JsonResult getList() {
        TaokeQueryVo params = getParams(TaokeQueryVo.class);
        params.setType("H5");
        PageInfo<TaokeQueryRetVo> info = taokeService.queryList(params, null);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getactivedetail")
    public JsonResult getaAtiveDetail() {
        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        String appId = params.getString("appId");
        TaokeActiveVo info = taokeService.getAtiveDetail(activeId, appId, null);
        return new JsonResult(info);
    }

    @RequestMapping(value = "gettaokeorderlist")
    public JsonResult getTaokeList() {
        H5TaokeOrderQueryVo params = getParams(H5TaokeOrderQueryVo.class);
        PageResult<H5TaokeOrderQueryRetVo> info = taokeService.queryOrderList(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "gettaokedetaillist")
    public JsonResult getTaokeDetailList() {
        H5TaokeDetailQueryVo params = getParams(H5TaokeDetailQueryVo.class);
        PageInfo<H5TaokeOrderDetailQueryRetVo> info = taokeService.queryDetailList(params);
        return new JsonResult(info);
    }

    @RequestMapping(value = "savetaokeorder")
    public JsonResult saveTaokeOrder() {
        HelperOrderVo params = getParams(HelperOrderVo.class);
        long id = taokeService.saveTaokeOrder(params);
        return new JsonResult(id);
    }

    @RequestMapping(value = "saveordernum")
    public JsonResult saveOrderNum() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        String orderNo = StringUtil.hasLength(params.getString("orderNo"))?params.getString("orderNo"):null;
        taokeService.saveOrderNum(appId, openId, orderId, orderNo);
        return new JsonResult();
    }

    @RequestMapping(value = "getorderinfo")
    public JsonResult getOrderInfo() {
        JSONObject params = getParams();
        long orderId = params.getLong("orderId");
        String appId = params.getString("appId");
        String openId = params.getString("openId");
        H5TaokeOrderQueryRetVo info = taokeService.getOrderInfo(appId, openId, orderId);
        return new JsonResult(info);
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
    public JsonResult poster(@PathVariable int id, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult result = new JsonResult();
        BufferedInputStream in = null;
        InputStream inputStream = null;
        String tempFileUrl = null;
        try {
            String openId = request.getParameter("openId");
            tempFileUrl = taokeService.createPoter(id, openId);
            inputStream = HttpConnUtil.getStreamFromNetByUrl(tempFileUrl);
            //FileUpOrDwUtil.deleteFile(tempFileUrl.replace(ApiCodeConstant.OSS_BASE,"")); //删除阿里上的图片
            if(inputStream == null){
                return result;
            }
            in = new BufferedInputStream(inputStream);
            byte[] bb = new byte[1024];// 用来存储每次读取到的字节数组
            int n;// 每次读取到的字节数组的长度
            while ((n = in.read(bb)) != -1) {
                response.getOutputStream().write(bb, 0, n);// 写入到输出流
            }
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }finally {
            try {
                if(in != null){
                    in.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
