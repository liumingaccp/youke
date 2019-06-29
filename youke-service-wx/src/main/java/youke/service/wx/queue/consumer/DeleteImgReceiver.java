package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.utils.HttpConnUtil;
import youke.facade.wx.provider.IWeixinFansService;
import youke.facade.wx.provider.IWeixinMaterialService;
import youke.facade.wx.util.Constants;

import javax.annotation.Resource;
import javax.jms.*;
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
@Service("deleteImgReceiver")
public class DeleteImgReceiver implements MessageListener {
    @Resource
    private IWeixinMaterialService weixinMaterialService;

    public void onMessage(Message message) {
        ObjectMessage tagMessage = (ObjectMessage)message;
        try {
            String params = (String) tagMessage.getObject();
            String[] split = params.split(",");
            List<String> list = Arrays.asList(split);
            list = new ArrayList<>(list);
            String appId = list.get(list.size() - 1);
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                if(next.equals(appId)){
                    iterator.remove();
                }
            }
            weixinMaterialService.deleteImgForWX(list, appId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

