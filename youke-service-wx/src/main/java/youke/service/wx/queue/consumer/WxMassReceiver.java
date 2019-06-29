package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.model.TMassTask;
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
public class WxMassReceiver implements MessageListener {

    @Resource
    private SchedulerService schedulerService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage =  (ObjectMessage)message;
            Integer taskId = 0;
            try {
                TMassTask task = (TMassTask) objectMessage.getObject();
                //确认接收，并成功处理了消息
                //开启定时器执行任务
                schedulerService.startSchedule(task);
            }catch (Exception e){
                e.printStackTrace();
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
