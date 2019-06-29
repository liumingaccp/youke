package youke.core.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.common.mybatis.support.SQLHelp;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.facade.market.vo.MarketConstant;
import youke.facade.user.vo.FansChartDateVo;
import youke.facade.user.vo.FansDataVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class HomeService {
    private static Logger logger = LoggerFactory.getLogger(SQLHelp.class);

    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IShopFansDao shopFansDao;
    @Resource
    private ISubscrDao subscrDao;

    /**
     * 刷新首页数据
     */
    public void doRefreshHomeData() {
        logger.info("首页数据同步开始" + System.currentTimeMillis());
        Map<String, Object> map;
        Map<String, Object> hmap = new HashMap<>();
        List<Map> list = subscrDao.selectAll();
        if (list != null && list.size() > 0) {
            for (Map subscr : list) {
                map = new HashMap<>();
                refreshAddFansNum((String) subscr.get("appId"), map);
                refreshActiveFansNum((String) subscr.get("appId"), map);
                refreshClientToFansNum((String) subscr.get("appId"), map);

                refreshBuyBackNum((String) subscr.get("youkeId"), map);
                refreshFansSale((String) subscr.get("youkeId"), map);
                refreshFansBuyProbability((String) subscr.get("youkeId"), map);
                hmap.put((String) subscr.get("youkeId"), map);
                RedisUtil.hmset(MarketConstant.KEY_HOME_DATA, hmap);
            }
        }
        logger.info("首页数据同步结束" + System.currentTimeMillis());
    }

    /**
     * 刷新图表数据
     */
    public void doUpdateFansChartData() {
        logger.info("首页图表数据同步开始" + System.currentTimeMillis());
        String date = DateUtil.formatDate(DateUtil.getDateBefore(new Date(), 1));
        HashMap<String, Object> map;
        List<Map> list = subscrDao.selectAll();
        if (list != null && list.size() > 0) {
            for (Map subscr : list) {
                map = new HashMap<>();
                updateFansChart((String) subscr.get("appId"), (String) subscr.get("youkeId"), date, map);
                updateAmountChart((String) subscr.get("youkeId"), date, map);
                updateBuyerChart((String) subscr.get("youkeId"), date, map);
                logger.info("首页图表数据同步结束" + System.currentTimeMillis());
            }
        }
    }

    /**
     * 更新购物粉丝图表数据
     *
     * @param dykId
     * @param date
     */
    @SuppressWarnings("all")
    private void updateBuyerChart(String dykId, String date, HashMap<String, Object> map) {
        int value = shopFansDao.selectBuyBackFansNumForYesterday(dykId);
        FansChartDateVo data;
        List<FansChartDateVo> buyers;
        if (RedisUtil.hasKey(MarketConstant.KEY_HOME_CHART + 2)) {
            buyers = (List<FansChartDateVo>) RedisUtil.hget(MarketConstant.KEY_HOME_CHART + 2, dykId);
            if (buyers != null && buyers.size() > 0) {
                data = new FansChartDateVo();
                data.setVal(value);
                data.setName(date);
                buyers.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 2, dykId, buyers);
            } else {
                data = new FansChartDateVo();
                buyers = new ArrayList<>();
                data.setVal(value);
                data.setName(date);
                buyers.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 2, dykId, buyers);
            }
        } else {
            data = new FansChartDateVo();
            buyers = new ArrayList<>();
            data.setVal(value);
            data.setName(date);
            buyers.add(data);
            map.put(dykId, buyers);
            RedisUtil.hmset(MarketConstant.KEY_HOME_CHART + 2, map);
        }
    }

    /**
     * 更新交易金额图表数据
     *
     * @param dykId
     * @param date
     */
    @SuppressWarnings("all")
    private void updateAmountChart(String dykId, String date, HashMap<String, Object> map) {
        int value = shopOrderDao.getTurnoverForYesterday(dykId);
        FansChartDateVo data;
        List<FansChartDateVo> amounts;
        if (RedisUtil.hasKey(MarketConstant.KEY_HOME_CHART + 1)) {
            amounts = (List<FansChartDateVo>) RedisUtil.hget(MarketConstant.KEY_HOME_CHART + 1, dykId);
            if (amounts != null && amounts.size() > 0) {
                data = new FansChartDateVo();
                data.setVal(value);
                data.setName(date);
                amounts.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 1, dykId, amounts);
            } else {
                data = new FansChartDateVo();
                amounts = new ArrayList<>();
                data.setVal(value);
                data.setName(date);
                amounts.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 1, dykId, amounts);
            }
        } else {
            data = new FansChartDateVo();
            amounts = new ArrayList<>();
            data.setVal(value);
            data.setName(date);
            amounts.add(data);
            map.put(dykId, amounts);
            RedisUtil.hmset(MarketConstant.KEY_HOME_CHART + 1, map);
        }
    }

    /**
     * 更新粉丝图表数据
     *
     * @param appId
     * @param date
     */
    @SuppressWarnings("all")
    private void updateFansChart(String appId, String dykId, String date, HashMap<String, Object> map) {
        int value = subscrFansDao.getAddFansNumForYesterday(appId);
        FansChartDateVo data;
        List<FansChartDateVo> charts;
        if (RedisUtil.hasKey(MarketConstant.KEY_HOME_CHART + 0)) {
            charts = (List<FansChartDateVo>) RedisUtil.hget(MarketConstant.KEY_HOME_CHART + 0, dykId);
            if (charts != null && charts.size() > 0) {
                data = new FansChartDateVo();
                data.setVal(value);
                data.setName(date);
                charts.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 0, dykId, charts);
            } else {
                data = new FansChartDateVo();
                charts = new ArrayList<>();
                data.setVal(value);
                data.setName(date);
                charts.add(data);
                RedisUtil.hset(MarketConstant.KEY_HOME_CHART + 0, dykId, charts);
            }
        } else {
            data = new FansChartDateVo();
            charts = new ArrayList<>();
            data.setVal(value);
            data.setName(date);
            charts.add(data);
            map.put(dykId, charts);
            RedisUtil.hmset(MarketConstant.KEY_HOME_CHART + 0, map);
        }
    }

    /**
     * 刷新昨日新增粉丝数据
     *
     * @param appId
     * @param map
     */
    private void refreshAddFansNum(String appId, Map<String, Object> map) {
        int num = subscrFansDao.queryForCount(appId);
        FansDataVo data = new FansDataVo();
        if (num > 0) {
            int num1 = subscrFansDao.getAddFansNumForYesterday(appId);//昨天新增粉丝数
            System.out.println("昨天新增粉丝数:" + num1);
            int num2 = subscrFansDao.getAddFansNumForTheDayBeforeYesterday(appId);//前天新增粉丝数
            System.out.println("前天新增粉丝数:" + num2);
            data.setVal(String.valueOf(num1));
            data.setPercent(getPercent(num1, num2));
            data.setType(num1 - num2 == 0 ? 2 : num1 - num2 > 0 ? 0 : 1);
        } else {
            data.setVal("0");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("ZRNewFansNum", data);
    }

    /**
     * 刷新昨日活跃粉丝数据
     *
     * @param appId
     * @param map
     */
    private void refreshActiveFansNum(String appId, Map<String, Object> map) {
        int num = subscrFansDao.queryForCount(appId);
        FansDataVo data = new FansDataVo();
        if (num > 0) {
            int num1 = subscrFansDao.getActiveFansNumForYesterday(appId);//昨日活跃粉丝数
            System.out.println("昨日活跃粉丝数:" + num1);
            int num2 = subscrFansDao.getActiveFansNumForTheDayBeforeYesterday(appId);//前日活跃粉丝数
            System.out.println("前日活跃粉丝数:" + num2);
            data.setVal(String.valueOf(num1));
            data.setPercent(getPercent(num1, num2));
            data.setType(num1 - num2 == 0 ? 2 : num1 - num2 > 0 ? 0 : 1);
        } else {
            data.setVal("0");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("ZRActiveFansNum", data);
    }

    /**
     * 刷新近七日粉丝回购数据
     *
     * @param dykId
     * @param map
     */
    private void refreshBuyBackNum(String dykId, Map<String, Object> map) {
        int num = shopFansDao.queryForCount(dykId);
        FansDataVo data = new FansDataVo();
        if (num > 0) {
            int num1 = shopFansDao.selectBuyBackFansNumForSevenDays(dykId);//近七日粉丝回购数量
            System.out.println("近七日粉丝回购数量:" + num1);
            int num2 = shopFansDao.selectBuyBackFansNumForElevenDays(dykId) - num1;//前一个七日粉丝回购数量
            System.out.println("前一个七日粉丝回购数量:" + num2);
            data.setVal(String.valueOf(num1));
            data.setPercent(getPercent(num1, num2));
            data.setType(num1 - num2 == 0 ? 2 : num1 - num2 > 0 ? 0 : 1);
        } else {
            data.setVal("0");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("WeekREFansNum", data);
    }

    /**
     * 刷新近七日客转粉数据
     *
     * @param appId
     * @param map
     */
    private void refreshClientToFansNum(String appId, Map<String, Object> map) {
        int num = subscrFansDao.queryForCount(appId);
        FansDataVo data = new FansDataVo();
        if (num > 0) {
            int num1 = subscrFansDao.getAddFansNumForSevenDays(appId);//近七日客转粉数量
            System.out.println("近七日客转粉数量:" + num1);
            int num2 = subscrFansDao.getAddFansNumForElevenDays(appId) - num1;//前一个近七日客转粉数量
            System.out.println("前一个近七日客转粉数量:" + num2);
            data.setVal(String.valueOf(num1));
            data.setPercent(getPercent(num1, num2));
            data.setType(num1 - num2 == 0 ? 2 : num1 - num2 > 0 ? 0 : 1);
        } else {
            data.setVal("0");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("WeekSTOFansNum", data);
    }

    /**
     * 更新近七日粉丝贡献销售额数据
     *
     * @param dykId
     * @param map
     */
    private void refreshFansSale(String dykId, Map<String, Object> map) {
        int count = shopFansDao.queryForCount(dykId);
        FansDataVo data = new FansDataVo();
        if (count > 0) {
            int d1 = shopOrderDao.getFansPaymentAmountForSevenDays(dykId);//近七日粉丝总贡献销售额
            System.out.println("近七日粉丝总贡献销售额:" + d1);
            int d2 = shopOrderDao.getFansPaymentAmountForElevenDays(dykId) - d1;//前一个七日粉丝总贡献销售额
            System.out.println("前一个七日粉丝总贡献销售额:" + d2);
            data.setVal(String.valueOf(d1));
            data.setPercent(getPercent(d1, d2));
            data.setType(d1 - d2 == 0 ? 2 : d1 - d2 > 0 ? 0 : 1);
        } else {
            data.setVal("0");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("WeekSaleNum", data);
    }

    /**
     * 更新近7日粉丝复购率数据
     *
     * @param dykId
     * @param map
     */
    private void refreshFansBuyProbability(String dykId, Map<String, Object> map) {
        int count = shopFansDao.queryForCount(dykId);
        FansDataVo data = new FansDataVo();
        if (count > 0) {
            String percent1 = shopFansDao.selectBuyBackProbabilityForSevenDays(dykId);//近七日粉丝复购率
            System.out.println("近七日粉丝复购率:" + percent1);
            String percent2 = shopFansDao.selectBuyBackProbabilityForElevenDays(dykId);//前一个近七日粉丝复购率
            System.out.println("前一个近七日粉丝复购率:" + percent2);
            data.setVal(percent1);
            data.setPercent(subtractPercent(percent1, percent2));
            int num = comparePercent(percent1, percent2);
            data.setType(num == 0 ? 2 : num == 1 ? 0 : 1);
        } else {
            data.setVal("0.00%");
            data.setPercent("0.00%");
            data.setType(2);
        }
        map.put("WeekREPercent", data);
    }
//======================================================================================================================

    /**
     * 计算数字增长率
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public static String getPercent(int numerator, int denominator) {
        DecimalFormat df = new DecimalFormat("0.00%");
        if (denominator == 0) {
            return "100%";
        }
        double num1 = numerator * 1.0;
        double num2 = denominator * 1.0;
        double result = Math.abs((num1 - num2)) / num2;
        return df.format(result);
    }


    /**
     * 百分比比较大小
     *
     * @param percent1
     * @param percent2
     * @return 大于为1，相同为0，小于为-1
     */
    public static int comparePercent(String percent1, String percent2) {
        String tempA = percent1.replace("%", "");
        String tempB = percent2.replace("%", "");
        BigDecimal dataA = new BigDecimal(tempA);
        BigDecimal dataB = new BigDecimal(tempB);
        return dataA.compareTo(dataB);
    }

    /**
     * 计算百分比差值
     *
     * @param percent1
     * @param percent2
     * @return
     */
    public static String subtractPercent(String percent1, String percent2) {
        DecimalFormat df = new DecimalFormat("0.00%");
        String tempA = percent1.replace("%", "");
        String tempB = percent2.replace("%", "");
        Float f1 = new Float(tempA) / 100;
        Float f2 = new Float(tempB) / 100;
        BigDecimal num1 = new BigDecimal(f1);
        BigDecimal num2 = new BigDecimal(f2);
        return df.format(Math.abs(num1.subtract(num2).doubleValue()));
    }
}
