package youke.service.market.biz.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.constants.ComeType;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.param.PrizeVo;
import youke.common.model.vo.param.market.MarketActivitySaveVo;
import youke.common.model.vo.param.market.MarketQueryVo;
import youke.common.model.vo.param.market.MarketRecordListQueryVo;
import youke.common.model.vo.result.*;
import youke.common.model.vo.result.market.MarketActivityVo;
import youke.common.model.vo.result.market.MarketExamineDetailVo;
import youke.common.model.vo.result.market.MarketRecordListVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.redis.RedisUtil;
import youke.common.utils.*;
import youke.facade.market.vo.MarketConstant;
import youke.service.market.biz.IMarketActivityBiz;
import youke.service.market.queue.producter.ActiveMessageProducer;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class MarketActivityBiz extends Base implements IMarketActivityBiz {

    @Resource
    private IMarketActiveBackorderDao marketActiveBackorderDao;
    @Resource
    private IMarketActiveSignruleDao marketActiveSignruleDao;
    @Resource
    private IMarketActiveRedruleDao marketActiveRedruleDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;
    @Resource
    private IMarketActiveVoteDao marketActiveVoteDao;
    @Resource
    private IMarketActiveLuckDao marketActiveLuckDao;
    @Resource
    private IMarketActivePicDao marketActivePicDao;
    @Resource
    private ISubscrConfigDao subscrConfigDao;
    @Resource
    private IMarketActiveDao marketActiveDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private ActiveMessageProducer activeMessageProducer;
    @Resource
    private IMarketActiveSubRedruleDao marketActiveSubRedruleDao;

    public PageInfo<MarketActivityVo> getActivityList(MarketQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<MarketActivityVo> info = marketActiveDao.queryList(params);
        return new PageInfo<>(info);
    }

    public PageInfo<MarketRecordListVo> getRecordList(MarketRecordListQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<MarketRecordListVo> list = marketActiveRecordDao.getRecordList(params);
        return new PageInfo<>(list);
    }

    @Override
    public List<MarketRecordListVo> getExportRecordList(MarketRecordListQueryVo params) {
        return marketActiveRecordDao.getRecordList(params);
    }

    public MarketExamineDetailVo getExamineDetail(int recordId, String dykId) {
        return marketActiveRecordDao.getExamineDetail(recordId, dykId);
    }

    @Override
    public List<String> getMarketActives(String appId) {
        List<String> actives = new ArrayList<>();
        Map<Integer, String> yingXiaoTypes = ComeType.YING_XIAO_TYPES;
        for (Integer type : yingXiaoTypes.keySet()) {
            Long count = marketActiveDao.queryCountForType(appId, type);
            if (count != null && count > 0) {
                actives.add(yingXiaoTypes.get(type));
            }
        }
        return actives;
    }

    @Override
    public Map<String, Object> getConsumption(MarketRecordListQueryVo params) {
        return marketActiveRecordDao.getConsumption(params);
    }

    /**
     * 批量审核
     *
     * @param recordIds
     * @param state
     * @param failReason
     * @param dykId
     * @param userId
     */
    public void updateState(String recordIds, Integer state, String failReason, String dykId, Integer userId) {
        TMarketActiveRecord record;
        ActiveMassMessage message;
        TUser user = userDao.selectByPrimaryKey(userId);
        String[] recordIdArr = recordIds.split(",");
        for (String recordId : recordIdArr) {
            record = marketActiveRecordDao.selectByPrimaryKey(Long.valueOf(recordId));
            if (notEmpty(record)) {
                message = new ActiveMassMessage();
                TMarketActive active = marketActiveDao.selectByPrimaryKey(record.getActiveid());
                record.setState(state);
                record.setExaminetype(2);
                record.setExaminename(user.getTruename());
                record.setExamineuserid(user.getId() + "");
                message.setRecordId(record.getId());
                message.setAppId(record.getAppid());
                message.setYoukeId(record.getYoukeid());
                message.setComeType(ComeType.getComeTypeFromActiveType(active.getType()));
                message.setOpenId(record.getOpenid());
                message.setTitle(active.getTitle());
                if (state == 2) {//未通过
                    record.setFailreason(failReason);
                    message.setFailReason(failReason);
                    message.setState(2);

                } else {//审核通过
                    if (notEmpty(record.getIntegral()) && record.getIntegral() > 0) {//积分
                        message.setIntegral(record.getIntegral());
                    }
                    if (notEmpty(record.getMoney()) && record.getMoney() > 0) {//现金
                        message.setMoney(record.getMoney());
                    }
                    message.setState(1);
                    updateTranChartData(record.getActiveid(), 1, 1);//更新转化数据
                }
                marketActiveRecordDao.updateByPrimaryKeySelective(record);
                activeMessageProducer.send("activemass.queue", message);
                activeMessageProducer.send("activeexamine.queue", message);
            }
        }
    }

    /**
     * 获取基础活动数据
     *
     * @param activeId
     * @return
     */
    @Override
    public ActiveDataVo getActiveData(long activeId) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(activeId);
        ActiveDataVo data = new ActiveDataVo();
        data.setState(active.getState());
        data.setTitle(active.getTitle());
        data.setType(active.getType());
        data.setPv(active.getPv());
        data.setUv(active.getUv());
        return data;
    }

    /**
     * 从redis获取活动数据
     *
     * @param activeId
     * @param begDate
     * @param endDate
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public List<ActiveChartVo> getActiveChart(long activeId, String begDate, String endDate) {
        List<ActiveChartVo> charts;
        Map<String, Object> map = new HashMap<>();
        if (RedisUtil.hasKey(MarketConstant.KEY_CHART)) {
            charts = (List<ActiveChartVo>) RedisUtil.hget(MarketConstant.KEY_CHART, activeId + "");
            if (notEmpty(charts) && charts.size() > 0) {
                return charts.stream().filter(chart -> DateUtil.isBetweenTwoDates(chart.getDate(), begDate, endDate)).collect(toList());
            } else {
                ActiveChartVo vo = new ActiveChartVo();
                charts = new ArrayList<>();
                vo.setDate(DateUtil.formatDate(new Date()));
                vo.setPv(0);
                vo.setUv(0);
                charts.add(vo);
                RedisUtil.hset(MarketConstant.KEY_CHART, activeId + "", charts);
            }
        } else {
            ActiveChartVo vo = new ActiveChartVo();
            charts = new ArrayList<>();
            vo.setDate(DateUtil.formatDate(new Date()));
            vo.setPv(0);
            vo.setUv(0);
            charts.add(vo);
            map.put(activeId + "", charts);
            RedisUtil.hmset(MarketConstant.KEY_CHART, map);
        }
        return charts;
    }

    /**
     * 从redis获取活动转化数据
     *
     * @param activeId
     * @param begDate
     * @param endDate
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public List<ActiveTranChartVo> getActiveTranChart(long activeId, String begDate, String endDate) {
        List<ActiveTranChartVo> trancharts;
        Map<String, Object> map = new HashMap<>();
        if (RedisUtil.hasKey(MarketConstant.KEY_TRANCHART)) {
            trancharts = (List<ActiveTranChartVo>) RedisUtil.hget(MarketConstant.KEY_TRANCHART, +activeId + "");
            if (notEmpty(trancharts) && trancharts.size() > 0) {
                return trancharts.stream().filter(tranchart -> DateUtil.isBetweenTwoDates(tranchart.getDate(), begDate, endDate)).collect(toList());
            } else {
                ActiveTranChartVo vo = new ActiveTranChartVo();
                trancharts = new ArrayList<>();
                vo.setBookNum(0);
                vo.setWinNum(0);
                vo.setActiveId(activeId);
                vo.setDate(DateUtil.formatDate(new Date()));
                trancharts.add(vo);
                RedisUtil.hset(MarketConstant.KEY_TRANCHART, activeId + "", trancharts);
            }
        } else {
            ActiveTranChartVo vo = new ActiveTranChartVo();
            trancharts = new ArrayList<>();
            vo.setBookNum(0);
            vo.setWinNum(0);
            vo.setActiveId(activeId);
            vo.setDate(DateUtil.formatDate(new Date()));
            trancharts.add(vo);
            map.put(activeId + "", trancharts);
            RedisUtil.hmset(MarketConstant.KEY_TRANCHART, map);
        }
        return trancharts;
    }

    /**
     * 下线
     *
     * @param id
     */
    @Override
    public void updateActiveStateForOffLine(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        if (notEmpty(active)) {
            if (active.getState() != 1) {
                throw new BusinessException("只能下线正在进行中的活动");
            } else {
                active.setState(2);
                active.setEndtime(new Date());//手动下线活动需要更改活动结束时间
                active.setUpdateTime(new Date());
                marketActiveDao.updateByPrimaryKeySelective(active);
            }
        } else {
            throw new BusinessException("活动不存在");
        }
    }

    /**
     * 上线
     *
     * @param id
     */
    @Override
    public void updateActiveStateForOnLine(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        if (notEmpty(active)) {
            if (active.getState() != 0) {
                throw new BusinessException("只能上线处于待上线状态的活动");
            } else {
                TMarketActive onGoingActivity = marketActiveDao.selectUnderWaySbActive(active.getAppid(), active.getType());
                if (notEmpty(onGoingActivity)) {
                    throw new BusinessException("当前已有同类型的活动正在进行中");
                } else {
                    List<TMarketActive> list = marketActiveDao.selectActiveWithOutThis(active.getAppid(), active.getType(), active.getId());
                    if (notEmpty(list) && list.size() > 0) {
                        for (TMarketActive activity : list) {
                            if (DateUtil.isOverlap(DateUtil.formatDateTime(activity.getBegtime()), DateUtil.formatDateTime(activity.getEndtime()), DateUtil.formatDateTime(new Date()), DateUtil.formatDateTime(active.getEndtime()))) {
                                throw new BusinessException("上线失败，与" + activity.getTitle() + "活动时间冲突。");
                            }
                        }
                    }
                    active.setState(1);
                    active.setBegtime(new Date());//手动上线需要更改活动开始时间
                    active.setUpdateTime(new Date());
                    marketActiveDao.updateByPrimaryKeySelective(active);
                }
            }
        } else {
            throw new BusinessException("活动不存在");
        }
    }

    @Override
    public ActiveInfoVo getHbActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        List<TMarketActivePic> pics = marketActivePicDao.selectPicsByActiveId(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setFanslimit(active.getFanslimit());
        vo.setRewardtype(active.getRewardtype());
        if (active.getExaminetype() == 1) {
            vo.setExaminehour(active.getExaminehour());
        }
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        if (notEmpty(pics) && pics.size() > 0) {
            HashMap<String, Object> map = null;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActivePic pic : pics) {
                map = new HashMap<>();
                map.put("url", pic.getPicurl());
                map.put("title", pic.getTitle());
                map.put("type", pic.getType());
                list.add(map);
            }
            vo.setDemoPics(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getCjActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setFanslimit(active.getFanslimit());
        vo.setLimitcount(active.getLimitcount());
        TMarketActiveLuck luck = marketActiveLuckDao.selectByPrimaryKey(id);
        if (notEmpty(luck)) {
            vo.setCostIntegral(luck.getCostintegral());
            try {
                JSONArray array = JsonUtils.JsonToJSONArray(luck.getPrizeobj());
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> map;
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    map = new HashMap<>();
                    map.put("id", obj.getString("id"));
                    map.put("prizeName", obj.getString("prizeName"));
                    map.put("rewardType", obj.getString("rewardType"));
                    map.put("rewardVal", obj.getString("rewardVal"));
                    map.put("percent", obj.getString("percent"));
                    list.add(map);
                    vo.setPrize(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getQdActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        List<TMarketActiveSignrule> rules = marketActiveSignruleDao.selectSignRule(active.getId(), active.getYoukeid());
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setRewardtype(active.getRewardtype());
        vo.setFanslimit(active.getFanslimit());
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        if (notEmpty(rules) && rules.size() > 0) {
            List<Map<String, Integer>> list = new ArrayList<>();
            Map<String, Integer> map;
            for (TMarketActiveSignrule rule : rules) {
                map = new HashMap<>();
                map.put("runDay", rule.getRunday());
                map.put("integral", rule.getIntegral());
                list.add(map);
            }
            vo.setRule(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getSbActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setRewardtype(active.getRewardtype());
        vo.setFanslimit(active.getFanslimit());
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getTpActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        List<TMarketActivePic> pics = marketActivePicDao.selectPicsByActiveId(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setFanslimit(active.getFanslimit());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setRewardtype(active.getRewardtype());
        vo.setMaxjoin(active.getMaxjoin());
        vo.setLimitcount(active.getLimitcount());
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        if (notEmpty(pics) && pics.size() > 0) {
            HashMap<String, Object> map = null;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActivePic pic : pics) {
                map = new HashMap<>();
                map.put("url", pic.getPicurl());
                map.put("title", pic.getTitle());
                list.add(map);
            }
            vo.setDemoPics(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getSsfActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setFanslimit(active.getFanslimit());
        vo.setFilename(active.getFilename());
        vo.setFileurl(active.getFileurl());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        vo.setRewardtype(active.getRewardtype());
        List<TMarketActiveBackorder> orders = marketActiveBackorderDao.selectInputOrders(id, 0);
        if (notEmpty(orders) && orders.size() > 0) {
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map;
            for (TMarketActiveBackorder order : orders) {
                map = new HashMap<>();
                map.put("orderno", order.getOrderno());
                map.put("price", order.getMoney());
                list.add(map);
            }
            vo.setInputOrders(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getZpActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        List<TMarketActivePic> pics = marketActivePicDao.selectPicsByActiveId(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setRewardtype(active.getRewardtype());
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        vo.setLimitcount(active.getLimitcount());
        vo.setFanslimit(active.getFanslimit());
        vo.setOpentype(active.getOpentype());
        vo.setExaminetype(active.getExaminetype());
        if (active.getExaminetype() == 1) {
            vo.setExaminehour(active.getExaminehour());
        }
        if (notEmpty(pics) && pics.size() > 0) {
            HashMap<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActivePic pic : pics) {
                map = new HashMap<>();
                map.put("url", pic.getPicurl());
                map.put("title", pic.getTitle());
                list.add(map);
            }
            vo.setDemoPics(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getStActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        List<TMarketActivePic> pics = marketActivePicDao.selectPicsByActiveId(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setRewardtype(active.getRewardtype());
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        vo.setLimitcount(active.getLimitcount());
        vo.setFanslimit(active.getFanslimit());
        vo.setExaminetype(active.getExaminetype());
        if (active.getExaminetype() == 1) {
            vo.setExaminehour(active.getExaminehour());
        }
        vo.setOpentype(active.getOpentype());
        if (notEmpty(pics) && pics.size() > 0) {
            HashMap<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActivePic pic : pics) {
                map = new HashMap<>();
                map.put("url", pic.getPicurl());
                map.put("title", pic.getTitle());
                list.add(map);
            }
            vo.setDemoPics(list);
        }
        return vo;
    }

    @Override
    public ActiveInfoVo getSdActiveInfo(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        TMarketActiveRedrule rule = marketActiveRedruleDao.selectByPrimaryKey(id);
        List<TMarketActiveSubRedrule> subRedrules = marketActiveSubRedruleDao.selectRulesByActiveId(id);
        List<TMarketActivePic> pics = marketActivePicDao.selectPicsByActiveId(id);
        ActiveInfoVo vo = new ActiveInfoVo();
        vo.setId(active.getId());
        vo.setTitle(active.getTitle());
        vo.setType(active.getType());
        vo.setIntro(JSON.parseArray(active.getIntro()));
        vo.setBegtime(active.getBegtime());
        vo.setEndtime(active.getEndtime());
        vo.setRewardtype(active.getRewardtype());
        vo.setLimitcount(active.getLimitcount());
        vo.setFanslimit(active.getFanslimit());
        vo.setExaminetype(active.getExaminetype());
        vo.setOpentype(active.getOpentype());
        if (active.getExaminetype() == 1) {
            vo.setExaminehour(active.getExaminehour());
        }
        if (active.getRewardtype() == 0) {
            vo.setFixedmoney(active.getFixedmoney());
        } else if (active.getRewardtype() == 1) {
            vo.setMinrandmoney(active.getMinrandmoney());
            vo.setMaxrandmoney(active.getMaxrandmoney());
        } else {
            vo.setIntegral(active.getIntegral());
        }
        if (notEmpty(rule)) {
            if (rule.getOpenminlimit() == 1) {
                vo.setOpenminlimit(rule.getOpenminlimit());
                vo.setMinlimit(rule.getMinlimit());
            }
            if (rule.getOpenmidlimit() == 1) {
                vo.setOpenmidlimit(rule.getOpenmidlimit());
                vo.setMidlimitbeg(rule.getMidlimitbeg());
                vo.setMidlimitend(rule.getMidlimitend());
                if (vo.getMidmoneyback() != 0) {
                    vo.setMidmoneyback(rule.getMidmoneyback());
                } else {
                    vo.setMidrandmoneybeg(rule.getMidrandmoneybeg());
                    vo.setMidrandmoneyend(rule.getMidrandmoneyend());
                }
            }
            if (rule.getOpenmaxlimit() == 1) {
                vo.setOpenmaxlimit(rule.getOpenmaxlimit());
                vo.setMaxlimit(rule.getMaxlimit());
                if (rule.getMaxmoneyback() != 0) {
                    vo.setMaxmoneyback(rule.getMaxmoneyback());
                } else {
                    vo.setMaxrandmoneybeg(rule.getMaxrandmoneybeg());
                    vo.setMaxrandmoneyend(rule.getMaxrandmoneyend());
                }
            }
        }
        if (subRedrules.size() > 0) {
            HashMap<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActiveSubRedrule redrule : subRedrules) {
                map = new HashMap<>();
                map.put("openMidLimit", redrule.getOpenmidlimit());
                map.put("midLimitBeg", redrule.getMidlimitbeg());
                map.put("midLimitEnd", redrule.getMidlimitend());
                map.put("midMoneyBack", redrule.getMidmoneyback());
                map.put("midRandMoneyBeg", redrule.getMidrandmoneybeg());
                map.put("midRandMoneyEnd", redrule.getMidrandmoneyend());
                list.add(map);
            }
            vo.setRedRules(list);
        }
        if (notEmpty(pics) && pics.size() > 0) {
            HashMap<String, Object> map;
            List<Map<String, Object>> list = new ArrayList<>();
            for (TMarketActivePic pic : pics) {
                map = new HashMap<>();
                map.put("url", pic.getPicurl());
                map.put("title", pic.getTitle());
                list.add(map);
            }
            vo.setDemoPics(list);
        }
        return vo;
    }

    /**
     * 获取投票信息
     *
     * @param activeId
     * @param dykId
     * @return
     */
    public List<ResultOfVotingVo> gettpdatas(Long activeId, String dykId) {
        return marketActiveVoteDao.gettpdatas(activeId, dykId);
    }

    /**
     * 活动删除
     *
     * @param id
     */
    @Override
    public void deleteActive(long id) {
        TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
        if (empty(active))
            throw new BusinessException("活动不存在");
        if (active.getState() == 1)
            throw new BusinessException("活动进行中,无法删除,如果需要停止该活动,请在活动列表进行下线");
        if (active.getState() == 0) {//待上线,物理删除
            marketActiveDao.deleteByPrimaryKey(id);
            switch (active.getType()) {
                case 0:
                    marketActiveSubRedruleDao.deleteByPrimaryKey(active.getId());
                    marketActiveRedruleDao.deleteByPrimaryKey(active.getId());
                    marketActivePicDao.deletePic(active.getId());
                    break;
                case 1:
                    marketActivePicDao.deletePic(active.getId());
                    break;
                case 2:
                    marketActivePicDao.deletePic(active.getId());
                    break;
                case 3:
                    marketActiveBackorderDao.deleteBackOrder(active.getId());
                    break;
                case 4:
                    marketActivePicDao.deletePic(active.getId());
                    break;
                case 6:
                    marketActiveSignruleDao.deleteRuleByActiveId(active.getId(), active.getYoukeid());
                    break;
                case 7:
                    marketActiveLuckDao.deleteByPrimaryKey(active.getId());
                    break;
                case 8:
                    marketActivePicDao.deletePic(active.getId());
                    break;
            }
        } else {//已结束,逻辑删除
            active.setState(3);
            marketActiveDao.updateByPrimaryKeySelective(active);
        }
    }

    /**
     * 保存抽奖活动
     *
     * @param params
     */
    @Override
    public Long saveCjActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//通用活动保存
        JSONArray array = params.getPrizeObj();
        if (array.size() <= 0) {
            throw new BusinessException("请至少设置一个奖项");
        }
        if (array.size() > 8) {
            throw new BusinessException("奖项个数超出限制");
        }
        PrizeVo prize;
        int probability = 0;
        JSONObject jsonObject;
        List<PrizeVo> prizeList = new ArrayList<>();
        for (Object obj : array) {
            jsonObject = JSONObject.fromObject(obj);
            prize = new PrizeVo();
            prize.setId(IDUtil.getRandomIntId());
            prize.setPrizeName(jsonObject.getString("prizeName"));
            prize.setRewardType(jsonObject.getInt("rewardType"));
            prize.setRewardVal(jsonObject.getInt("rewardVal"));
            prize.setPercent(jsonObject.getDouble("percent"));
            prizeList.add(prize);
            probability += prize.getPercent();
        }
        if (probability > 100) {
            throw new BusinessException("总中奖率超过100%,建议重新设置中奖几率");
        }
        TMarketActiveLuck luck = marketActiveLuckDao.selectByPrimaryKey(active.getId());
        if (empty(luck)) {
            luck = new TMarketActiveLuck();
        }
        luck.setActiveid(active.getId());
        luck.setCostintegral(params.getCostIntegral());
        luck.setPrizeobj(com.alibaba.fastjson.JSONArray.toJSONString(prizeList));
        if (params.getId() == -1) {
            marketActiveLuckDao.insertSelective(luck);
        } else {
            marketActiveLuckDao.updateByPrimaryKeySelective(luck);
        }
        return active.getId();
    }

    /**
     * 保存海报活动
     *
     * @param params
     */
    public Long savehbActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//通用活动保存
        JSONArray array = params.getPosterPics();
        if (params.getId() != -1) {//更新
            //删除图片
            marketActivePicDao.deletePic(active.getId());
        }
        if (array.size() >= 2) {
            JSONObject jsonObject;
            TMarketActivePic marketActivePic;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                marketActivePic = new TMarketActivePic();
                marketActivePic.setActiveid(active.getId());
                marketActivePic.setYoukeid(params.getDykId());
                marketActivePic.setType(jsonObject.getInt("type"));
                marketActivePic.setTitle(jsonObject.getInt("type") == 0 ? "分享海报截图示例" : "海报");
                marketActivePic.setPicurl(jsonObject.getString("url"));
                marketActivePicDao.insertSelective(marketActivePic);
            }
        } else {
            throw new BusinessException("请至少上传两张图片,包括但不限于一张截图,一张海报");
        }
        return active.getId();
    }

    /**
     * 保存签到活动
     *
     * @param params
     */
    public Long saveqdActive(MarketActivitySaveVo params) {
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);
        JSONArray array = params.getSignRule();
        if (params.getId() != -1) {
            marketActiveSignruleDao.deleteRuleByActiveId(params.getId(), params.getDykId());//删除之前的规则
        }
        if (array.size() > 0) {
            JSONObject jsonObject;
            TMarketActiveSignrule rule;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                rule = new TMarketActiveSignrule();
                rule.setYoukeid(params.getDykId());
                rule.setActiveid(active.getId());
                rule.setRunday(jsonObject.getInt("runDay"));
                rule.setIntegral(jsonObject.getInt("integral"));
                marketActiveSignruleDao.insertSelective(rule);
            }
        } else {
            throw new BusinessException("请设置签到规则");
        }
        return active.getId();
    }


    /**
     * 保存首绑有礼活动
     *
     * @param params
     */
    public Long savesbActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//通用活动保存
        return active.getId();
    }

    /**
     * 保存投票测款活动
     *
     * @param params
     */
    public Long savetpActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());
        TMarketActive active = saveCommon(params);
        JSONArray array = params.getGoodPics();
        if (params.getId() != -1) {//更新
            //删除之前的旧图片
            marketActivePicDao.deletePic(active.getId());
        }
        if (array.size() >= 2) {
            JSONObject jsonObject;
            TMarketActivePic marketActivePic;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                marketActivePic = new TMarketActivePic();
                marketActivePic.setActiveid(active.getId());
                marketActivePic.setYoukeid(params.getDykId());
                marketActivePic.setType(1);
                marketActivePic.setPicurl(jsonObject.getString("url"));
                marketActivePic.setTitle(jsonObject.getString("title"));
                marketActivePicDao.insertSelective(marketActivePic);
            }
        } else {
            throw new BusinessException("请保证至少有两个或两个以上的投票商品");
        }
        return active.getId();
    }

    /**
     * 保存随时返活动
     *
     * @param params
     */
    public Long saveflActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//保存或更新基本信息
        if (params.getId() != -1) {//删除旧单号
            marketActiveBackorderDao.deleteBackOrder(active.getId());
        }
        if (notEmpty(params.getInputOrders())) {
            saveInputOrders(params, active.getId());
        }

        if (notEmpty(params.getFileUrl())) {
            saveImportOrders(active.getId(), params.getFileUrl(), params.getDykId());
        }
        return active.getId();
    }

    /**
     * 导入模板订单号保存
     *
     * @param activeId
     * @param csvUrl
     * @param dykId
     */
    @Override
    public void saveImportOrders(long activeId, String csvUrl, String dykId) {
        String key = csvUrl.replace(ApiCodeConstant.AUTH_DOMAIN + "/", "");
        try {
            if (FileUpOrDwUtil.isExist(key)) {
                ReadExcelUtils excelUtil = new ReadExcelUtils(key);
                Map<Integer, Map<Integer, Object>> maps = excelUtil.readExcelContent();
                Set<String> set = new HashSet<>();
                Map<String, Integer> map = new HashMap<>();
                TMarketActive active = marketActiveDao.selectByPrimaryKey(activeId);
                for (Map<Integer, Object> m : maps.values()) {
                    //过滤重复订单号
                    set.add(m.get(0).toString());
                    if (active.getRewardtype() == 0) {
                        //红包
                        map.put(m.get(0).toString(), new BigDecimal(m.get(1).toString()).multiply(new BigDecimal(100)).intValue());
                    } else {
                        //积分
                        map.put(m.get(0).toString(), new BigDecimal(m.get(1).toString()).intValue());
                    }
                }
                TMarketActiveBackorder backorder;
                for (String order : set) {
                    backorder = marketActiveBackorderDao.selectBackorderByOrderNo(order, activeId);
                    if (notEmpty(backorder)) {
                        marketActiveBackorderDao.deleteByPrimaryKey(backorder.getId());
                    }
                    backorder = new TMarketActiveBackorder();
                    backorder.setOrderno(order);
                    backorder.setMoney(map.get(order));
                    backorder.setState(0);
                    backorder.setCreatetime(new Date());
                    backorder.setActiveid(activeId);
                    backorder.setYoukeid(dykId);
                    backorder.setType(1);
                    marketActiveBackorderDao.insertSelective(backorder);
                }
            } else {
                throw new BusinessException("文件丢失,请重新上传");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 手动输入订单号保存
     *
     * @param params
     * @param activeId
     */
    @Override
    public void saveInputOrders(MarketActivitySaveVo params, Long activeId) {
        Set<String> set = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();
        if (notEmpty(params.getInputOrders())) {
            JSONArray array = params.getInputOrders();
            if (array.size() > 0) {
                JSONObject jsonObject;
                for (Object obj : array) {
                    jsonObject = JSONObject.fromObject(obj);
                    set.add(jsonObject.getString("orderno"));
                    map.put(jsonObject.getString("orderno"), jsonObject.getInt("price"));
                }
                TMarketActiveBackorder backorder;
                for (String order : set) {
                    backorder = marketActiveBackorderDao.selectBackorderByOrderNo(order, activeId);
                    if (notEmpty(backorder)) {
                        marketActiveBackorderDao.deleteByPrimaryKey(backorder.getId());
                    }
                    backorder = new TMarketActiveBackorder();
                    backorder.setOrderno(order);
                    backorder.setMoney(map.get(order));
                    backorder.setCreatetime(new Date());
                    backorder.setActiveid(activeId);
                    backorder.setYoukeid(params.getDykId());
                    backorder.setType(0);
                    backorder.setState(0);
                    marketActiveBackorderDao.insertSelective(backorder);
                }
            } else {
                throw new BusinessException("请填写返现单号");
            }
        } else {
            throw new BusinessException("无效的请求");
        }
    }

    /**
     * 保存晒图有礼活动
     *
     * @param params
     */
    public Long savestActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());
        TMarketActive active = saveCommon(params);//通用活动保存
        JSONArray array = params.getDemoPics();
        if (params.getId() != -1) {//更新删除旧图片
            marketActivePicDao.deletePic(active.getId());
        }
        if (array.size() >= 1) {
            JSONObject jsonObject;
            TMarketActivePic marketActivePic;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                marketActivePic = new TMarketActivePic();
                marketActivePic.setActiveid(active.getId());
                marketActivePic.setType(0);
                marketActivePic.setYoukeid(params.getDykId());
                marketActivePic.setPicurl(jsonObject.getString("url"));
                if (jsonObject.has("title")) {
                    marketActivePic.setTitle(jsonObject.getString("title"));
                } else {
                    marketActivePic.setTitle("示例截图");
                }
                marketActivePicDao.insertSelective(marketActivePic);
            }
        } else {
            throw new BusinessException("请上传您的的活动示例截图");
        }
        return active.getId();
    }

    /**
     * 追评
     *
     * @param params
     */
    public Long saveZPActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//通用活动保存
        JSONArray array = params.getDemoPics();
        if (params.getId() != -1) {//更新删除旧图片
            marketActivePicDao.deletePic(active.getId());
        }
        if (array.size() >= 1) {
            JSONObject jsonObject;
            TMarketActivePic marketActivePic;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                marketActivePic = new TMarketActivePic();
                marketActivePic.setType(0);//截图
                marketActivePic.setActiveid(active.getId());
                marketActivePic.setYoukeid(params.getDykId());
                marketActivePic.setPicurl(jsonObject.getString("url"));
                if (jsonObject.has("title")) {//如果有标题,则保存
                    marketActivePic.setTitle(jsonObject.getString("title"));
                } else {
                    marketActivePic.setTitle("示例截图");
                }
                marketActivePicDao.insertSelective(marketActivePic);
            }
        } else {
            throw new BusinessException("请上传您的的活动示例截图");
        }
        return active.getId();
    }

    /**
     * 晒单
     *
     * @param params
     */
    public Long savesdActive(MarketActivitySaveVo params) {
        payStateCheck(params.getAppId());//支付状态检测
        activeCheck(params.getId(), params.getAppId(), params.getType(), params.getBegTime(), params.getEndTime());//活动检测
        TMarketActive active = saveCommon(params);//通用活动保存
        JSONArray array = params.getDemoPics();
        if (params.getId() != -1) {//更新删除旧图片
            marketActivePicDao.deletePic(active.getId());
        }
        if (array.size() >= 1) {
            JSONObject jsonObject;
            TMarketActivePic marketActivePic;
            for (Object obj : array) {
                jsonObject = JSONObject.fromObject(obj);
                marketActivePic = new TMarketActivePic();
                marketActivePic.setType(0);//截图
                marketActivePic.setActiveid(active.getId());
                marketActivePic.setPicurl(jsonObject.getString("url"));
                marketActivePic.setYoukeid(params.getDykId());
                if (jsonObject.has("title")) {//有标题则保存
                    marketActivePic.setTitle(jsonObject.getString("title"));
                } else {
                    marketActivePic.setTitle("示例截图");
                }
                marketActivePicDao.insertSelective(marketActivePic);
            }
        }
        TMarketActiveRedrule rule = new TMarketActiveRedrule();
        rule.setActiveid(active.getId());
        rule.setOpenminlimit(params.getOpenMinLimit());
        rule.setOpenmaxlimit(params.getOpenMaxLimit());

        if (params.getOpenMinLimit() == 1) {
            rule.setMinlimit(params.getMinLimit());
            rule.setMinmoneyback(params.getMinMoneyBack());
        }
        if (params.getOpenMaxLimit() == 1) {
            rule.setMaxlimit(params.getMaxLimit());
            if (params.getMaxMoneyBack() == 0) {//区间
                rule.setMaxrandmoneybeg(params.getMaxRandMoneyBeg());
                rule.setMaxrandmoneyend(params.getMaxRandMoneyEnd());
            } else {//固定
                rule.setMaxmoneyback(params.getMaxMoneyBack());
            }
        }
        if (params.getId() != -1) {//编辑晒单时删除之前的区间匹配规则
            marketActiveSubRedruleDao.deleteByPrimaryKey(active.getId());
        }
        JSONObject jsonObject;
        TMarketActiveSubRedrule subRedrule;
        if (params.getRedRules().size() > 0) {
            for (Object obj : params.getRedRules()) {
                jsonObject = JSONObject.fromObject(obj);
                Integer openMidLimit = (Integer) jsonObject.get("openMidLimit");
                Integer midLimitBeg = (Integer) jsonObject.get("midLimitBeg");
                Integer midLimitEnd = (Integer) jsonObject.get("midLimitEnd");
                Integer midMoneyBack = (Integer) jsonObject.get("midMoneyBack");
                Integer midRandMoneyBeg = (Integer) jsonObject.get("midRandMoneyBeg");
                Integer midRandMoneyEnd = (Integer) jsonObject.get("midRandMoneyEnd");
                subRedrule = new TMarketActiveSubRedrule();
                subRedrule.setActiveid(active.getId());
                subRedrule.setOpenmidlimit(openMidLimit);
                subRedrule.setMidlimitbeg(midLimitBeg);
                subRedrule.setMidlimitend(midLimitEnd);
                subRedrule.setMidrandmoneybeg(midRandMoneyBeg);
                subRedrule.setMidrandmoneyend(midRandMoneyEnd);
                subRedrule.setMidmoneyback(midMoneyBack);
                marketActiveSubRedruleDao.insertSelective(subRedrule);
            }
        }
        if (params.getId() == -1) {
            marketActiveRedruleDao.insertSelective(rule);
        } else {
            marketActiveRedruleDao.updateByPrimaryKeySelective(rule);
        }
        return active.getId();
    }


    /**
     * 通用活动保存
     *
     * @param params
     * @return
     */
    public TMarketActive saveCommon(MarketActivitySaveVo params) {
        TMarketActive active = new TMarketActive();
        if (empty(params.getTitle())) {
            throw new BusinessException("活动标题不能为空");
        }
        active.setTitle(StringUtil.deleteSpace(params.getTitle()));
        if (empty(params.getBegTime()) || empty(params.getEndTime())) {
            throw new BusinessException("活动开始或结束时间不能为空");
        }
        active.setBegtime(DateUtil.parseDate(params.getBegTime()));
        active.setEndtime(DateUtil.parseDate(params.getEndTime()));
        if (empty(params.getIntro())) {
            throw new BusinessException("活动说明不能为空");
        }
        active.setIntro(JSON.toJSONString(params.getIntro()));

        if (notEmpty(params.getRewardType())) {
            active.setRewardtype(params.getRewardType());
            if (params.getRewardType() == 0) {
                if (notEmpty(params.getFixedMoney())) {
                    if (params.getFixedMoney() > 0 && params.getFixedMoney() < 200000) {
                        active.setFixedmoney(params.getFixedMoney());
                    } else {
                        throw new BusinessException("固定红包金额设置需要大于0元,小于或等于2000元");
                    }
                }
            } else if (params.getRewardType() == 1) {
                if (notEmpty(params.getMaxRandMoney()) && notEmpty(params.getMinRandMoney())) {
                    if (params.getMaxRandMoney() < 200000 && params.getMaxRandMoney() > 0 && params.getMinRandMoney() < 200000 && params.getMinRandMoney() > 0) {
                        active.setMaxrandmoney(params.getMaxRandMoney());
                        active.setMinrandmoney(params.getMinRandMoney());
                    } else {
                        throw new BusinessException("随机金额红包设置需要大于0元,小于或等于2000元");
                    }
                }
            } else {
                if (notEmpty(params.getIntegral())) {
                    if (params.getIntegral() > 0 && params.getIntegral() < 99999999) {
                        active.setIntegral(params.getIntegral());
                    } else {
                        throw new BusinessException("积分数额设置需要大于0分,小于99999999分");
                    }
                }
            }
        }
        if (notEmpty(params.getExamineType())) {
            active.setExaminetype(params.getExamineType());
            if (params.getExamineType() == 1) {
                active.setExaminehour(params.getExamineHour());
            }
        } else {
            active.setExaminetype(0);
        }
        if (notEmpty(params.getMaxJoin())) {
            active.setMaxjoin(params.getMaxJoin());
        }
        if (notEmpty(params.getFansLimit())) {
            active.setFanslimit(params.getFansLimit());
        }
        if (notEmpty(params.getLimitCount())) {
            active.setLimitcount(params.getLimitCount());
        }
        if (notEmpty(params.getFileUrl())) {
            active.setFileurl(params.getFileUrl());
        } else {
            active.setFileurl("");
        }
        if (notEmpty(params.getFileName())) {
            active.setFilename(params.getFileName());
        } else {
            active.setFilename("");
        }

        active.setState(0);
        active.setType(params.getType());
        active.setAppid(params.getAppId());
        active.setYoukeid(params.getDykId());

        if (params.getId() == -1) {
            active.setCreatetime(new Date());
            marketActiveDao.insertSelective(active);
        } else {
            active.setId(params.getId());
            active.setUpdateTime(new Date());
            marketActiveDao.updateByPrimaryKeySelective(active);
        }
        return active;
    }

    /**
     * 活动检测
     *
     * @param id      活动id
     * @param appId
     * @param type    活动类型
     * @param begTime 活动开始时间
     * @param endTime 活动结束时间
     */
    private void activeCheck(Long id, String appId, int type, String begTime, String endTime) {
        if (empty(begTime) || empty(endTime)) {
            throw new BusinessException("活动时间不能为空");
        } else {
            if (DateUtil.parseDate(begTime).getTime() > DateUtil.parseDate(endTime).getTime()) {
                throw new BusinessException("活动开始时间不能大于结束时间");
            }
        }
        if (id != -1) {//编辑
            TMarketActive active = marketActiveDao.selectByPrimaryKey(id);
            if (notEmpty(active)) {
                if (active.getState() != 0) {
                    throw new BusinessException("只能编辑待上线的活动");
                }
                //查询除了本活动之外的所有同类型活动
                List<TMarketActive> list = marketActiveDao.selectActiveWithOutThis(appId, type, active.getId());
                if (notEmpty(list) && list.size() > 0) {
                    for (TMarketActive activity : list) {
                        if (DateUtil.isOverlap(DateUtil.formatDateTime(activity.getBegtime()), DateUtil.formatDateTime(activity.getEndtime()), begTime, endTime)) {
                            throw new BusinessException("当前活动时间范围，存在进行中或待上线的活动，请修改活动时间。");
                        }
                    }
                }
            }
        } else {//创建
            //查询所有同类型活动
            List<TMarketActive> list = marketActiveDao.selectAllActive(appId, type);
            if (notEmpty(list) && list.size() > 0) {
                for (TMarketActive active : list) {
                    if (DateUtil.isOverlap(DateUtil.formatDateTime(active.getBegtime()), DateUtil.formatDateTime(active.getEndtime()), begTime, endTime)) {
                        throw new BusinessException("当前活动时间范围，存在进行中或待上线的活动，请修改活动时间。");
                    }
                }
            }
        }
    }

    /**
     * 支付检测
     *
     * @param appId
     */
    private void payStateCheck(String appId) {
        int state = subscrConfigDao.selectPayState(appId);
        if (empty(state) || state == 0) {
            throw new BusinessException("尚未设置绑定微信支付");
        }
    }

    /**
     * 更新转化数据
     *
     * @param activeId
     * @param winNum
     */
    private void updateTranChartData(Long activeId, int bookNum, int winNum) {
        ActiveTranChartVo message = new ActiveTranChartVo();
        message.setActiveId(activeId);
        message.setBookNum(bookNum);
        message.setWinNum(winNum);
        activeMessageProducer.send("tranchart.queue", message);
    }
}

