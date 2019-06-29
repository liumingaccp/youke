package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.service.wx.biz.IFansBiz;
import youke.service.wx.biz.IKefuBiz;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.biz.IMenuBiz;
import javax.annotation.Resource;
import javax.jms.*;

@Service
public class SyncSubscrReceiver implements MessageListener {

    @Resource
    private IFansBiz fansBiz;
    @Resource
    private IKefuBiz kefuBiz;
    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private IMenuBiz menuBiz;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            String appId = (String)objectMessage.getObject();
            fansBiz.doSyncFans(appId);
            materialBiz.doSyncImage(appId);
            materialBiz.doSyncNews(appId);
            menuBiz.doSyncMenu(appId);
            //kefuBiz.doSyncKefu(appId);
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
