package youke.service.mass.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.queue.message.InitCodeMessage;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMobmsgBiz;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * 初始化公众号短信发送模板
 */
@Service
public class InitCodeTempConsumer implements MessageListener {
    @Resource
    private IMobmsgBiz mobmsgBiz;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            InitCodeMessage queuemessage = (InitCodeMessage) objectMessage.getObject();
            //开启定时器执行任务
            mobmsgBiz.createTemplate(queuemessage.getAppId(),queuemessage.getContent(),queuemessage.getLabel());
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
