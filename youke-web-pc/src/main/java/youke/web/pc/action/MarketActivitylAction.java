package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ComeType;
import youke.common.exception.BusinessException;
import youke.common.model.TSysMessage;
import youke.common.model.vo.param.market.MarketActivitySaveVo;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.*;
import youke.common.model.vo.result.market.MarketActivityVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;
import youke.common.utils.DateUtil;
import youke.common.utils.EncodeUtil;
import youke.common.utils.ExportUtil;
import youke.common.utils.MoneyUtil;
import youke.facade.market.provider.IMarketActivityService;
import youke.facade.market.vo.MarketConstant;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("market")
public class MarketActivitylAction extends BaseAction {

    @Resource
    private IMarketActivityService marketActivityService;


    /**
     * 活动统计数据
     *
     * @return
     */
    @RequestMapping(value = "getactivedata", method = RequestMethod.POST)
    public JsonResult getActiveData() {
        JSONObject params = getParams();
        ActiveDataVo data = marketActivityService.getActiveData(params.getLong("activeId"));
        return new JsonResult(data);
    }

    /**
     * 活动报表数据获取
     *
     * @return
     */
    @RequestMapping(value = "getactivechart", method = RequestMethod.POST)
    public JsonResult getActiveChart() {
        JSONObject params = getParams();
        List<ActiveChartVo> list = marketActivityService.getActiveChart(params.getLong("activeId"), params.getString("begDate"), params.getString("endDate"));
        return new JsonResult(list);
    }


    /**
     * 活动转化数据报表获取
     *
     * @return
     */
    @RequestMapping(value = "getactivetranchart", method = RequestMethod.POST)
    public JsonResult getactivetranchart() {
        JSONObject params = getParams();
        List<ActiveTranChartVo> list = marketActivityService.getActiveTranChart(params.getLong("activeId"), params.getString("begDate"), params.getString("endDate"));
        return new JsonResult(list);
    }

    /**
     * 获取营销活动模板
     *
     * @return
     */
    @RequestMapping(value = "gettemplete", method = RequestMethod.POST)
    public JsonResult getTemplete() {
        JSONObject params = getParams();
        String key = params.getString("key");
        String data = marketActivityService.getTemplate(key);
        return new JsonResult(data);
    }

    /**
     * 营销活动分页获取-倒序
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getactivelist", method = RequestMethod.POST)
    public JsonResult getActiveList(HttpServletRequest request) {
        MarketQueryVo params = getParams(MarketQueryVo.class);
        UserVo user = AuthUser.getUser();
        params.setYoukeId(user.getDykId());
        PageInfo<MarketActivityVo> info = marketActivityService.getActivityList(params);
        for (MarketActivityVo marketActivityVo : info.getList()) {
            ActiveLinkVo link = getActiveUrl(marketActivityVo.getType(), user.getAppId(), marketActivityVo.getId(), request);
            marketActivityVo.setPreUrl(link.getUrl());
            marketActivityVo.setPreCodeUrl(link.getQrdUrl());
        }
        return new JsonResult(info);
    }

    /**
     * 参与记录分页获取-倒序
     *
     * @return
     */
    @RequestMapping(value = "getrecordlist", method = RequestMethod.POST)
    public JsonResult getRecordList() {
        MarketRecordListQueryVo params = getParams(MarketRecordListQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<MarketRecordListVo> info = marketActivityService.getRecordList(params, user.getDykId());
        return new JsonResult(info);
    }


    /**
     * 参与记录查看审核详情
     *
     * @return
     */
    @RequestMapping(value = "getexaminedetail", method = RequestMethod.POST)
    public JsonResult getExamineDetail() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        MarketExamineDetailVo detail = marketActivityService.getExamineDetail(params.getInt("recordId"), user.getDykId());
        return new JsonResult(detail);
    }

