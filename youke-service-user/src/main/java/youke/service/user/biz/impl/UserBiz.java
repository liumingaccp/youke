package youke.service.user.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.bean.JsonResult;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.*;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.*;
import youke.facade.user.vo.SubAccountAuditRetVo;
import youke.facade.user.vo.SubAccountSaveVo;
import youke.facade.user.vo.SubConfigVo;
import youke.facade.user.vo.UserVo;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.service.user.biz.IUserBiz;
import youke.service.user.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserBiz extends Base implements IUserBiz {

    @Resource
    private IUserDao userDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IYoukeBankDao youkeBankDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private ISubscrFansIntegralDao subscrFansIntegralDao;
    @Resource
    private IAdminAgencyDao adminAgencyDao;
    @Resource
    private QueueSender queueSender;
    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private IPositionDao positionDao;
    @Resource
    private IFuncDao funcDao;
    @Resource
    private IPositionFuncDao positionFuncDao;
    @Resource
    private IUserPositionDao userPositionDao;
    @Resource
    private ILoginHistoryDao loginHistoryDao;
    @Resource
    private IWeixinMessageService weixinMessageService;


    public TUser getUser(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    public PageInfo<TUser> getUsers(int page) {
        PageHelper.startPage(page, PAGESIZE);
        List<TUser> users = userDao.selectAll();
        return new PageInfo<>(users);
    }

    public void updateUser(String mobile, String password) {
        Integer userId = userDao.selectIdByMobile(mobile);
        if (empty(userId)) {
            throw new BusinessException("手机号码未注册。");
        }

        int count = userDao.updatePassByMobile(mobile, password);

        if (count <= 0) {
            throw new BusinessException("密码更新失败。");
        }
    }

    public boolean checkUser(String mobile) {
        String check = userDao.selectMobile(mobile);
        if (null != check && check.equals(mobile)) {
            return true;
        }
        return false;
    }

    public AdminAccountVo getAccount(String youkeId) {
        return userDao.getAccount(youkeId);
    }

    public CurUserVo getCurrentUser(Integer userId) {
        CurUserVo userVo = userDao.getCurrentUser(userId);
        if (userVo != null)
            userVo.setBindShopNum(shopDao.selectBindShopNum(userVo.getDykId()));
        return userVo;
    }

    @Override
    public int getfollowSubscrState(Integer userId) {
        return userDao.selectByPrimaryKey(userId).getFollowsubscr();
    }

    public UserVo doLoginUser(String mobile, String password) {
        TUser tUser = userDao.selectUserByLogin(mobile, password);
        if (empty(tUser))
            throw new BusinessException("账户或密码错误");
        if (tUser.getState() == 1) {
            throw new BusinessException("该账户已被冻结");
        }
        TYouke tYouke = youkeDao.selectByPrimaryKey(tUser.getYoukeid());
        //判断youke账户状态
        if (tYouke == null) {
            throw new BusinessException("账户异常，已经停用");
        }
        if (tYouke.getState() == 1) {
            String appId = subscrDao.selectLoginAppId(tUser.getYoukeid());
            UserVo userVo = new UserVo();
            userVo.setDykId(tUser.getYoukeid());
            userVo.setUserId(tUser.getId());
            userVo.setMobile(tUser.getMobile());
            userVo.setVip(tYouke.getVip());
            userVo.setAppId(appId);
            userVo.setRole(tUser.getRole());
            return userVo;
        }
        if (tYouke.getState() == 0)
            throw new BusinessException("该账户正在审核中");
        throw new BusinessException("该账户已欠费停用");
    }

    public CurUserVo saveRegister(String mobile, String password, String ip, String deviceName) {
        String isExist = userDao.selectMobile(mobile);
        if (empty(isExist)) {
            TYouke youke = new TYouke();
            youke.setId(IDUtil.getDykId());
            youke.setState(0);
            youke.setVip(0);
            youke.setLinktel(mobile);
            youke.setCreatetime(new Date());
            youkeDao.insertSelective(youke);
            TUser user = new TUser();
            user.setMobile(mobile);
            user.setPassword(MD5Util.MD5(password));
            user.setRole(0);
            user.setFollowsubscr(0);
            user.setState(0);
            user.setLoginip(ip);
            user.setLogindevice(deviceName);
            user.setCreatetime(new Date());
            user.setYoukeid(youke.getId());
            userDao.insertSelective(user);
            CurUserVo currentUser = new CurUserVo();
            currentUser.setId(user.getId());
            currentUser.setUsername(mobile);
            currentUser.setDykId(youke.getId());
            currentUser.setFollowSubscr(0);
            currentUser.setDykState(0);
            currentUser.setVip(0);
            currentUser.setBindShopNum(0);
            TMobcode mobcode = new TMobcode();
            mobcode.setYoukeid(youke.getId());
            mobcode.setCount(100);
            mobcode.setIcecount(0);
            mobcodeDao.insertSelective(mobcode);
            doPushRegisterTemp(mobile);
            return currentUser;
        } else {
            throw new BusinessException("该账号已被注册。");
        }
    }

    private void doPushRegisterTemp(String mobile){
        Date now = new Date();
        try {
            Map<String,String> map = new HashMap<>();
            map.put("first","您好，有新用户在官网注册店有客啦！");
            map.put("keyword1",mobile);
            map.put("keyword2", DateUtil.formatDate(now,"yyyy-MM-dd HH:mm"));
            map.put("remark","请登录业务后台跟进处理。");
            weixinMessageService.sendUserJoinPush(map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public CurUserVo saveCompantInfo(CompanyInfoVo companyInfo, Integer userId) {
        TYouke youke = youkeDao.selectByPrimaryKey(companyInfo.getDykId());
        if (notEmpty(youke)) {
            youke.setShopurl(companyInfo.getShopUrl());
            youke.setCompany(companyInfo.getCompany());
            youke.setLinktruename(companyInfo.getLinkTruename());
            youke.setLinkposition(companyInfo.getLinkPosition());
            youke.setLinkqq(companyInfo.getLinkQQ());
            youkeDao.updateByPrimaryKeySelective(youke);

            //保存联系人姓名
            TUser user = new TUser();
            user.setId(userId);
            user.setTruename(companyInfo.getLinkTruename());
            userDao.updateByPrimaryKeySelective(user);

            user = userDao.selectByPrimaryKey(userId);
            CurUserVo currentUser = new CurUserVo();
            currentUser.setId(userId);
            currentUser.setDykState(0);
            currentUser.setFollowSubscr(0);
            currentUser.setDykId(user.getYoukeid());
            currentUser.setUsername(user.getMobile());
            currentUser.setCompany(companyInfo.getCompany());
            return currentUser;
        } else {
            throw new BusinessException("账户不存在。");
        }
    }

    public CurrentAccountVo getCurrentAccount(Integer userId) {
        CurrentAccountVo currentAccount = userDao.getCurrentAccount(userId);
        if (empty(currentAccount))
            throw new BusinessException("账户不存在。");
        return currentAccount;

    }

    public SubConfigVo getsubscr(String appId) {
        TSubscr subscr = subscrDao.selectByPrimaryKey(appId);
        TSubscrConfig config = subscrConfigDao.selectByPrimaryKey(appId);
        if (subscr != null && config != null) {
            SubConfigVo vo = new SubConfigVo();
            vo.setHeadImg(subscr.getHeadimg());
            vo.setNickName(subscr.getNickname());
            vo.setQrcodeUrl(subscr.getQrcodeurl());
            vo.setServiceType(subscr.getServicetype());
            vo.setPayBuss(config.getPaybuss());
            vo.setPayState(config.getPaystate());
            vo.setPaycert(config.getPaycert());
            vo.setPayNumber(config.getPaynumber());
            if (config.getValidtimebeg() != null)
                vo.setValidtimeBeg(DateUtil.formatDate(config.getValidtimebeg()));
            if (config.getValidtimeend() != null)
                vo.setValidtimeEnd(DateUtil.formatDate(config.getValidtimeend()));
            return vo;
        }
        return null;
    }

    @Override
    public UserVo getUserVo(int id) {
        TUser tUser = userDao.selectByPrimaryKey(id);
        TYouke tYouke = youkeDao.selectByPrimaryKey(tUser.getYoukeid());
        //todo 判断youke账户状态以及是否过期
        //if(tYouke.getState())
        String appId = subscrDao.selectLoginAppId(tUser.getYoukeid());
        UserVo userVo = new UserVo();
        userVo.setDykId(tUser.getYoukeid());
        userVo.setUserId(tUser.getId());
        userVo.setMobile(tUser.getMobile());
        userVo.setVip(tYouke.getVip());
        userVo.setAppId(appId);
        return userVo;
    }


    public void updateCompanyName(String dykId, String companyName) {
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        if (empty(youke))
            throw new BusinessException("账户信息出现错误，请联系客服处理。");
        youke.setCompany(companyName);
        youkeDao.updateByPrimaryKeySelective(youke);
    }

    public void updateMobile(String mobile, String password, Integer userId) {
        TUser user = userDao.selectByPrimaryKey(userId);
        if (empty(user))
            throw new BusinessException("账户信息出现错误，请联系客服处理。");
        if (!password.equals(user.getPassword()))
            throw new BusinessException("密码错误。");
        user.setMobile(mobile);
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateLinkman(String youkeId, String linkName) {
        TYouke youke = youkeDao.selectByPrimaryKey(youkeId);
        if (empty(youke))
            throw new BusinessException("账户信息出现错误，请联系客服处理。");
        youke.setLinktruename(linkName);
        youkeDao.updateByPrimaryKeySelective(youke);
    }

    @Override
    public void updateQQ(String dykId, String qq) {
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        if (empty(youke))
            throw new BusinessException("账户信息出现错误，请联系客服处理。");
        if (!ValidatorUtil.isQQ(qq))
            throw new BusinessException("QQ格式错误。");
        youke.setLinkqq(qq);
        youkeDao.updateByPrimaryKeySelective(youke);
    }

    @Override
    public PageInfo<TYoukeBank> getBankList(AccountRecordQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<TYoukeBank> bankList = youkeBankDao.selectBankList(params);
        if (bankList.size() > 0) {
            PageModel<TYoukeBank> model = new PageModel<>((Page) bankList);
            for (TYoukeBank bank : bankList) {
                model.getResult().add(bank);
            }
            return new PageInfo<>(model);
        } else {
            return null;
        }
    }

    @Override
    public List<TYoukeBank> getRecordList(AccountRecordQueryVo params) {
        return youkeBankDao.selectBankList(params);
    }

    @Override
    public void savepayCert(PaymentSettingParamVo params) {
        TSubscrConfig config = subscrConfigDao.selectByPrimaryKey(params.getAppId());
        if (notEmpty(config)) {
            config.setPaynumber(params.getPayNumber());
            config.setPaycert(params.getPaycert());
            config.setValidtimebeg(DateUtil.parseDate(params.getValidtimeBeg()));
            config.setValidtimeend(DateUtil.parseDate(params.getValidtimeEnd()));
            config.setAppid(params.getAppId());
            config.setFilecert(params.getFilecert());
            config.setPaybuss(1);
            config.setPaystate(1);
            subscrConfigDao.updateByPrimaryKeySelective(config);
        }
    }

    @Override
    public Integer getIntegral(String openId) {
        Integer integral = subscrFansDao.getIntegral(openId);
        return integral == null ? 0 : integral;
    }

    @Override
    public TSubscrFans getsubInfo(String openId) {
        return subscrFansDao.selectByPrimaryKey(openId);
    }

    @Override
    public void saveMobile(String openId, String mobile, String appId) {
        int num = subscrFansDao.selectByMobile(mobile, appId);
        if (num > 0) {
            throw new BusinessException("该手机号已被使用。");
        }
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
        if (notEmpty(subscrFans)) {
            if (empty(subscrFans.getMobile())) {
                subscrFans.setMobile(mobile);
                subscrFans.setRegtime(new Date());
                subscrFansDao.updateByPrimaryKeySelective(subscrFans);
                //绑定手机号的时候发消息队列去查看是否有正在进行中的首绑有礼活动,有则参加活动并生成记录并发送通知
                queueSender.send("user.sbactive.queue", subscrFans.getOpenid());
            }
        }
    }

    @Override
    public List<SubFansIntegralDetailVo> getIntegralDetail(IntegralDetailQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "createTime desc");
        return subscrFansIntegralDao.queryListByOpenId(params.getOpenId());
    }

    @Override
    public int getAccmoney(String dykId) {
        return youkeDao.selectMoney(dykId);
    }

    @Override
    public void saveApplyAgencyInfo(String name, String province, String city, String mobile) {
        TAdminAgency adminAgency = adminAgencyDao.selectByMobile(mobile);
        if (notEmpty(adminAgency)) {
            throw new BusinessException("该手机号已被使用。");
        }
        TAdminAgency agency = new TAdminAgency();
        agency.setName(name);
        agency.setProvince(province);
        agency.setCity(city);
        agency.setMobile(mobile);
        agency.setCreatetime(new Date());
        adminAgencyDao.insertSelective(agency);
    }

    @Override
    public void addSubAccount(SubAccountSaveVo params, String dykId, Integer userId) {
        TUser user;
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        TUser auditer = userDao.selectByPrimaryKey(userId);
        if (params.getId() == -1) {
            Integer totalNum = ApiCodeConstant.SUBACCOUNT_NUM.get(youke.getVip());
            if(RedisSlaveUtil.hasKey("HEZUO-DIANYK-NUM")){
                if(RedisSlaveUtil.hHasKey("HEZUO-DIANYK-NUM",dykId)){
                    totalNum = (Integer) RedisSlaveUtil.hget("HEZUO-DIANYK-NUM",dykId);
                }
            }
            Integer usedNum = userDao.selectSubAccountNum(dykId);
            if (usedNum >= totalNum) {
                throw new BusinessException("子账户数量已达上限。");
            }
            user = userDao.selectUserByMobile(params.getMobile());
            if (notEmpty(user)) {
                throw new BusinessException("该手机号已被使用。");
            } else {
                user = new TUser();
                user.setCreatetime(new Date());
                user.setFollowsubscr(1);
                user.setYoukeid(dykId);
                user.setRole(1);
                user.setState(0);
            }
        } else {
            user = userDao.selectByPrimaryKey(params.getId());
            if (user.getRole() == 0) {
                throw new BusinessException("无法编辑主账户。");
            }
        }
        user.setMobile(params.getMobile());
        user.setTruename(params.getEmpName());
        if (notEmpty(params.getExpTime())) {//如果過期時間不為空
            user.setExptime(DateUtil.parseDate(params.getExpTime()));//设置过期时间
        } else {
            if (auditer.getRole() == 0) {//如果编辑人为主账号,则过期时间为空说明设置为永久.如果编辑人为子账号,子账户不能编辑过期时间,为空不做处理
                user.setExptime(null);
            }
        }
        if (notEmpty(params.getPassword())) {
            user.setPassword(MD5Util.MD5(params.getPassword()));
        }
        if (params.getId() == -1) {
            userDao.insertSelective(user);
            youkeDao.updateSubAccountNum(user.getYoukeid(), 1);
        } else {
            userDao.updateByPrimaryKey(user);
        }
        saveUserPosition(params.getPositionId(),user.getId());
    }


    @Override
    public SubAccountAuditRetVo auditSubAccount(Integer id, String dykId) {
        TUser user = userDao.selectByPrimaryKey(id);
        if (user != null) {
            SubAccountAuditRetVo vo = new SubAccountAuditRetVo();
            vo.setMobile(user.getMobile());
            vo.setEmpName(user.getTruename());
            vo.setPassword(user.getPassword());
            vo.setExpTime(user.getExptime());
            List<TPosition> positions = userPositionDao.selectByUserId(id);
            if(positions.size()>0)
               vo.setPositionId(positions.get(0).getId());
            return vo;
        } else {
            throw new BusinessException("该账户不存在。");
        }
    }

    @Override
    public PageInfo<SubAccountListRetVo> selectSubAccountList(QueryObjectVO params, String dykId) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<SubAccountListRetVo> list = userDao.selectSubAccountList(dykId);
        PageInfo<SubAccountListRetVo> pageList = new PageInfo<>(list);
        for(SubAccountListRetVo subAccount:list){
            List<TPosition> positions = userPositionDao.selectByUserId(subAccount.getId());
            if(positions.size()==0)
               subAccount.setPositionName("");
            else{
                subAccount.setPositionName(positions.get(0).getTitle());
            }
        }
        pageList.setList(list);
        return pageList;
    }

    @Override
    public PageInfo<SubAccountLoginRecordRetVo> getRegistrationRecord(SubAccountLoginRecordQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<SubAccountLoginRecordRetVo> list = loginHistoryDao.selectRegistrationRecordList(params);
        return new PageInfo<>(list);
    }

    @Override
    public void doFreezeSubAccount(Integer id) {
        TUser user = userDao.selectByPrimaryKey(id);
        if (notEmpty(user)) {
            if (user.getRole() == 0) {
                throw new BusinessException("无法冻结主账户。");
            } else {
                user.setState(1);
                userDao.updateByPrimaryKeySelective(user);
            }
        } else {
            throw new BusinessException("账户不存在。");
        }
    }

    @Override
    public void doUnfreezeSubAccount(Integer id) {
        TUser user = userDao.selectByPrimaryKey(id);
        if (notEmpty(user)) {
            if (user.getRole() == 0) {
                throw new BusinessException("无法操作主账户。");
            } else {
                if (user.getState() != 1) {
                    throw new BusinessException("只能解冻处于冻结状态中的账户。");
                } else {
                    user.setState(0);
                    userDao.updateByPrimaryKeySelective(user);
                }
            }
        } else {
            throw new BusinessException("账户不存在。");
        }
    }

    @Override
    public void deleteSubAccount(Integer id) {
        TUser user = userDao.selectByPrimaryKey(id);
        if (notEmpty(user)) {
            if (user.getRole() != 0) {
                userDao.deleteByPrimaryKey(id);
                youkeDao.updateSubAccountNum(user.getYoukeid(), -1);
            } else {
                throw new BusinessException("无法删除主账户。");
            }
        } else {
            throw new BusinessException("该账户不存在。");
        }
    }

    @Override
    public void addLoginHistory(String ip, String mobile, String youkeId) {
        TLoginHistory history = new TLoginHistory();
        String address;
        if ("127.0.0.1".equals(ip)) {
            address = "localhost";
        } else if (IPv4Util.innerIP(ip)) {
            address = "内网";
        } else {
            address = AddressUtils.getAddressByIp(ip);
        }

        history.setLoginip(ip);
        history.setMobile(mobile);
        history.setLogintime(new Date());
        history.setRegion(address);
        history.setYoukeid(youkeId);
        loginHistoryDao.insertSelective(history);
    }

    @Override
    public int getRemainingAmount(String dykId) {
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        Integer totalNum = ApiCodeConstant.SUBACCOUNT_NUM.get(youke.getVip());
        if(RedisSlaveUtil.hasKey("HEZUO-DIANYK-NUM")){
           if(RedisSlaveUtil.hHasKey("HEZUO-DIANYK-NUM",dykId)){
               totalNum = (Integer) RedisSlaveUtil.hget("HEZUO-DIANYK-NUM",dykId);
           }
        }
        Integer usedNum = userDao.selectSubAccountNum(dykId);
        return totalNum - usedNum;
    }

    @Override
    public String getYoukeIdByAppId(String appId) {
        return subscrDao.selectDyk(appId);
    }

    @Override
    public PositionVo getPosition(int id, String dykId) {
        TPosition position = positionDao.selectByPrimaryKey(id);
        PositionVo positionVo = new PositionVo();
        positionVo.setId(position.getId());
        positionVo.setTitle(position.getTitle());
        positionVo.setCreateTime(DateUtil.formatDateTime(position.getCreatetime()));
        positionVo.setFuncs(funcDao.selectPositionFuncs(position.getId()));
        return positionVo;
    }

    @Override
    public PageInfo<PositionVo> getPositionList(int page,int limit, String dykId) {
        PageHelper.startPage(page,limit);
        List<TPosition> list = positionDao.selectPositions(dykId);
        List<PositionVo> positionVos = new ArrayList<>();
        PageInfo<TPosition> pageList = new PageInfo<>(list);
        if(list.size()>0)
        {
            for (TPosition position:list){
                PositionVo positionVo = new PositionVo();
                positionVo.setId(position.getId());
                positionVo.setTitle(position.getTitle());
                positionVo.setCreateTime(DateUtil.formatDateTime(position.getCreatetime()));
                positionVo.setFuncs(funcDao.selectPositionFuncs(position.getId()));
                positionVos.add(positionVo);
            }
        }
        PageInfo<PositionVo> result = new PageInfo<>();
        result.setList(positionVos);
        result.setPageNum(pageList.getPageNum());
        result.setPages(pageList.getPages());
        result.setPageSize(pageList.getPageSize());
        result.setSize(pageList.getSize());
        result.setTotal(pageList.getTotal());
        return result;
    }

    @Override
    public List<TPosition> getPositionNames(String dykId) {
        return positionDao.selectPositions(dykId);
    }

    @Override
    public void deletePosition(int id, String dykId) {
        if(positionDao.selectPositionUserCount(id)>0){
            throw new BusinessException("该职位还有绑定的账户，请先解除绑定");
        }
        positionDao.deleteByPrimaryKey(id);
    }

    @Override
    public void savePosition(int id, String title, String funcs, String dykId) {
        TPosition position = new TPosition();
        position.setTitle(title);
        position.setYoukeid(dykId);
        position.setCreatetime(new Date());
        if(id>0){
            position.setId(id);
            positionDao.updateByPrimaryKeySelective(position);
        }else{
            positionDao.insertSelective(position);
        }
        positionFuncDao.deletePositionFuncs(position.getId());
        String[] funcArr = funcs.split(",");
        for (String func:funcArr){
            TPositionFunc positionFunc = new TPositionFunc();
            positionFunc.setFuncid(func);
            positionFunc.setPositionid(position.getId());
            positionFuncDao.insertSelective(positionFunc);
        }
    }

    @Override
    public void saveUserPosition(int positionId, int userId) {
        userPositionDao.deleteUserPosition(positionId,userId);
        TUserPosition userPosition = new TUserPosition();
        userPosition.setPositionid(positionId);
        userPosition.setUserid(userId);
        userPositionDao.insertSelective(userPosition);
    }

    @Override
    public List<VipFuncVo> getFuncsByVip(int vip) {
        List<TFunc> funcs  = funcDao.selectFuncsByVip("%"+vip+"%");
        List<VipFuncVo> vipFuncVos = new ArrayList<>();
        List<TFunc> nFuncs = doEachFuncs("", 1, funcs);
        for (TFunc nfunc:nFuncs) {
            VipFuncVo vipFuncVo = new VipFuncVo();
            vipFuncVo.setKey(nfunc.getId());
            vipFuncVo.setVal(nfunc.getTitle());
            List<TFunc> nFuncs2 = doEachFuncs(nfunc.getId()+"_", 2, funcs);
            List<VipFuncVo> vipFuncVos2 = new ArrayList<>();
            for(TFunc nFunc2:nFuncs2){
                VipFuncVo vipFuncVo2 = new VipFuncVo();
                vipFuncVo2.setKey(nFunc2.getId());
                vipFuncVo2.setVal(nFunc2.getTitle());
                List<TFunc> nFuncs3 = doEachFuncs(nFunc2.getId()+"_", 3, funcs);
                List<VipFuncVo> vipFuncVos3 = new ArrayList<>();
                for(TFunc nFunc3:nFuncs3) {
                    VipFuncVo vipFuncVo3 = new VipFuncVo();
                    vipFuncVo3.setKey(nFunc3.getId());
                    vipFuncVo3.setVal(nFunc3.getTitle());
                    List<TFunc> nFuncs4 = doEachFuncs(nFunc3.getId()+"_", 4, funcs);
                    List<VipFuncVo> vipFuncVos4 = new ArrayList<>();
                    for(TFunc nFunc4:nFuncs4) {
                        VipFuncVo vipFuncVo4 = new VipFuncVo();
                        vipFuncVo4.setKey(nFunc4.getId());
                        vipFuncVo4.setVal(nFunc4.getTitle());
                        List<VipFuncVo> vipFuncVos5 = new ArrayList<>();
                        vipFuncVo4.setChildrens(vipFuncVos5);
                        vipFuncVos4.add(vipFuncVo4);
                    }
                    vipFuncVo3.setChildrens(vipFuncVos4);
                    vipFuncVos3.add(vipFuncVo3);
                }
                vipFuncVo2.setChildrens(vipFuncVos3);
                vipFuncVos2.add(vipFuncVo2);
            }
            vipFuncVo.setChildrens(vipFuncVos2);
            vipFuncVos.add(vipFuncVo);
        }
        return vipFuncVos;
    }

    private List<TFunc> doEachFuncs(String pre,int rank,List<TFunc> funcs){
        List<TFunc> newFuncs = new ArrayList<>();
        for (TFunc func:funcs){
            if(func.getId().startsWith(pre)&&func.getRank()==rank){
                newFuncs.add(func);
            }
        }
        return newFuncs;
    }

    @Override
    public List<String> getUserFuncs(int userId) {
        CurUserVo userVo = userDao.getCurrentUser(userId);
        List<KeyValVo> tfuncs = new ArrayList<>();
        List<String> funcs = new ArrayList<>();
        if(userVo.getRole()==0)
        {
            tfuncs = funcDao.selectFuncs("%"+userVo.getVip()+"%");

        }else{
            tfuncs = userPositionDao.selectFuncsByUserId(userId);
            if(tfuncs.size()==0){
                tfuncs = funcDao.selectDefaultFuncs("%"+userVo.getVip()+"%");
            }
        }
        for(KeyValVo tFunc:tfuncs){
            funcs.add(tFunc.getKey());
        }
        return funcs;
    }
}
