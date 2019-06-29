package youke.service.mass.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.queue.message.PayFailMessage;
import youke.facade.mass.provider.IMobmsgService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class PayFailConsumer implements MessageListener {
    @Resource
    private IMobmsgService mobmsgService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            PayFailMessage obj = (PayFailMessage) objectMessage.getObject();
            mobmsgService.sendPayFailMsg(obj.getMobile(),obj.getMoney(), obj.getReason());
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
