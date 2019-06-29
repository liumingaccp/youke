package youke.service.market.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.model.vo.result.ActiveTranChartVo;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.DayUtil;
import youke.facade.market.vo.MarketConstant;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TranChartMessageConsumer implements MessageListener {

    @Override
    @SuppressWarnings("all")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            ActiveTranChartVo massMessage = (ActiveTranChartVo) objectMessage.getObject();
            Map<String, Object> map = new HashMap<>();
            List<ActiveTranChartVo> trancharts;
            List<ActiveTranChartVo> list;
            ActiveTranChartVo vo;
            boolean flag = true;
            if (RedisUtil.hasKey(MarketConstant.KEY_TRANCHART)) {
                trancharts = (List<ActiveTranChartVo>) RedisUtil.hget(MarketConstant.KEY_TRANCHART, +massMessage.getActiveId() + "");
                if (trancharts != null && trancharts.size() > 0) {
                    list = trancharts.stream().filter(chart -> DayUtil.isToday(chart.getDate())).collect(Collectors.toList());
                    if (list != null && list.size() >= 1) {
                        vo = list.get(0);
                        vo.setActiveId(massMessage.getActiveId());
                        vo.setBookNum(vo.getBookNum() + massMessage.getBookNum());
                        vo.setWinNum(vo.getWinNum() + massMessage.getWinNum());
                        RedisUtil.hset(MarketConstant.KEY_TRANCHART, massMessage.getActiveId() + "", trancharts);
                    } else {
                        vo = new ActiveTranChartVo();
                        vo.setActiveId(massMessage.getActiveId());
                        vo.setDate(DateUtil.formatDate(new Date()));
                        vo.setBookNum(massMessage.getBookNum());
                        vo.setWinNum(massMessage.getWinNum());
                        trancharts.add(vo);
                        RedisUtil.hset(MarketConstant.KEY_TRANCHART, massMessage.getActiveId() + "", trancharts);
                    }
                } else {
                    vo = new ActiveTranChartVo();
                    trancharts = new ArrayList<>();
                    vo.setActiveId(massMessage.getActiveId());
                    vo.setDate(DateUtil.formatDate(new Date()));
                    vo.setBookNum(massMessage.getBookNum());
                    vo.setWinNum(massMessage.getWinNum());
                    trancharts.add(vo);
                    map.put(massMessage.getActiveId() + "", trancharts);
                    RedisUtil.hmset(MarketConstant.KEY_TRANCHART, map);
                }
            } else {
                vo = new ActiveTranChartVo();
                trancharts = new ArrayList<>();
                vo.setActiveId(massMessage.getActiveId());
                vo.setDate(DateUtil.formatDate(new Date()));
                vo.setBookNum(massMessage.getBookNum());
                vo.setWinNum(massMessage.getWinNum());
                trancharts.add(vo);
                map.put(massMessage.getActiveId() + "", trancharts);
                RedisUtil.hmset(MarketConstant.KEY_TRANCHART, map);
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
