package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.queue.message.SysRechargeMessage;
import youke.facade.wx.provider.IWeixinMessageService;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class SysRechargeConsumer implements MessageListener {

    @Resource
    private IWeixinMessageService weixinMessageService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            SysRechargeMessage obj = (SysRechargeMessage) objectMessage.getObject();
            weixinMessageService.sendSysRecharge(obj.getOpenId(),obj.getTitle(),obj.getType(),obj.getPrice());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
