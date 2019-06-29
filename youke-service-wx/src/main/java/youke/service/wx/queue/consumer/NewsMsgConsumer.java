package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.NewsMsgMessage;
import youke.facade.wx.provider.IWeixinMessageService;
import youke.facade.wx.vo.message.Article;
import youke.service.wx.biz.IKefuMessageBiz;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息统一发送
 */
@Service
public class NewsMsgConsumer implements MessageListener {

    @Resource
    private IKefuMessageBiz kefuMessageBiz;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            NewsMsgMessage newsMsg = (NewsMsgMessage) objectMessage.getObject();
            Article article = new Article();
            article.setUrl(newsMsg.getUrl());
            article.setPicUrl(newsMsg.getPicUrl());
            article.setTitle(newsMsg.getTitle());
            article.setDescription(newsMsg.getDescription());
            List<Article> articleList = new ArrayList<>();
            articleList.add(article);
            try {
                kefuMessageBiz.sendNews(newsMsg.getAppId(), newsMsg.getOpenId(), articleList);
            }catch (Exception e){

            }
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
