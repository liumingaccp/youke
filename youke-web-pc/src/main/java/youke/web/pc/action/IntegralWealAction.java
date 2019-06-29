package youke.web.pc.action;

import com.csvreader.CsvWriter;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.model.vo.param.WealIntegralQueryVo;
import youke.common.model.vo.param.WealOrderQueryVo;
import youke.common.model.vo.result.*;
import youke.common.utils.DateUtil;
import youke.common.utils.EncodeUtil;
import youke.facade.market.provider.IIntegralWealService;
import youke.facade.market.vo.IntegralActiveParamVo;
import youke.facade.shop.provider.IShopService;
import youke.facade.user.vo.UserVo;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/31
 * Time: 11:39
 */
@RestController
@RequestMapping("weal")
public class IntegralWealAction extends BaseAction {

    @Resource
    private IIntegralWealService integralWealService;
    @Resource
    private IShopService shopService;

    @RequestMapping(value = "getactivelist", method = RequestMethod.POST)
    public JsonResult getActiveList() {
        JSONObject params = getParams();
        String title = params.getString("title");
        int state = params.getInt("state");
        int page = params.getInt("page");
        int limit = params.getInt("limit");
        UserVo user = AuthUser.getUser();
        PageInfo<IntegralActiveVo> infos = integralWealService.getIntegralActiveList(title, state, page, limit,user.getDykId());

        return new JsonResult(infos);
    }

    @RequestMapping(value = "addactive", method = RequestMethod.POST)
    public JsonResult addActive(HttpServletRequest request) {

        IntegralActiveParamVo params = getParams(IntegralActiveParamVo.class);
        UserVo user = AuthUser.getUser();
        if(params.getGoodsUrl().contains("taobao.com") || params.getGoodsUrl().contains("tmall.com")){
            String tkl = shopService.getTaoKouLing(params.getGoodsUrl(), "口令内容");
            params.setTaoCode(tkl);
        }
        params.setYoukeId(user.getDykId());
        params.setAppId(user.getAppId());
        int id = integralWealService.addIntegraActive(params);
        ActiveLinkVo link = new ActiveLinkVo();
        String url = integralWealService.getConfig();
        link.setUrl(url.replace("{appId}", user.getAppId() + "&id=" + id));
        link.setQrdUrl(getBasePath(request) + "common/qrcode?d=" + EncodeUtil.urlEncode(link.getUrl()));
        return new JsonResult(link);
    }

    @RequestMapping(value = "getorderlist", method = RequestMethod.POST)
    public JsonResult getOrderList() {

        WealOrderQueryVo params = getParams(WealOrderQueryVo.class);
        UserVo user = AuthUser.getUser();
        params.setDykId(user.getDykId());

        PageInfo<IntegralOrderVo> infos = integralWealService.getIntegralOrderList(params);
        return new JsonResult(infos);
    }

    @RequestMapping(value = "getordercount", method = RequestMethod.POST)
    public JsonResult getOrderCount() {

        WealOrderQueryVo params = getParams(WealOrderQueryVo.class);
        UserVo user = AuthUser.getUser();
        params.setDykId(user.getDykId());

        long count = integralWealService.getIntegralOrderCount(params);
        return new JsonResult(count);
    }

    @RequestMapping(value = "gettotalintegral", method = RequestMethod.POST)
    public JsonResult getTotalIntegral() {
        WealIntegralQueryVo params = getParams(WealIntegralQueryVo.class);
        params.setDykId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        long count = integralWealService.getTotalIntegral(params);
        return new JsonResult(count);
    }

