package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.service.wx.provider.WxFansTagUpOrDnService;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/12
 * Time: 15:05
 */
@Service("downloadTagsReceiver")
public class DownloadTagsReceiver implements MessageListener {

    @Resource
    private WxFansTagUpOrDnService wxFansTagUpOrDnService;

    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            String val = (String)objectMessage.getObject();
            wxFansTagUpOrDnService.doDownload(val);
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
