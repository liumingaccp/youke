package youke.service.user.queue.consumer;

import org.springframework.stereotype.Component;
import youke.common.constants.ComeType;
import youke.common.dao.IMarketActiveDao;
import youke.common.dao.IMarketActiveRecordDao;
import youke.common.dao.IShopOrderDao;
import youke.common.dao.ISubscrFansDao;
import youke.common.model.TMarketActive;
import youke.common.model.TMarketActiveRecord;
import youke.common.model.TSubscrFans;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.utils.MoneyUtil;
import youke.service.user.queue.producer.QueueSender;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Date;

@Component
public class SbActiveConsumer implements MessageListener {

    @Resource
    private QueueSender queueSender;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IMarketActiveDao marketActiveDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        ActiveMassMessage massMessage;
        try {
            String openId = (String) objectMessage.getObject();
            TSubscrFans fans = subscrFansDao.selectByPrimaryKey(openId);
            TMarketActive active = marketActiveDao.selectUnderWaySbActive(fans.getAppid(), 5);
            if (active != null) {
                if (active.getFanslimit() == 1) {
                    Long id = shopOrderDao.selectOrderByMobile(fans.getMobile(), active.getYoukeid());
                    if (id == null) {
                        return;
                    }
                }
                massMessage = new ActiveMassMessage();
                TMarketActiveRecord record = new TMarketActiveRecord();
                record.setWxfansname(fans.getNickname());
                record.setExaminetype(active.getExaminetype());
                record.setCreatetime(new Date());
                record.setAppid(fans.getAppid());
                record.setOpenid(openId);
                record.setState(1);
                record.setActiveid(active.getId());
                record.setYoukeid(active.getYoukeid());
                record.setRewardtype(active.getRewardtype());
                if (active.getRewardtype() == 0) {
                    record.setMoney(active.getFixedmoney());
                } else if (active.getRewardtype() == 1) {
                    record.setMoney(MoneyUtil.getRandomMoney(active.getMinrandmoney(), active.getMaxrandmoney()));
                } else {
                    record.setIntegral(active.getIntegral());
                }
                if (record.getIntegral() != null) {//积分
                    massMessage.setIntegral(record.getIntegral());
                }
                if (record.getMoney() != null) {//红包
                    massMessage.setMoney(record.getMoney());
                }
                marketActiveRecordDao.insertSelective(record);
                massMessage.setRecordId(record.getId());
                massMessage.setAppId(active.getAppid());
                massMessage.setOpenId(openId);
                massMessage.setTitle(ComeType.COME_TYPE.get(ComeType.SHOU_BANG_YOU_LI));
                massMessage.setState(1);
                queueSender.send("activemass.queue", massMessage);
                queueSender.send("activeexamine.queue", massMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
