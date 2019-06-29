package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.service.wx.biz.IFansBiz;
import youke.service.wx.biz.IKefuBiz;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.biz.IMenuBiz;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class SyncSubscrFansConsumer implements MessageListener {

    @Resource
    private IFansBiz fansBiz;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            String appId = (String)objectMessage.getObject();
            System.out.println("收到同步微信粉丝:"+appId);
            fansBiz.doSyncFans(appId);
            System.out.println("同步完毕:"+appId);
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
