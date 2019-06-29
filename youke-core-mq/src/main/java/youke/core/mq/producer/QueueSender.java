package youke.core.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/**
 * 
 * @author liang
 * @description  队列消息生产者，发送消息到队列
 * 
 */
@Service("queueSender")
public class QueueSender {
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param obj 消息内容
	 */
	public void send(String queueName,final Serializable obj){
			jmsTemplate.send(queueName, new MessageCreator(){
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(obj);
			}
		});
	}
	
}
