package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.model.vo.param.helper.CutPriceActiveQueryVo;
import youke.common.model.vo.param.helper.CutPriceOrderQueryVo;
import youke.common.model.vo.result.ActiveLinkVo;
import youke.common.model.vo.result.helper.CutPriceActiveDetailRetVo;
import youke.common.model.vo.result.helper.CutPriceActiveRetVo;
import youke.common.model.vo.result.helper.CutPriceOrderDetailRetVo;
import youke.common.model.vo.result.helper.CutPriceOrderRetVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.EncodeUtil;
import youke.common.utils.ExportUtil;
import youke.common.utils.MoneyUtil;
import youke.facade.helper.provider.ICutPriceService;
import youke.facade.helper.vo.Constants.ActiveState;
import youke.facade.helper.vo.Constants.OrderState;
import youke.facade.helper.vo.CutPriceActiveVo;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@RestController
@RequestMapping("cutprice")
public class CutPriceAction extends BaseAction {

    @Resource
    private ICutPriceService cutPriceService;

    /**
     * 保存砍价活动
     *
     * @return
     */
    @RequestMapping(value = "saveactive", method = RequestMethod.POST)
    public JsonResult saveActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        CutPriceActiveVo params = getParams(CutPriceActiveVo.class);
        Integer activeId = cutPriceService.saveActive(params, user.getDykId(), user.getAppId());
        return new JsonResult(getActiveUrl(user.getAppId(), activeId, request));
    }


    /**
     * 获取砍价活动列表
     *
     * @return
     */
    @RequestMapping(value = "getactivelist", method = RequestMethod.POST)
    public JsonResult getActiveList(HttpServletRequest request) {
        CutPriceActiveQueryVo params = getParams(CutPriceActiveQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<CutPriceActiveRetVo> info = cutPriceService.getActiveList(params, user.getDykId());
        ActiveLinkVo link;
        for (CutPriceActiveRetVo vo : info.getList()) {
            link = getActiveUrl(user.getAppId(), vo.getId(), request);
            vo.setPreUrl(link.getUrl());
            vo.setPreCodeUrl(link.getQrdUrl());
        }
        return new JsonResult(info);
    }

    /**
     * 砍价活动记录分页获取
     *
     * @return
     */
    @RequestMapping(value = "getorderlist", method = RequestMethod.POST)
    public JsonResult getOrderList() {
        CutPriceOrderQueryVo params = getParams(CutPriceOrderQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<CutPriceOrderRetVo> info = cutPriceService.getOrderList(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 获取活动地址和二维码地址
     *
     * @param appId
     * @param activeId
     * @param request
     * @return
     */
    private ActiveLinkVo getActiveUrl(String appId, Integer activeId, HttpServletRequest request) {
        ActiveLinkVo link = new ActiveLinkVo();
        String url = cutPriceService.getConfig(ActiveState.KJ);
        link.setUrl(url.replace("{appId}", appId + "&id=" + activeId));
        link.setQrdUrl(getBasePath(request) + "common/qrcode?d=" + EncodeUtil.urlEncode(link.getUrl()));
        return link;
    }


    /**
     * 砍价记录查看审核详情
     *
     * @return
     */
    @RequestMapping(value = "getexaminedetail", method = RequestMethod.POST)
    public JsonResult getExamineDetail() {
        UserVo user = AuthUser.getUser();
        CutPriceOrderDetailRetVo result = cutPriceService.getExamineDetail(getParams().getLong("orderId"), user.getDykId());
        return new JsonResult(result);
    }

    /**
     * 活动删除
     *
     * @return
     */
    @RequestMapping(value = "delactive", method = RequestMethod.POST)
    public JsonResult delActive() {
        cutPriceService.deleteActive(AuthUser.getUser().getDykId(), getParams().getInt("activeId"));
        return new JsonResult();
    }

    /**
     * 活动编辑
     *
     * @return
     */
    @RequestMapping(value = "getactivedetail", method = RequestMethod.POST)
    public JsonResult getactivedetail() {
        CutPriceActiveDetailRetVo active = cutPriceService.getActiveDetail(getParams().getInt("id"), AuthUser.getUser().getDykId());
        return new JsonResult(active);
    }

    /**
     * 活动上下线
     *
     * @return
     */
    @RequestMapping(value = "toupordown", method = RequestMethod.POST)
    public JsonResult toUpOrDown() {
        JSONObject params = getParams();
        cutPriceService.toUpOrDown(params.getInt("activeId"), params.getInt("state"), AuthUser.getUser().getDykId());
        return new JsonResult();
    }

    /**
     * 活动参与记录导出
     *
     * @return
     */
    @RequestMapping(value = "exportorder", method = RequestMethod.POST)
    public JsonResult exportorder() {
        CutPriceOrderQueryVo params = getParams(CutPriceOrderQueryVo.class);
        UserVo user = AuthUser.getUser();
        List<CutPriceOrderRetVo> list = cutPriceService.getExportOrderList(params, user.getDykId());
        if (notEmpty(list)) {
            List<Map<String, Object>> datalist = new ArrayList<>();
            Map<String, Object> map;
            File tempFile;
            String oss_url = "";
            String headers = "记录编号,商品名称,淘宝账户,微信昵称,购物订单号,创建时间,返利金额(单位:元),记录状态";
            String mapkey = "id,title,buyerName,wxfansname,orderno,createTime,backPrice,state";
            for (CutPriceOrderRetVo vo : list) {
                map = new HashMap<>();
                map.put("id", vo.getId());
                map.put("title", vo.getTitle() == null ? "" : vo.getTitle());
                map.put("buyerName", vo.getBuyerName() == null ? "" : vo.getBuyerName());
                map.put("wxfansname", vo.getWxfansName() == null ? "" : vo.getWxfansName());
                map.put("orderno", vo.getOrderno() == null ? "" : vo.getOrderno());
                map.put("createTime", DateUtil.formatDateTime(vo.getCreateTime()));
                map.put("state", OrderState.MAP_STATE.get(vo.getState()));
                map.put("backPrice", MoneyUtil.fenToYuan((vo.getPrice() - vo.getDealPrice()) + ""));
                datalist.add(map);
            }
            try {
                tempFile = File.createTempFile(UUID.randomUUID().toString(), ".csv");
                ExportUtil.doExport(datalist, headers, mapkey, new FileOutputStream(tempFile));
                oss_url = FileUpOrDwUtil.uploadLocalFile(tempFile, "helper/order/" + tempFile.getName());
            } catch (Exception e) {
                logger.error("活动参与记录数据导出失败:" + e.getMessage());
                e.printStackTrace();
            }
            return "".equals(oss_url) ? new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "网络异常，请稍后再试") : new JsonResult(oss_url);
        } else {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "暂无数据导出。");
        }
    }
}
