package youke.service.fans.queue.consumer;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import youke.common.dao.IShopFansDao;
import youke.common.queue.message.WxperFansMessage;
import youke.common.utils.StringUtil;
import youke.facade.fans.provider.IShopFansService;
import youke.service.fans.biz.IFansShopBiz;
import youke.service.fans.queue.message.FansImportMessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *  微信个人号粉丝绑定购物账户
 */
@Service
public class WxperFansReceiver implements MessageListener {

    @Resource
    private IFansShopBiz fansShopBiz;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = ((ObjectMessage) message);
            WxperFansMessage wxmessage = (WxperFansMessage) objectMessage.getObject();
            fansShopBiz.saveFriendMobile(wxmessage.getNickName(), wxmessage.getMobile(), wxmessage.getWeixinId());
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
