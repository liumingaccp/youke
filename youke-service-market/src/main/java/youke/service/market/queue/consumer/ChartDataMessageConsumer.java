package youke.service.market.queue.consumer;

import org.springframework.stereotype.Component;
import youke.common.dao.IMarketActiveDao;
import youke.common.model.TMarketActive;
import youke.common.model.vo.result.ActiveChartVo;
import youke.common.queue.message.ActiveChartMassMessage;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.DayUtil;
import youke.facade.market.vo.MarketConstant;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChartDataMessageConsumer implements MessageListener {

    @Resource
    private IMarketActiveDao marketActiveDao;

    @Override
    @SuppressWarnings("all")
    public void onMessage(Message message) {
        int uv = 1;
        List<String> openIds;
        ActiveChartMassMessage massMessage;
        Map<String, Object> hmap = new HashMap<>();
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            massMessage = (ActiveChartMassMessage) objectMessage.getObject();
            if (RedisUtil.hasKey(MarketConstant.KEY_RECORD)) {
                openIds = (List<String>) RedisUtil.hget(MarketConstant.KEY_RECORD, massMessage.getActiveId() + "");
                if (openIds != null && openIds.size() > 0) {
                    if (openIds.contains(massMessage.getOpenId())) {
                        uv = 0;
                    } else {
                        openIds.add(massMessage.getOpenId());
                        RedisUtil.hset(MarketConstant.KEY_RECORD, massMessage.getActiveId() + "", openIds, DateUtil.getRemainSecondsOneDay(new Date()));
                    }
                } else {
                    openIds = new ArrayList<>();
                    openIds.add(massMessage.getOpenId());
                    RedisUtil.hset(MarketConstant.KEY_RECORD, massMessage.getActiveId() + "", openIds, DateUtil.getRemainSecondsOneDay(new Date()));
                }
            } else {
                RedisUtil.hmset(MarketConstant.KEY_RECORD, hmap);
                openIds = new ArrayList<>();
                openIds.add(massMessage.getOpenId());
                RedisUtil.hset(MarketConstant.KEY_RECORD, massMessage.getActiveId() + "", openIds, DateUtil.getRemainSecondsOneDay(new Date()));
            }
            TMarketActive active = marketActiveDao.selectByPrimaryKey(massMessage.getActiveId());
            if (active != null) {
                active.setPv(active.getPv() + 1);
                active.setUv(active.getUv() + uv);
                marketActiveDao.updateByPrimaryKeySelective(active);
            }
            Map<String, Object> map = new HashMap<>();
            List<ActiveChartVo> charts;
            List<ActiveChartVo> list;
            ActiveChartVo vo;
            if (RedisUtil.hasKey(MarketConstant.KEY_CHART)) {
                charts = (List<ActiveChartVo>) RedisUtil.hget(MarketConstant.KEY_CHART, massMessage.getActiveId() + "");
                if (charts != null && charts.size() > 0) {
                    list = charts.stream().filter(chart -> DayUtil.isToday(chart.getDate())).collect(Collectors.toList());
                    if (list != null && list.size() >= 1) {
                        vo = list.get(0);
                        vo.setPv(vo.getPv() + 1);
                        vo.setUv(vo.getUv() + uv);
                        RedisUtil.hset(MarketConstant.KEY_CHART, massMessage.getActiveId() + "", charts);
                    } else {
                        vo = new ActiveChartVo();
                        vo.setPv(1);
                        vo.setUv(uv);
                        vo.setDate(DateUtil.formatDate(new Date()));
                        charts.add(vo);
                        RedisUtil.hset(MarketConstant.KEY_CHART, massMessage.getActiveId() + "", charts);
                    }
                } else {
                    vo = new ActiveChartVo();
                    charts = new ArrayList<>();
                    vo.setPv(1);
                    vo.setUv(1);
                    vo.setDate(DateUtil.formatDate(new Date()));
                    charts.add(vo);
                    map.put(massMessage.getActiveId() + "", charts);
                    RedisUtil.hmset(MarketConstant.KEY_CHART, map);
                }
            } else {
                vo = new ActiveChartVo();
                charts = new ArrayList<>();
                vo.setPv(1);
                vo.setUv(1);
                vo.setDate(DateUtil.formatDate(new Date()));
                charts.add(vo);
                map.put(massMessage.getActiveId() + "", charts);
                RedisUtil.hmset(MarketConstant.KEY_CHART, map);
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
