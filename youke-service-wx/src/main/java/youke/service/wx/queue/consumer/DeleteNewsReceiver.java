package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.facade.wx.provider.IWeixinMaterialService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/19
 * Time: 16:54
 */
@Service("deleteNewsReceiver")
public class DeleteNewsReceiver implements MessageListener {
    @Resource
    private IWeixinMaterialService weixinMaterialService;

    public void onMessage(Message message) {
        ObjectMessage tagMessage = (ObjectMessage)message;
        try {
            String params = (String) tagMessage.getObject();
            String[] split = params.split(",");
            String mediaId = split[0];
            String appId = split[1];
            weixinMaterialService.deleteNewsForWX(mediaId, appId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

