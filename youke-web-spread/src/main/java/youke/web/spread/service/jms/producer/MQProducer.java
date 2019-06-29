package youke.web.spread.service.jms.producer;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import youke.web.spread.common.base.Base;

import javax.annotation.Resource;
import java.io.Serializable;

@Service
public class MQProducer extends Base {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;//发布订阅

    public void send(String queueName, final Serializable obj) {
        jmsMessagingTemplate.convertAndSend(queueName, obj);
        System.out.println("发送:"+queueName);
    }
}