    @RequestMapping(value = "exportorder")
    public JsonResult getExportorder(HttpServletRequest request, HttpServletResponse response) {
        String fileName = null;
        String realPath = null;
        WealOrderQueryVo params = getParams(WealOrderQueryVo.class);
        params.setDykId(AuthUser.getUser().getDykId());
        try {
            PageInfo<IntegralOrderVo> infos = integralWealService.getIntegralOrderList(params);
            realPath = request.getSession().getServletContext().getRealPath("temp/integra_order");
            fileName = UUID.randomUUID().toString() + ".csv";
            File file = new File(realPath, fileName);
            doImportOrder(infos.getList(), new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonResult(getBasePath(request) + "temp/integra_order/" + fileName);
    }

    @RequestMapping(value = "getintegrallist", method = RequestMethod.POST)
    public JsonResult getIntegralList() {
        WealIntegralQueryVo params = getParams(WealIntegralQueryVo.class);
        params.setDykId(AuthUser.getUser().getDykId());
        params.setAppId(AuthUser.getUser().getAppId());
        PageInfo<SubFansIntegralVo> infos = integralWealService.getIntegralList(params);
        return new JsonResult(infos);
    }

    @RequestMapping(value = "getactive", method = RequestMethod.POST)
    public JsonResult getactive(HttpServletRequest request) {
        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        IntegralActiveDetailVo vo = integralWealService.getActice(activeId , AuthUser.getUser().getAppId());
        String pageUrl = integralWealService.getPageUrl();
        String preUrl = pageUrl.replace("{appId}", AuthUser.getUser().getAppId());
        preUrl = preUrl + "&id="+ activeId;
        String codeUrl = getBasePath(request) + "common/qrcode?d=" + URLEncoder.encode(preUrl);
        vo.setPageQrcode(codeUrl);
        return new JsonResult(vo);
    }

    @RequestMapping(value = "getactivedetail", method = RequestMethod.POST)
    public JsonResult getActiveDetail(HttpServletRequest request) {
        JSONObject params = getParams();
        int id = params.getInt("id");
        String appId = AuthUser.getUser().getAppId();
        String dykId = AuthUser.getUser().getDykId();
        IntegralActiveRetVo info = integralWealService.getactiveinfo(id, appId, dykId);
        return new JsonResult(info);
    }

    @RequestMapping(value = "getintegralset", method = RequestMethod.POST)
    public JsonResult getintegralset() {
        String dykId = AuthUser.getUser().getDykId();
        IntegralSetVo setVo = integralWealService.getIntegralSet(dykId);
        return new JsonResult(setVo);
    }

    @RequestMapping(value = "saveintegralset", method = RequestMethod.POST)
    public JsonResult saveintegralset() {
        JSONObject params = getParams();
        IntegralSetVo setVo = new IntegralSetVo();
        setVo.setIntegralRate(params.getInt("integralRate"));
        setVo.setOpenBackIntegral(params.getInt("openBackIntegral"));
        setVo.setNumForMoney(params.getInt("numForMoney"));
        setVo.setNumForIntegral(params.getInt("numForIntegral"));
        String dykId = AuthUser.getUser().getDykId();
        integralWealService.saveIntegralSet(setVo,dykId);
        return new JsonResult();
    }

    @RequestMapping(value = "delActive", method = RequestMethod.POST)
    public JsonResult delActive() {
        JSONObject params = getParams();
        int activeId = params.getInt("activeId");
        integralWealService.deleteActive(activeId);
        return new JsonResult();
    }

    @RequestMapping("toupordown")
    public JsonResult doUp_Down() {
        JSONObject params = getParams();
        Integer activeId = params.getInt("activeId");
        Integer state = params.getInt("state");
        integralWealService.updateState(activeId, AuthUser.getUser().getDykId(), state);
        return new JsonResult();
    }


    private static void doImportOrder(List<IntegralOrderVo> orderList, OutputStream outputStream){
        //写入临时文件
        //表头
        String[] headers = new String[]{
                "记录编号",
                "购物订单号",
                "商品名称",
                "微信昵称",
                "购物账户",
                "兑换时间",
                "商品价格(分)",
                "消耗积分",
                "支付现金(分)",
                "返现金额(分)",
                "状态"
        };
        String[] content = new String[11];
        try {
            CsvWriter csvWriter = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
            csvWriter.writeRecord(headers);
            for(IntegralOrderVo vo : orderList){
                content[0] = vo.getId() + "";
                content[1] = vo.getOrderno();
                content[2] = vo.getTitle();
                content[3] = vo.getWxfansname();
                content[4] = vo.getShopfansname();
                content[5] = DateUtil.formatDateTime(vo.getCreatetime());
                content[6] = vo.getPrice() + "";
                content[7] = vo.getIntegral() + "";
                content[8] = vo.getMoney() + "";
                content[9] = vo.getBackmoney() + "";
                content[10] = vo.getStateDisplay();

                csvWriter.writeRecord(content);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
