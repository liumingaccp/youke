package youke.web.pc.action;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import youke.common.bean.JsonResult;
import youke.common.exception.BusinessException;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.facade.wx.provider.IWeixinKefuService;
import youke.facade.wx.vo.kefu.KefuStateVo;
import youke.facade.wx.vo.kefu.KefuVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/6
 * Time: 9:04
 */
@RestController
@RequestMapping("wxkefu")
public class WxKefuAction extends BaseAction {

    @Resource
    private IWeixinKefuService wxKefuService;

    /**
     * 获取在线数
     *
     * @return
     */
    @RequestMapping(value = "getonlinelist", method = RequestMethod.POST)
    public JsonResult getonlinelist() {
        JsonResult result = new JsonResult();
        List<KefuStateVo> kefuStateList = wxKefuService.getKefuStateList(AuthUser.getUser().getAppId());
        result.setData(kefuStateList);
        return result;
    }

    /**
     * 删除客服
     *
     * @return
     */
    @RequestMapping(value = "delkefu", method = RequestMethod.POST)
    public JsonResult delkefu() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        int id = params.getInt("id");
        String account = params.getString("account");
        wxKefuService.deleteKefu(account, AuthUser.getUser().getAppId(), id);
        return result;
    }

    /**
     * 绑定微信
     *
     * @return
     */
    @RequestMapping(value = "bindwechat", method = RequestMethod.POST)
    public JsonResult bindwechat() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        String account = params.getString("account");
        String wechat = params.getString("wechat");
        int id = params.getInt("id");
        wxKefuService.saveBindKefuWechat(account, wechat, AuthUser.getUser().getAppId(), id);
        return result;
    }

    /**
     * 获取客服列表
     *
     * @return
     */
    @RequestMapping(value = "getkefulist", method = RequestMethod.POST)
    public JsonResult getkefulist() {
        JsonResult result = new JsonResult();
        List<KefuVo> kefus = wxKefuService.getKefus(AuthUser.getUser().getAppId());
        result.setData(kefus);
        return result;
    }

    /**
     * 添加客服
     *
     * @param file
     * @param nickName
     * @param account
     * @return
     */
    @RequestMapping(value = "addkefu", method = RequestMethod.POST)
    public JsonResult addkefu(MultipartFile file, String nickName, String account) {
        JsonResult result = new JsonResult();
        if(file == null){
            new BusinessException("请选择微信客服头像");
        }
        try {
            SerializeMultipartFile serializeMultipartFile = new SerializeMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), file.isEmpty(), file.getSize(), file.getBytes());
            wxKefuService.addKefu(serializeMultipartFile, nickName, account, AuthUser.getUser().getAppId());
        }catch (IOException e){result.setData("传输的文件错误");}
        return result;
    }

    /**
     * 客服图片保存
     *
     * @param file
     * @param account
     * @return
     */
    @RequestMapping(value = "savekefuimg", method = RequestMethod.POST)
    public JsonResult savekefuimg(MultipartFile file, String account, int id) {
        JsonResult result = new JsonResult();
        try {SerializeMultipartFile serializeMultipartFile = new SerializeMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), file.isEmpty(), file.getSize(), file.getBytes());
            wxKefuService.postHeadImage(account, serializeMultipartFile, AuthUser.getUser().getAppId(), id);
        }catch (IOException e){
            result.setData("传输的文件错误");
        }
        return result;
    }
}
