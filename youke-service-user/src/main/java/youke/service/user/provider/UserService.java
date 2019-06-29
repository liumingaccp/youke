package youke.service.user.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.TPosition;
import youke.common.model.TSubscrFans;
import youke.common.model.TYoukeBank;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.*;
import youke.common.utils.PhoneCheckUtil;
import youke.facade.cloudcode.provider.IVipKefuService;
import youke.facade.helper.provider.IHelperService;
import youke.facade.market.provider.IMarketActivityService;
import youke.facade.user.provider.IUserService;
import youke.facade.user.vo.*;
import youke.service.user.biz.IUserBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends Base implements IUserService {

    @Resource
    private IUserBiz userBiz;
    @Resource
    private IHelperService helperService;
    @Resource
    private IMarketActivityService marketActivityService;
    @Resource
    private IVipKefuService vipKefuService;

    private static String CODE_PRE = "mobile";
    private static Long CODE_EXPIRE = 100L;

    public void updatePass(String mobile, String password) {
        if (notEmpty(mobile) && notEmpty(password)) {
            userBiz.updateUser(mobile, password);
        } else {
            throw new BusinessException("用户名或密码不能为空");
        }
    }

    public boolean checkUser(String mobile) {
        if (notEmpty(mobile)) {
            return userBiz.checkUser(mobile);
        } else {
            throw new BusinessException("用户名不能为空");
        }
    }

    public AdminAccountVo getAccount(String youkeId) {
        if (notEmpty(youkeId)) {
            return userBiz.getAccount(youkeId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public CurUserVo getCurrentUser(Integer userId) {
        if (notEmpty(userId)) {
            return userBiz.getCurrentUser(userId);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    @Override
    public int getfollowSubscrState(Integer userId) {
        return userBiz.getfollowSubscrState(userId);
    }

    public UserVo doLogin(String mobile, String password) {
        if (empty(mobile) && empty(password))
            throw new BusinessException("账户或密码不能为空");
        return userBiz.doLoginUser(mobile, password);
    }

    public CurUserVo register(String mobile, String password, String ip, String deviceName) {
        if (notEmpty(mobile) && notEmpty(password)) {
            return userBiz.saveRegister(mobile, password, ip, deviceName);
        } else {
            throw new BusinessException("账户或密码不能为空");
        }
    }

    public CurUserVo saveCompanyInfo(CompanyInfoVo companyInfo, Integer userId) {
        if (empty(companyInfo) && empty(userId))
            throw new BusinessException("无效的请求或参数");
        return userBiz.saveCompantInfo(companyInfo, userId);
    }

    public CurrentAccountVo getCurrentAccount(Integer userId) {
        if (empty(userId))
            throw new BusinessException("无效的请求或参数");
        return userBiz.getCurrentAccount(userId);
    }

    public SubConfigVo getsubscr(String appId) {
        return userBiz.getsubscr(appId);
    }

    @Override
    public UserVo getUserVo(int id) {
        return userBiz.getUserVo(id);
    }


    public void updateCompanyName(String dykId, String companyName) {
        if (empty(dykId) && empty(companyName))
            throw new BusinessException("无效的请求或参数");
        userBiz.updateCompanyName(dykId, companyName);
    }

    public void updateMobile(String mobile, String password, Integer userId) {
        if (empty(mobile) && empty(password) && empty(userId))
            throw new BusinessException("无效的请求或参数");
        userBiz.updateMobile(mobile, password, userId);
    }

    public void updateLinkman(String youkeId, String linkName) {
        if (empty(youkeId) && empty(linkName))
            throw new BusinessException("无效的请求或参数");
        userBiz.updateLinkman(youkeId, linkName);
    }

    public void updateQQ(String dykId, String qq) {
        if (empty(dykId) && empty(qq))
            throw new BusinessException("无效的请求或参数");
        userBiz.updateQQ(dykId, qq);
    }

    @Override
    public PageInfo<TYoukeBank> getBankList(AccountRecordQueryVo params, String dykId) {
        if (empty(params) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        params.setDykId(dykId);
        return userBiz.getBankList(params);
    }

    @Override
    public List<TYoukeBank> getRecordList(AccountRecordQueryVo params, String dykId) {
        if (empty(dykId))
            throw new BusinessException("无效的请求或参数");
        params.setDykId(dykId);
        return userBiz.getRecordList(params);
    }

    @Override
    public void savePayCert(PaymentSettingParamVo params, String appId) {
        if (empty(params) && empty(appId))
            throw new BusinessException("无效的请求或参数");
        params.setAppId(appId);
        userBiz.savepayCert(params);
    }

    @Override
    public Integer getIntegral(String openId) {
        return userBiz.getIntegral(openId);
    }

    @Override
    public SubFansInfo getMemInfo(String appId, String openId) {
        TSubscrFans info = userBiz.getsubInfo(openId);
        SubFansInfo user = new SubFansInfo();
        if (info != null) {
            if (info.getSubstate() == 0)
                user.setHasSubscr(1);
            if (info.getMobile() != null) {
                user.setHasMobile(1);
                String mobile = info.getMobile();
                if (mobile.length() == 11)
                    user.setMobile(mobile.replace(mobile.substring(3, 7), "****"));
            }
            if (info.getIntegral() != null) {
                user.setIntegral(info.getIntegral());
            }
        }
        List<String> helperActives = helperService.getHelperActives(appId);
        List<String> marketActives = marketActivityService.getMarketActives(appId);
        Map qcodeUrl = vipKefuService.getQcodeUrl(appId);
        if (qcodeUrl != null) {
            user.getIcons().add("KeFu");
        }
        if (helperActives != null && helperActives.size() > 0) {
            user.getIcons().addAll(helperActives);
        }
        if (marketActives != null && marketActives.size() > 0) {
            user.getIcons().addAll(marketActives);
        }

        return user;
    }


    @Override
    public void saveMobile(String openId, String mobile, String appId) {
        userBiz.saveMobile(openId, mobile, appId);
    }

    @Override
    public PageInfo<SubFansIntegralDetailVo> getIntegralDetail(IntegralDetailQueryVo params) {
        if (params.getOpenId() == null) {
            throw new BusinessException("参数异常");
        }
        List<SubFansIntegralDetailVo> list = userBiz.getIntegralDetail(params);
        return new PageInfo<>(list);
    }

    @Override
    public int getAccmoney(String dykId) {
        return userBiz.getAccmoney(dykId);
    }

    @Override
    public void saveApplyAgencyInfo(String name, String province, String city, String mobile) {
        if (empty(name) && empty(province) && empty(city) && empty(mobile)) {
            throw new BusinessException("请完善您的信息");
        }
        if (!PhoneCheckUtil.isPhoneSimple(mobile)) {
            throw new BusinessException("请输入正确的手机号码");
        }
        if (name.length() > 20) {
            throw new BusinessException("请输入正确的姓名");
        }
        userBiz.saveApplyAgencyInfo(name, province, city, mobile);
    }

    @Override
    public void addSubAccount(SubAccountSaveVo params, String dykId, Integer userId) {
        if (empty(params.getId())) {
            throw new BusinessException("无效的请求或参数");
        }
        userBiz.addSubAccount(params, dykId,userId);
    }

    @Override
    public SubAccountAuditRetVo auditSubAccount(Integer id, String dykId) {
        if (empty(id) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        return userBiz.auditSubAccount(id, dykId);
    }

    @Override
    public PageInfo<SubAccountListRetVo> getSubAccountList(QueryObjectVO params, String dykId) {
        if (empty(dykId) && empty(params)) {
            throw new BusinessException("无效的请求或参数");
        }
        return userBiz.selectSubAccountList(params, dykId);
    }

    @Override
    public PageInfo<SubAccountLoginRecordRetVo> getRegistrationRecord(SubAccountLoginRecordQueryVo params, String dykId) {
        if (empty(params) && empty(dykId)) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        return userBiz.getRegistrationRecord(params);
    }

    @Override
    public void doFreezeSubAccount(Integer id) {
        if (empty(id)) {
            throw new BusinessException("请选择要冻结的子账户");
        } else {
            userBiz.doFreezeSubAccount(id);
        }
    }

    @Override
    public void unfreezeSubAccount(Integer id) {
        if (empty(id)) {
            throw new BusinessException("请选择要解冻的子账号");
        } else {
            userBiz.doUnfreezeSubAccount(id);
        }
    }

    @Override
    public void deleteSubAccount(Integer id) {
        if (empty(id)) {
            throw new BusinessException("请选择要删除的子账户");
        } else {
            userBiz.deleteSubAccount(id);
        }
    }

    @Override
    public void addLoginHistory(String ip, String mobile, String youkeId) {
        userBiz.addLoginHistory(ip, mobile, youkeId);
    }

    @Override
    public int getRemainingAmount(String dykId) {
        return userBiz.getRemainingAmount(dykId);
    }

    @Override
    public String getYoukeIdByAppId(String appId) {
        return userBiz.getYoukeIdByAppId(appId);
    }

    @Override
    public PageInfo<PositionVo> getPositionList(QueryObjectVO params, String dykId) {
        if (empty(dykId) || empty(params)) {
            throw new BusinessException("无效的请求或参数");
        }
        return userBiz.getPositionList(params.getPage(),params.getLimit(), dykId);
    }

    @Override
    public PositionVo getPosition(int id, String dykId) {
        return userBiz.getPosition(id,dykId);
    }

    @Override
    public List<TPosition> getPositionNames(String dykId) {
        return userBiz.getPositionNames(dykId);
    }

    @Override
    public void deletePosition(int id, String dykId) {
        userBiz.deletePosition(id,dykId);
    }

    @Override
    public void savePosition(int id, String title, String funcs, String dykId) {
        userBiz.savePosition(id,title,funcs,dykId);
    }

    @Override
    public List<VipFuncVo> getVipFuncs(int vip) {
        return userBiz.getFuncsByVip(vip);
    }

    @Override
    public List<String> getUserFuncs(int userId) {
        return userBiz.getUserFuncs(userId);
    }

}
