package youke.web.pc.action;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.model.TPosition;
import youke.common.model.TYoukeBank;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.*;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.*;
import youke.facade.mass.provider.IMobmsgService;
import youke.facade.pay.provider.IPayServcie;
import youke.facade.user.provider.IUserService;
import youke.facade.user.vo.*;
import youke.facade.wx.provider.IWeixinService;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.SceneType;
import youke.web.pc.interceptor.AuthUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("member")
public class MemberAction extends BaseAction {

    @Resource
    private IUserService userService;
    @Resource
    private IMobmsgService mobmsgService;
    @Resource
    private IWeixinService weixinService;
    @Resource
    private IPayServcie payServcie;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonResult login(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        JSONObject obj = getParams();
        String mobile = obj.getString("mobile");
        String password = obj.getString("password");
        String deviceId = obj.getString("deviceId");
        String ip = IPv4Util.getIpAddress(request);
        UserVo userVo = userService.doLogin(mobile, password);
        String token = AuthUser.setUser(userVo);
        tokenManage(deviceId, token);
        userService.addLoginHistory(ip, mobile, userVo.getDykId());
        CurUserVo currentUser = userService.getCurrentUser(userVo.getUserId());
        Map resultMap = new HashMap();
        try {
            BeanUtil.bean2Map(currentUser,resultMap);
            resultMap.put("funcs",userService.getUserFuncs(currentUser.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setMessage(token);
        result.setData(resultMap);
        return result;
    }


    /**
     * 微信客户端登录
     *
     * @return
     */
    @RequestMapping(value = "loginPC", method = RequestMethod.POST)
    public JsonResult loginPC(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        JSONObject objs = getParams();
        String ip = IPv4Util.getIpAddress(request);
        String mobile = objs.getString("mobile");
        String password = objs.getString("password");
        UserVo userVo = userService.doLogin(mobile, password);
        if (userVo.getRole() == 0) {
            result.setData(null);
            result.setMessage("只支持子账户登录客户端");
            result.setCode(ApiCodeConstant.COMMON_ERROR_BIZ);
            return result;
        }else{ //判单是否有登录权限
            List<String> funcs = userService.getUserFuncs(userVo.getUserId());
            if(!funcs.contains("TS_GRH_DZHZNYKF")){
                result.setData(null);
                result.setMessage("该账户没有客户端登录权限");
                result.setCode(ApiCodeConstant.COMMON_ERROR_BIZ);
                return result;
            }
        }
        CurUserVo currentUser = userService.getCurrentUser(userVo.getUserId());
        result.setMessage("成功");
        Map map = new HashMap();
        map.put("userId", currentUser.getId());
        map.put("username", currentUser.getUsername());
        map.put("truename", currentUser.getTruename());
        map.put("youkeId", currentUser.getDykId());
        map.put("appId", currentUser.getAppId());
        map.put("company", currentUser.getCompany());
        map.put("vip", currentUser.getVip());
        map.put("limitNum", ApiCodeConstant.OPENWEIXIN_NUM.get(currentUser.getVip()));
        map.put("dykEndTime", DateUtil.formatDate(currentUser.getDykEndTime(), "yyyy-MM-dd"));
        if (currentUser.getDykState() == 1 && currentUser.getVip() > 0) {
            String token = AuthUser.setUser(userVo);
            loginManage(token, ip, request.getRemoteHost(), userVo.getUserId());
            map.put("access_token", token);
            result.setData(map);
            result.setCode(ApiCodeConstant.COMMON_SUCCESS);
        } else if (currentUser.getDykState() == 0) {
            result.setData(null);
            result.setMessage("账户尚未开通,请联系客服");
            result.setCode(ApiCodeConstant.COMMON_ERROR_BIZ);
        } else if (currentUser.getDykState() == 2) {
            result.setData(null);
            result.setMessage("账户已欠费,请登录官网续费");
            result.setCode(ApiCodeConstant.COMMON_ERROR_BIZ);
        } else {
            result.setData(null);
            result.setMessage("账户尚未开通,请联系客服");
            result.setCode(ApiCodeConstant.COMMON_ERROR_BIZ);
        }
        return result;
    }


    /**
     * 验证token
     *
     * @return
     */
    @RequestMapping(value = "tokenvalidate")
    public JsonResult tokenValidate(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        Map<String, Object> map = new HashMap<>();
        String access_token = request.getParameter("access_token");
        String msg = "";
        int expire;
        if (notEmpty(access_token)) {
            if (RedisUtil.hasKey(access_token)) {
                expire = 0;
            } else {
                Integer userId = (Integer) RedisUtil.hget("TOKEN_HISTORY", access_token);
                String currentToken = (String) RedisUtil.hget("TOKEN_MAP", userId + "");
                if (notEmpty(currentToken) && notEmpty(userId)) {
                    if (!access_token.equals(currentToken)) {
                        LoginHistoryVo history = (LoginHistoryVo) RedisUtil.hget("LOGIN_HISTORY", userId + "");
                        if (history != null) {
                            msg = "当前账户于 " + history.getLoginTime() + " 在设备:" + history.getHostName() + " IP: " + history.getIp() + " 处登录，如非本人操作，请及时修改账户密码！";
                        } else {
                            msg = "登录过期，请重新登录";
                        }
                    }
                } else {
                    msg = "登录过期，请重新登录";
                }
                expire = 1;
            }
        } else {
            expire = 1;
            msg = "登录过期，请重新登录";
        }
        map.put("msg", msg);
        map.put("expire", expire);
        result.setData(map);
        return result;
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public JsonResult logout() {
        AuthUser.removeUser();
        return new JsonResult();
    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JsonResult register(HttpServletRequest request) {
        JSONObject objs = getParams();
        String mobile = objs.getString("mobile");
        String code = objs.getString("code");
        String password = objs.getString("password");
        String invitecode = "";
        if(objs.has("invitecode")){
            invitecode = objs.getString("invitecode");
        }
        if(notEmpty(invitecode)){
            try {
                if (HttpConnUtil.doHttpRequest("https://pcapi2.dianyk.com/spread/common/checkspread?id=" + invitecode).equals("0"))
                    return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "邀请码有误,请检查");
            }catch (Exception e){
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "邀请码有误,请检查");
            }
        }
        JsonResult result = new JsonResult();
        String ip = IPv4Util.getIpAddress(request);
        String agent = request.getHeader("User-Agent");
        String device = MD5Util.MD5(agent);
        boolean success = mobmsgService.checkMobCode(mobile, code);
        if (success) {
            CurUserVo currentUser = userService.register(mobile, password, ip, device);
            result.setData(currentUser);
            UserVo user = new UserVo();
            user.setDykId(currentUser.getDykId());
            user.setMobile(currentUser.getUsername());
            user.setUserId(currentUser.getId());
            user.setVip(0);
            String token = AuthUser.setUser(user);
            result.setMessage(token);
            if(notEmpty(invitecode)){
                try {
                    HttpConnUtil.doHttpRequest("https://pcapi2.dianyk.com/spread/common/registerspread?id=" + invitecode + "&userid=" + user.getUserId());
                }catch (Exception e){}
            }
        } else {
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_BIZ, "验证码过期或有误");
        }
        return result;
    }

    /**
     * 获取带参临时二维码
     *
     * @param request
     * @retu
     */
    @RequestMapping(value = "getqrcode", method = RequestMethod.POST)
    public JsonResult getQrCode(HttpServletRequest request) {
        String mobile = AuthUser.getUser().getMobile();
        String codeUrl = weixinService.getQrcodeTmp(Constants.APPID, SceneType.MOBILE, mobile, 3600);
        return new JsonResult(getBasePath(request) + "common/qrcode?d=" + codeUrl);
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "updatepwd", method = RequestMethod.POST)
    public JsonResult updatepwd() {
        JsonResult result = new JsonResult();
        JSONObject objs = getParams();
        String mobile = objs.getString("mobile");
        String code = objs.getString("code");
        String password = objs.getString("password");
        //校验验证码
        boolean success = mobmsgService.checkMobCode(mobile, code);
        if (success) {
            userService.updatePass(mobile, password);
        } else {
            throw new BusinessException("验证码过期或有误");
        }
        return result;
    }

    /**
     * 店有客账户信息获取
     *
     * @return
     */
    @RequestMapping(value = "getdykinfo", method = RequestMethod.POST)
    public JsonResult getAccount() {
        JsonResult result = new JsonResult();
        UserVo user = AuthUser.getUser();
        String youkeId = user.getDykId();
        AdminAccountVo admin = userService.getAccount(youkeId);
        result.setData(admin);
        return result;
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    @RequestMapping(value = "getcuruser", method = RequestMethod.POST)
    public JsonResult getCurrentUser() {
        UserVo user = AuthUser.getUser();
        UserVo userVo = userService.getUserVo(AuthUser.getUser().getUserId());
        AuthUser.saveUser(userVo);
        JsonResult result = new JsonResult();
        CurUserVo currentUser = userService.getCurrentUser(user.getUserId());
        Map resultMap = new HashMap();
        try {
            BeanUtil.bean2Map(currentUser,resultMap);
            resultMap.put("funcs",userService.getUserFuncs(currentUser.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(resultMap);
        return result;
    }

    /**
     * 企业信息保存同时返回当前用户的信息
     *
     * @return
     */
    @RequestMapping(value = "savecompany", method = RequestMethod.POST)
    public JsonResult saveCompany() {
        CompanyInfoVo companyInfo = getParams(CompanyInfoVo.class);
        JsonResult result = new JsonResult();
        Integer userId = AuthUser.getUser().getUserId();
        CurUserVo currentUser = userService.saveCompanyInfo(companyInfo, userId);
        result.setData(currentUser);
        return result;
    }

    /**
     * 获取当前登录账户信息
     *
     * @return
     */
    @RequestMapping(value = "getaccountinfo", method = RequestMethod.POST)
    public JsonResult getAccountInfo() {
        JsonResult result = new JsonResult();
        Integer userId = AuthUser.getUser().getUserId();
        CurrentAccountVo account = userService.getCurrentAccount(userId);
        result.setData(account);
        return result;
    }

    /**
     * 获取当前登录账户信息
     *
     * @return
     */
    @RequestMapping(value = "getsubscr", method = RequestMethod.POST)
    public JsonResult getSubscr() {
        UserVo userVo = userService.getUserVo(AuthUser.getUser().getUserId());
        AuthUser.saveUser(userVo);
        SubConfigVo config = userService.getsubscr(userVo.getAppId());
        return new JsonResult(config);
    }


    @RequestMapping(value = "getfollowSubscrState", method = RequestMethod.POST)
    public JsonResult getfollowSubscrState() {
        UserVo user = AuthUser.getUser();
        return new JsonResult(userService.getfollowSubscrState(user.getUserId()));
    }

    /**
     * 修改公司名称
     *
     * @return
     */
    @RequestMapping(value = "updateCompanyName", method = RequestMethod.POST)
    public JsonResult updateCompanyName() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        userService.updateCompanyName(user.getDykId(), params.getString("companyName"));
        return result;
    }


    /**
     * 修改手机号
     *
     * @return
     */
    @RequestMapping(value = "updateMobile", method = RequestMethod.POST)
    public JsonResult updateMobile() {
        JsonResult result = new JsonResult();
        Integer userId = AuthUser.getUser().getUserId();
        JSONObject params = getParams();
        String password = params.getString("password");
        String mobile = params.getString("mobile");
        String code = params.getString("code");
        boolean success = mobmsgService.checkMobCode(mobile, code);
        if (success) {
            userService.updateMobile(mobile, password, userId);
        } else {
            throw new BusinessException("验证码过期或有误");
        }
        return result;
    }

    /**
     * 修改联系人
     *
     * @return
     */
    @RequestMapping(value = "updateLinkman", method = RequestMethod.POST)
    public JsonResult updateLinkman() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        userService.updateLinkman(user.getDykId(), params.getString("linkName"));
        return result;
    }


    /**
     * 修改QQ
     *
     * @return
     */
    @RequestMapping(value = "updateQQ", method = RequestMethod.POST)
    public JsonResult updateQQ() {
        JsonResult result = new JsonResult();
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        userService.updateQQ(user.getDykId(), params.getString("QQ"));
        return result;
    }

    /**
     * 获取账户余额
     *
     * @return
     */
    @RequestMapping(value = "getaccmoney", method = RequestMethod.POST)
    public JsonResult getAccmoney() {
        UserVo user = AuthUser.getUser();
        int money = userService.getAccmoney(user.getDykId());
        return new JsonResult(money);
    }

    /**
     * 获取账户交易记录
     *
     * @return
     */
    @RequestMapping(value = "getbanklist", method = RequestMethod.POST)
    public JsonResult getBankList() {
        AccountRecordQueryVo params = getParams(AccountRecordQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<TYoukeBank> info = userService.getBankList(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 添加子账户
     *
     * @return
     */
    @RequestMapping(value = "addsubaccount", method = RequestMethod.POST)
    public JsonResult addSubccount() {
        SubAccountSaveVo params = getParams(SubAccountSaveVo.class);
        UserVo user = AuthUser.getUser();
        if (notEmpty(params.getMobile()) && notEmpty(params.getCode())) {
            boolean success = mobmsgService.checkMobCode(params.getMobile(), params.getCode());
            if (!success) {
                return new JsonResult(1001, "验证码错误");
            }
        }
        userService.addSubAccount(params, user.getDykId(),user.getUserId());
        return new JsonResult();
    }

    /**
     * 子账户编辑数据获取
     *
     * @return
     */
    @RequestMapping(value = "auditsubaccount", method = RequestMethod.POST)
    public JsonResult auditSubAccount() {
        JSONObject params = getParams();
        UserVo user = AuthUser.getUser();
        SubAccountAuditRetVo vo = userService.auditSubAccount(params.getInt("id"), user.getDykId());
        return new JsonResult(vo);
    }

    /**
     * 获取子账户列表
     *
     * @return
     */
    @RequestMapping(value = "getsubaccountlist", method = RequestMethod.POST)
    public JsonResult getSubAccountList() {
        UserVo user = AuthUser.getUser();
        QueryObjectVO params = getParams(QueryObjectVO.class);
        PageInfo<SubAccountListRetVo> info = userService.getSubAccountList(params, user.getDykId());
        return new JsonResult(info);
    }


    /**
     * 获取版本功能集合
     *
     * @return
     */
    @RequestMapping(value = "getvipfuncs", method = RequestMethod.POST)
    public JsonResult getVipFuncs() {
        UserVo user = AuthUser.getUser();
        List<VipFuncVo> result = userService.getVipFuncs(user.getVip());
        String resStr = JSONArray.fromObject(result).toString().replace("val","title").replace("childrens","children");
        return new JsonResult(JSONArray.fromObject(resStr));
    }

    /**
     * 获取职位列表
     *
     * @return
     */
    @RequestMapping(value = "getpositionlist", method = RequestMethod.POST)
    public JsonResult getPositionList() {
        UserVo user = AuthUser.getUser();
        QueryObjectVO params = getParams(QueryObjectVO.class);
        PageInfo<PositionVo> result = userService.getPositionList(params, user.getDykId());
        return new JsonResult(result);
    }

    /**
     * 获取职位名称列表
     *
     * @return
     */
    @RequestMapping(value = "getpositionnames", method = RequestMethod.POST)
    public JsonResult getPositionNames() {
        UserVo user = AuthUser.getUser();
        List<TPosition> result = userService.getPositionNames(user.getDykId());
        return new JsonResult(result);
    }

    /**
     * 删除职位
     *
     * @return
     */
    @RequestMapping(value = "deleteposition", method = RequestMethod.POST)
    public JsonResult deletePosition() {
        UserVo user = AuthUser.getUser();
        userService.deletePosition(getParams().getInt("id"),user.getDykId());
        return new JsonResult();
    }

    /**
     * 编辑职位
     *
     * @return
     */
    @RequestMapping(value = "saveposition", method = RequestMethod.POST)
    public JsonResult savePosition() {
        UserVo user = AuthUser.getUser();
        JSONObject params = getParams();
        int id = params.getInt("id");
        String title = params.getString("title");
        String funcs = params.getString("funcs");
        if(empty(title))
           return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"职位名称不能为空");
        if(empty(funcs))
            return new JsonResult(ApiCodeConstant.COMMON_ERROR_PARAM,"请选择职位功能");
        userService.savePosition(id,title,funcs,user.getDykId());
        return new JsonResult();
    }

    /**
     * 获取职位
     *
     * @return
     */
    @RequestMapping(value = "getposition", method = RequestMethod.POST)
    public JsonResult getPosition() {
        UserVo user = AuthUser.getUser();
        JSONObject params = getParams();
        int id = params.getInt("id");
        PositionVo positionVo = userService.getPosition(id,user.getDykId());
        return new JsonResult(positionVo);
    }

    /**
     * 获取子账号登录记录
     *
     * @return
     */
    @RequestMapping(value = "getregistrationrecord", method = RequestMethod.POST)
    public JsonResult getRegistrationRecord() {
        SubAccountLoginRecordQueryVo params = getParams(SubAccountLoginRecordQueryVo.class);
        UserVo user = AuthUser.getUser();
        PageInfo<SubAccountLoginRecordRetVo> info = userService.getRegistrationRecord(params, user.getDykId());
        return new JsonResult(info);
    }

    /**
     * 获取可添加子账户数量
     *
     * @return
     */
    @RequestMapping(value = "getremainingamount", method = RequestMethod.POST)
    public JsonResult getRemainingAmount() {
        UserVo user = AuthUser.getUser();
        int num = userService.getRemainingAmount(user.getDykId());
        return new JsonResult(num);
    }

    /**
     * 冻结子账户
     *
     * @return
     */
    @RequestMapping(value = "freezesubaccount", method = RequestMethod.POST)
    public JsonResult freezeSubAccount() {
        JSONObject params = getParams();
        userService.doFreezeSubAccount(params.getInt("id"));
        return new JsonResult();
    }

    /**
     * 解除子账号冻结
     *
     * @return
     */
    @RequestMapping(value = "unfreezesubaccount", method = RequestMethod.POST)
    public JsonResult unfreezeSubAccount() {
        JSONObject params = getParams();
        userService.unfreezeSubAccount(params.getInt("id"));
        return new JsonResult();
    }

    /**
     * 删除子账户
     *
     * @return
     */
    @RequestMapping(value = "deletesubaccount", method = RequestMethod.POST)
    public JsonResult deleteSubAccount() {
        JSONObject params = getParams();
        userService.deleteSubAccount(params.getInt("id"));
        return new JsonResult();
    }

    /**
     * 保存支付设置
     *
     * @return
     */
    @RequestMapping(value = "savepaycert", method = RequestMethod.POST)
    public JsonResult savePayCert() {
        PaymentSettingParamVo params = getParams(PaymentSettingParamVo.class);
        String errMsg = "";
        String oss_url;
        ZipFile zf = null;
        ZipEntry ze = null;
        InputStream in = null;
        ZipInputStream zin = null;
        UserVo user = AuthUser.getUser();
        if (notEmpty(params.getFilecert())) {
            String key = params.getFilecert().replaceAll(ApiCodeConstant.OSS_BASE, "");
            String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            try {
                if (FileUpOrDwUtil.isExist(key)) {
                    //创建临时文件
                    File file = File.createTempFile(prefix, ".zip");
                    //下载zip,存入temp/p12内
                    FileUpOrDwUtil.downloadFile(key, file);
                    //解压zip文件,获取p12证书,存入oss
                    in = new BufferedInputStream(new FileInputStream(file));
                    zf = new ZipFile(file, Charset.forName("UTF-8"));
                    zin = new ZipInputStream(in);
                    boolean flag = true;
                    while ((ze = zin.getNextEntry()) != null && flag) {
                        if ((ze.getName().endsWith(".p12"))) {
                            InputStream inputStream = zf.getInputStream(ze);
                            oss_url = FileUpOrDwUtil.uploadNetStream(inputStream, "p12/" + prefix + ".p12");
                            params.setFilecert(oss_url);
                            flag = false;
                            inputStream.close();
                        }
                    }
                    if (flag) {
                        errMsg = "未找到您的证书,请上传正确的证书文件";
                    }
                } else {
                    errMsg = "文件丢失,请重新上传您的zip文件";
                }
            } catch (IOException | IllegalArgumentException e) {
                errMsg = "zip文件解压失败,请确定您上传的zip文件中含有.p12文件,或者删除zip文件中的证书使用说明后重试!";
            } finally {
                if (zin != null) {
                    try {
                        zin.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (zf != null) {
                    try {
                        zf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            FileUpOrDwUtil.deleteFile(key);
            if (!"".equals(errMsg)) {
                return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS, errMsg);
            }
        }
        String valid = payServcie.doValidSSL(params.getPayNumber(), params.getPaycert(), params.getFilecert());
        if (valid.equals("success")) {
            userService.savePayCert(params, user.getAppId());
            return new JsonResult();
        }
        return new JsonResult(ApiCodeConstant.COMMON_ERROR_SYS, valid);
    }

    /**
     * 账户交易记录导出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "exportbank", method = RequestMethod.POST)
    public JsonResult exportbank(HttpServletRequest request) {
        AccountRecordQueryVo params = getParams(AccountRecordQueryVo.class);
        List<TYoukeBank> recordList = userService.getRecordList(params, AuthUser.getUser().getDykId());
        List<Map<String, Object>> dataList = null;
        if (recordList.size() == 0) {
            throw new BusinessException("暂无数据导出");
        }
        String headers = "交易类型,交易金额(单位:元),账户余额(单位:元),交易时间";
        String mapKey = "classify,price,leftprice,createTime";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (TYoukeBank bank : recordList) {
            map = new HashMap<>();
            map.put("classify", bank.getClassify());
            map.put("price", MoneyUtil.fenToYuan(String.valueOf(bank.getPrice())));
            map.put("leftprice", MoneyUtil.fenToYuan(String.valueOf(bank.getLeftprice())));
            map.put("createTime", DateUtil.formatDate(bank.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
            dataList.add(map);
        }
        String realPath;
        String fileName = "";
        try {
            realPath = request.getSession().getServletContext().getRealPath("temp/account_record");
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
            File file = new File(realPath, fileName);
            ExportUtil.doExport(dataList, headers, mapKey, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(getBasePath(request) + "temp/account_record/" + fileName);
    }


    private void loginManage(String token, String ip, String hostName, Integer userId) {
        //单点登录
        if (RedisUtil.hasKey("TOKEN_MAP")) {
            String oldToken = (String) RedisUtil.hget("TOKEN_MAP", userId + "");
            if (oldToken != null && !"".equals(oldToken)) {
                RedisUtil.del(oldToken);//删除旧token
            }
        } else {
            Map<String, Object> token_map = new HashMap<>();
            RedisUtil.hmset("TOKEN_MAP", token_map);
        }
        RedisUtil.hset("TOKEN_MAP", userId + "", token, 86400);
        //记录登录历史
        LoginHistoryVo history = new LoginHistoryVo();
        Map<String, Object> map = new HashMap<>();
        history.setIp(ip);
        history.setHostName(hostName);
        history.setLoginTime(DateUtil.formatDateTime(new Date()));
        if (!RedisUtil.hasKey("LOGIN_HISTORY")) {
            RedisUtil.hmset("LOGIN_HISTORY", map);
        }
        RedisUtil.hset("LOGIN_HISTORY", userId + "", history, 86400);
        //记录token历史
        Map<String, Object> token_history = new HashMap<>();
        if (!RedisUtil.hasKey("TOKEN_HISTORY")) {
            RedisUtil.hmset("TOKEN_HISTORY", token_history);
        }
        RedisUtil.hset("TOKEN_HISTORY", token, userId, 86400);
    }

    private void tokenManage(String deviceId, String token) {
        if (RedisUtil.hasKey("DEVICEID_TOKEN_MAP")) {
            String oldToken = (String) RedisUtil.hget("DEVICEID_TOKEN_MAP", deviceId);
            if (oldToken != null && !"".equals(oldToken)) {
                RedisUtil.del(oldToken);
            }
        } else {
            Map<String, Object> token_map = new HashMap<>();
            RedisUtil.hmset("DEVICEID_TOKEN_MAP", token_map);
        }
        RedisUtil.hset("DEVICEID_TOKEN_MAP", deviceId, token, 86400);
    }
}