    /**
     * 获取投票数据
     *
     * @return
     */
    @RequestMapping(value = "gettpdatas", method = RequestMethod.POST)
    public JsonResult getTPDatas() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        List<ResultOfVotingVo> list = marketActivityService.gettpdatas(params.getLong("activeId"), user.getDykId());
        result.setData(list);
        return result;
    }

    /**
     * 参与记录批量审核
     * 审核成功添加活动转化记录-获奖人数
     *
     * @return
     */
    @RequestMapping(value = "examinerecord", method = RequestMethod.POST)
    public JsonResult examineRecord() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        String recordIds = params.getString("recordIds");
        int state = params.getInt("state");
        String failReason = "";
        if (state == 2 && recordIds.split(",").length == 1) {
            failReason = params.getString("failReason");
        }
        marketActivityService.batchAudit(recordIds, state, failReason, user.getDykId(), user.getUserId());
        return new JsonResult();
    }

    /**
     * 晒单活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savesdactive", method = RequestMethod.POST)
    public JsonResult saveSDActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(0);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 追评活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savezpactive", method = RequestMethod.POST)
    public JsonResult savezpActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(1);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 晒图活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savestactive", method = RequestMethod.POST)
    public JsonResult savestActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(2);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }


    /**
     * 随时返活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "saveflactive", method = RequestMethod.POST)
    public JsonResult saveflActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(3);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 投票活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savetpactive", method = RequestMethod.POST)
    public JsonResult savetpActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(4);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 首绑活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savesbactive", method = RequestMethod.POST)
    public JsonResult savesbActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(5);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 签到活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "saveqdactive", method = RequestMethod.POST)
    public JsonResult saveqdActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(6);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 抽奖活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savecjactive", method = RequestMethod.POST)
    public JsonResult savecjactive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(7);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 海报活动保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "savehbactive", method = RequestMethod.POST)
    public JsonResult savehbActive(HttpServletRequest request) {
        UserVo user = AuthUser.getUser();
        MarketActivitySaveVo params = getParams(MarketActivitySaveVo.class);
        params.setAppId(user.getAppId());
        params.setType(8);
        Long activeId = marketActivityService.saveActive(params, user.getDykId());
        ActiveLinkVo link = getActiveUrl(params.getType(), user.getAppId(), activeId, request);
        addSysMessage(params.getType(), "操作提醒", params.getTitle(), user.getDykId(), link.getUrl());
        return new JsonResult(link);
    }

    /**
     * 随时返订单csv保存
     *
     * @return
     */
    @RequestMapping(value = "saveImportOrders", method = RequestMethod.POST)
    public JsonResult saveImportOrders() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        marketActivityService.saveImportOrders(params.getLong("activeId"), params.getString("csvUrl"), user.getDykId());
        return new JsonResult();
    }

    /**
     * 随时返手动输入订单号保存
     *
     * @return
     */
    @RequestMapping(value = "saveInputOrders", method = RequestMethod.POST)
    public JsonResult saveInputOrders() {
        UserVo user = AuthUser.getUser();
        JSONArray inputOrders = getParams().getJSONArray("inputOrders");
        MarketActivitySaveVo params = new MarketActivitySaveVo();
        params.setInputOrders(inputOrders);
        long activeId = getParams().getLong("activeId");
        params.setId(activeId);
        marketActivityService.saveInputOrders(params, user.getDykId(), activeId);
        return new JsonResult();
    }

    /**
     * 活动删除-物理和逻辑删除
     *
     * @return
     */
    @RequestMapping(value = "deleteActive", method = RequestMethod.POST)
    public JsonResult deleteActive() {
        JSONObject params = getParams();
        marketActivityService.deleteActive(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 活动编辑数据获取
     *
     * @return
     */
    @RequestMapping(value = "activeAudit", method = RequestMethod.POST)
    public JsonResult activeAudit() {
        JSONObject params = getParams();
        ActiveInfoVo info = marketActivityService.activeAudit(params.getLong("id"), params.getInt("type"));
        return new JsonResult(info);
    }

    /**
     * 获取 积分/现金 消耗
     *
     * @return
     */
    @RequestMapping(value = "getconsumption", method = RequestMethod.POST)
    public JsonResult getConsumption() {
        MarketRecordListQueryVo params = getParams(MarketRecordListQueryVo.class);
        UserVo user = AuthUser.getUser();
        Map<String, Object> result = marketActivityService.getConsumption(params, user.getDykId());
        return new JsonResult(result);
    }

    /**
     * 活动上线
     *
     * @return
     */
    @RequestMapping(value = "onLine", method = RequestMethod.POST)
    public JsonResult onLine() {
        JSONObject params = getParams();
        marketActivityService.onLine(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 活动下线
     *
     * @return
     */
    @RequestMapping(value = "offLine", method = RequestMethod.POST)
    public JsonResult offLine() {
        JSONObject params = getParams();
        marketActivityService.offLine(params.getLong("id"));
        return new JsonResult();
    }

    /**
     * 参与记录导出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exportrecord", method = RequestMethod.POST)
    public JsonResult exportRecord(HttpServletRequest request) {
        List<Map<String, Object>> dataList;
        MarketRecordListQueryVo params = getParams(MarketRecordListQueryVo.class);
        UserVo user = AuthUser.getUser();
        List<MarketRecordListVo> list = marketActivityService.getExportRecordList(params, user.getDykId());
        if (list.size() == 0) {
            throw new BusinessException("当前暂无数据导出!");
        }
        String headers = "活动标题,微信昵称,购物账户,购物订单号,奖品类型,金额(单位:元),积分,提交时间,审核状态,最后操作人";
        String mapKey = "title,wxfansName,shopfansName,orderno,rewardType,money,integral,updateTime,state,lastOpt";
        dataList = new ArrayList<>();
        Map<String, Object> map;
        for (MarketRecordListVo record : list) {
            map = new HashMap<>();
            map.put("title", record.getTitle());
            map.put("wxfansName", record.getWxfansName());
            map.put("shopfansName", record.getShopfansName());
            map.put("orderno", record.getOrderno());
            map.put("rewardType", record.getRewardType() == null ? "积分/红包" : record.getRewardType() == 2 ? "积分" : "红包");
            map.put("integral", record.getIntegral() == null ? 0 : record.getIntegral());
            map.put("money", record.getMoney() == null ? 0 : MoneyUtil.fenToYuan(record.getMoney() + ""));
            map.put("updateTime", null == record.getCreateTime() ? "暂无更新" : DateUtil.formatDate(record.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            map.put("state", MarketConstant.RECORD_STATE.get(record.getState()));
            map.put("lastOpt", record.getLastOpt());
            dataList.add(map);
        }
        String realPath;
        String fileName = "";
        try {
            realPath = request.getSession().getServletContext().getRealPath("temp/market_record");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
            File file = new File(realPath, fileName);
            ExportUtil.doExport(dataList, headers, mapKey, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(getBasePath(request) + "temp/market_record/" + fileName);
    }

    /**
     * JsonArray解析
     * TODO 可优化为JsonArray接收
     *
     * @param news
     * @return
     */
   /* private List<MarketParamVo> parseJsonArray(JSONArray news) {
        List<MarketParamVo> list = new ArrayList<MarketParamVo>();
        if (null != news && news.size() > 0) {
            for (int i = 0; i < news.size(); i++) {
                JSONObject obj = news.getJSONObject(i);
                MarketParamVo vo = new MarketParamVo();
                if (obj.has("type")) {
                    vo.setType(obj.getInt("type"));
                }
                if (obj.has("url")) {
                    vo.setUrl(obj.getString("url"));
                }
                if (obj.has("title")) {
                    vo.setTitle(obj.getString("title"));
                }
                if (obj.has("integral")) {
                    vo.setIntegral(obj.getInt("integral"));
                }
                if (obj.has("runDay")) {
                    vo.setRunDay(obj.getInt("runDay"));
                }
                if (obj.has("price")) {
                    vo.setPrice(obj.getInt("price"));
                }
                if (obj.has("orderno")) {
                    vo.setOrderno(obj.getString("orderno"));
                }
                if (obj.has("prizeName")) {
                    vo.setPrizeName(obj.getString("prizeName"));
                }
                if (obj.has("rewardType")) {
                    vo.setRewardType(obj.getInt("rewardType"));
                }
                if (obj.has("rewardVal")) {
                    vo.setRewardVal(obj.getInt("rewardVal"));
                }
                if (obj.has("percent")) {
                    vo.setPercent(obj.getDouble("percent"));
                }
                list.add(vo);
            }
        }
        return list;
    }*/

    /**
     * 获取活动地址
     *
     * @param type
     * @param appId
     * @param activeId
     * @param request
     * @return
     */
    private ActiveLinkVo getActiveUrl(Integer type, String appId, Long activeId, HttpServletRequest request) {
        ActiveLinkVo link = new ActiveLinkVo();
        Integer comeType = ComeType.getComeTypeFromActiveType(type);
        link.setUrl(ComeType.COME_TYPE_URL.get(comeType).replace("{appId}", appId + "&id=" + activeId));
        link.setQrdUrl(getBasePath(request) + "common/qrcode?d=" + EncodeUtil.urlEncode(link.getUrl()));
        return link;
    }


    /**
     * 活动操作提醒
     *
     * @param type
     * @param title
     * @param activeTitle
     * @param dykId
     * @param url
     */
    private void addSysMessage(int type, String title, String activeTitle, String dykId, String url) {
        TSysMessage message = new TSysMessage();
        message.setTitle(title);
        message.setContent("您好,您的活动"
                + ComeType.COME_TYPE.get(ComeType.getComeTypeFromActiveType(type))
                + " - " + activeTitle
                + "已经创建成功,相应的活动参与链接为:"
                + url
                + ",为了用户的使用方便,请将链接贴入自定义菜单中");
        message.setYoukeid(dykId);
        marketActivityService.addSysMessage(message);
    }
}
