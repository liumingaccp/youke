package youke.web.spread.service.jms.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import youke.web.spread.common.base.Base;
import youke.web.spread.service.jms.config.MQConstant;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;


@Service
public class MQConsumer extends Base {


    @JmsListener(destination = MQConstant.default_queue , containerFactory="jmsListenerContainerQueue")
    public void receiveQueue(ObjectMessage message) {
        try {
            System.out.println("queue接收:"+message.getObject().toString());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    @JmsListener(destination = MQConstant.default_topic , containerFactory="jmsListenerContainerTopic")
    public void receiveTopic(ObjectMessage message) {
        try {
            System.out.println("topic接收:"+message.getObject().toString());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
