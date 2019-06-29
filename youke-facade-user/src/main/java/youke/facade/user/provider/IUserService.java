package youke.facade.user.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.TPosition;
import youke.common.model.TYoukeBank;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.*;
import youke.facade.user.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/2
 * Time: 17:56
 */
public interface IUserService {
    /**
     * 更改密码
     *
     * @param mobile
     * @param password
     */
    void updatePass(String mobile, String password);

    /**
     * 是否存在对应用户
     *
     * @param mobile
     * @return
     */
    boolean checkUser(String mobile);

    /**
     * 获取系统管理员
     *
     * @param youkeId
     * @return
     */
    AdminAccountVo getAccount(String youkeId);

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    CurUserVo getCurrentUser(Integer userId);

    /**
     * 登录
     *
     * @param mobile
     * @param password
     * @return
     */
    UserVo doLogin(String mobile, String password);

    /**
     * 用户注册
     *
     * @param mobile
     * @param password
     */
    CurUserVo register(String mobile, String password, String ip, String deviceName);

    /**
     * 企业信息保存
     *
     * @param companyInfo
     * @return
     */
    CurUserVo saveCompanyInfo(CompanyInfoVo companyInfo, Integer userId);

    /**
     * 获取当前登录账户信息
     *
     * @param userId
     * @return
     */
    CurrentAccountVo getCurrentAccount(Integer userId);

    /**
     * 获取当前公众号信息
     *
     * @param appId
     * @return
     */
    SubConfigVo getsubscr(String appId);

    /**
     * @param id
     * @return
     */
    UserVo getUserVo(int id);

    /**
     * 公司名称更改
     *
     * @param dykId
     * @param companyName
     */
    void updateCompanyName(String dykId, String companyName);

    /**
     * 修改登录手机号
     *
     * @param mobile
     * @param password
     */
    void updateMobile(String mobile, String password, Integer userId);

    /**
     * 修改联系人
     *
     * @param youkeId
     * @param linkName
     */
    void updateLinkman(String youkeId, String linkName);

    /**
     * 修改QQ
     *
     * @param dykId
     * @param qq
     */
    void updateQQ(String dykId, String qq);

    /**
     * 获取账户交易记录
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<TYoukeBank> getBankList(AccountRecordQueryVo params, String dykId);

    /**
     * 保存支付设置
     *
     * @param params
     * @param appId
     */
    void savePayCert(PaymentSettingParamVo params, String appId);

    /**
     * 获取用户分
     *
     * @param openId
     * @return
     */
    Integer getIntegral(String openId);

    /**
     * 获取会员数据
     *
     * @param openId
     * @return
     */
    SubFansInfo getMemInfo(String appId, String openId);

    /**
     * 绑定手机号码
     *
     * @param openId
     * @param mobile
     */
    void saveMobile(String openId, String mobile, String appId);

    /**
     * 获取个人积分明细
     *
     * @param params
     * @return
     */
    PageInfo<SubFansIntegralDetailVo> getIntegralDetail(IntegralDetailQueryVo params);


    int getAccmoney(String dykId);

    /**
     * 保存申请代理人信息
     *
     * @param name
     * @param province
     * @param city
     * @param mobile
     */
    void saveApplyAgencyInfo(String name, String province, String city, String mobile);

    /**
     * 账户交易记录导出
     *
     * @param params
     * @param dykId
     * @return
     */
    List<TYoukeBank> getRecordList(AccountRecordQueryVo params, String dykId);

    /**
     * 登录用户店有客关注状态
     *
     * @param userId
     * @return
     */
    int getfollowSubscrState(Integer userId);

    /**
     * 添加子账户
     *  @param params
     * @param dykId
     * @param userId
     */
    void addSubAccount(SubAccountSaveVo params, String dykId, Integer userId);

    /**
     * 子账户编辑数据获取
     *
     * @param id
     * @param dykId
     * @return
     */
    SubAccountAuditRetVo auditSubAccount(Integer id, String dykId);

    /**
     * 子账户列表获取
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<SubAccountListRetVo> getSubAccountList(QueryObjectVO params, String dykId);

    /**
     * 获取子账号登录记录
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<SubAccountLoginRecordRetVo> getRegistrationRecord(SubAccountLoginRecordQueryVo params, String dykId);

    /**
     * 冻结子账户
     *
     * @param id
     */
    void doFreezeSubAccount(Integer id);

    /**
     * 删除子账户
     *
     * @param id
     */
    void deleteSubAccount(Integer id);

    /**
     * 记录登录信息
     *
     * @param ip
     */
    void addLoginHistory(String ip, String mobile, String youkeId);

    /**
     * 解除子账号冻结
     *
     * @param id
     */
    void unfreezeSubAccount(Integer id);

    /**
     * 获取可添加子账户数量
     *
     * @param dykId
     * @return
     */
    int getRemainingAmount(String dykId);

    String getYoukeIdByAppId(String appId);

    PageInfo<PositionVo> getPositionList(QueryObjectVO params, String dykId);

    List<TPosition> getPositionNames(String dykId);

    void deletePosition(int id, String dykId);

    void savePosition(int id, String title, String funcs, String dykId);

    List<VipFuncVo> getVipFuncs(int vip);

    List<String> getUserFuncs(int userId);

    PositionVo getPosition(int id, String dykId);
}
