package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.queue.message.SysOpenMessage;
import youke.common.queue.message.TempMassMessage;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.service.wx.biz.IFansBiz;

import javax.annotation.Resource;
import javax.jms.*;

@Service
public class SysOpenAccountConsumer implements MessageListener {

    @Resource
    private IWeixinMessageService weixinMessageService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            SysOpenMessage obj = (SysOpenMessage) objectMessage.getObject();
            weixinMessageService.sendSysOpenAccount(obj.getOpenId(),obj.getVip(),obj.getPrice(),obj.getTitle(),obj.getEndTime());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}