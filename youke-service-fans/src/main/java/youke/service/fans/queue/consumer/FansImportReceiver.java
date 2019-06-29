package youke.service.fans.queue.consumer;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import youke.facade.fans.provider.IShopFansService;
import youke.service.fans.queue.message.FansImportMessage;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 18:45
 */
@Service
public class FansImportReceiver implements MessageListener {

    @Resource
    private IShopFansService shopFansService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = ((ObjectMessage) message);
            FansImportMessage fansImportMessage = JSON.parseObject((String) objectMessage.getObject(), FansImportMessage.class);
            String url = fansImportMessage.getUrl();
            int shopId = fansImportMessage.getShopId();
            String youkeId = fansImportMessage.getYoukeId();
            int importId = fansImportMessage.getImportId();
            shopFansService.doImportFans(url, shopId, youkeId, importId);
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
