package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.dao.ISubscrFansDao;
import youke.service.wx.provider.WxFansTagUpOrDnService;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/12
 * Time: 15:05
 */
@Service("uploadTagsReceiver")
public class UploadTagsReceiver implements MessageListener {

    @Resource
    private WxFansTagUpOrDnService wxFansTagUpOrDnService;

    public void onMessage(Message message) {
        ObjectMessage objectMessage =  (ObjectMessage)message;
        try {
            wxFansTagUpOrDnService.doUpload((String)objectMessage.getObject());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
