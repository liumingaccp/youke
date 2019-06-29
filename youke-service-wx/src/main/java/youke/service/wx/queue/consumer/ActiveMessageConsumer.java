package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.queue.message.ActiveMassMessage;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class ActiveMessageConsumer implements MessageListener {

    @Resource
    private IWeixinMessageService weixinMessageService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            ActiveMassMessage massMessage = (ActiveMassMessage) objectMessage.getObject();
            if (massMessage.getComeType() != ComeType.MEI_RI_QIAN_DAO && massMessage.getComeType() != ComeType.XING_YUN_CHOU_JIANG) {
                if (massMessage.getState() == 1) {
                    if (massMessage.getMoney() != 0 || massMessage.getIntegral() != 0) {
                        System.out.println("审核通过模板发送成功");
                        weixinMessageService.sendTempExamineSuccess(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getComeType(), massMessage.getTitle());
                    }
                }
                if (massMessage.getState() == 2) {
                    if (massMessage.getMoney() != 0 || massMessage.getIntegral() != 0) {
                        System.out.println("审核不通过模板发送成功");
                        weixinMessageService.sendTempExamineFail(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getComeType(), massMessage.getTitle(), massMessage.getFailReason());
                    }
                }
            }
//            if (massMessage.getComeType() == ComeType.MEI_RI_QIAN_DAO) {
//                System.out.println("签到提醒模板发送成功");
//                weixinMessageService.sendTempSignNotice(massMessage.getAppId(), massMessage.getOpenId(), massMessage.getTitle());
//            }
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
