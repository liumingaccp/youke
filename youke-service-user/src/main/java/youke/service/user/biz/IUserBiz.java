package youke.service.user.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.TPosition;
import youke.common.model.TSubscrFans;
import youke.common.model.TUser;
import youke.common.model.TYoukeBank;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.*;
import youke.facade.user.vo.*;

import java.util.List;
import java.util.Map;

public interface IUserBiz {
    TUser getUser(Integer id);

    /**
     * 获取所有用户
     *
     * @param page
     * @return
     */
    PageInfo<TUser> getUsers(int page);

    /**
     * 更新密码
     *
     * @param mobile
     * @param password
     */
    void updateUser(String mobile, String password);

    /**
     * 检查用户是否存在
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
     * 用户登录
     *
     * @param mobile
     * @param password
     * @return
     */
    UserVo doLoginUser(String mobile, String password);

    /**
     * 用户注册
     *
     * @param mobile
     * @param password
     */
    CurUserVo saveRegister(String mobile, String password, String ip, String deviceName);

    /**
     * 企业信息保存
     *
     * @return
     * @paracompanyInfo
     */
    CurUserVo saveCompantInfo(CompanyInfoVo companyInfo, Integer userId);

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

    UserVo getUserVo(int id);

    /**
     * 修改公司名称
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
     * @param userId
     */
    void updateMobile(String mobile, String password, Integer userId);

    /**
     * 修改联系人姓名
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
     * @return
     */
    PageInfo<TYoukeBank> getBankList(AccountRecordQueryVo params);

    /**
     * 保存支付设置
     *
     * @param params
     */
    void savepayCert(PaymentSettingParamVo params);

    /**
     * 获取H5端当前用户的积分
     *
     * @param openId
     * @return
     */
    Integer getIntegral(String openId);


    TSubscrFans getsubInfo(String openId);

    void saveMobile(String openId, String mobile, String appId);

    List<SubFansIntegralDetailVo> getIntegralDetail(IntegralDetailQueryVo params);

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
     * @return
     */
    List<TYoukeBank> getRecordList(AccountRecordQueryVo params);

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
     * 子账户获取列表
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<SubAccountListRetVo> selectSubAccountList(QueryObjectVO params, String dykId);

    /**
     * 获取子账户登录列表
     *
     * @param params
     * @return
     */
    PageInfo<SubAccountLoginRecordRetVo> getRegistrationRecord(SubAccountLoginRecordQueryVo params);

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
     * 添加登录信息
     *
     * @param youkeId
     * @param mobile
     * @param ip
     */
    void addLoginHistory(String ip, String mobile, String youkeId);

    /**
     * 解除子账号冻结
     *
     * @param id
     */
    void doUnfreezeSubAccount(Integer id);

    /**
     * 获取可添加子账户数量
     *
     * @param dykId
     * @return
     */
    int getRemainingAmount(String dykId);

    /**
     * 获取YoukeId
     * @param appId
     * @return
     */
    String getYoukeIdByAppId(String appId);

    PageInfo<PositionVo> getPositionList(int page,int limit, String dykId);

    PositionVo getPosition(int id, String dykId);

    List<TPosition> getPositionNames(String dykId);

    void deletePosition(int id, String dykId);

    void savePosition(int id, String title, String funcs, String dykId);

    void saveUserPosition(int positionId,int userId);

    List<VipFuncVo> getFuncsByVip(int vip);

    List<String> getUserFuncs(int userId);

}
