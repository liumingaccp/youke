package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.queue.message.WxExpireMessage;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.facade.wx.queue.message.SuperMassMessage;
import youke.service.wx.provider.SchedulerService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/9
 * Time: 15:28
 */
@Service
public class SysSerExpireConsumer implements MessageListener {

    @Resource
    private IWeixinMessageService weixinMessageService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage =  (ObjectMessage)message;
            WxExpireMessage expireVo = (WxExpireMessage) objectMessage.getObject();
            //确认接收，并成功处理了消息
            weixinMessageService.sendSysServer(expireVo.getOpenId(),expireVo.getTitle(),"店铺绑定",expireVo.getDay(),expireVo.getEndtime());
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
