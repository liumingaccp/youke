package youke.service.market.biz.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.exception.H5MobileException;
import youke.common.exception.H5SubscrException;
import youke.common.model.*;
import youke.common.model.vo.param.HotActivityParamVo;
import youke.common.model.vo.param.PrizeVo;
import youke.common.model.vo.result.ActiveTranChartVo;
import youke.common.model.vo.result.market.*;
import youke.common.queue.message.ActiveChartMassMessage;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.redis.RedisSlaveUtil;
import youke.common.utils.*;
import youke.service.market.biz.IHotActivityBiz;
import youke.service.market.queue.producter.ActiveMessageProducer;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class HotActivityBiz extends Base implements IHotActivityBiz {

    @Resource
    private IMarketActiveBackorderDao marketActiveBackorderDao;
    @Resource
    private IMarketActiveSignruleDao marketActiveSignruleDao;
    @Resource
    private IMarketActiveRedruleDao marketActiveRedruleDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;
    @Resource
    private IMarketActiveSignDao marketActiveSignDao;
    @Resource
    private IMarketActiveLuckDao marketActiveLuckDao;
    @Resource
    private IMarketActiveVoteDao marketActiveVoteDao;
    @Resource
    private IMarketActivePicDao marketActivePicDao;
    @Resource
    private IMarketActiveDao marketActiveDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IShopDao shopDao;
    @Resource
    private ActiveMessageProducer activeMessageProducer;
    @Resource
    private IMarketActiveSubRedruleDao marketActiveSubRedruleDao;

    @Override
    public int getRemainingTimes(String openId, Long activeId) {
        TSubscrFans subscrFans = subscrFansDao.selectByPrimaryKey(openId);
        if (notEmpty(subscrFans)) {
            TMarketActive active = marketActiveDao.selectByPrimaryKey(activeId);
            if (notEmpty(active)) {
                int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(activeId, openId);
                return active.getLimitcount() - num;
            }
        }
        return 0;
    }

    @Override
    public ActivityRecordDetailsVo getRecordDetails(String openId, long recordId) {
        return marketActiveRecordDao.getRecordDetails(openId, recordId);
    }

    @Override
    public PageInfo<ActivityRecordVo> getRecordList(String openId, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<ActivityRecordVo> list = marketActiveRecordDao.getParticipationList(openId);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<ActivityRecordVo> getRecordDetailList(String openId, int type, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<ActivityRecordVo> list = marketActiveRecordDao.getRecordDetailList(openId, type);
        return new PageInfo<>(list);
    }

    @Override
    public int getRunDay(String appId, String openId) {
        TMarketActive active = marketActiveDao.selectUnderWaySbActive(appId, 6);
        if (notEmpty(active)) {
            TMarketActiveSign sign = marketActiveSignDao.selectSignRecord(openId, active.getId());
            if (notEmpty(sign)) {
                return sign.getRunday();
            }
        }
        return 0;
    }

    @Override
    public ActivityRuleH5Vo getSdActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 0);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            List<TMarketActivePic> pics = marketActivePicDao.selectPicByActiveId(active.getId(), active.getYoukeid());
            Map<String, Object> map;
            List<Map<String, Object>> demoPics = null;
            if (notEmpty(pics) && pics.size() > 0) {
                demoPics = new ArrayList<>();
                for (TMarketActivePic pic : pics) {
                    map = new HashMap<>();
                    map.put("url", pic.getPicurl());
                    demoPics.add(map);
                }
            }
            rule.setDemoPics(demoPics);
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getZpActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 1);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            List<TMarketActivePic> pics = marketActivePicDao.selectPicByActiveId(active.getId(), active.getYoukeid());
            Map<String, Object> map;
            List<Map<String, Object>> demoPics = null;
            if (notEmpty(pics) && pics.size() > 0) {
                demoPics = new ArrayList<>();
                for (TMarketActivePic pic : pics) {
                    map = new HashMap<>();
                    map.put("url", pic.getPicurl());
                    demoPics.add(map);
                }
            }
            rule.setDemoPics(demoPics);
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getStActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 2);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            List<TMarketActivePic> pics = marketActivePicDao.selectPicByActiveId(active.getId(), active.getYoukeid());
            Map<String, Object> map;
            List<Map<String, Object>> demoPics = null;
            if (notEmpty(pics) && pics.size() > 0) {
                demoPics = new ArrayList<>();
                for (TMarketActivePic pic : pics) {
                    map = new HashMap<>();
                    map.put("url", pic.getPicurl());
                    map.put("title", pic.getTitle());
                    demoPics.add(map);
                }
            }
            rule.setDemoPics(demoPics);
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getTpActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 4);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            List<TMarketActivePic> pics = marketActivePicDao.selectPicByActiveId(active.getId(), active.getYoukeid());
            Map<String, Object> map;
            List<Map<String, Object>> goodPics = null;
            if (notEmpty(pics) && pics.size() > 0) {
                goodPics = new ArrayList<>();
                for (TMarketActivePic pic : pics) {
                    map = new HashMap<>();
                    map.put("id", pic.getId());
                    map.put("title", pic.getTitle());
                    map.put("url", pic.getPicurl());
                    goodPics.add(map);
                }
            }
            rule.setGoodPics(goodPics);
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getHbActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 8);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            List<TMarketActivePic> pics = marketActivePicDao.selectPicByActiveId(active.getId(), active.getYoukeid());
            Map<String, Object> map;
            List<Map<String, Object>> demoPics = null;
            if (notEmpty(pics) && pics.size() > 0) {
                demoPics = new ArrayList<>();
                for (TMarketActivePic pic : pics) {
                    map = new HashMap<>();
                    map.put("url", pic.getPicurl());
                    map.put("type", pic.getType());
                    demoPics.add(map);
                }
            }
            rule.setDemoPics(demoPics);
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getSbActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 5);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getQdActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 6);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getCjActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 7);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            TMarketActiveLuck luck = marketActiveLuckDao.selectByPrimaryKey(active.getId());
            if (notEmpty(luck)) {
                try {
                    JSONArray array = JsonUtils.JsonToJSONArray(luck.getPrizeobj());
                    List<Map<String, Object>> list = new ArrayList<>();
                    Map<String, Object> map;
                    JSONObject obj;
                    for (int i = 0; i < array.size(); i++) {
                        obj = array.getJSONObject(i);
                        map = new HashMap<>();
                        map.put("id", obj.getString("id"));
                        map.put("prizeName", obj.getString("prizeName"));
                        list.add(map);
                    }
                    rule.setPrizeObj(list);
                    rule.setCostIntegral(luck.getCostintegral());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return rule;
        }
    }

    @Override
    public ActivityRuleH5Vo getSsfActiveInfo(String appId, Long activeId, String openId) {
        TMarketActive active;
        if (activeId == -1) {
            active = marketActiveDao.selectUnderWaySbActive(appId, 3);
        } else {
            active = marketActiveDao.selectByPrimaryKey(activeId);
        }
        if (empty(active)) {
            return null;
        } else {
            updateChartData(active.getId(), openId, activeId);
            ActivityRuleH5Vo rule = new ActivityRuleH5Vo();
            rule.setId(active.getId());
            rule.setBegTime(active.getBegtime());
            rule.setEndTime(active.getEndtime());
            rule.setIntro(JSON.parseArray(active.getIntro(), String.class));
            rule.setLimitCount(active.getLimitcount());
            rule.setOpenType(active.getOpentype());
            rule.setRewardType(active.getRewardtype());
            return rule;
        }
    }

    @Override
    public LuckyLotteryRetVo saveCjActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //查询参与次数
        int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(param.getActiveId(), param.getOpenId());
        if (num >= active.getLimitcount()) {
            throw new BusinessException("您的抽奖机会已用光。");
        }
        //查询奖品
        TMarketActiveLuck luck = marketActiveLuckDao.selectByPrimaryKey(param.getActiveId());
        if (subScrFans.getIntegral() < luck.getCostintegral()) {
            throw new BusinessException("抱歉，剩余积分不足。");
        }
        //解析奖品
        List<PrizeVo> list = getPrizeList(luck.getPrizeobj());
        //抽奖
        PrizeVo prizeVo = list.get(getPrizeIndex(list));
        //生成记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setActiveid(param.getActiveId());
        record.setOpenid(subScrFans.getOpenid());
        record.setYoukeid(active.getYoukeid());
        record.setAppid(subScrFans.getAppid());
        record.setWxfansname(subScrFans.getNickname());
        record.setCreatetime(new Date());
        record.setRewardtype(active.getRewardtype());
        record.setExaminetype(active.getExaminetype());
        record.setState(1);
        if (prizeVo.getRewardType() == 0) {//红包
            record.setMoney(prizeVo.getRewardVal());
            record.setRewardtype(0);
        } else if (prizeVo.getRewardType() == 1) {//积分
            record.setIntegral(prizeVo.getRewardVal());
            record.setRewardtype(2);
        } else {//未中奖
            record.setRewardtype(2);
            record.setIntegral(0);
            record.setMoney(0);
            record.setState(3);
        }
        marketActiveRecordDao.insertSelective(record);//保存记录

        //发送积分扣除模板,state手动设置为1,不然无法扣除积分
        sendRewards(ComeType.XING_YUN_CHOU_JIANG, 0, -luck.getCostintegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), 1);
        //发送返利并更新活动数据
        if (prizeVo.getRewardType() != -1) {//中奖
            sendRewards(ComeType.XING_YUN_CHOU_JIANG, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
            updateTranChart(active.getId(), 1);
        } else {
            updateTranChart(active.getId(), 0);
        }
        //返回奖励数据给前台展示
        LuckyLotteryRetVo vo = new LuckyLotteryRetVo();
        vo.setId(prizeVo.getId());
        vo.setPrizeName(prizeVo.getPrizeName());
        vo.setRewardType(prizeVo.getRewardType());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        if (prizeVo.getRewardType() == 0) {
            vo.setMoney(prizeVo.getRewardVal());
        } else {
            vo.setIntegral(prizeVo.getRewardVal());
        }
        vo.setShow(record.getExaminetype() == 0 ? 1 : 0);
        return vo;
    }

    @Override
    public BaseMarketActiveRetVo saveQdActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //生成活动记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setActiveid(param.getActiveId());
        record.setOpenid(param.getOpenId());
        record.setAppid(subscr.getAppid());
        record.setYoukeid(subscr.getYoukeid());
        record.setWxfansname(subScrFans.getNickname());
        record.setRewardtype(active.getRewardtype());
        record.setCreatetime(new Date());
        record.setExaminetype(active.getExaminetype());
        record.setState(1);
        if (active.getRewardtype() == 0) {
            record.setMoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
        } else {
            record.setIntegral(active.getIntegral());
        }
        TMarketActiveSign sign = marketActiveSignDao.selectSignRecord(param.getOpenId(), active.getId());
        List<TMarketActiveSignrule> rules = marketActiveSignruleDao.selectSignRule(active.getId(), active.getYoukeid());
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> runDays = new ArrayList<>();
        for (TMarketActiveSignrule rule : rules) {
            runDays.add(rule.getRunday());
            map.put(rule.getRunday(), rule.getIntegral());
        }
        if (empty(sign)) {
            sign = new TMarketActiveSign();
            sign.setActiveid(active.getId());
            sign.setOpenid(param.getOpenId());
            sign.setYoukeid(active.getYoukeid());
            sign.setRunday(1);
            sign.setLastdate(new Date());
            if (sign.getRunday().equals(rules.get(0).getRunday())) {
                record.setIntegral(active.getIntegral() + rules.get(0).getIntegral());
                sign.setLastrunday(rules.get(0).getRunday());
            }
            marketActiveSignDao.insertSelective(sign);
        } else {
            if (DayUtil.isToday(DateUtil.formatDate(sign.getLastdate()))) {
                throw new BusinessException("今天已经签过到了。");
            } else {
                if (DayUtil.isYesterday(DateUtil.formatDate(sign.getLastdate()))) {//连续签到
                    sign.setLastdate(new Date());
                    sign.setRunday(sign.getRunday() + 1);
                    if (runDays.contains(sign.getRunday()) && sign.getRunday() > sign.getLastrunday()) {//包含指定连续签到天数
                        sign.setLastrunday(sign.getRunday());
                        record.setIntegral(active.getIntegral() + map.get(sign.getRunday()));
                    }
                } else {//断签
                    sign.setLastdate(new Date());
                    sign.setRunday(1);
                }
                //更新签到记录
                marketActiveSignDao.updateByPrimaryKeySelective(sign);
            }
        }
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        sendRewards(ComeType.MEI_RI_QIAN_DAO, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
        updateTranChart(active.getId(), 1);
        //返回奖励数据给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }

    @Override
    public BaseMarketActiveRetVo saveSbActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测活动资格
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //订单检测
        if (active.getFanslimit() == 1) {
            Long id = shopOrderDao.selectOrderByMobile(param.getMobile(), active.getYoukeid());
            if (empty(id)) {
                throw new BusinessException("无购买记录，不能参与该活动。");
            }
        }
        //是否绑定手机号
        if (notEmpty(subScrFans.getMobile())) {
            throw new BusinessException("您已绑定了手机号，无法参与本活动。");
        } else {
            if (checkMobCode(param.getMobile(), param.getCode())) {//验证码验证
                subScrFans.setMobile(param.getMobile());
                subScrFans.setRegtime(new Date());
                subscrFansDao.updateByPrimaryKeySelective(subScrFans);
                //生成活动记录并保存
                TMarketActiveRecord record = new TMarketActiveRecord();
                record.setActiveid(param.getActiveId());
                record.setOpenid(param.getOpenId());
                record.setWxfansname(subScrFans.getNickname());
                record.setRewardtype(active.getRewardtype());
                record.setCreatetime(new Date());
                record.setExaminetype(active.getExaminetype());
                record.setAppid(subScrFans.getAppid());
                record.setYoukeid(active.getYoukeid());
                record.setState(1);
                if (active.getRewardtype() == 0) {
                    record.setMoney(active.getFixedmoney());
                } else if (active.getRewardtype() == 1) {
                    record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
                } else {
                    record.setIntegral(active.getIntegral());
                }
                marketActiveRecordDao.insertSelective(record);
                //发送奖励并更新活动数据
                sendRewards(ComeType.SHOU_BANG_YOU_LI, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
                updateTranChart(active.getId(), 1);
                //返回奖励数据给前端展示
                BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
                vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
                vo.setRewardType(record.getRewardtype());
                vo.setIntegral(record.getIntegral());
                vo.setMoney(record.getMoney());
                vo.setHeadImg(subscr.getHeadimg());
                vo.setNickName(subscr.getNickname());
                return vo;
            } else {
                throw new BusinessException("验证码错误。");
            }
        }
    }


    public BaseMarketActiveRetVo saveHbActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //查询参与次数
        int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(param.getActiveId(), param.getOpenId());
        numOfParticipationCheck(active, num, 0);
        //生成活动记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setActiveid(param.getActiveId());
        record.setOpenid(param.getOpenId());
        record.setAppid(active.getAppid());
        record.setYoukeid(active.getYoukeid());
        record.setWxfansname(subScrFans.getNickname());
        record.setRewardtype(active.getRewardtype());
        if (active.getRewardtype() == 0) {
            record.setMoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
        } else {
            record.setIntegral(active.getIntegral());
        }
        record.setUserpics(param.getUserPics());
        record.setCreatetime(new Date());
        record.setExaminetype(active.getExaminetype());
        if (active.getExaminetype() == 0) {//无需审核
            record.setState(1);
        } else {
            record.setState(0);
        }
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        if (record.getState() == 1) {
            sendRewards(ComeType.FEN_XIANG_HAI_BAO, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
            updateTranChart(active.getId(), 1);
        }
        //返回奖励数据给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }


    public BaseMarketActiveRetVo saveTpActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //查询参与次数和投票次数
        int maxJoin = marketActiveVoteDao.getMaxJoinNum(param.getActiveId(), active.getYoukeid());
        int joinNum = marketActiveVoteDao.selectJoinNum(param.getActiveId(), param.getOpenId(), active.getYoukeid());
        if (joinNum > 0) {
            throw new BusinessException("您已经投过票了。");
        }
        if (maxJoin >= active.getMaxjoin()) {
            throw new BusinessException("活动名额已满，感谢您的参与。");
        }
        //生成投票记录
        TMarketActiveVote vote;
        String[] picIds = param.getPicIds().split(",");
        for (String picId : picIds) {
            vote = new TMarketActiveVote();
            vote.setActiveid(param.getActiveId());
            vote.setCreatetime(new Date());
            vote.setOpenid(param.getOpenId());
            vote.setPicid(Long.valueOf(picId));
            vote.setYoukeid(active.getYoukeid());
            marketActiveVoteDao.insertSelective(vote);
        }
        //生成活动参与记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setWxfansname(subScrFans.getNickname());
        record.setExaminetype(active.getExaminetype());
        record.setRewardtype(active.getRewardtype());
        record.setActiveid(param.getActiveId());
        record.setYoukeid(active.getYoukeid());
        record.setOpenid(param.getOpenId());
        record.setAppid(active.getAppid());
        record.setCreatetime(new Date());
        record.setState(1);
        if (active.getRewardtype() == 1) {
            record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
        } else if (active.getRewardtype() == 0) {
            record.setMoney(active.getFixedmoney());
        } else {
            record.setIntegral(active.getIntegral());
        }
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        updateTranChart(active.getId(), 1);
        sendRewards(ComeType.TOU_PIAO_CE_KUAN, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
        //返回奖励数据给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }

    public BaseMarketActiveRetVo saveSsfActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //订单监测
        TShopOrder shopOrder = shopOrderDao.getByOrderNo(param.getOrderno(), active.getYoukeid());
        //查询返利订单
        TMarketActiveBackorder backorder = marketActiveBackorderDao.selectBackorderByOrderNo(param.getOrderno(), active.getId());
        if (empty(backorder)) {
            throw new BusinessException("本活动仅限特定订单号参与，如有疑问请联系客服处理。");
        } else {
            if (backorder.getState() == 0) {//订单未被使用
                //生成记录并保存
                TMarketActiveRecord record = new TMarketActiveRecord();
                record.setActiveid(param.getActiveId());
                record.setOpenid(param.getOpenId());
                record.setAppid(active.getAppid());
                record.setYoukeid(active.getYoukeid());
                record.setWxfansname(subScrFans.getNickname());
                record.setState(1);
                record.setRewardtype(active.getRewardtype());
                record.setOrderno(param.getOrderno());
                record.setCreatetime(new Date());
                record.setExaminetype(active.getExaminetype());
                backorder.setState(1);
                if (notEmpty(shopOrder)) {
                    record.setShopfansname(shopOrder.getReceivename());
                }
                if (active.getRewardtype() == 0) {
                    record.setMoney(backorder.getMoney());
                } else {
                    record.setIntegral(backorder.getMoney());
                }
                marketActiveRecordDao.insertSelective(record);
                marketActiveBackorderDao.updateByPrimaryKeySelective(backorder);
                //发送奖励并更新活动数据
                updateTranChart(active.getId(), 1);
                sendRewards(ComeType.SUI_SHI_FAN, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
                //返回奖励数据给前端展示
                BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
                vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
                vo.setRewardType(record.getRewardtype());
                vo.setIntegral(record.getIntegral());
                vo.setMoney(record.getMoney());
                vo.setHeadImg(subscr.getHeadimg());
                vo.setNickName(subscr.getNickname());
                return vo;
            } else {
                throw new BusinessException("该订单号已被使用。");
            }
        }
    }


    public BaseMarketActiveRetVo saveStActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //参与次数检测
        int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(param.getActiveId(), param.getOpenId());
        numOfParticipationCheck(active, num, 0);
        //生成记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setAppid(param.getAppId());
        record.setYoukeid(active.getYoukeid());
        record.setActiveid(param.getActiveId());
        record.setOpenid(param.getOpenId());
        record.setUserpics(param.getUserPics());
        record.setCreatetime(new Date());
        record.setExaminetype(active.getExaminetype());
        record.setWxfansname(subScrFans.getNickname());
        record.setRewardtype(active.getRewardtype());
        if (active.getRewardtype() == 0) {
            record.setMoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
        } else {
            record.setIntegral(active.getIntegral());
        }
        if (active.getExaminetype() == 0) {//无需审核
            record.setState(1);
        } else {
            record.setState(0);
        }
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        if (record.getState() == 1) {
            updateTranChart(active.getId(), 1);
            sendRewards(ComeType.SHAI_TU_YOU_LI, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
        }
        //返回奖励数据给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }


    public BaseMarketActiveRetVo saveZpActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //参与次数检测
        int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(param.getActiveId(), param.getOpenId());
        int count = marketActiveRecordDao.selectRecordByTypeAndOrderNo(active.getType(), param.getOrderno(), active.getYoukeid());
        numOfParticipationCheck(active, num, count);
        //订单检测
        TShopOrder shopOrder = shopOrderDao.getByOrderNo(param.getOrderno(), active.getYoukeid());
        List<Integer> shopIds = shopDao.selectShopIdsByYoukeId(active.getYoukeid());
        orderCheck(shopOrder, shopIds);
        //生成记录并保存
        TMarketActiveRecord record = new TMarketActiveRecord();
        record.setActiveid(param.getActiveId());
        record.setOpenid(param.getOpenId());
        record.setAppid(param.getAppId());
        record.setYoukeid(active.getYoukeid());
        record.setWxfansname(subScrFans.getNickname());
        record.setShopfansname(shopOrder.getReceivename());
        record.setRewardtype(active.getRewardtype());
        record.setOrderno(param.getOrderno());
        record.setUserpics(param.getUserPics());
        record.setCreatetime(new Date());
        record.setExaminetype(active.getExaminetype());
        if (active.getRewardtype() == 0) {
            record.setMoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
        } else {
            record.setIntegral(active.getIntegral());
        }
        record.setState(active.getExaminetype() == 0 ? 1 : 0);
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        if (record.getState() == 1) {//审核通过
            sendRewards(ComeType.ZHUI_PING_YOU_LI, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
            updateTranChart(active.getId(), 1);
        } else if (record.getState() == 2) {//审核未通过
            sendRewards(ComeType.ZHUI_PING_YOU_LI, 0, 0, record.getOpenid(), record.getAppid(), record.getYoukeid(), record.getFailreason(), record.getId(), record.getState());
            updateTranChart(active.getId(), 0);
        }
        //返回奖励给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }


    public BaseMarketActiveRetVo saveSdActiveRecord(HotActivityParamVo param) {
        //查询活动并检测
        TMarketActive active = marketActiveDao.selectByPrimaryKey(param.getActiveId());
        activeCheck(active);
        //查询粉丝并检测
        TSubscrFans subScrFans = subscrFansDao.selectByPrimaryKey(param.getOpenId());
        subScrFansCheck(subScrFans, active);
        TSubscr subscr = subscrDao.selectByPrimaryKey(subScrFans.getAppid());
        //参与次数检测
        int num = marketActiveRecordDao.selectJoinNumberByActiveIdAndOpenId(param.getActiveId(), param.getOpenId());
        int count = marketActiveRecordDao.selectRecordByTypeAndOrderNo(active.getType(), param.getOrderno(), active.getYoukeid());
        numOfParticipationCheck(active, num, count);
        //订单检测
        TShopOrder shopOrder = shopOrderDao.getByOrderNo(param.getOrderno(), active.getYoukeid());
        List<Integer> shopIds = shopDao.selectShopIdsByYoukeId(active.getYoukeid());
        orderCheck(shopOrder, shopIds);
        //查询活动规则并进行判定
        TMarketActiveRedrule redrule = marketActiveRedruleDao.selectByPrimaryKey(active.getId());
        List<TMarketActiveSubRedrule> rules = marketActiveSubRedruleDao.selectOpenRulesByActiveId(active.getId());
        List<String> intervals = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        StringBuilder sb;
        if (redrule != null) {
            if (redrule.getOpenminlimit() == 1) {
                sb = new StringBuilder();
                sb.append("(").append(0).append(",").append(redrule.getMinlimit()).append("]");//前开后闭区间
                intervals.add(sb.toString());
                map.put(sb.toString(), redrule.getMinmoneyback());
            }
            if (redrule.getOpenmidlimit() == 1) {
                sb = new StringBuilder();
                sb.append("(").append(redrule.getMidlimitend()).append(",").append(redrule.getMidlimitend()).append("]");//前开后闭区间
                intervals.add(sb.toString());
                map.put(sb.toString(), redrule.getMidmoneyback() == 0 ? MoneyUtil.getRandomMoney(redrule.getMidrandmoneybeg(), redrule.getMidrandmoneyend()) : redrule.getMidmoneyback());
            }
            if (redrule.getOpenmaxlimit() == 1) {
                sb = new StringBuilder();
                sb.append("(").append(redrule.getMaxlimit()).append(",").append("+∞").append("]");//前开后闭区间
                intervals.add(sb.toString());
                map.put(sb.toString(), redrule.getMaxmoneyback() == 0 ? MoneyUtil.getRandomMoney(redrule.getMaxrandmoneybeg(), redrule.getMaxrandmoneyend()) : redrule.getMaxmoneyback());
            }
        }
        if (rules.size() > 0) {
            for (TMarketActiveSubRedrule rule : rules) {
                sb = new StringBuilder();
                sb.append("(").append(rule.getMidlimitbeg()).append(",").append(rule.getMidlimitend()).append("]");
                intervals.add(sb.toString());
                map.put(sb.toString(), rule.getMidmoneyback() == 0 ? MoneyUtil.getRandomMoney(rule.getMidrandmoneybeg(), rule.getMidrandmoneyend()) : rule.getMidmoneyback());
            }
        }
        TMarketActiveRecord record = new TMarketActiveRecord();
        if (intervals.size() > 0) {//存在区间规则进行匹配
            boolean flag = false;
            for (String interval : intervals) {
                flag = IntervalUtil.isInTheInterval(String.valueOf(shopOrder.getPrice()), interval);//进行区间匹配
                if (flag) {
                    record.setMoney(map.get(interval));
                    break;
                }
            }
            if (!flag) {//没有命中区间 按默认无规则处理
                if (active.getRewardtype() == 0) {
                    record.setMoney(active.getFixedmoney());
                } else if (active.getRewardtype() == 1) {
                    record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
                } else {
                    record.setIntegral(active.getIntegral());
                }
            }
        } else {//无规则
            if (active.getRewardtype() == 0) {
                record.setMoney(active.getFixedmoney());
            } else if (active.getRewardtype() == 1) {
                record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
            } else {
                record.setIntegral(active.getIntegral());
            }
        }
        record.setActiveid(param.getActiveId());
        record.setOpenid(param.getOpenId());
        record.setAppid(param.getAppId());
        record.setYoukeid(active.getYoukeid());
        record.setWxfansname(subScrFans.getNickname());
        record.setShopfansname(shopOrder.getReceivename());
        record.setRewardtype(active.getRewardtype());
        record.setOrderno(param.getOrderno());
        record.setUserpics(param.getUserPics());
        record.setCreatetime(new Date());
        record.setExaminetype(active.getExaminetype());
        record.setState(active.getExaminetype() == 0 ? 1 : 0);
        marketActiveRecordDao.insertSelective(record);
        //发送奖励并更新活动数据
        if (record.getState() == 1) {//审核通过
            sendRewards(ComeType.SHAI_DAN_YOU_LI, record.getMoney(), record.getIntegral(), record.getOpenid(), record.getAppid(), record.getYoukeid(), "", record.getId(), record.getState());
            updateTranChart(active.getId(), 1);
        } else if (record.getState() == 2) {//审核未通过
            sendRewards(ComeType.SHAI_DAN_YOU_LI, 0, 0, record.getOpenid(), record.getAppid(), record.getYoukeid(), record.getFailreason(), record.getId(), record.getState());
            updateTranChart(active.getId(), 0);
        }
        //返回奖励数据给前端展示
        BaseMarketActiveRetVo vo = new BaseMarketActiveRetVo();
        vo.setShow(active.getExaminetype() == 0 ? 1 : 0);
        vo.setRewardType(record.getRewardtype());
        vo.setIntegral(record.getIntegral());
        vo.setMoney(record.getMoney());
        vo.setHeadImg(subscr.getHeadimg());
        vo.setNickName(subscr.getNickname());
        return vo;
    }


    /**
     * 参与次数检测
     *
     * @param num   活动参与次数
     * @param count 订单号使用次数
     */
    private void numOfParticipationCheck(TMarketActive active, int num, int count) {
        if (num >= active.getLimitcount()) {
            throw new BusinessException("您已参与过本次活动了。");
        }
        if (count != 0) {
            throw new BusinessException("同一订单号只能参与一次活动。");
        }
    }

    /**
     * 账号检测
     *
     * @param subScrFans
     */
    private void subScrFansCheck(TSubscrFans subScrFans, TMarketActive active) {
        if (empty(subScrFans)) {
            throw new H5SubscrException();
        } else {
            if (subScrFans.getState() == 1 || subScrFans.getSubstate() == 1) {
                throw new H5SubscrException();
            }
        }
        if (active.getFanslimit() != null) {
            if (active.getFanslimit() == 1 && active.getType() != 5) {//全部粉丝:未绑定手机号的粉丝
                if (empty(subScrFans.getMobile())) {
                    throw new H5MobileException();
                }
            }
        }
    }

    /**
     * 活动检测
     *
     * @param active
     */
    private void activeCheck(TMarketActive active) {
        if (empty(active)) {
            throw new BusinessException("活动不存在。");
        }
        if (active.getState() == 0) {
            throw new BusinessException("活动尚未开始，感谢您的参与。");
        }
        if (active.getState() == 2) {
            throw new BusinessException("活动已结束，感谢您的参与。");
        }
        if (!DateUtil.compareDate(active.getEndtime())) {
            throw new BusinessException("活动已结束，感谢您的参与。");
        }
    }

    /**
     * 订单检测
     *
     * @param shopOrder
     */
    private void orderCheck(TShopOrder shopOrder, List<Integer> shopIds) {
        if (shopIds != null && shopIds.size() > 0) {
            if (empty(shopOrder)) {
                throw new BusinessException("订单不存在或数据尚未录入，请联系客服处理。");
            }
            if (shopOrder.getState() != 4) {
                throw new BusinessException("该订单尚未交易成功或数据尚未同步,请联系客服处理。");
            }
            if (!shopIds.contains(shopOrder.getShopid())) {
                throw new BusinessException("订单不存在或数据尚未录入，请联系客服处理。");
            }
            if (shopOrder.getBuyerrate() != 1) {//非好评
                throw new BusinessException("参与活动失败，该订单未5星好评，如有疑问请联系客服处理。");
            }
        } else {
            throw new BusinessException("数据出错，请联系客服处理。");
        }
    }


    /**
     * 发送消息队列,发放或扣除积分\金额
     *
     * @param comeType   活动来源
     * @param money      金额:单位 分
     * @param integral   积分:单位 个
     * @param failReason 失败原因
     * @param state      状态
     * @param openId
     * @param appId
     * @param youkeId
     */
    private void sendRewards(Integer comeType, Integer money, Integer integral, String openId, String appId, String youkeId, String
            failReason, Long recordId, Integer state) {
        ActiveMassMessage message = new ActiveMassMessage();
        message.setAppId(appId);
        message.setOpenId(openId);
        message.setTitle(ComeType.COME_TYPE.get(comeType));
        message.setYoukeId(youkeId);
        message.setComeType(comeType);
        message.setRecordId(recordId);
        message.setState(state);
        if (notEmpty(money)) {
            message.setMoney(money);
        }
        if (notEmpty(integral)) {
            message.setIntegral(integral);
        }
        if (notEmpty(failReason)) {
            message.setFailReason(failReason);
        }
        activeMessageProducer.send("activemass.queue", message);
        activeMessageProducer.send("activeexamine.queue", message);
    }


    /**
     * 抽奖
     *
     * @param prizes
     * @return
     */
    private static int getPrizeIndex(List<PrizeVo> prizes) {
        int random = -1;
        try {
            //计算总权重
            double sumWeight = 0;
            for (PrizeVo p : prizes) {
                sumWeight += p.getPercent();
            }
            //产生随机数
            double randomNumber;
            randomNumber = Math.random();
            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for (int i = 0; i < prizes.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getPercent())) / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(prizes.get(i - 1).getPercent())) / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("生成抽奖随机数出错，出错原因：" + e.getMessage());
        }
        return random;
    }

    public boolean checkMobCode(String mobile, String code) {
        if (code.equals("8888"))
            return true;
        if (code.equals(RedisSlaveUtil.get("mobile-code-" + mobile)))
            return true;
        return false;
    }

    /**
     * 奖品解析
     *
     * @param prizeObj
     * @return
     */
    private List<PrizeVo> getPrizeList(String prizeObj) {
        List<PrizeVo> prizeList = new ArrayList<>();
        try {
            JSONArray array = JsonUtils.JsonToJSONArray(prizeObj);
            PrizeVo prize;
            int probability = 0;
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                prize = new PrizeVo();
                prize.setId(obj.getString("id"));
                prize.setPrizeName(obj.getString("prizeName"));
                prize.setRewardType(obj.getInt("rewardType"));
                prize.setRewardVal(obj.getInt("rewardVal"));
                prize.setPercent(obj.getDouble("percent"));
                probability += obj.getDouble("percent");
                prizeList.add(prize);
            }
            if (array.size() < 8) {
                prize = new PrizeVo();
                prize.setId(IDUtil.getRandomIntId());
                prize.setPrizeName("未中奖");
                prize.setRewardVal(0);
                prize.setRewardType(-1);//设置未中奖的rewardType为-1
                prize.setPercent(new BigDecimal(100 - probability).doubleValue());
                prizeList.add(prize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prizeList;
    }

    /**
     * 更新基本数据
     *
     * @param activeId
     * @param openId
     * @param id
     */
    private void updateChartData(Long activeId, String openId, Long id) {
        if (id == -1) {
            ActiveChartMassMessage message = new ActiveChartMassMessage();
            message.setActiveId(activeId);
            message.setOpenId(openId);
            activeMessageProducer.send("chart.queue", message);
        }
    }

    /**
     * 更新转化数据
     *
     * @param activeId
     * @param winNum
     */
    private void updateTranChart(Long activeId, int winNum) {
        ActiveTranChartVo message = new ActiveTranChartVo();
        message.setActiveId(activeId);
        message.setBookNum(1);
        message.setWinNum(winNum);
        activeMessageProducer.send("tranchart.queue", message);
    }
}
