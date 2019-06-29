package youke.service.mass.queue.consumer;

import org.springframework.stereotype.Component;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMassSMSBiz;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
public class MassSendConsumer implements MessageListener {
    @Resource
    private IMassSMSBiz massSMSBiz;

    @Override
    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            MassSMSMessage queuemessage = (MassSMSMessage) objectMessage.getObject();
            massSMSBiz.doPostTemplate(queuemessage);
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
