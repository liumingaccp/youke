package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.queue.message.TempMassMessage;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class TempMessageConsumer implements MessageListener {

    @Resource
    private IWeixinMessageService weixinMessageService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            TempMassMessage massMessage = (TempMassMessage) objectMessage.getObject();
            if (massMessage.getMoney() != null && massMessage.getMoney() > 0) {
                weixinMessageService.sendTempMoneyGain(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getComeType(), massMessage.getTitle(), massMessage.getMoney());
            }
            if (massMessage.getIntegral() != null) {
                if (massMessage.getIntegral() > 0) {
                    weixinMessageService.sendTempIntegralGain(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getComeType(), massMessage.getTitle(), massMessage.getIntegral());
                } else if (massMessage.getIntegral() < 0) {
                    weixinMessageService.sendTempIntegralConsume(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getComeType(), massMessage.getTitle(), Math.abs(massMessage.getIntegral()));
                }
            }
            message.acknowledge();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
