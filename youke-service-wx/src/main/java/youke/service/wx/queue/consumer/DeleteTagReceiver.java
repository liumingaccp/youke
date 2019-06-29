package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.facade.wx.provider.IWeixinFansService;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/19
 * Time: 16:54
 */
@Service("deleteTagReceiver")
public class DeleteTagReceiver implements MessageListener {
    @Resource
    private IWeixinFansService weixinFansService;

    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            String params = (String)objectMessage.getObject();
            String[] split = params.split(",");
            if(split.length == 2){
                String tag = split[0];
                String appId= split[1];
                weixinFansService.deleteTag(Integer.parseInt(tag), appId);
            }
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
