package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.wxper.DeviceQueryVo;
import youke.common.model.vo.param.wxper.DeviceSaveVo;
import youke.common.model.vo.param.wxper.GrowTaskSaveVo;
import youke.common.model.vo.result.wxper.DeviceDetailRetVo;
import youke.common.model.vo.result.wxper.DeviceQueryRetVo;
import youke.common.model.vo.result.wxper.DeviceSelectRetVo;
import youke.facade.user.vo.UserVo;
import youke.facade.wxper.provider.IWxPerDeviceService;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "devicemanage")
public class WxPerDeviceAction extends BaseAction {

    @Resource
    private IWxPerDeviceService wxPerDeviceService;

    /**
     * 获取设备管理列表
     *
     * @return
     */
    @RequestMapping(value = "getdevicemanagelist", method = RequestMethod.POST)
    public JsonResult getDeviceManageList() {
        DeviceQueryVo params = getParams(DeviceQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<DeviceQueryRetVo> info = wxPerDeviceService.getDeviceManageList(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 获取设备选择列表
     *
     * @return
     */
    @RequestMapping(value = "getdeviceselectlist", method = RequestMethod.POST)
    public JsonResult getDeviceSelectList() {
        UserVo user = AuthUser.getUser();
        List<DeviceSelectRetVo> list = wxPerDeviceService.getDeviceSelectList(user.getDykId());
        return new JsonResult(list);
    }

    /**
     * 获取剩余可添加设备数量
     *
     * @return
     */
    @RequestMapping(value = "getfreenum", method = RequestMethod.POST)
    public JsonResult getFreeNum() {
        UserVo user = AuthUser.getUser();
        Map<String,Integer> map = wxPerDeviceService.getFreeNum(user.getDykId());
        return new JsonResult(map);
    }

    /**
     * 添加设备
     *
     * @return
     */
    @RequestMapping(value = "adddevice", method = RequestMethod.POST)
    public JsonResult addDevice() {
        DeviceSaveVo params = getParams(DeviceSaveVo.class);
        UserVo user = AuthUser.getUser();
        wxPerDeviceService.addDevice(params, user.getDykId());
        return new JsonResult();
    }

    /**
     * 获取设备详情
     *
     * @return
     */
    @RequestMapping(value = "getdevicedetail", method = RequestMethod.POST)
    public JsonResult getDeviceDetail() {
        JSONObject params = getParams();
        DeviceDetailRetVo vo = wxPerDeviceService.getDeviceDetail(params.getLong("id"));
        return new JsonResult(vo);
    }

    /**
     * 删除设备
     *
     * @return
     */
    @RequestMapping(value = "deldevice", method = RequestMethod.POST)
    public JsonResult delDevice() {
        JSONObject params = getParams();
        wxPerDeviceService.delDevice(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 设置随机养号
     *
     * @return
     */
    @RequestMapping(value = "batchgrowtask", method = RequestMethod.POST)
    public JsonResult batchGrowTask() {
        GrowTaskSaveVo params = getParams(GrowTaskSaveVo.class);
        UserVo user = AuthUser.getUser();
        wxPerDeviceService.saveBatchGrowTask(params, user.getDykId());
        return new JsonResult();
    }

    /**
     * 检测设备连接
     *
     * @return
     */
    @RequestMapping(value = "connection", method = RequestMethod.POST)
    public JsonResult connection() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        wxPerDeviceService.doConnection(params.getLong("id"), user.getDykId());
        return new JsonResult();
    }

    /**
     * 获取养号任务详情
     * @return
     */
    @RequestMapping(value = "growinfo", method = RequestMethod.POST)
    public JsonResult growInfo() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        Map<String,Object>map =wxPerDeviceService.getGrowInfo(params.getLong("id"), user.getDykId());
        return new JsonResult(map);
    }

}
