
package youke.core.mq.consumer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Service;

/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Service
public class QueueReceiver1 implements MessageListener {

	public void onMessage(Message message) {
		try {
			//获取到接收的数据
			String text = ((TextMessage)message).getText();
			System.out.println(text);
			//确认接收，并成功处理了消息
			message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
